package soulchasm.main.Misc.Others;

import necesse.engine.Screen;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.packet.PacketPlayerStopAttack;
import necesse.engine.network.packet.PacketShowAttack;
import necesse.engine.network.packet.PacketShowAttackOnlyItem;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.MousePositionAttackHandler;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import soulchasm.main.Items.Tools.soulmetalbow;

import java.awt.*;
import java.awt.geom.Point2D;
public class soulmetalbowattackhandler extends MousePositionAttackHandler {
    public int chargeTime;
    public boolean fullyCharged;
    public soulmetalbow toolItem;
    public long startTime;
    public InventoryItem item;
    public int seed;
    public Color particleColors;
    public boolean endedByInteract;

    public soulmetalbowattackhandler(PlayerMob player, PlayerInventorySlot slot, InventoryItem item, soulmetalbow toolItem, int chargeTime, Color particleColors, int seed) {
        super(player, slot, 20);
        this.item = item;
        this.toolItem = toolItem;
        this.chargeTime = chargeTime;
        this.particleColors = particleColors;
        this.seed = seed;
        this.startTime = player.getWorldEntity().getLocalTime();
    }
    public long getTimeSinceStart() {
        return this.player.getWorldEntity().getLocalTime() - this.startTime;
    }

    public float getChargePercent() {
        return (float)this.getTimeSinceStart() / (float)this.chargeTime;
    }

    public void onUpdate() {
        super.onUpdate();
        Point2D.Float dir = GameMath.normalize((float)this.lastX - this.player.x, (float)this.lastY - this.player.y);
        float chargePercent = this.getChargePercent();
        InventoryItem showItem = this.item.copy();
        showItem.getGndData().setFloat("chargePercent", chargePercent);
        Packet attackContent = new Packet();
        this.player.showAttack(showItem, this.lastX, this.lastY, this.seed, attackContent);
        if (this.player.isServer()) {
            ServerClient client = this.player.getServerClient();
            this.player.getLevel().getServer().network.sendToClientsAtExcept(new PacketShowAttack(this.player, showItem, this.lastX, this.lastY, this.seed, attackContent), client, client);
        }

        if (chargePercent >= 1.0F) {
            if (this.player.isClient()) {
                this.player.getLevel().entityManager.addParticle(this.player.x + dir.x * 16.0F + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), this.player.y + 4.0F + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).movesConstant(this.player.dx / 10.0F, this.player.dy / 10.0F).color(this.particleColors).height(20.0F - dir.y * 16.0F);
            }

            if (!this.fullyCharged) {
                this.fullyCharged = true;
                if (this.player.isClient()) {
                    int particles = 35;
                    float anglePerParticle = 360.0F / (float)particles;
                    ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);

                    for(int i = 0; i < particles; ++i) {
                        int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
                        float dx = (float)Math.sin(Math.toRadians((double)angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50);
                        float dy = (float)Math.cos(Math.toRadians((double)angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50) * 0.8F;
                        this.player.getLevel().entityManager.addParticle(this.player, typeSwitcher.next()).movesFriction(dx, dy, 0.8F).color(this.particleColors).heightMoves(0.0F, 30.0F).lifeTime(500);
                    }

                    Screen.playSound(GameResources.magicbolt4, SoundEffect.effect(this.player).volume(0.1F).pitch(2.5F));
                }
            }
        }

    }

    public void onMouseInteracted(int levelX, int levelY) {
        this.endedByInteract = true;
        this.player.endAttackHandler(false);
    }

    public void onControllerInteracted(float aimX, float aimY) {
        this.endedByInteract = true;
        this.player.endAttackHandler(false);
    }

    public void onEndAttack(boolean bySelf) {
        float chargePercent = this.getChargePercent();
        if (!this.endedByInteract && (this.getTimeSinceStart() >= 200L || chargePercent >= 0.5F)) {
            this.player.constantAttack = true;
            GameMath.normalize((float)this.lastX - this.player.x, (float)this.lastY - this.player.y);
            InventoryItem attackItem = this.item.copy();
            attackItem.getGndData().setBoolean("shouldFire", true);
            attackItem.getGndData().setFloat("chargePercent", chargePercent);
            ActiveBuff buff =  player.buffManager.getBuff("soulbowbuff");
            buff.getGndData().setInt("stacks", buff.getGndData().getInt("stacks")+1);
            Packet attackContent = new Packet();
            PacketWriter writer = new PacketWriter(attackContent);
            this.toolItem.setupAttackContentPacket(writer, this.player.getLevel(), this.lastX, this.lastY, this.player, attackItem);
            attackItem.item.showAttack(this.player.getLevel(), this.lastX, this.lastY, this.player, this.player.getCurrentAttackHeight(), attackItem, this.seed, new PacketReader(attackContent));
            this.toolItem.superOnAttack(this.player.getLevel(), this.lastX, this.lastY, this.player, this.player.getCurrentAttackHeight(), attackItem, this.slot, 0, this.seed, new PacketReader(attackContent));
            for (ActiveBuff b : this.player.buffManager.getArrayBuffs()) {
                b.onItemAttacked(this.lastX, this.lastY, this.player, this.player.getCurrentAttackHeight(), attackItem, this.slot, 0);
            }
            if (this.player.isServer()) {
                ServerClient client = this.player.getServerClient();
                Server server = this.player.getLevel().getServer();
                server.network.sendToClientsAtExcept(new PacketShowAttackOnlyItem(this.player, attackItem, this.lastX, this.lastY, this.seed, attackContent), client, client);
            }
        }

        this.player.stopAttack();
        if (this.player.isServer()) {
            ServerClient client = this.player.getServerClient();
            this.player.getLevel().getServer().network.sendToClientsAtExcept(new PacketPlayerStopAttack(client.slot), client, client);
        }

    }
}

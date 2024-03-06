package soulchasm.main.Misc.BiomeMisc;

import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.registries.MusicRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.MobSpawnTable;

public class soulcavernbiome extends Biome {
    public static MobSpawnTable critters;
    public static MobSpawnTable mobs;

    public soulcavernbiome() {
    }
    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        return new MusicList(MusicRegistry.VoidsEmbrace);
    }
    public MobSpawnTable getCritterSpawnTable(Level level) {
        return critters;
    }
    @Override
    public MobSpawnTable getMobSpawnTable(Level level) {
        return mobs;
    }
    public LootTable getExtraMobDrops(Mob mob) {
        if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
            if (mob.getLevel().getIslandDimension() == -2) {
                return new LootTable(new ChanceLootItem(0.05F, "soulsigil"), super.getExtraMobDrops(mob));
            }
        }
        return super.getExtraMobDrops(mob);
    }
    static {
        critters = (new MobSpawnTable()).add(25, "soulcavecaveling").add(100, "wisp");
        mobs = (new MobSpawnTable()).add(80, "lostsoul").add(80, "soulmage").add(20, "soulpillar").add(10, "possesedstatue");
    }
}

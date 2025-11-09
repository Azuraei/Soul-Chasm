package soulchasm;

import necesse.engine.GameLoadingScreen;
import necesse.engine.journal.JournalEntry;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.*;
import necesse.engine.sound.gameSound.GameSound;
import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorItem;
import necesse.inventory.item.armorItem.HelmetArmorItem;
import necesse.inventory.item.matItem.EssenceMatItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.StonePlaceableItem;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;
import necesse.inventory.item.placeableItem.tileItem.GrassSeedItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.*;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.*;
import necesse.level.gameObject.container.*;
import necesse.level.gameObject.furniture.*;
import necesse.level.gameObject.furniture.doubleBed.DoubleBedBaseObject;
import necesse.level.gameTile.PathTiledTile;
import necesse.level.gameTile.SimpleFloorTile;
import necesse.level.gameTile.SimpleTiledFloorTile;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.plains.PlainsBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.WallSet;
import soulchasm.main.buffs.IdolShieldBuff;
import soulchasm.main.buffs.SoulIdolSummonBuff;
import soulchasm.main.buffs.SoulBleedStackBuff;
import soulchasm.main.buffs.SoulFireBuff;
import soulchasm.main.buffs.armorbuffs.*;
import soulchasm.main.buffs.toolbuffs.BookofSoulBuff;
import soulchasm.main.buffs.toolbuffs.SoulofSoulsOverchargeBuff;
import soulchasm.main.buffs.toolbuffs.SoulBowBuff;
import soulchasm.main.buffs.toolbuffs.SoulBowCooldownBuff;
import soulchasm.main.buffs.toolbuffs.SoulAbsorbShieldBuff;
import soulchasm.main.buffs.toolbuffs.SoulDeathMarkStackBuff;
import soulchasm.main.buffs.toolbuffs.SoulScytheBuff;
import soulchasm.main.buffs.trinketbuffs.PhantomDashersActiveBuff;
import soulchasm.main.buffs.trinketbuffs.PhantomDashersBuff;
import soulchasm.main.buffs.trinketbuffs.PickaxeHeadBuff;
import soulchasm.main.buffs.trinketbuffs.PickaxeHeadStackBuff;
import soulchasm.main.buffs.trinketbuffs.sealbuffs.*;
import soulchasm.main.buffs.trinketbuffs.PhantomFeatherBuff;
import soulchasm.main.buffs.trinketbuffs.SoulStealerBuff;
import soulchasm.main.items.armor.*;
import soulchasm.main.items.CarKeys;
import soulchasm.main.items.tools.*;
import soulchasm.main.items.trinkets.sealtrinkets.*;
import soulchasm.main.items.trinkets.PhantomDashersTrinket;
import soulchasm.main.items.trinkets.PhantomFeatherTrinket;
import soulchasm.main.items.trinkets.PickaxeHeadTrinket;
import soulchasm.main.items.trinkets.SoulStealerTrinket;
import soulchasm.main.misc.carcolorsui.CarColorContainer;
import soulchasm.main.misc.carcolorsui.CarColorContainerForm;
import soulchasm.main.misc.levelevents.HomingProjectilesEvent;
import soulchasm.main.misc.levelevents.grounderuptionevents.DragonExplosionEvent;
import soulchasm.main.misc.levelevents.grounderuptionevents.DragonGroundEruptionEvent;
import soulchasm.main.misc.levelevents.spinningspawnerevents.SpinSpawnEvent;
import soulchasm.main.misc.levelevents.spinningspawnerevents.SpinSpawnVisualEvent;
import soulchasm.main.misc.levelevents.IdolShieldVisualEvent;
import soulchasm.main.misc.levelevents.MeleeGhostSpawnEvent;
import soulchasm.main.incursion.haunted.HauntedIncursionModifier;
import soulchasm.main.incursion.haunted.HauntedModifierLevelEvent;
import soulchasm.main.incursion.SoulChasmBiome;
import soulchasm.main.incursion.SoulChasmIncursionBiome;
import soulchasm.main.incursion.SoulChasmIncursionLevel;
import soulchasm.main.mobs.boss.ChasmDragon;
import soulchasm.main.objects.*;
import soulchasm.main.objects.jars.BigJarObject;
import soulchasm.main.objects.jars.FireFlyJarObject;
import soulchasm.main.objects.jars.WispJarObject;
import soulchasm.main.mobs.hostile.*;
import soulchasm.main.mobs.boss.ChasmDragonBody;
import soulchasm.main.mobs.friendly.Firefly;
import soulchasm.main.mobs.friendly.ChasmCaveling;
import soulchasm.main.mobs.friendly.AltarEffectEntity;
import soulchasm.main.mobs.friendly.Wisp;
import soulchasm.main.mobs.summons.CarMob;
import soulchasm.main.mobs.summons.SealGhostSummon;
import soulchasm.main.mobs.summons.SoulStatueSummon;
import soulchasm.main.plushies.*;
import soulchasm.main.plushies.characters.*;
import soulchasm.main.projectiles.bossprojectiles.SoulFlamethrowerProjectile;
import soulchasm.main.projectiles.bossprojectiles.SpinnerSpikeProjectile;
import soulchasm.main.projectiles.sealprojectiles.*;
import soulchasm.main.projectiles.weaponprojectiles.*;
import soulchasm.main.projectiles.SoulDiscProjectile;
import soulchasm.main.projectiles.SoulHomingProjectile;
import soulchasm.main.tiles.*;

import java.awt.*;
import java.util.ArrayList;

import static necesse.engine.registries.JournalRegistry.registerJournalEntry;
import static necesse.engine.registries.ObjectRegistry.getObject;

@ModEntry
public class SoulChasm {
    public static int CAR_COLOR_CONTAINER;
    public static final ArrayList<Color> carColors = new ArrayList<>();
    public static Color chasmStoneMapColor = new Color(1, 30, 42);
    public static Color chasmStoneLightMapColor = new Color(2, 59, 80);
    public static Color chasmMagmaMapColor = new Color(8, 197, 246);
    public static Color chasmCrystalMapColor = new Color(12, 106, 154);
    public static Color chasmWoodMapColor = new Color(82, 137, 157);
    public static Color chasmWoodFurnitureMapColor = new Color(117, 165, 183);
    public static Color chasmGrassMapColor = new Color(93, 132, 143);
    public static Color chasmTorchMapColor = new Color(128, 235, 255);
    public static Color asphaltMapColor = new Color(23, 23, 23);
    public static Color lunarTearMapColor = new Color(93, 132, 143);
    public static HumanTexture chasmCaveling;
    public static ChestRoomSet chasmChestRoomSet;
    public static LootTable chasmShrineLootTable;
    public static GameTexture[] carMask;
    public static GameTexture eruptionShadow;
    public static GameTextureSection spinSpawnVisual;
    public static GameTextureSection particleFlamethrowerSection;
    public static GameTextureSection particleFireflySection;
    public static GameTextureSection particleWispSection;
    public static GameTextureSection particleBookSection;
    public static GameTextureSection particlePhantomBodySection;
    public static GameTextureSection particleGhostSpawnSection;
    public static GameTextureSection particleMeleeGhostParticleSection;
    public static GameTextureSection particleMonumentRingSection;
    public static GameSound plushieSqueak;
    public static GameTexture carColorContainerIcon;

    public void init() {
        GameLoadingScreen.drawLoadingString("Loading Soul Chasm");
        // === Tiles ===
        TileRegistry.registerTile("chasmgrass", new ChasmGrassTile(), 0.0F, true);
        TileRegistry.registerTile("chasmrocktile", new ChasmRockTile(), 0.0F, true);
        TileRegistry.registerTile("chasmfloortile", new SimpleFloorTile("chasmfloortile", chasmStoneLightMapColor), 0.0F, true);
        TileRegistry.registerTile("chasmbrickfloortile", new SimpleFloorTile("chasmbrickfloortile", chasmStoneLightMapColor), 2.0F, true);
        TileRegistry.registerTile("chasmtiledfloortile", new SimpleTiledFloorTile("chasmtiledfloortile", chasmStoneLightMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodfloor", new SimpleFloorTile("soulwoodfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodtiledfloor", new SimpleTiledFloorTile("soulwoodtiledfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodpath", new PathTiledTile("soulwoodpath", SoulChasm.chasmWoodMapColor), 5.0F, true);
        TileRegistry.registerTile("moltenspirits", new MoltenSpiritsTile(), 10.0F, true);
        TileRegistry.registerTile("chasmcracktile", new ChasmCrackTile(), 0.0F, false);
        TileRegistry.registerTile("asphalttile", new AsphaltTile(), -1.0F, true);
        // === Objects ===
        RockObject chasmrock;
        ObjectRegistry.registerObject("chasmrock", chasmrock = new RockObject("chasmrock", chasmStoneMapColor, "chasmrockitem"), -1.0F, true);
        chasmrock.toolTier = 4;
        WallObject.registerWallObjects("chasmbrick", "chasmbrickwall", chasmrock.toolTier, chasmStoneMapColor, 2.0F, 6.0F);
        WallObject.registerWallObjects("soulwood", "soulwoodwall", 0, chasmWoodMapColor, ToolType.ALL, 2.0F, 6.0F);
        SingleRockObject.registerSurfaceRock(chasmrock, "chasmrocks", chasmStoneMapColor, -1.0F, true);
        ObjectRegistry.registerObject("chasmrocksmall", new SingleRockSmall(chasmrock, "chasmrocksmall", chasmStoneMapColor), -1.0F, true);
        ObjectRegistry.registerObject("crystalizedsoul", new RockOreObject(chasmrock, "oremask", "crystalizedsoulore", chasmCrystalMapColor, "crystalizedsouloreitem", 1, 2, 1), -1.0F, true);
        ObjectRegistry.registerObject("alchemyshardchasmrock", new RockOreObject(chasmrock, "oremask", "alchemyshardore", new Color(102, 0, 61), "alchemyshard", 1, 1, 1, false), -1.0F, true);
        ObjectRegistry.registerObject("upgradeshardchasmrock", new RockOreObject(chasmrock, "oremask", "upgradeshardore", new Color(0, 27, 107), "upgradeshard", 1, 1, 1, false), -1.0F, true);
        ObjectRegistry.registerObject("chasmdebree", new ChasmDebreeObject(chasmrock, "chasmdebree", chasmStoneMapColor), 0.0F, false);
        ObjectRegistry.registerObject("chasmflametrap", new WallFlameTrapObject((WallObject)getObject("chasmbrickwall")), 50.0F, true);
        ObjectRegistry.registerObject("chasmpressureplate", new MaskedPressurePlateObject("pressureplatemask", "chasmfloortile", chasmStoneMapColor), 15.0F, true);
        ObjectRegistry.registerObject("soultree", new TreeObject("soultree", "soulwoodlogitem", "soultreesapling", chasmWoodMapColor, 60,80,100, "soultreeleaves"), 0.0F, false);
        ObjectRegistry.registerObject("soultreesapling", new TreeSaplingObject("soultreesapling", "soultree", 1800, 2700, false, "chasmgrass"), 5.0F, true);
        ObjectRegistry.registerObject("lunartear", new LunarTearObject(), 5.0F, true);
        FlowerPatchObject lunartearspath = new FlowerPatchObject("lunartearspath", lunarTearMapColor);{lunartearspath.lightLevel = 30;lunartearspath.lightHue = 240.0F;lunartearspath.lightSat = 0.05F;}
        ObjectRegistry.registerObject("lunartearspath", lunartearspath, 5.0F, true);
        TorchObject soulTorch = new TorchObject("soultorch", chasmTorchMapColor, 240F, 0.3F);{soulTorch.flameHue = 190;}
        ObjectRegistry.registerObject("soultorch", soulTorch, 1, true);
        TorchObject soulLantern = new TorchObject("soullantern", chasmTorchMapColor, 240.0F, 0.3F);{soulLantern.flameHue = 190;}
        ObjectRegistry.registerObject("soullantern", soulLantern, -1.0F, true);
        ObjectRegistry.registerObject("chasmgrassobject", new GrassObject("chasmgrassobject", 4), 0.0F, false);
        ObjectRegistry.registerObject("bigsoulcrystal", new BigSoulCrystalObject(), 0.0F, false);
        ObjectRegistry.registerObject("bigjarobject", new BigJarObject(), -1.0F, true);
        ObjectRegistry.registerObject("wispjarobject", new WispJarObject(), 0.0F, false);
        ObjectRegistry.registerObject("fireflyjarobject", new FireFlyJarObject(), 0.0F, false);
        ObjectRegistry.registerObject("chasmmonumentobject", new ChasmMonumentObject(), 50.0F, true);
        ObjectRegistry.registerObject("oldbarrel", new InventoryObject("oldbarrel", 20, new Rectangle(8, 4, 16, 16), ToolType.PICKAXE, chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("chasmcrates", new RandomCrateObject("chasmcrates"), 0.0F, false);
        ObjectRegistry.registerObject("chasmstatueobject", new ChasmStatueObject(), 50.0F, true);
        ObjectRegistry.registerObject("chasmmagestatueobject", new ChasmMageStatueObject(), -1.0F, true);
        ObjectRegistry.registerObject("altarspikeobject", new AltarSpikeObject(), 0.0F, false);
        TikiTorchObject soulTikiTorch = new TikiTorchObject();{soulTikiTorch.flameHue = 190; soulTikiTorch.lightLevel = 130; soulTikiTorch.lightHue = 240F; soulTikiTorch.lightSat = 0.3F; soulTikiTorch.mapColor = chasmTorchMapColor;}
        ObjectRegistry.registerObject("soultikitorchobject", soulTikiTorch, -1.0F, true);
        ObjectRegistry.registerObject("chasmchest", new StorageBoxInventoryObject("chasmchest",40, chasmStoneMapColor), 10.0F, true);
        // = Furniture =
        registerFurnitureSet("soulwood", chasmWoodFurnitureMapColor, 60.0F, 0.3F);
        // === Buffs/Debuffs ===
        BuffRegistry.registerBuff("soulstealerbuff", new SoulStealerBuff());
        BuffRegistry.registerBuff("phantomfeatherbuff", new PhantomFeatherBuff());
        BuffRegistry.registerBuff("soulfirebuff", new SoulFireBuff());
        BuffRegistry.registerBuff("soulabsorbshieldbuff", new SoulAbsorbShieldBuff());
        BuffRegistry.registerBuff("soulsealfollowerbuff", new SoulSealFollowerBuff());
        BuffRegistry.registerBuff("soulbleedstackbuff", new SoulBleedStackBuff());
        BuffRegistry.registerBuff("soulscythebuff", new SoulScytheBuff());
        BuffRegistry.registerBuff("souldeathmarkstackbuff", new SoulDeathMarkStackBuff());
        BuffRegistry.registerBuff("pickaxeheadbuff", new PickaxeHeadBuff());
        BuffRegistry.registerBuff("pickaxeheadstackbuff", new PickaxeHeadStackBuff());
        BuffRegistry.registerBuff("phantomdashersactivebuff", new PhantomDashersActiveBuff());
        BuffRegistry.registerBuff("phantomdashersbuff", new PhantomDashersBuff());
        BuffRegistry.registerBuff("bookofsoulbuff", new BookofSoulBuff());
        BuffRegistry.registerBuff("soulofsoulsoverchargebuff", new SoulofSoulsOverchargeBuff());
        BuffRegistry.registerBuff("soulbowbuff", new SoulBowBuff());
        BuffRegistry.registerBuff("soulbowcooldownbuff", new SoulBowCooldownBuff());
        BuffRegistry.registerBuff("idolshieldbuff", new IdolShieldBuff());
        BuffRegistry.registerBuff("soulstatuebuff", new SoulIdolSummonBuff());
        // = Armor =
        BuffRegistry.registerBuff("soularmorhelmetsetbonus", new SoulArmorHelmetSetBonus());
        BuffRegistry.registerBuff("soularmorhoodsetbonus", new SoulArmorHoodSetBonus());
        BuffRegistry.registerBuff("soularmorcrownsetbonus", new SoulArmorCrownSetBonus());
        BuffRegistry.registerBuff("soularmorhatsetbonus", new SoulArmorHatSetBonus());
        BuffRegistry.registerBuff("soularmorcooldown", new SoulArmorCooldown());
        BuffRegistry.registerBuff("souldischargebuff", new SoulDischargeBuff());
        BuffRegistry.registerBuff("souldischargesicknessdebuff", new SoulDischargeSicknessDebuff());
        // = Seal =
        BuffRegistry.registerBuff("meleesoulsealbuff", new MeleeSoulSealBuff());
        BuffRegistry.registerBuff("summonsoulsealbuff", new SummonSoulSealBuff());
        BuffRegistry.registerBuff("magicsoulsealbuff", new MagicSoulSealBuff());
        BuffRegistry.registerBuff("rangesoulsealbuff", new RangeSoulSealBuff());
        BuffRegistry.registerBuff("balancedsealbuff", new BalancedSealBuff());
        // === Items ===
        ItemRegistry.registerItem("soulessence", new EssenceMatItem(250, Item.Rarity.EPIC, 2), 30.0F, true);
        ItemRegistry.registerItem("soulcore", new MatItem(250, Item.Rarity.UNCOMMON), 15, true);
        ItemRegistry.registerItem("chasmrockitem", new StonePlaceableItem(5000), 0.1F, true);
        ItemRegistry.registerItem("crystalizedsouloreitem", new MatItem(500, Item.Rarity.UNCOMMON), 10.0F, true);
        ItemRegistry.registerItem("soulmetalbar", new MatItem(250, Item.Rarity.UNCOMMON), -1.0F, true);
        ItemRegistry.registerItem("soulwoodlogitem", (new MatItem(500, "anylog")).setItemCategory("materials", "logs"), 2.0F, true);
        ItemRegistry.registerItem("chasmgrassseeditem", new GrassSeedItem("chasmgrass"), 2.0F, true);
        ItemRegistry.registerItem("wispitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 1.0F, true);
        ItemRegistry.registerItem("fireflyitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 1.0F, true);
        ItemRegistry.registerItem("fireflyjar", new ObjectItem(getObject("fireflyjarobject")), -1.0F, true);
        ItemRegistry.registerItem("wispjar", new ObjectItem(getObject("wispjarobject")), -1.0F, true);
        ItemRegistry.registerItem("carkeys", new CarKeys(), 2000, true);
        // = Trinkets =
        ItemRegistry.registerItem("phantomfeathertrinket", new PhantomFeatherTrinket(), 500, true);
        ItemRegistry.registerItem("soulstealertrinket", new SoulStealerTrinket(), 500, true);
        ItemRegistry.registerItem("pickaxeheadtrinket", new PickaxeHeadTrinket(), 500, true);
        ItemRegistry.registerItem("soulabsorbshield", new SoulbreakerShield(), 750, true);
        ItemRegistry.registerItem("phantomdasherstrinket", new PhantomDashersTrinket(), 750, true);
        // = Weapons =
        ItemRegistry.registerItem("soulscythe", new SoulReaper(), 2000, true);
        ItemRegistry.registerItem("soulmetalbow", new PhantasmBow(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalsword", new SoulfurySword(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalspear", new CursedTrident(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalrevolver", new SoultakerGun(), -1.0F, true);
        ItemRegistry.registerItem("bookofsouls", new BookofSouls(), -1.0F, true);
        ItemRegistry.registerItem("idolsummon", new SoulIdolSummon(), -1.0F, true);
        // = Armor =
        ItemRegistry.registerItem("soularmorboots", new SoulsteelBoots(), -1.0F, true);
        ItemRegistry.registerItem("soularmorchestplate", new SoulsteelChestplate(), -1.0F, true);
        ItemRegistry.registerItem("soularmorcrown", new SoulsteelCrown(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhelmet", new SoulsteelHelmet(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhood", new SoulsteelHood(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhat", new SoulsteelHat(),-1.0F, true);
        // = Seal =
        ItemRegistry.registerItem("meleesoulsealtrinket", new MeleeSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("summonsoulsealtrinket", new SummonSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("magicsoulsealtrinket", new MagicSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("rangesoulsealtrinket", new RangeSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("soulsealtrinket", new SoulSealTrinket(), -1.0F, true);
        ItemRegistry.registerItem("balancedsealtrinket", new BalancedSealTrinket(), -1.0F, true);
        // = Vanity =
        ItemRegistry.registerItem("lunartearflowerhead", new HelmetArmorItem(0, null, 0, Item.Rarity.RARE, "lunartearflowerhead", null).hairDrawMode(ArmorItem.HairDrawMode.OVER_HAIR), 50.0F, true);
        ItemRegistry.registerItem("tobeblindfold", new HelmetArmorItem(0, null, 0, Item.Rarity.EPIC, "tobeblindfold", null).hairDrawMode(ArmorItem.HairDrawMode.UNDER_HAIR), 250.0F, true);
        // === Mobs ===
        MobRegistry.registerMob("chasmdragon", ChasmDragon.class, true, true);
        MobRegistry.registerMob("chasmdragonbody", ChasmDragonBody.class, false, true);
        // = Hostile =
        MobRegistry.registerMob("lostsoul", LostSoul.class, true);
        MobRegistry.registerMob("chasmwarriorstatue", ChasmWarriorStatue.class, true);
        MobRegistry.registerMob("chasmmagestatue", ChasmMageStatue.class, true);
        MobRegistry.registerMob("chasmmage", ChasmMage.class, true);
        MobRegistry.registerMob("lessersoul", LesserSoul.class, false);
        // = Friendly =
        MobRegistry.registerMob("wisp", Wisp.class, false);
        MobRegistry.registerMob("firefly", Firefly.class, false);
        MobRegistry.registerMob("chasmcaveling", ChasmCaveling.class, true);
        MobRegistry.registerMob("carmob", CarMob.class, false);
        MobRegistry.registerMob("sealghostsummon", SealGhostSummon.class, false);
        MobRegistry.registerMob("soulstatuesummon", SoulStatueSummon.class, false);
        MobRegistry.registerMob("altareffectentity", AltarEffectEntity.class, false);
        // === Projectiles ===
        ProjectileRegistry.registerProjectile("soulwaveprojectile", SoulWaveProjectile.class, "soulwaveprojectile", null);
        ProjectileRegistry.registerProjectile("soularrowprojectile", SoulArrowProjectile.class, "soularrowprojectile", null);
        ProjectileRegistry.registerProjectile("soulhomingprojectile", SoulHomingProjectile.class, "soulhomingprojectile",null);
        ProjectileRegistry.registerProjectile("soulmissileprojectile", SoulMissileProjectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulflamethrower", SoulFlamethrowerProjectile.class, null,null);
        ProjectileRegistry.registerProjectile("soulspearprojectile", SoulSpearProjectile.class, "soulspearprojectile",null);
        ProjectileRegistry.registerProjectile("spiritswordprojectile", SouldSwordProjectile.class, "spiritswordprojectile",null);
        ProjectileRegistry.registerProjectile("soulrevolverprojectile", SoulRevolverProjectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulbigbulletprojectile", SoulBigBulletProjectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulboomerangprojectile", SoulBoomerangProjectile.class, "soulboomerangprojectile", null);
        ProjectileRegistry.registerProjectile("soulpointywaveprojectile", SoulPointyWaveProjectile.class, "soulpointywaveprojectile", null);
        ProjectileRegistry.registerProjectile("spinspawnspikeprojectile", SpinnerSpikeProjectile.class, "spinspawnspikeprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulsmainprojectile", BookofSoulsProjectile.class, "bookofsoulsmainprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulssmallprojectile", BookofSoulsSmallProjectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulscytheprojectile", SoulScytheProjectile.class, "soulscytheprojectile",null);
        ProjectileRegistry.registerProjectile("soulscythesmallprojectile", SoulScytheSmallProjectile.class, "soulscythesmallprojectile",null);
        ProjectileRegistry.registerProjectile("souldiscprojectile", SoulDiscProjectile.class, "soulboomerangprojectile", null);
        // === Events ===
        LevelEventRegistry.registerEvent("dragongrounderuptionevent", DragonGroundEruptionEvent.class);
        LevelEventRegistry.registerEvent("dragonexplosionevent", DragonExplosionEvent.class);
        LevelEventRegistry.registerEvent("spinspawnevent", SpinSpawnEvent.class);
        LevelEventRegistry.registerEvent("spinspawnvisualevent", SpinSpawnVisualEvent.class);
        LevelEventRegistry.registerEvent("idolshieldvisualevent", IdolShieldVisualEvent.class);
        LevelEventRegistry.registerEvent("meleeghostspawnevent", MeleeGhostSpawnEvent.class);
        LevelEventRegistry.registerEvent("hauntedmodifierlevelevent", HauntedModifierLevelEvent.class);
        LevelEventRegistry.registerEvent("homingprojectileslevelevent", HomingProjectilesEvent.class);
        // === Incursion ===
        BiomeRegistry.registerBiome("soulchasm", new SoulChasmBiome(), false);
        IncursionBiomeRegistry.registerBiome("soulchasm", new SoulChasmIncursionBiome(), 2);
        LevelRegistry.registerLevel("soulchasmincursion", SoulChasmIncursionLevel.class);
        UniqueIncursionModifierRegistry.registerUniqueModifier("haunted", new HauntedIncursionModifier());
        // = Journal =
        JournalEntry soulChasmIncursion = registerJournalEntry("soulchasmincursion", new JournalEntry(BiomeRegistry.getBiome("soulchasm"), IncursionBiomeRegistry.getBiome("soulchasm")));
        soulChasmIncursion.addBiomeLootEntry("crystalizedsouloreitem", "alchemyshard", "upgradeshard", "chasmrockitem", "soulwoodlogitem");
        soulChasmIncursion.addMobEntries("lostsoul", "chasmmage", "chasmmagestatue", "chasmwarriorstatue", "chasmdragon");
        // == Containers ==
        CAR_COLOR_CONTAINER = ContainerRegistry.registerContainer((client, uniqueSeed, content) -> new CarColorContainerForm(client, new CarColorContainer(client.getClient(), uniqueSeed, content)), (client, uniqueSeed, content, serverObject) -> new CarColorContainer(client, uniqueSeed, content));
        // == Plushies ==
        registerPlushie("v1", V1Plushie.class, true);
        registerPlushie("fair", FairPlushie.class, false);
        registerPlushie("argemia", ArgemiaPlushie.class, true);
        registerPlushie("fumo", FumoPlushie.class, false);
        registerPlushie("dev", DevPlushie.class, true);
        registerPlushie("shark", SharkPlushie.class, false);
        registerPlushie("rock", RockPlushie.class, false);
        // == Car Colors ==
        carColors.add(new Color(210, 0, 0));
        carColors.add(new Color(210, 91, 0));
        carColors.add(new Color(210, 191, 0));
        carColors.add(new Color(11, 210, 0));
        carColors.add(new Color(0, 207, 210));
        carColors.add(new Color(0, 81, 210));
        carColors.add(new Color(210, 0, 210));
        carColors.add(new Color(165, 0, 210));
        carColors.add(new Color(210, 210, 210));
        carColors.add(new Color(105, 105, 105));
        carColors.add(new Color(36, 36, 36));
    }

    private void registerPlushie(String id, Class<? extends Mob> mob_class, boolean addTooltip){
        String mob_id = id+"plushie";
        String item_id = mob_id + "item";
        MobRegistry.registerMob(mob_id, mob_class, false);
        ItemRegistry.registerItem(item_id, new PlushieItem(mob_id, addTooltip, true), (float) 500.0, true);
    }

    private void registerFurnitureSet(String setID, Color mapColor, float lightHue, float lightSat) {
        BathtubObject.registerBathtub(setID+"bathtub", setID+"bathtub", mapColor, -1.0F);
        BedObject.registerBed(setID+"bed", setID+"bed", mapColor, -1.0F);
        DoubleBedBaseObject.registerDoubleBed(setID+"doublebed", setID+"doublebed", mapColor, -1.0F);
        BenchObject.registerBench(setID+"bench", setID+"bench", mapColor, -1.0F);
        DinnerTableObject.registerDinnerTable(setID+"dinnertable", setID+"dinnertable", mapColor, -1.0F);
        ObjectRegistry.registerObject(setID+"bookshelf", new BookshelfObject(setID+"bookshelf", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"cabinet", new CabinetObject(setID+"cabinet", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"chair", new ChairObject(setID+"chair", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"chest", new StorageBoxInventoryObject(setID+"chest",40, mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"clock", new ClockObject(setID+"clock", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"desk", new DeskObject(setID+"desk", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"display", new DisplayStandObject(setID+"display",ToolType.PICKAXE, mapColor, 32), -1.0F, true);
        ObjectRegistry.registerObject(setID+"dresser", new DresserObject(setID+"dresser", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"modulartable", new ModularTableObject(setID+"modulartable", mapColor), -1.0F, true);
        ObjectRegistry.registerObject(setID+"toilet", new ToiletObject(setID+"toilet", mapColor), -1.0F, true);
        CandelabraObject soulWoodCandelabra = new CandelabraObject(setID+"candelabra", mapColor, lightHue, lightSat);
        ObjectRegistry.registerObject(setID+"candelabra", soulWoodCandelabra, -1.0F, true);
        int soulWoodFenceID = ObjectRegistry.registerObject(setID+"fence", new FenceObject(setID+"fence", mapColor, 12, 10, -24), -1.0F, true);
        FenceGateObject.registerGatePair(soulWoodFenceID, setID+"fencegate", setID+"fencegate", mapColor, 12, 10, -1.0F);
    }

    public void initResources(){
        // === Plushie Textures ===
        V1Plushie.gameTexture = GameTexture.fromFile("mobs/plushies/v1plushie");
        FairPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/fairplushie");
        ArgemiaPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/argemiaplushie");
        FumoPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/fumoplushie");
        DevPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/devplushie");
        SharkPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/sharkplushie");
        RockPlushie.gameTexture = GameTexture.fromFile("mobs/plushies/rockplushie");
        // === Car Textures ===
        CarMob.texture_body =  GameTexture.fromFile("mobs/car_body");
        CarMob.texture_top =  GameTexture.fromFile("mobs/car_top");
        CarMob.texture_mask = GameTexture.fromFile("mobs/car_top_mask");
        // === Hostile Textures ===
        LostSoul.texture = GameTexture.fromFile("mobs/lostsoul");
        ChasmWarriorStatue.texture = GameTexture.fromFile("mobs/chasmwarriorstatue");
        ChasmMageStatue.texture = GameTexture.fromFile("mobs/chasmmagestatue");
        //GameTexture soulMageTexture = ;
        ChasmMage.texture = new HumanTexture(GameTexture.fromFile("mobs/chasmmage"), null, null);
        LesserSoul.texture = GameTexture.fromFile("mobs/lostsoul");
        // === Friendly Textures ===
        Wisp.texture = GameTexture.fromFile("mobs/wisp");
        Firefly.texture = GameTexture.fromFile("mobs/firefly");
        SoulStatueSummon.texture = GameTexture.fromFile("mobs/soulstatuesummon");
        SoulStatueSummon.texture_ring = GameTexture.fromFile("particles/soulstatuering");
        chasmCaveling = new HumanTexture(GameTexture.fromFile("mobs/chasmcaveling"), GameTexture.fromFile("mobs/chasmcavelingarms_front"), GameTexture.fromFile("mobs/chasmcavelingarms_back"));
        SealGhostSummon.texture = GameTexture.fromFile("mobs/sealghostsummon");
        // === Boss Textures ===
        ChasmDragon.texture = GameTexture.fromFile("mobs/chasmdragon");
        ChasmDragonBody.texture = GameTexture.fromFile("mobs/chasmdragon");
        // === Misc Textures ===
        AltarEffectEntity.texture_ball = GameTexture.fromFile("particles/altarball");
        eruptionShadow = GameTexture.fromFile("particles/dragongrounderuption_shadow");
        // = Sections =
        spinSpawnVisual = generateTextureSection("particles/spinspawnvisual");
        particleGhostSpawnSection = generateTextureSection("particles/meleeghostspawnparticle");
        particleFlamethrowerSection = generateTextureSection("particles/soulfiresparks");
        particleFireflySection = generateTextureSection("particles/fireflyparticle");
        particleWispSection = generateTextureSection("particles/wispparticle");
        particleBookSection = generateTextureSection("particles/bookparticle");
        particlePhantomBodySection = generateTextureSection("particles/phantombody");
        particleMeleeGhostParticleSection = generateTextureSection("particles/meleeghostparticle");
        particleMonumentRingSection = generateTextureSection("particles/soulmonumentring");
        // = Car textures =
        GameTexture car_mask_sprites = GameTexture.fromFile("mobs/car_mask");
        int carSprites = car_mask_sprites.getHeight() / 64;
        carMask = new GameTexture[carSprites];
        for(int i = 0; i < carSprites; ++i) {carMask[i] = new GameTexture(car_mask_sprites, 0, i, 64);}
        carColorContainerIcon = GameTexture.fromFile("ui/paint_bucket");
        // === Sounds ===
        plushieSqueak = GameSound.fromFile("plushie_squeak");
    }

    public GameTextureSection generateTextureSection(String texture_path) {
        GameTexture gameTexture = GameTexture.fromFile(texture_path);
        return GameResources.particlesTextureGenerator.addTexture(gameTexture);
    }

    public void registerModRecipes(ArrayList<Recipe> recipes){
        for (Recipe recipe : recipes) {
            Recipes.registerModRecipe(recipe);
        }
    }

    public void postInit() {
        ForestBiome.defaultSurfaceCritters.add(100, "firefly");
        PlainsBiome.defaultSurfaceCritters.add(100, "firefly");
        SwampBiome.surfaceCritters.add(120, "firefly");

        chasmChestRoomSet = new ChestRoomSet("chasmfloortiles", "chasmpressureplate", new WallSet("chasmbrick"), null, null, "chasmflametrap");
        chasmShrineLootTable = new LootTable(new LootItemList(new OneOfLootItems(new ChanceLootItem(0.8F,"phantomfeathertrinket"), new ChanceLootItem(0.8F,"soulstealertrinket"), new ChanceLootItem(0.8F,"pickaxeheadtrinket"), new ChanceLootItem(0.2F,"soulmetalsword"), new ChanceLootItem(0.05F, "carkeys"))));

        ArrayList<Recipe> modRecipes = new ArrayList<>();
        modRecipes.add(new Recipe("soulmetalbar", 1, RecipeTechRegistry.FORGE, Recipes.ingredientsFromScript("{{crystalizedsouloreitem, 4}}")));
        modRecipes.add(new Recipe("soulessence", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{anytier2essence, 2}}")));
        modRecipes.add(new Recipe("soulmetalsword", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcore, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalspear", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 16}, {soulcore, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalrevolver", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {handgun, 1}, {soulmetalbar, 12}, {soulcore, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalbow", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcore, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("bookofsouls", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {book, 3}, {soulmetalbar, 4}, {soulcore, 12}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("idolsummon", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 6}, {soulmetalbar, 10}, {soulcore, 12}}")));
        modRecipes.add(new Recipe("soularmorhelmet", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcore, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorhood", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcore, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorhat", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcore, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorcrown", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcore, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorchestplate", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 16}, {soulcore, 30}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorboots", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 8}, {soulcore, 15}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulabsorbshield", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcore, 8}}")));
        modRecipes.add(new Recipe("soulsealtrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{meleesoulsealtrinket, 1}, {rangesoulsealtrinket, 1}, {magicsoulsealtrinket, 1}, {summonsoulsealtrinket, 1}, {soulessence, 5}, {soulcore, 20}}")));
        modRecipes.add(new Recipe("balancedsealtrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulsealtrinket, 1}, {balancedfoci, 1}, {soulessence, 5}, {soulmetalbar, 5}, {soulcore, 20}}")));
        modRecipes.add(new Recipe("phantomdasherstrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{zephyrboots, 1}, {soulessence, 4}, {soulcore, 8}}")));
        modRecipes.add(new Recipe("asphalttile", 100, RecipeTechRegistry.ALCHEMY, Recipes.ingredientsFromScript("{{anystone, 100}, {speedpotion, 2}}")));
        modRecipes.add(new Recipe("bigjarobject", 1, RecipeTechRegistry.WORKSTATION, Recipes.ingredientsFromScript("{{glass, 2}}")));
        modRecipes.add(new Recipe("fireflyjar", 1, RecipeTechRegistry.WORKSTATION, Recipes.ingredientsFromScript("{{fireflyitem, 3}, {bigjarobject, 1}}")));
        modRecipes.add(new Recipe("wispjar", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{wispitem, 3}, {bigjarobject, 1}}")));
        modRecipes.add(new Recipe("soultorch", 8, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcore, 1}, {soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soultikitorchobject", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soultorch, 1}, {soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soullantern", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulmetalbar, 3}, {soultorch, 1}}")));
        modRecipes.add(new Recipe("soulwoodwall", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 2}}")));
        modRecipes.add(new Recipe("soulwooddoor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 4}}")));
        modRecipes.add(new Recipe("soulwoodfloor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soulwoodpath", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soulwoodtiledfloor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 2}}")));
        modRecipes.add(new Recipe("chasmbrickdoor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 15}}")));
        modRecipes.add(new Recipe("chasmbrickwall", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 5}}")));
        modRecipes.add(new Recipe("chasmfloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 1}}")));
        modRecipes.add(new Recipe("chasmbrickfloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 1}}")));
        modRecipes.add(new Recipe("chasmtiledfloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 1}}")));
        modRecipes.add(new Recipe("chasmpressureplate", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{chasmrockitem, 1}}")));
        modRecipes.add(new Recipe("chasmrock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 4}}")));
        modRecipes.add(new Recipe("crystalizedsoul", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 4}, {crystalizedsouloreitem, 1}}")));
        modRecipes.add(new Recipe("upgradeshardchasmrock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 4}, {upgradeshard, 1}}")));
        modRecipes.add(new Recipe("alchemyshardchasmrock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 4}, {alchemyshard, 1}}")));
        modRecipes.add(new Recipe("chasmrocksmall", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 20}}")));
        modRecipes.add(new Recipe("chasmrocks", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 30}}")));
        modRecipes.add(new Recipe("chasmmagestatueobject", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{chasmrockitem, 50}}")));
        modRecipes.add(new Recipe("soulwoodbathtub", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 12}}")));
        modRecipes.add(new Recipe("soulwoodbed", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 10}, {wool, 10}}")));
        modRecipes.add(new Recipe("soulwooddoublebed", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 20}, {wool, 20}}")));
        modRecipes.add(new Recipe("soulwoodbench", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 10}}")));
        modRecipes.add(new Recipe("soulwooddinnertable", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 16}}")));
        modRecipes.add(new Recipe("soulwoodbookshelf", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 10}}")));
        modRecipes.add(new Recipe("soulwoodcabinet", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 10}}")));
        modRecipes.add(new Recipe("soulwoodchair", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 4}}")));
        modRecipes.add(new Recipe("soulwoodchest", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 8}}")));
        modRecipes.add(new Recipe("soulwoodclock", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 8}, {ironbar, 2}}")));
        modRecipes.add(new Recipe("soulwooddesk", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 8}}")));
        modRecipes.add(new Recipe("soulwooddisplay", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 10}}")));
        modRecipes.add(new Recipe("soulwooddresser", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 8}}")));
        modRecipes.add(new Recipe("soulwoodmodulartable", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 8}}")));
        modRecipes.add(new Recipe("soulwoodtoilet", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 6}}")));
        modRecipes.add(new Recipe("soulwoodcandelabra", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 6}, {torch, 3}}")));
        modRecipes.add(new Recipe("soulwoodfencegate", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 4}}")));
        modRecipes.add(new Recipe("soulwoodfence", 1, RecipeTechRegistry.CARPENTER, Recipes.ingredientsFromScript("{{soulwoodlogitem, 2}}")));
        registerModRecipes(modRecipes);
    }
}

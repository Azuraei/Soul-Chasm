package soulchasm;

import necesse.engine.GameLoadingScreen;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.engine.sound.gameSound.GameSound;
import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
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
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.*;
import necesse.level.gameObject.furniture.*;
import necesse.level.gameTile.PathTiledTile;
import necesse.level.gameTile.SimpleFloorTile;
import necesse.level.gameTile.SimpleTiledFloorTile;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.plains.PlainsBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;
import necesse.level.maps.incursion.UniqueIncursionReward;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.WallSet;
import soulchasm.main.Buffs.IdolShieldBuff;
import soulchasm.main.Buffs.SoulStatueBuff;
import soulchasm.main.Buffs.SoulBleedStackBuff;
import soulchasm.main.Buffs.SoulFireBuff;
import soulchasm.main.Buffs.ArmorBuffs.*;
import soulchasm.main.Buffs.ToolBuffs.BookBuffs.BookofSoulBuff;
import soulchasm.main.Buffs.ToolBuffs.BookBuffs.SoulofSoulsOverchargeBuff;
import soulchasm.main.Buffs.ToolBuffs.BowBuffs.SoulBowBuff;
import soulchasm.main.Buffs.ToolBuffs.BowBuffs.SoulBowCooldownBuff;
import soulchasm.main.Buffs.ToolBuffs.SoulAbsorbShieldBuff;
import soulchasm.main.Buffs.ToolBuffs.SoulDeathMarkStackBuff;
import soulchasm.main.Buffs.ToolBuffs.SoulScytheBuff;
import soulchasm.main.Buffs.TrinketsBuffs.PhantomDashersBuffs.PhantomDashersActiveBuff;
import soulchasm.main.Buffs.TrinketsBuffs.PhantomDashersBuffs.PhantomDashersBuff;
import soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs.PickaxeHeadBuff;
import soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs.PickaxeHeadStackBuff;
import soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs.*;
import soulchasm.main.Buffs.TrinketsBuffs.PhantomFeatherBuff;
import soulchasm.main.Buffs.TrinketsBuffs.SoulStealerBuff;
import soulchasm.main.Items.Armor.*;
import soulchasm.main.Items.CarKeys;
import soulchasm.main.Items.Tools.*;
import soulchasm.main.Items.Trinkets.SealTrinkets.*;
import soulchasm.main.Items.Trinkets.PhantomDashersTrinket;
import soulchasm.main.Items.Trinkets.PhantomFeatherTrinket;
import soulchasm.main.Items.Trinkets.PickaxeHeadTrinket;
import soulchasm.main.Items.Trinkets.SoulStealerTrinket;
import soulchasm.main.Misc.Events.GroundEruptionEvent.DragonExplosionEvent;
import soulchasm.main.Misc.Events.GroundEruptionEvent.DragonGroundEruptionEvent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.SpinSpawnEvent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.SpinSpawnVisualEvent;
import soulchasm.main.Misc.Events.IdolShieldVisualEvent;
import soulchasm.main.Misc.Events.MeleeGhostSpawnEvent;
import soulchasm.main.Misc.HauntedModifier.HauntedIncursionModifier;
import soulchasm.main.Misc.HauntedModifier.HauntedModifierLevelEvent;
import soulchasm.main.Misc.Incursion.SoulChasmBiome;
import soulchasm.main.Misc.Incursion.SoulChasmIncursionBiome;
import soulchasm.main.Misc.Incursion.SoulChasmIncursionLevel;
import soulchasm.main.Mobs.Boss.SoulDragon;
import soulchasm.main.Objects.*;
import soulchasm.main.Objects.Jars.BigJarObject;
import soulchasm.main.Objects.Jars.FireFlyJarObject;
import soulchasm.main.Objects.Jars.WispJarObject;
import soulchasm.main.Mobs.Agressive.*;
import soulchasm.main.Mobs.Boss.SoulDragonBody;
import soulchasm.main.Mobs.Passive.Firefly;
import soulchasm.main.Mobs.Passive.SoulCaveCaveling;
import soulchasm.main.Mobs.Passive.SphereEffectMob;
import soulchasm.main.Mobs.Passive.Wisp;
import soulchasm.main.Mobs.Summon.CarMob;
import soulchasm.main.Mobs.Summon.SmallSoulSummon;
import soulchasm.main.Mobs.Summon.SoulStatueSummon;
import soulchasm.main.Plushies.*;
import soulchasm.main.Projectiles.BossProjectiles.SoulFlamethrowerProjectile;
import soulchasm.main.Projectiles.BossProjectiles.SpinSpawnSpikeProjectile;
import soulchasm.main.Projectiles.SealProjectiles.*;
import soulchasm.main.Projectiles.WeaponProjectiles.*;
import soulchasm.main.Projectiles.SoulDiscProjectile;
import soulchasm.main.Projectiles.SoulHomingProjectile;
import soulchasm.main.Tiles.*;

import java.awt.*;

import static necesse.engine.registries.ObjectRegistry.getObject;

@ModEntry
public class SoulChasm {
    public static Color chasmStoneMapColor = new Color(1, 37, 51,255);
    public static Color chasmStoneLightMapColor = new Color(2, 68, 93,255);
    public static Color chasmMagmaMapColor = new Color(7, 207, 255,255);
    public static Color chasmCrystalMapColor = new Color(0, 134, 203,255);
    public static Color chasmWoodMapColor = new Color(117, 165, 183,255);
    public static Color chasmWoodFurnitureMapColor = new Color(98, 153, 173, 255);
    public static Color chasmGrassMapColor = new Color(93, 132, 143, 255);
    public static Color chasmTorchMapColor = new Color(0, 167, 255);
    public static Color asphaltTileMapColor = new Color(23, 23, 23);
    public static Color lunarTearMapColor = new Color(227, 245, 251);

    public static ChestRoomSet chasmChestRoomSet;
    public static LootTable chasmShrineLootTable;
    public static GameTexture[] car_mask;
    public static GameTexture eruption_shadow;
    public static GameTextureSection spinspawnvisual;
    public static GameTextureSection particleFlamethrowerSection;
    public static GameTextureSection particleFireflySection;
    public static GameTextureSection particleWispSection;
    public static GameTextureSection particleBookSection;
    public static GameTextureSection particlePhantomBodySection;
    public static GameTextureSection particleGhostSpawnSection;
    public static GameTextureSection particleMeleeGhostParticleSection;
    public static GameTextureSection particleMonumentRingSection;
    public static GameSound plushie_squeak;

    public void init() {
        GameLoadingScreen.drawLoadingString("Loading Soul Chasm");
        //TILES
        TileRegistry.registerTile("soulcavegrass", new SoulCaveGrass(), 0.0F, true);
        TileRegistry.registerTile("soulcaverocktile", new SoulCaveRockTile(), 0.0F, true);
        TileRegistry.registerTile("soulcavefloortile", new SimpleFloorTile("soulcavefloortile", chasmStoneLightMapColor), 0.0F, true);

        TileRegistry.registerTile("soulcavebrickfloortile", new SimpleFloorTile("soulcavebrickfloortile", chasmStoneLightMapColor), 2.0F, true);
        TileRegistry.registerTile("soulcavetiledfloortile", new SimpleTiledFloorTile("soulcavetiledfloortile", chasmStoneLightMapColor), 2.0F, true);

        TileRegistry.registerTile("soulwoodfloor", new SimpleFloorTile("soulwoodfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodtiledfloor", new SimpleTiledFloorTile("soulwoodtiledfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodpath", new PathTiledTile("soulwoodpath", SoulChasm.chasmWoodMapColor), 5.0F, true);

        TileRegistry.registerTile("meltedsouls", new MeltedSouls(), 10.0F, true);
        TileRegistry.registerTile("soulcavecracktile", new SoulCaveCrackTile(), 0.0F, false);

        TileRegistry.registerTile("asphalttile", new AsphaltTile(), -1.0F, true);

        //OBJECTS
        RockObject chasmrock;
        ObjectRegistry.registerObject("soulcaverock", chasmrock = new RockObject("soulcaverock", chasmStoneMapColor, "soulcaverockitem"), -1.0F, true);
        chasmrock.toolTier = 4;

        SingleRockObject.registerSurfaceRock(chasmrock, "soulcaverocks", chasmStoneMapColor, -1.0F, true);
        ObjectRegistry.registerObject("soulcaverocksmall", new SingleRockSmall(chasmrock, "soulcaverocksmall", chasmStoneMapColor), -1.0F, true);

        ObjectRegistry.registerObject("crystalizedsoul", new RockOreObject(chasmrock, "oremask", "crystalizedsoulore", chasmCrystalMapColor, "crystalizedsouloreitem", 1, 2, 1), -1.0F, true);
        ObjectRegistry.registerObject("alchemyshardsoulcaverock", new RockOreObject(chasmrock, "oremask", "alchemyshardore", new Color(102, 0, 61), "alchemyshard", 1, 1, 1, false), -1.0F, true);
        ObjectRegistry.registerObject("upgradeshardsoulcaverock", new RockOreObject(chasmrock, "oremask", "upgradeshardore", new Color(0, 27, 107), "upgradeshard", 1, 1, 1, false), -1.0F, true);

        ObjectRegistry.registerObject("soulcavedecorations", new ChasmDecorationObject(chasmrock, "soulcavedecorations", chasmStoneMapColor), 0.0F, false);

        WallObject.registerWallObjects("soulbrick", "soulbrickwall", chasmrock.toolTier, chasmStoneMapColor, 2.0F, 6.0F);
        WallObject.registerWallObjects("soulwood", "soulwoodwall", 0, chasmWoodMapColor, ToolType.ALL, 2.0F, 6.0F);

        ObjectRegistry.registerObject("soulstoneflametrap", new WallFlameTrapObject((WallObject)getObject("soulbrickwall")), 50.0F, true);
        ObjectRegistry.registerObject("soulstonepressureplate", new MaskedPressurePlateObject("pressureplatemask", "soulcavefloortile", chasmStoneMapColor), 15.0F, true);

        ObjectRegistry.registerObject("soultree", new TreeObject("soultree", "soulwoodlogitem", "soultreesappling", chasmWoodMapColor, 60,80,100, "soultreeleaves"), 0.0F, false);
        ObjectRegistry.registerObject("soultreesappling", new TreeSaplingObject("soultreesappling", "soultree", 1800, 2700, true), 5.0F, true);

        ObjectRegistry.registerObject("lunartear", new LunarTearObject(), 5.0F, true);
        ObjectRegistry.registerObject("lunartearspath", new FlowerPatchObject("lunartearspath", lunarTearMapColor), 5.0F, true);

        TorchObject soulTorch = new TorchObject("soultorch", chasmTorchMapColor, 240F, 0.3F);
        soulTorch.flameHue = 190;
        ObjectRegistry.registerObject("soultorch", soulTorch, 1, true);
        TorchObject soulLantern = new TorchObject("soullantern", chasmTorchMapColor, 240.0F, 0.3F);
        soulLantern.flameHue = 190;
        ObjectRegistry.registerObject("soullantern", soulLantern, -1.0F, true);

        ObjectRegistry.registerObject("soulcavegrassobject", new GrassObject("soulcavegrassobject", 4), 0.0F, false);
        ObjectRegistry.registerObject("soulcrystalbig", new SoulCrystalBigObject(), 0.0F, false);

        ObjectRegistry.registerObject("bigjarobject", new BigJarObject(), -1.0F, true);
        ObjectRegistry.registerObject("wispjarobject", new WispJarObject(), 0.0F, false);
        ObjectRegistry.registerObject("fireflyjarobject", new FireFlyJarObject(), 0.0F, false);

        ObjectRegistry.registerObject("soulmonumentobject", new SoulMonumentObject(), 50.0F, true);
        ObjectRegistry.registerObject("oldbarrel", new InventoryObject("oldbarrel", 20, new Rectangle(8, 4, 16, 16), ToolType.PICKAXE, chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("chasmcrates", new RandomCrateObject("chasmcrates"), 0.0F, false);
        ObjectRegistry.registerObject("statueobject", new StatueObject(), 50.0F, true);
        ObjectRegistry.registerObject("magestatueobject", new MageStatueObject(), -1.0F, true);
        ObjectRegistry.registerObject("spikeobject", new SpikeObject(), 0.0F, false);

        TikiTorchObject soulTikiTorch = new TikiTorchObject();
        {
            soulTikiTorch.flameHue = 190;
            soulTikiTorch.lightLevel = 130;
            soulTikiTorch.lightHue = 240F;
            soulTikiTorch.lightSat = 0.3F;
            soulTikiTorch.mapColor = chasmTorchMapColor;
        }
        ObjectRegistry.registerObject("soultikitorchobject", soulTikiTorch, -1.0F, true);

        //Furniture
        BathtubObject.registerBathtub("soulwoodbathtub", "soulwoodbathtub", chasmWoodFurnitureMapColor, -1.0F);
        BedObject.registerBed("soulwoodbed", "soulwoodbed", chasmWoodFurnitureMapColor, -1.0F);
        BenchObject.registerBench("soulwoodbench", "soulwoodbench", chasmWoodFurnitureMapColor, -1.0F);
        DinnerTableObject.registerDinnerTable("soulwooddinnertable", "soulwooddinnertable", chasmWoodFurnitureMapColor, -1.0F);
        ObjectRegistry.registerObject("soulwoodbookshelf", new BookshelfObject("soulwoodbookshelf", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodcabinet", new CabinetObject("soulwoodcabinet", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodchair", new ChairObject("soulwoodchair", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodchest", new StorageBoxInventoryObject("soulwoodchest",40, chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodclock", new ClockObject("soulwoodclock", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwooddesk", new DeskObject("soulwooddesk", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwooddisplay", new DisplayStandObject("soulwooddisplay",ToolType.PICKAXE, chasmWoodFurnitureMapColor, 32), -1.0F, true);
        ObjectRegistry.registerObject("soulwooddresser", new DresserObject("soulwooddresser", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodmodulartable", new ModularTableObject("soulwoodmodulartable", chasmWoodFurnitureMapColor), -1.0F, true);
        ObjectRegistry.registerObject("soulwoodtoilet", new ToiletObject("soulwoodtoilet", chasmWoodFurnitureMapColor), -1.0F, true);

        CandelabraObject soulWoodCandelabra = new CandelabraObject("soulwoodcandelabra", chasmWoodFurnitureMapColor, 60.0F, 0.3F);
        ObjectRegistry.registerObject("soulwoodcandelabra", soulWoodCandelabra, -1.0F, true);

        ObjectRegistry.registerObject("soulcavechest", new StorageBoxInventoryObject("soulcavechest",40, chasmStoneMapColor), 10.0F, true);

        int soulWoodFenceID = ObjectRegistry.registerObject("soulwoodfence", new FenceObject("soulwoodfence", chasmWoodMapColor, 12, 10, -24), -1.0F, true);
        FenceGateObject.registerGatePair(soulWoodFenceID, "soulwoodfencegate", "soulwoodfencegate", chasmWoodMapColor, 12, 10, -1.0F);

        //INCURSION
        BiomeRegistry.registerBiome("soulcavern", new SoulChasmBiome(), 0, null);
        IncursionBiomeRegistry.registerBiome("soulchasmincursionbiome", new SoulChasmIncursionBiome(), 2);
        LevelRegistry.registerLevel("soulchasmincursionlevel", SoulChasmIncursionLevel.class);

        //BUFFS
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
        BuffRegistry.registerBuff("soulstatuebuff", new SoulStatueBuff());
        //SetBonus
        BuffRegistry.registerBuff("soularmorhelmetsetbonus", new SoulArmorHelmetSetBonus());
        BuffRegistry.registerBuff("soularmorhoodsetbonus", new SoulArmorHoodSetBonus());
        BuffRegistry.registerBuff("soularmorcrownsetbonus", new SoulArmorCrownSetBonus());
        BuffRegistry.registerBuff("soularmorhatsetbonus", new SoulArmorHatSetBonus());
        BuffRegistry.registerBuff("soularmorcooldown", new SoulArmorCooldown());
        BuffRegistry.registerBuff("souldischargebuff", new SoulDischargeBuff());
        BuffRegistry.registerBuff("souldischargesicknessdebuff", new SoulDischargeSicknessDebuff());
        //SealTrinketsBuffs
        BuffRegistry.registerBuff("meleesoulsealbuff", new MeleeSoulSealBuff());
        BuffRegistry.registerBuff("summonsoulsealbuff", new SummonSoulSealBuff());
        BuffRegistry.registerBuff("magicsoulsealbuff", new MagicSoulSealBuff());
        BuffRegistry.registerBuff("rangesoulsealbuff", new RangeSoulSealBuff());
        BuffRegistry.registerBuff("balancedsealbuff", new BalancedSealBuff());

        //ITEMS
        ItemRegistry.registerItem("soulessence", new EssenceMatItem(250, Item.Rarity.EPIC, 2), 30.0F, true);
        ItemRegistry.registerItem("soulcoreitem", new MatItem(250, Item.Rarity.UNCOMMON), 15, true);

        ItemRegistry.registerItem("soulcaverockitem", new StonePlaceableItem(5000), 0.1F, true);
        ItemRegistry.registerItem("crystalizedsouloreitem", new MatItem(500, Item.Rarity.UNCOMMON), 10.0F, true);
        ItemRegistry.registerItem("soulmetalbar", new MatItem(250, Item.Rarity.UNCOMMON), -1.0F, true);
        ItemRegistry.registerItem("soulwoodlogitem", (new MatItem(500, "anylog")).setItemCategory("materials", "logs"), 2.0F, true);

        ItemRegistry.registerItem("soulgrassseeditem", new GrassSeedItem("soulcavegrass"), 2.0F, true);

        ItemRegistry.registerItem("wispitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 1.0F, true);
        ItemRegistry.registerItem("fireflyitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 1.0F, true);
        ItemRegistry.registerItem("fireflyjar", new ObjectItem(getObject("fireflyjarobject")), -1.0F, true);
        ItemRegistry.registerItem("wispjar", new ObjectItem(getObject("wispjarobject")), -1.0F, true);

        ItemRegistry.registerItem("carkeys", new CarKeys(), 2000, true);
        //Trinkets
        ItemRegistry.registerItem("phantomfeathertrinket", new PhantomFeatherTrinket(), 500, true);
        ItemRegistry.registerItem("soulstealertrinket", new SoulStealerTrinket(), 500, true);
        ItemRegistry.registerItem("pickaxeheadtrinket", new PickaxeHeadTrinket(), 500, true);
        ItemRegistry.registerItem("soulabsorbshield", new SoulAbsorbShield(), 750, true);
        ItemRegistry.registerItem("phantomdasherstrinket", new PhantomDashersTrinket(), 750, true);
        //Weapons
        ItemRegistry.registerItem("soulscythe", new SoulScythe(), 2000, true);
        ItemRegistry.registerItem("soulmetalbow", new SoulMetalBow(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalsword", new SoulMetalSword(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalspear", new SoulMetalSpear(), -1.0F, true);
        ItemRegistry.registerItem("soulmetalrevolver", new SoulMetalRevolver(), -1.0F, true);
        ItemRegistry.registerItem("bookofsouls", new BookofSouls(), -1.0F, true);
        ItemRegistry.registerItem("soulstatue", new SoulStatue(), -1.0F, true);
        //Armor
        ItemRegistry.registerItem("soularmorboots", new SoulArmorBoots(), -1.0F, true);
        ItemRegistry.registerItem("soularmorchestplate", new SoulArmorChestplate(), -1.0F, true);
        ItemRegistry.registerItem("soularmorcrown", new SoulArmorCrown(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhelmet", new SoulArmorHelmet(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhood", new SoulArmorHood(), -1.0F, true);
        ItemRegistry.registerItem("soularmorhat", new SoulArmorHat(),-1.0F, true);
        //Seal
        ItemRegistry.registerItem("meleesoulsealtrinket", new MeleeSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("summonsoulsealtrinket", new SummonSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("magicsoulsealtrinket", new MagicSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("rangesoulsealtrinket", new RangeSoulSealTrinket(), 200, true);
        ItemRegistry.registerItem("soulsealtrinket", new SoulSealTrinket(), -1.0F, true);
        ItemRegistry.registerItem("balancedsealtrinket", new BalancedSealTrinket(), -1.0F, true);
        //Vanity
        ItemRegistry.registerItem("lunartearflowerhead", new HelmetArmorItem(0, null, 0, Item.Rarity.RARE, "lunartearflowerhead").hairDrawMode(ArmorItem.HairDrawMode.OVER_HAIR), 50.0F, true);
        ItemRegistry.registerItem("tobeblindfold", new HelmetArmorItem(0, null, 0, Item.Rarity.EPIC, "tobeblindfold").hairDrawMode(ArmorItem.HairDrawMode.UNDER_HAIR), 250.0F, true);

        //MOBS
        MobRegistry.registerMob("souldragonhead", SoulDragon.class, true, true);
        MobRegistry.registerMob("souldragonbody", SoulDragonBody.class, false, true);

        MobRegistry.registerMob("lostsoul", LostSoul.class, true);
        MobRegistry.registerMob("meleestatue", MeleeStatue.class, true);
        MobRegistry.registerMob("magestatue", MageStatue.class, true);
        MobRegistry.registerMob("soulmage", SoulMage.class, true);
        MobRegistry.registerMob("meleeghost", MeleeGhost.class, false);

        MobRegistry.registerMob("wisp", Wisp.class, false);
        MobRegistry.registerMob("firefly", Firefly.class, false);
        MobRegistry.registerMob("soulcavecaveling", SoulCaveCaveling.class, true);

        MobRegistry.registerMob("carmob", CarMob.class, false);
        MobRegistry.registerMob("smallsoulsummon", SmallSoulSummon.class, false);
        MobRegistry.registerMob("soulstatuesummon", SoulStatueSummon.class, false);
        MobRegistry.registerMob("sphereeffectmob", SphereEffectMob.class, false);

        //PROJECTILES
        ProjectileRegistry.registerProjectile("soulwaveprojectile", SoulWaveProjectile.class, "soulwaveprojectile", null);
        ProjectileRegistry.registerProjectile("soularrowprojectile", SoulArrowProjectile.class, "soularrowprojectile", null);
        ProjectileRegistry.registerProjectile("soulhomingprojectile", SoulHomingProjectile.class, "soulhomingprojectile",null);
        ProjectileRegistry.registerProjectile("soulmissileprojectile", SoulMissileProjectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulflamethrower", SoulFlamethrowerProjectile.class, "shadow_simple",null);
        ProjectileRegistry.registerProjectile("soulspearprojectile", SoulSpearProjectile.class, "soulspearprojectile",null);
        ProjectileRegistry.registerProjectile("spiritswordprojectile", SpiritSwordProjectile.class, "spiritswordprojectile",null);
        ProjectileRegistry.registerProjectile("soulrevolverprojectile", SoulRevolverProjectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulboomerangprojectile", SoulBoomerangProjectile.class, "soulboomerangprojectile", null);
        ProjectileRegistry.registerProjectile("soulpointywaveprojectile", SoulPointyWaveProjectile.class, "soulpointywaveprojectile", null);
        ProjectileRegistry.registerProjectile("soulbigbulletprojectile", SoulBigBulletProjectile.class, null, null);
        ProjectileRegistry.registerProjectile("spinspawnspikeprojectile", SpinSpawnSpikeProjectile.class, "spinspawnspikeprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulsmainprojectile", BookofSoulsMainProjectile.class, "bookofsoulsmainprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulssmallprojectile", BookofSoulsSmallProjectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulscytheprojectile", SoulScytheProjectile.class, "soulscytheprojectile",null);
        ProjectileRegistry.registerProjectile("soulscythesmallprojectile", SoulScytheSmallProjectile.class, "soulscythesmallprojectile",null);
        ProjectileRegistry.registerProjectile("souldiscprojectile", SoulDiscProjectile.class, "soulboomerangprojectile", null);

        //INCURSION_LOOT
        LootTable helmetReward = new LootTable(new LootItemList(new OneOfLootItems(
                        new LootItem("soularmorhelmet"),
                        new LootItem("soularmorhood"),
                        new LootItem("soularmorhat"),
                        new LootItem("soularmorcrown")
        )));
        LootTable armorReward = new LootTable(new LootItemList(new LootItem("soularmorchestplate")));
        LootTable bootsReward = new LootTable(new LootItemList(new LootItem("soularmorboots")));
        LootTable scytheReward = new LootTable(new LootItemList(new LootItem("soulscythe")));
        UniqueIncursionRewardsRegistry.registerIncursionHeadArmors("soularmorhead", new UniqueIncursionReward(helmetReward, UniqueIncursionModifierRegistry.ModifierChallengeLevel.Hard));
        UniqueIncursionRewardsRegistry.registerIncursionBodyArmors("soularmorbody", new UniqueIncursionReward(armorReward, UniqueIncursionModifierRegistry.ModifierChallengeLevel.Hard));
        UniqueIncursionRewardsRegistry.registerIncursionFeetArmors("soularmorfeet", new UniqueIncursionReward(bootsReward, UniqueIncursionModifierRegistry.ModifierChallengeLevel.Hard));
        UniqueIncursionRewardsRegistry.registerGreatswordWeapon("soulscythereward", new UniqueIncursionReward(scytheReward, UniqueIncursionModifierRegistry.ModifierChallengeLevel.Hard));

        //LEVEL_EVENTS
        LevelEventRegistry.registerEvent("dragongrounderuptionevent", DragonGroundEruptionEvent.class);
        LevelEventRegistry.registerEvent("dragonexplosionevent", DragonExplosionEvent.class);
        LevelEventRegistry.registerEvent("spinspawnevent", SpinSpawnEvent.class);
        LevelEventRegistry.registerEvent("spinspawnvisualevent", SpinSpawnVisualEvent.class);
        LevelEventRegistry.registerEvent("idolshieldvisualevent", IdolShieldVisualEvent.class);
        LevelEventRegistry.registerEvent("meleeghostspawnevent", MeleeGhostSpawnEvent.class);
        LevelEventRegistry.registerEvent("hauntedmodifierlevelevent", HauntedModifierLevelEvent.class);

        //INCURSION_MODS
        UniqueIncursionModifierRegistry.registerUniqueModifier("haunted", new HauntedIncursionModifier(UniqueIncursionModifierRegistry.ModifierChallengeLevel.Medium));

        //PLUSHIE
        registerPlushie("v1", V1Plushie.class, true);
        registerPlushie("fair", FairPlushie.class, false);
        registerPlushie("argemia", ArgemiaPlushie.class, true);
        registerPlushie("fumo", FumoPlushie.class, false);
        registerPlushie("dev", DevPlushie.class, true);
    }

    private void registerPlushie(String id, Class<? extends Mob> mob_class, boolean addTooltip){
        String mob_id = id+"plushie";
        String item_id = mob_id + "item";
        MobRegistry.registerMob(mob_id, mob_class, false);
        ItemRegistry.registerItem(item_id, new PlushieItem(mob_id, addTooltip), (float) 500.0, true);
    }

    public void initResources(){
        V1Plushie.gameTexture = GameTexture.fromFile("mobs/v1plushie");
        FairPlushie.gameTexture = GameTexture.fromFile("mobs/fairplushie");
        ArgemiaPlushie.gameTexture = GameTexture.fromFile("mobs/argemiaplushie");
        FumoPlushie.gameTexture = GameTexture.fromFile("mobs/fumoplushie");
        DevPlushie.gameTexture = GameTexture.fromFile("mobs/devplushie");

        LostSoul.texture = GameTexture.fromFile("mobs/lostsoul");
        CarMob.texture =  GameTexture.fromFile("mobs/car");
        CarMob.texture_top = GameTexture.fromFile("mobs/car_top_mask");
        MeleeStatue.texture = GameTexture.fromFile("mobs/meleestatue");
        Wisp.texture = GameTexture.fromFile("mobs/wisp");
        Firefly.texture = GameTexture.fromFile("mobs/firefly");
        MageStatue.texture = GameTexture.fromFile("mobs/magestatue");
        GameTexture soulmageTexture = GameTexture.fromFile("mobs/soulmage");
        SoulMage.texture = new HumanTexture(soulmageTexture,soulmageTexture,soulmageTexture);
        SmallSoulSummon.texture = GameTexture.fromFile("mobs/smallsoul");
        SoulDragon.texture = GameTexture.fromFile("mobs/souldragon");
        SoulDragonBody.texture = GameTexture.fromFile("mobs/souldragon");
        SphereEffectMob.texture_ball = GameTexture.fromFile("particles/altarball");
        MeleeGhost.texture = GameTexture.fromFile("mobs/lostsoul");
        SoulStatueSummon.texture = GameTexture.fromFile("items/magestatueobject");
        SoulStatueSummon.texture_ring = GameTexture.fromFile("particles/soulstatuering");

        //TextureSections
        eruption_shadow = GameTexture.fromFile("particles/dragongrounderuption_shadow");
        GameTexture spinspawnvisualtexture = GameTexture.fromFile("particles/spinspawnvisual");
        spinspawnvisual = GameResources.particlesTextureGenerator.addTexture(spinspawnvisualtexture);

        GameTexture meleeghostspawnparticle = GameTexture.fromFile("particles/meleeghostspawnparticle");
        particleGhostSpawnSection = GameResources.particlesTextureGenerator.addTexture(meleeghostspawnparticle);

        GameTexture flamethrowerParticleTexture = GameTexture.fromFile("particles/soulfiresparks");
        particleFlamethrowerSection = GameResources.particlesTextureGenerator.addTexture(flamethrowerParticleTexture);

        GameTexture fireflyParticleTexture = GameTexture.fromFile("particles/fireflyparticle");
        particleFireflySection = GameResources.particlesTextureGenerator.addTexture(fireflyParticleTexture);

        GameTexture wispParticleTexture = GameTexture.fromFile("particles/wispparticle");
        particleWispSection = GameResources.particlesTextureGenerator.addTexture(wispParticleTexture);

        GameTexture bookParticleTexture = GameTexture.fromFile("particles/bookparticle");
        particleBookSection = GameResources.particlesTextureGenerator.addTexture(bookParticleTexture);

        GameTexture phantomBodyParticleTexture = GameTexture.fromFile("particles/phantombody");
        particlePhantomBodySection = GameResources.particlesTextureGenerator.addTexture(phantomBodyParticleTexture);

        GameTexture meleeGhostParticleTexture = GameTexture.fromFile("particles/meleeghostparticle");
        particleMeleeGhostParticleSection = GameResources.particlesTextureGenerator.addTexture(meleeGhostParticleTexture);

        GameTexture monumentRingParticleTexture = GameTexture.fromFile("particles/soulmonumentring");
        particleMonumentRingSection = GameResources.particlesTextureGenerator.addTexture(monumentRingParticleTexture);

        GameTexture car_mask_sprites = GameTexture.fromFile("mobs/car_mask");
        int carSprites = car_mask_sprites.getHeight() / 64;
        car_mask = new GameTexture[carSprites];

        for(int i = 0; i < carSprites; ++i) {
            car_mask[i] = new GameTexture(car_mask_sprites, 0, i, 64);
        }

        plushie_squeak = GameSound.fromFile("plushie_squeak");
    }
    public void postInit() {
        ForestBiome.defaultSurfaceCritters.add(80, "firefly");
        PlainsBiome.defaultSurfaceCritters.add(80, "firefly");
        SwampBiome.surfaceCritters.add(100, "firefly");

        chasmChestRoomSet = new ChestRoomSet("soulcavefloortile", "soulstonepressureplate", WallSet.loadByStringID("soulbrick"), "soulcavechest", "soulstoneflametrap");
        chasmShrineLootTable = new LootTable(new LootItemList(
                new OneOfLootItems(
                        new ChanceLootItem(0.8F,"phantomfeathertrinket"),
                        new ChanceLootItem(0.8F,"soulstealertrinket"),
                        new ChanceLootItem(0.8F,"pickaxeheadtrinket"),
                        new ChanceLootItem(0.2F,"soulmetalsword"),
                        new ChanceLootItem(0.05F, "carkeys")
                )
        ));

        //---CRAFTING---//
        Recipes.registerModRecipe(new Recipe(
                "soulmetalbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("crystalizedsouloreitem", 4)
                }
        ).showAfter("ancientfossilbar"));

        Recipes.registerModRecipe(new Recipe(
                "soulessence",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("anytier2essence", 2)
                }
        ));

        //ARMOR_AND_WEAPONS
        Recipes.registerModRecipe(new Recipe(
                "soulmetalsword",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("nightsteelboots"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalspear",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 16),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("soulmetalsword"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalrevolver",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("handgun", 1),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5),
                }
        ).showAfter("soulmetalspear"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalbow",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("soulmetalrevolver"));

        Recipes.registerModRecipe(new Recipe(
                "bookofsouls",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("book", 3),
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("soulmetalbow"));

        Recipes.registerModRecipe(new Recipe(
                "soulstatue",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 6),
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("bookofsouls"));

        Recipes.registerModRecipe(new Recipe(
                "soulabsorbshield",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 8),
                }
        ).showAfter("soulstatue"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhelmet",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 20)
                }
        ).showAfter("soulabsorbshield"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhood",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 20)
                }
        ).showAfter("soularmorhelmet"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhat",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 20)
                }
        ).showAfter("soularmorhood"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorcrown",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 20)
                }
        ).showAfter("soularmorhat"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorchestplate",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 16),
                        new Ingredient("soulcoreitem", 30)
                }
        ).showAfter("soularmorcrown"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorboots",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 8),
                        new Ingredient("soulcoreitem", 15)
                }
        ).showAfter("soularmorchestplate"));

        Recipes.registerModRecipe(new Recipe(
                "soulsealtrinket",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("meleesoulsealtrinket", 1),
                        new Ingredient("rangesoulsealtrinket", 1),
                        new Ingredient("magicsoulsealtrinket", 1),
                        new Ingredient("summonsoulsealtrinket", 1),
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulcoreitem", 20)
                }
        ).showAfter("soularmorboots"));

        Recipes.registerModRecipe(new Recipe(
                "balancedsealtrinket",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulsealtrinket", 1),
                        new Ingredient("balancedfoci", 1),
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 5),
                        new Ingredient("soulcoreitem", 20),
                }
        ).showAfter("soulsealtrinket"));

        Recipes.registerModRecipe(new Recipe(
                "phantomdasherstrinket",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("zephyrboots", 1),
                        new Ingredient("soulessence", 4),
                        new Ingredient("soulcoreitem", 8)
                }
        ).showAfter("balancedsealtrinket"));

        //MISC_AND_FURNITURE
        Recipes.registerModRecipe(new Recipe(
                "asphalttile",
                100,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("anystone", 100),
                        new Ingredient("speedpotion", 2),
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "bigjarobject",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("sandstonetile", 5)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "fireflyjar",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("fireflyitem", 3),
                        new Ingredient("bigjarobject", 1)
                }
        ).showAfter("torch"));

        Recipes.registerModRecipe(new Recipe(
                "wispjar",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wispitem", 2),
                        new Ingredient("bigjarobject", 1)
                }
        ).showAfter("fireflyjar"));

        //LANDSCAPING
        Recipes.registerModRecipe(new Recipe(
                "magestatueobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 50)
                }
        ).showAfter("soulcavetiledfloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soulcaverock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 4)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "crystalizedsoul",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 4),
                        new Ingredient("crystalizedsouloreitem", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "upgradeshardsoulcaverock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 4),
                        new Ingredient("upgradeshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "alchemyshardsoulcaverock",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 4),
                        new Ingredient("alchemyshard", 1)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulcaverocksmall",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 20)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulcaverocks",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 30)
                }
        ));

        Recipes.registerModRecipe(new Recipe(
                "soultorch",
                8,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulcoreitem", 1),
                        new Ingredient("soulwoodlogitem", 1)
                }
        ).showAfter("torch"));

        Recipes.registerModRecipe(new Recipe(
                "soultikitorchobject",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soultorch", 1),
                        new Ingredient("soulwoodlogitem", 1)
                }
        ).showAfter("tikitorch"));

        Recipes.registerModRecipe(new Recipe(
                "soullantern",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 3),
                        new Ingredient("soultorch", 1)
                }
        ).showAfter("goldlamp"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodwall",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 2)}
        ).showAfter("deadwoodfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddoor",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 4)}
        ).showAfter("soulwoodwall"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodfloor",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 1)}

        ).showAfter("soulwooddoor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodpath",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 1)}

        ).showAfter("soulwoodfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodtiledfloor",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 2)}
        ).showAfter("soulwoodpath"));


        Recipes.registerModRecipe(new Recipe(
                "soulwoodbathtub",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 12)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodbed",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 10),
                        new Ingredient("wool", 10)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodbench",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 10)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddinnertable",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 16)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodbookshelf",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 10)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodcabinet",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 10)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodchair",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 4)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodchest",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 8)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodclock",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 8),
                        new Ingredient("ironbar", 2)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddesk",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 8)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddisplay",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 10)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddresser",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 8)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodmodulartable",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 8)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodtoilet",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 6)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodcandelabra",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 6),
                        new Ingredient("torch", 3)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodfencegate",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 4)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodfence",
                1, RecipeTechRegistry.CARPENTER,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 2)}
        ));

        Recipes.registerModRecipe(new Recipe(
                "soulbrickdoor",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 15)}
        ).showAfter("soulbrickwall"));

        Recipes.registerModRecipe(new Recipe(
                "soulbrickwall",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 5)}
        ).showAfter("deepswampstonebrickfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavefloortile",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulbrickdoor"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavebrickfloortile",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulcavefloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavetiledfloortile",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulcavebrickfloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soulstonepressureplate",
                1, RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("deepswampstonepressureplate"));
    }
}

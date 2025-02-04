package soulchasm;

import necesse.engine.GameLoadingScreen;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.engine.sound.gameSound.GameSound;
import necesse.entity.mobs.HumanTexture;
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
import soulchasm.main.Buffs.idolshieldbuff;
import soulchasm.main.Buffs.soulstatuebuff;
import soulchasm.main.Buffs.soulbleedstackbuff;
import soulchasm.main.Buffs.soulfirebuff;
import soulchasm.main.Buffs.ArmorBuffs.*;
import soulchasm.main.Buffs.ToolBuffs.BookBuffs.bookofsoulbuff;
import soulchasm.main.Buffs.ToolBuffs.BookBuffs.soulofsoulsoverchargebuff;
import soulchasm.main.Buffs.ToolBuffs.BowBuffs.soulbowbuff;
import soulchasm.main.Buffs.ToolBuffs.BowBuffs.soulbowcooldownbuff;
import soulchasm.main.Buffs.ToolBuffs.soulabsorbshieldbuff;
import soulchasm.main.Buffs.ToolBuffs.souldeathmarkstackbuff;
import soulchasm.main.Buffs.ToolBuffs.soulscythebuff;
import soulchasm.main.Buffs.TrinketsBuffs.PhantomDashersBuffs.phantomdashersactivebuff;
import soulchasm.main.Buffs.TrinketsBuffs.PhantomDashersBuffs.phantomdashersbuff;
import soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs.pickaxeheadbuff;
import soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs.pickaxeheadstackbuff;
import soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs.*;
import soulchasm.main.Buffs.TrinketsBuffs.phantomfeatherbuff;
import soulchasm.main.Buffs.TrinketsBuffs.soulstealerbuff;
import soulchasm.main.Items.Armor.*;
import soulchasm.main.Items.carkeys;
import soulchasm.main.Items.Tools.*;
import soulchasm.main.Items.Trinkets.SealTrinkets.*;
import soulchasm.main.Items.Trinkets.phantomdasherstrinket;
import soulchasm.main.Items.Trinkets.phantomfeathertrinket;
import soulchasm.main.Items.Trinkets.pickaxeheadtrinket;
import soulchasm.main.Items.Trinkets.soulstealertrinket;
import soulchasm.main.Misc.Events.GroundEruptionEvent.dragonexplosionevent;
import soulchasm.main.Misc.Events.GroundEruptionEvent.dragongrounderuptionevent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.spinspawnevent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.spinspawnvisualevent;
import soulchasm.main.Misc.Events.idolshieldvisualevent;
import soulchasm.main.Misc.Events.meleeghostspawnevent;
import soulchasm.main.Misc.HauntedModifier.hauntedincursionmodifier;
import soulchasm.main.Misc.HauntedModifier.hauntedmodifierlevelevent;
import soulchasm.main.Misc.Incursion.soulchasmbiome;
import soulchasm.main.Misc.Incursion.soulchasmincursionbiome;
import soulchasm.main.Misc.Incursion.soulchasmincursionlevel;
import soulchasm.main.Objects.*;
import soulchasm.main.Objects.Jars.bigjarobject;
import soulchasm.main.Objects.Jars.fireflyjarobject;
import soulchasm.main.Objects.Jars.wispjarobject;
import soulchasm.main.Mobs.Agressive.*;
import soulchasm.main.Mobs.Boss.souldragonbody;
import soulchasm.main.Mobs.Boss.souldragonhead;
import soulchasm.main.Mobs.Passive.firefly;
import soulchasm.main.Mobs.Passive.soulcavecaveling;
import soulchasm.main.Mobs.Passive.sphereeffectmob;
import soulchasm.main.Mobs.Passive.wisp;
import soulchasm.main.Mobs.Summon.carmob;
import soulchasm.main.Mobs.Summon.smallsoulsummon;
import soulchasm.main.Mobs.Summon.soulstatuesummon;
import soulchasm.main.Objects.Plushies.argemiaplushieobject;
import soulchasm.main.Objects.Plushies.fairplushieobject;
import soulchasm.main.Objects.Plushies.v1plushieobject;
import soulchasm.main.Projectiles.BossProjectiles.soulflamethrower;
import soulchasm.main.Projectiles.BossProjectiles.spinspawnspikeprojectile;
import soulchasm.main.Projectiles.SealProjectiles.*;
import soulchasm.main.Projectiles.WeaponProjectiles.*;
import soulchasm.main.Projectiles.souldiscprojectile;
import soulchasm.main.Projectiles.soulhomingprojectile;
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
    public static GameSound argemiaplushie_meow;
    public static GameSound plushie_squeak;

    public void init() {
        GameLoadingScreen.drawLoadingString("Loading Soul Chasm");
        //TILES
        TileRegistry.registerTile("soulcavegrass", new SoulCaveGrass(), 0.0F, true);
        TileRegistry.registerTile("soulcaverocktile", new soulcaverocktile(), 0.0F, true);
        TileRegistry.registerTile("soulcavefloortile", new SimpleFloorTile("soulcavefloortile", chasmStoneLightMapColor), 0.0F, true);

        TileRegistry.registerTile("soulcavebrickfloortile", new SimpleFloorTile("soulcavebrickfloortile", chasmStoneLightMapColor), 2.0F, true);
        TileRegistry.registerTile("soulcavetiledfloortile", new SimpleTiledFloorTile("soulcavetiledfloortile", chasmStoneLightMapColor), 2.0F, true);

        TileRegistry.registerTile("soulwoodfloor", new SimpleFloorTile("soulwoodfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodtiledfloor", new SimpleTiledFloorTile("soulwoodtiledfloor", SoulChasm.chasmWoodMapColor), 2.0F, true);
        TileRegistry.registerTile("soulwoodpath", new PathTiledTile("soulwoodpath", SoulChasm.chasmWoodMapColor), 5.0F, true);

        TileRegistry.registerTile("meltedsouls", new meltedsouls(), 10.0F, true);
        TileRegistry.registerTile("soulcavecracktile", new soulcavecracktile(), 0.0F, false);

        TileRegistry.registerTile("asphalttile", new asphalttile(), 20.0F, true);

        //OBJECTS
        RockObject chasmrock;
        ObjectRegistry.registerObject("soulcaverock", chasmrock = new RockObject("soulcaverock", chasmStoneMapColor, "soulcaverockitem"), -1.0F, true);
        chasmrock.toolTier = 4;
        SingleRockObject.registerSurfaceRock(chasmrock, "soulcaverocks", chasmStoneMapColor, -0.1F, false);
        ObjectRegistry.registerObject("soulcaverockssmall", new SingleRockSmall(chasmrock, "soulcaverockssmall", chasmStoneMapColor), 0.0F, false);

        ObjectRegistry.registerObject("crystalizedsoul", new RockOreObject(chasmrock, "oremask", "crystalizedsoulore", chasmCrystalMapColor, "crystalizedsouloreitem", 1, 2, 1), -1.0F, true);
        ObjectRegistry.registerObject("alchemyshardsoulcaverock", new RockOreObject(chasmrock, "oremask", "alchemyshardore", new Color(102, 0, 61), "alchemyshard", 1, 1, 1), -1.0F, true);
        ObjectRegistry.registerObject("upgradeshardsoulcaverock", new RockOreObject(chasmrock, "oremask", "upgradeshardore", new Color(0, 27, 107), "upgradeshard", 1, 1, 1), -1.0F, true);

        ObjectRegistry.registerObject("soulcavedecorations", new ChasmDecorationObject(chasmrock, "soulcavedecorations", chasmStoneMapColor), 0.0F, false);

        WallObject.registerWallObjects("soulbrick", "soulbrickwall", chasmrock.toolTier, chasmStoneMapColor, 2.0F, 6.0F);
        WallObject.registerWallObjects("soulwood", "soulwoodwall", 0, chasmWoodMapColor, ToolType.ALL, 2.0F, 6.0F);

        ObjectRegistry.registerObject("soulstoneflametrap", new WallFlameTrapObject((WallObject)getObject("soulbrickwall")), 50.0F, true);
        ObjectRegistry.registerObject("soulstonepressureplate", new MaskedPressurePlateObject("pressureplatemask", "soulcavefloortile", chasmStoneMapColor), 15.0F, true);

        ObjectRegistry.registerObject("soultree", new TreeObject("soultree", "soulwoodlogitem", "soultreesappling", chasmWoodMapColor, 60,80,100, "soultreeleaves"), 0.0F, false);
        ObjectRegistry.registerObject("soultreesappling", new TreeSaplingObject("soultreesappling", "soultree", 1800, 2700, true), 5.0F, true);

        ObjectRegistry.registerObject("lunartear", new lunartear(), 5.0F, true);
        ObjectRegistry.registerObject("lunartearspath", new lunartearspath(), 5.0F, true);

        TorchObject soulTorch = new TorchObject("soultorch", chasmTorchMapColor, 240F, 0.3F);
        soulTorch.flameHue = 190;
        ObjectRegistry.registerObject("soultorch", soulTorch, 1, true);
        TorchObject soulLantern = new TorchObject("soullantern", chasmTorchMapColor, 240.0F, 0.3F);
        soulLantern.flameHue = 190;
        ObjectRegistry.registerObject("soullantern", soulLantern, 10, true);

        ObjectRegistry.registerObject("soulcavegrassobject", new GrassObject("soulcavegrassobject", 4), 0.0F, false);
        ObjectRegistry.registerObject("soulcrystalbig", new soulcrystalbig(), 0.0F, false);

        ObjectRegistry.registerObject("bigjarobject", new bigjarobject(), 10.0F, true);
        ObjectRegistry.registerObject("wispjarobject", new wispjarobject(), 0.0F, false);
        ObjectRegistry.registerObject("fireflyjarobject", new fireflyjarobject(), 0.0F, false);

        ObjectRegistry.registerObject("soulmonumentobject", new soulmonumentobject(), 50.0F, true);
        ObjectRegistry.registerObject("oldbarrel", new InventoryObject("oldbarrel", 20, new Rectangle(8, 4, 16, 16), ToolType.PICKAXE, chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("chasmcrates", new RandomCrateObject("chasmcrates"), 0.0F, false);
        ObjectRegistry.registerObject("statueobject", new statueobject(), 50.0F, true);
        ObjectRegistry.registerObject("magestatueobject", new magestatueobject(), 50.0F, true);
        ObjectRegistry.registerObject("spikeobject", new spikeobject(), 0.0F, false);

        TikiTorchObject soulTikiTorch = new TikiTorchObject();
        {
            soulTikiTorch.flameHue = 190;
            soulTikiTorch.lightLevel = 130;
            soulTikiTorch.lightHue = 240F;
            soulTikiTorch.lightSat = 0.3F;
            soulTikiTorch.mapColor = chasmTorchMapColor;
        }
        ObjectRegistry.registerObject("soultikitorchobject", soulTikiTorch, 2.0F, true);

        ObjectRegistry.registerObject("argemiaplushieobject", new argemiaplushieobject(), 10.0F, true);
        ObjectRegistry.registerObject("fairplushieobject", new fairplushieobject(), 10.0F, true);
        ObjectRegistry.registerObject("v1plushieobject", new v1plushieobject(), 10.0F, true);

        //Furniture
        BathtubObject.registerBathtub("soulwoodbathtub", "soulwoodbathtub", chasmWoodFurnitureMapColor, 10.0F);
        BedObject.registerBed("soulwoodbed", "soulwoodbed", chasmWoodFurnitureMapColor, 100.0F);
        BenchObject.registerBench("soulwoodbench", "soulwoodbench", chasmWoodFurnitureMapColor, 10.0F);
        DinnerTableObject.registerDinnerTable("soulwooddinnertable", "soulwooddinnertable", chasmWoodFurnitureMapColor, 20.0F);
        ObjectRegistry.registerObject("soulwoodbookshelf", new BookshelfObject("soulwoodbookshelf", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodcabinet", new CabinetObject("soulwoodcabinet", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodchair", new ChairObject("soulwoodchair", chasmWoodFurnitureMapColor), 5.0F, true);
        ObjectRegistry.registerObject("soulwoodchest", new StorageBoxInventoryObject("soulwoodchest",40, chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodclock", new ClockObject("soulwoodclock", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddesk", new DeskObject("soulwooddesk", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddisplay", new DisplayStandObject("soulwooddisplay",ToolType.PICKAXE, chasmWoodFurnitureMapColor, 32), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddresser", new DresserObject("soulwooddresser", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodmodulartable", new ModularTableObject("soulwoodmodulartable", chasmWoodFurnitureMapColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodtoilet", new ToiletObject("soulwoodtoilet", chasmWoodFurnitureMapColor), 10.0F, true);

        CandelabraObject soulWoodCandelabra = new CandelabraObject("soulwoodcandelabra", chasmWoodFurnitureMapColor, 60.0F, 0.3F);
        ObjectRegistry.registerObject("soulwoodcandelabra", soulWoodCandelabra, 10.0F, true);

        ObjectRegistry.registerObject("soulcavechest", new StorageBoxInventoryObject("soulcavechest",40, chasmStoneMapColor), 10.0F, true);

        int soulWoodFenceID = ObjectRegistry.registerObject("soulwoodfence", new FenceObject("soulwoodfence", chasmWoodMapColor, 12, 10, -24), 2.0F, true);
        FenceGateObject.registerGatePair(soulWoodFenceID, "soulwoodfencegate", "soulwoodfencegate", chasmWoodMapColor, 12, 10, 4.0F);

        //INCURSION
        BiomeRegistry.registerBiome("soulcavern", new soulchasmbiome(), 0, null);
        IncursionBiomeRegistry.registerBiome("soulchasmincursionbiome", new soulchasmincursionbiome(), 2);
        LevelRegistry.registerLevel("soulchasmincursionlevel", soulchasmincursionlevel.class);

        //BUFFS
        BuffRegistry.registerBuff("soulstealerbuff", new soulstealerbuff());
        BuffRegistry.registerBuff("phantomfeatherbuff", new phantomfeatherbuff());
        BuffRegistry.registerBuff("soulfirebuff", new soulfirebuff());
        BuffRegistry.registerBuff("soulabsorbshieldbuff", new soulabsorbshieldbuff());
        BuffRegistry.registerBuff("soulsealfollowerbuff", new soulsealfollowerbuff());
        BuffRegistry.registerBuff("soulbleedstackbuff", new soulbleedstackbuff());
        BuffRegistry.registerBuff("soulscythebuff", new soulscythebuff());
        BuffRegistry.registerBuff("souldeathmarkstackbuff", new souldeathmarkstackbuff());
        BuffRegistry.registerBuff("pickaxeheadbuff", new pickaxeheadbuff());
        BuffRegistry.registerBuff("pickaxeheadstackbuff", new pickaxeheadstackbuff());
        BuffRegistry.registerBuff("phantomdashersactivebuff", new phantomdashersactivebuff());
        BuffRegistry.registerBuff("phantomdashersbuff", new phantomdashersbuff());
        BuffRegistry.registerBuff("bookofsoulbuff", new bookofsoulbuff());
        BuffRegistry.registerBuff("soulofsoulsoverchargebuff", new soulofsoulsoverchargebuff());
        BuffRegistry.registerBuff("soulbowbuff", new soulbowbuff());
        BuffRegistry.registerBuff("soulbowcooldownbuff", new soulbowcooldownbuff());
        BuffRegistry.registerBuff("idolshieldbuff", new idolshieldbuff());
        BuffRegistry.registerBuff("soulstatuebuff", new soulstatuebuff());
        //SetBonus
        BuffRegistry.registerBuff("soularmorhelmetsetbonus", new soularmorhelmetsetbonus());
        BuffRegistry.registerBuff("soularmorhoodsetbonus", new soularmorhoodsetbonus());
        BuffRegistry.registerBuff("soularmorcrownsetbonus", new soularmorcrownsetbonus());
        BuffRegistry.registerBuff("soularmorhatsetbonus", new soularmorhatsetbonus());
        BuffRegistry.registerBuff("soularmorcooldown", new soularmorcooldown());
        BuffRegistry.registerBuff("souldischargebuff", new souldischargebuff());
        BuffRegistry.registerBuff("souldischargesicknessdebuff", new souldischargesicknessdebuff());
        //SealTrinketsBuffs
        BuffRegistry.registerBuff("meleesoulsealbuff", new meleesoulsealbuff());
        BuffRegistry.registerBuff("summonsoulsealbuff", new summonsoulsealbuff());
        BuffRegistry.registerBuff("magicsoulsealbuff", new magicsoulsealbuff());
        BuffRegistry.registerBuff("rangesoulsealbuff", new rangesoulsealbuff());
        BuffRegistry.registerBuff("balancedsealbuff", new balancedsealbuff());

        //ITEMS
        ItemRegistry.registerItem("soulessence", new EssenceMatItem(250, Item.Rarity.EPIC, 2), 30.0F, true);
        ItemRegistry.registerItem("soulcoreitem", new MatItem(250, Item.Rarity.UNCOMMON), 15, true);
        ItemRegistry.registerItem("souldragonscales", new MatItem(250, Item.Rarity.EPIC), 150, true);

        ItemRegistry.registerItem("soulcaverockitem", new StonePlaceableItem(5000), 0.1F, true);
        ItemRegistry.registerItem("crystalizedsouloreitem", new MatItem(500, Item.Rarity.UNCOMMON), 15, true);
        ItemRegistry.registerItem("soulmetalbar", new MatItem(250, Item.Rarity.UNCOMMON), 80, true);
        ItemRegistry.registerItem("soulwoodlogitem", (new MatItem(500, "anylog")).setItemCategory("materials", "logs"), 2.0F, true);

        ItemRegistry.registerItem("soulgrassseeditem", new GrassSeedItem("soulcavegrass"), 2.0F, true);

        ItemRegistry.registerItem("wispitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 5.0F, true);
        ItemRegistry.registerItem("fireflyitem", new MatItem(500, Item.Rarity.UNCOMMON, "glowingbugs"), 5.0F, true);
        ItemRegistry.registerItem("fireflyjar", new ObjectItem(getObject("fireflyjarobject")), 20.0F, true);
        ItemRegistry.registerItem("wispjar", new ObjectItem(getObject("wispjarobject")), 20.0F, true);

        ItemRegistry.registerItem("carkeys", new carkeys(), 2000, true);
        //Trinkets
        ItemRegistry.registerItem("phantomfeathertrinket", new phantomfeathertrinket(), 500, true);
        ItemRegistry.registerItem("soulstealertrinket", new soulstealertrinket(), 500, true);
        ItemRegistry.registerItem("pickaxeheadtrinket", new pickaxeheadtrinket(), 500, true);
        ItemRegistry.registerItem("soulabsorbshield", new soulabsorbshield(), 750, true);
        ItemRegistry.registerItem("phantomdasherstrinket", new phantomdasherstrinket(), 750, true);
        //Weapons
        ItemRegistry.registerItem("soulscythe", new soulscythe(), 2000, true);
        ItemRegistry.registerItem("soulmetalbow", new soulmetalbow(), 500, true);
        ItemRegistry.registerItem("soulmetalsword", new soulmetalsword(), 500, true);
        ItemRegistry.registerItem("soulmetalspear", new soulmetalspear(), 500, true);
        ItemRegistry.registerItem("soulmetalrevolver", new soulmetalrevolver(), 500, true);
        ItemRegistry.registerItem("bookofsouls", new bookofsouls(), 500, true);
        ItemRegistry.registerItem("soulstatue", new soulstatue(), 500, true);
        //Armor
        ItemRegistry.registerItem("soularmorboots", new soularmorboots(), 200.0F, true);
        ItemRegistry.registerItem("soularmorchestplate", new soularmorchestplate(), 200.0F, true);
        ItemRegistry.registerItem("soularmorcrown", new soularmorcrown(), 200.0F, true);
        ItemRegistry.registerItem("soularmorhelmet", new soularmorhelmet(), 200.0F, true);
        ItemRegistry.registerItem("soularmorhood", new soularmorhood(), 200.0F, true);
        ItemRegistry.registerItem("soularmorhat", new soularmorhat(),200.0F, true);
        //Seal
        ItemRegistry.registerItem("meleesoulsealtrinket", new meleesoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("summonsoulsealtrinket", new summonsoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("magicsoulsealtrinket", new magicsoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("rangesoulsealtrinket", new rangesoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("soulsealtrinket", new soulsealtrinket(), 750, true);
        ItemRegistry.registerItem("balancedsealtrinket", new balancedsealtrinket(), 1250, true);
        //Vanity
        ItemRegistry.registerItem("lunartearflowerhead", new HelmetArmorItem(0, null, 0, Item.Rarity.RARE, "lunartearflowerhead").hairDrawMode(ArmorItem.HairDrawMode.OVER_HAIR), 50.0F, true);
        ItemRegistry.registerItem("tobeblindfold", new HelmetArmorItem(0, null, 0, Item.Rarity.EPIC, "tobeblindfold").hairDrawMode(ArmorItem.HairDrawMode.UNDER_HAIR), 250.0F, true);

        //MOBS
        MobRegistry.registerMob("lostsoul", lostsoul.class, true);
        MobRegistry.registerMob("carmob", carmob.class, false);
        MobRegistry.registerMob("meleestatue", meleestatue.class, true);
        MobRegistry.registerMob("wisp", wisp.class, false);
        MobRegistry.registerMob("firefly", firefly.class, false);
        MobRegistry.registerMob("magestatue", magestatue.class, true);
        MobRegistry.registerMob("soulmage", soulmage.class, true);
        MobRegistry.registerMob("smallsoulsummon", smallsoulsummon.class, false);
        MobRegistry.registerMob("soulcavecaveling", soulcavecaveling.class, true);
        MobRegistry.registerMob("souldragonhead", souldragonhead.class, true, true);
        MobRegistry.registerMob("souldragonbody", souldragonbody.class, false, true);
        MobRegistry.registerMob("sphereeffectmob", sphereeffectmob.class, false);
        MobRegistry.registerMob("meleeghost", meleeghost.class, false);
        MobRegistry.registerMob("soulstatuesummon", soulstatuesummon.class, false);

        //PROJECTILES
        ProjectileRegistry.registerProjectile("soulwaveprojectile", soulwaveprojectile.class, "soulwaveprojectile", null);
        ProjectileRegistry.registerProjectile("soularrowprojectile", soularrowprojectile.class, "soularrowprojectile", null);
        ProjectileRegistry.registerProjectile("soulhomingprojectile", soulhomingprojectile.class, "soulhomingprojectile",null);
        ProjectileRegistry.registerProjectile("soulmissileprojectile", soulmissileprojectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulflamethrower", soulflamethrower.class, "shadow_simple",null);
        ProjectileRegistry.registerProjectile("soulspearprojectile", soulspearprojectile.class, "soulspearprojectile",null);
        ProjectileRegistry.registerProjectile("spiritswordprojectile", spiritswordprojectile.class, "spiritswordprojectile",null);
        ProjectileRegistry.registerProjectile("soulrevolverprojectile", soulrevolverprojectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulboomerangprojectile", soulboomerangprojectile.class, "soulboomerangprojectile", null);
        ProjectileRegistry.registerProjectile("soulpointywaveprojectile", soulpointywaveprojectile.class, "soulpointywaveprojectile", null);
        ProjectileRegistry.registerProjectile("soulbigbulletprojectile", soulbigbulletprojectile.class, null, null);
        ProjectileRegistry.registerProjectile("spinspawnspikeprojectile", spinspawnspikeprojectile.class, "spinspawnspikeprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulsmainprojectile", bookofsoulsmainprojectile.class, "bookofsoulsmainprojectile",null);
        ProjectileRegistry.registerProjectile("bookofsoulssmallprojectile", bookofsoulssmallprojectile.class, "soulmissileprojectile",null);
        ProjectileRegistry.registerProjectile("soulscytheprojectile", soulscytheprojectile.class, "soulscytheprojectile",null);
        ProjectileRegistry.registerProjectile("soulscythesmallprojectile", soulscythesmallprojectile.class, "soulscythesmallprojectile",null);
        ProjectileRegistry.registerProjectile("souldiscprojectile", souldiscprojectile.class, "soulboomerangprojectile", null);

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
        LevelEventRegistry.registerEvent("dragongrounderuptionevent", dragongrounderuptionevent.class);
        LevelEventRegistry.registerEvent("dragonexplosionevent", dragonexplosionevent.class);
        LevelEventRegistry.registerEvent("spinspawnevent", spinspawnevent.class);
        LevelEventRegistry.registerEvent("spinspawnvisualevent", spinspawnvisualevent.class);
        LevelEventRegistry.registerEvent("idolshieldvisualevent", idolshieldvisualevent.class);
        LevelEventRegistry.registerEvent("meleeghostspawnevent", meleeghostspawnevent.class);
        LevelEventRegistry.registerEvent("hauntedmodifierlevelevent", hauntedmodifierlevelevent.class);

        //INCURSION_MODS
        UniqueIncursionModifierRegistry.registerUniqueModifier("haunted", new hauntedincursionmodifier(UniqueIncursionModifierRegistry.ModifierChallengeLevel.Medium));
    }
    public void initResources(){
        lostsoul.texture = GameTexture.fromFile("mobs/lostsoul");
        carmob.texture =  GameTexture.fromFile("mobs/car");
        carmob.texture_top = GameTexture.fromFile("mobs/car_top_mask");
        meleestatue.texture = GameTexture.fromFile("mobs/meleestatue");
        wisp.texture = GameTexture.fromFile("mobs/wisp");
        firefly.texture = GameTexture.fromFile("mobs/firefly");
        magestatue.texture = GameTexture.fromFile("mobs/magestatue");
        GameTexture soulmageTexture = GameTexture.fromFile("mobs/soulmage");
        soulmage.texture = new HumanTexture(soulmageTexture,soulmageTexture,soulmageTexture);
        smallsoulsummon.texture = GameTexture.fromFile("mobs/smallsoul");
        souldragonhead.texture = GameTexture.fromFile("mobs/souldragon");
        souldragonbody.texture = GameTexture.fromFile("mobs/souldragon");
        sphereeffectmob.texture_ball = GameTexture.fromFile("particles/altarball");
        meleeghost.texture = GameTexture.fromFile("mobs/lostsoul");
        soulstatuesummon.texture = GameTexture.fromFile("items/magestatueobject");
        soulstatuesummon.texture_ring = GameTexture.fromFile("particles/soulstatuering");

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

        argemiaplushie_meow = GameSound.fromFile("argemiaplushie_meow");
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
        ).showAfter("slimeessence"));

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
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulcoreitem", 4)
                }
        ).showAfter("soulabsorbshield"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhood",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("soularmorhelmet"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhat",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("soularmorhood"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorcrown",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 8),
                        new Ingredient("soulcoreitem", 8)
                }
        ).showAfter("soularmorhat"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorchestplate",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 18),
                        new Ingredient("soulcoreitem", 4)
                }
        ).showAfter("soularmorcrown"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorboots",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 4)
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
                        new Ingredient("soulcoreitem", 10)
                }
        ).showAfter("soularmorboots"));

        Recipes.registerModRecipe(new Recipe(
                "balancedsealtrinket",
                1,
                RecipeTechRegistry.FALLEN_ANVIL,
                new Ingredient[]{
                        new Ingredient("soulsealtrinket", 1),
                        new Ingredient("balancedfoci", 1),
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulcoreitem", 10),
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

        Recipes.registerModRecipe(new Recipe(
                "magestatueobject",
                1,
                RecipeTechRegistry.LANDSCAPING,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 50)
                }
        ).showAfter("soulcavetiledfloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soultorch",
                20,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("torch", 20),
                        new Ingredient("soulcoreitem", 1)
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

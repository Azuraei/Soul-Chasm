package soulchasm;

import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.engine.sound.gameSound.GameSound;
import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorItem;
import necesse.inventory.item.armorItem.HelmetArmorItem;
import necesse.inventory.item.matItem.EssenceMatItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;
import necesse.inventory.item.placeableItem.tileItem.GrassSeedItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.*;
import necesse.inventory.lootTable.presets.IncursionCrateLootTable;
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
import soulchasm.main.Buffs.Buffs.idolshieldbuff;
import soulchasm.main.Buffs.Buffs.soulstatuebuff;
import soulchasm.main.Buffs.Debuffs.soulbleedstackbuff;
import soulchasm.main.Buffs.Debuffs.soulfirebuff;
import soulchasm.main.Buffs.SetBonusBuffs.*;
import soulchasm.main.Buffs.ToolBuffs.BookOfSoulsBuffs.bookofsoulbuff;
import soulchasm.main.Buffs.ToolBuffs.BookOfSoulsBuffs.soulofsoulsoverchargebuff;
import soulchasm.main.Buffs.ToolBuffs.SoulMetalBowBuffs.soulbowbuff;
import soulchasm.main.Buffs.ToolBuffs.SoulMetalBowBuffs.soulbowcooldownbuff;
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
import soulchasm.main.Items.Others.carkeys;
import soulchasm.main.Items.Others.soulsigil;
import soulchasm.main.Items.Tools.*;
import soulchasm.main.Items.Trinkets.SealVariantsItems.*;
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
import soulchasm.main.Misc.Incursion.HauntedModifier.hauntedincursionmodifier;
import soulchasm.main.Misc.Incursion.HauntedModifier.hauntedmodifierlevelevent;
import soulchasm.main.Misc.Incursion.soulchasmbiome;
import soulchasm.main.Misc.Incursion.soulchasmincursionbiome;
import soulchasm.main.Misc.Incursion.soulchasmincursionlevel;
import soulchasm.main.Misc.Others.decorationobject;
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
import soulchasm.main.Objects.BiomeEnviroment.*;
import soulchasm.main.Objects.OtherObjects.*;
import soulchasm.main.Projectiles.BossProjectiles.soulflamethrower;
import soulchasm.main.Projectiles.BossProjectiles.spinspawnspikeprojectile;
import soulchasm.main.Projectiles.SealProjectiles.*;
import soulchasm.main.Projectiles.WeaponProjectiles.*;
import soulchasm.main.Projectiles.souldiscprojectile;
import soulchasm.main.Projectiles.soulhomingprojectile;

import java.awt.*;

import static necesse.engine.registries.ObjectRegistry.getObject;

@ModEntry
public class SoulChasm {
    public static Color SoulStoneColor = new Color(1, 37, 51,255);
    public static Color SoulStoneColorLight = new Color(2, 68, 93,255);
    public static Color SoulMagmaStone = new Color(7, 207, 255,255);
    public static Color SoulCrystalColor = new Color(0, 134, 203,255);
    public static Color SoulWoodColor = new Color(117, 165, 183,255);
    public static Color SoulFurnitureColor = new Color(98, 153, 173, 255);
    public static Color SoulGrassColor = new Color(93, 132, 143, 255);
    public static ChestRoomSet SoulCaveChestRoomSet;
    public static LootTable soulcavechestloottable;
    public static LootTable soulcaveruinsloottable;
    public static LootTable soulcavemonumentshrineloottable;
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
        System.out.println("no idea what to type here, so I will just say hi");
        //GameLoadingScreen.drawLoadingString(Localization.translate("loading", "objects"));
        //TILES
        TileRegistry.registerTile("soulcavegrass", new soulcavegrass(), 1, true);
        TileRegistry.registerTile("soulcaverocktile", new soulcaverocktile(), 1, true);
        TileRegistry.registerTile("soulwoodfloor", new SimpleFloorTile("soulwoodfloor", SoulChasm.SoulWoodColor), 1, true);
        TileRegistry.registerTile("soulwoodpath", new PathTiledTile("soulwoodpath", SoulChasm.SoulWoodColor), 1, true);
        TileRegistry.registerTile("soulwoodtiledfloor", new SimpleTiledFloorTile("soulwoodtiledfloor", SoulChasm.SoulWoodColor), 1, true);
        TileRegistry.registerTile("soulcavefloortile", new SimpleFloorTile("soulcavefloortile", SoulStoneColorLight), 1, true);
        TileRegistry.registerTile("soulcavetiledfloortile", new soulcavetiledfloortile(), 1, true);
        TileRegistry.registerTile("asphalttile", new asphalttile(), 1, true);
        TileRegistry.registerTile("meltedsouls", new meltedsouls(), 0, true);
        TileRegistry.registerTile("soulcavecracktile", new soulcavecracktile(), 0, false);
        TileRegistry.registerTile("soulcavebrickfloortile", new SimpleFloorTile("soulcavebrickfloortile", SoulStoneColorLight), 1, true);
        //OBJECTS
        RockObject soulcaverock;
        ObjectRegistry.registerObject("soulcaverock", soulcaverock = new RockObject("soulcaverock", SoulStoneColor, "soulcaverockitem"), 0.0F, false);
        soulcaverock.toolTier = 4;
        SingleRockObject.registerSurfaceRock(soulcaverock, "soulcaverocks", SoulStoneColor, 0.0F, false);
        ObjectRegistry.registerObject("soulcaverockssmall", new SingleRockSmall(soulcaverock, "soulcaverockssmall", SoulStoneColor), 0.0F, false);
        ObjectRegistry.registerObject("soulstonepressureplate", new MaskedPressurePlateObject("pressureplatemask", "soulcavefloortile", SoulStoneColor), 15.0F, true);
        //Wood
        int[] soulWoodWallIDs = WallObject.registerWallObjects("soulwood", "soulwoodtexture", 0, SoulWoodColor, ToolType.ALL, 2.0F, 6.0F);
        WallObject soulWoodWall = (WallObject)getObject(soulWoodWallIDs[0]);
        int soulwoodFenceID = ObjectRegistry.registerObject("soulwoodfence", new FenceObject("soulwoodfence", soulWoodWall.mapColor, 12, 10, -24), 2.0F, true);
        FenceGateObject.registerGatePair(soulwoodFenceID, "soulwoodfencegate", "soulwoodfencegate", soulWoodWall.mapColor, 12, 10, 4.0F);
        //Brick
        int[] soulBrickWallIDs = WallObject.registerWallObjects("soulbrick", "soulbricktexture", soulcaverock.toolTier, SoulStoneColor, 2.0F, 6.0F);
        WallObject soulBrickWall = (WallObject)getObject(soulBrickWallIDs[0]);
        //
        ObjectRegistry.registerObject("soulstoneflametrap", new WallFlameTrapObject((WallObject) getObject("soulbrickwall")), 50.0F, true);
        ObjectRegistry.registerObject("soulcavedecorations", new decorationobject(soulcaverock, "soulcavedecorations", SoulStoneColor), 0.0F, false);
        ObjectRegistry.registerObject("crystalizedsoul", new RockOreObject((RockObject) getObject("soulcaverock"), "oremask", "crystalizedsoulore", SoulStoneColor, "crystalizedsouloreitem"), 0.0F, false);
        ObjectRegistry.registerObject("alchemyshardsoulcaverock", new RockOreObject((RockObject) getObject("soulcaverock"), "oremask", "alchemyshardore", new Color(102, 0, 61), "alchemyshard", 1, 1, 1), 0.0F, false);
        ObjectRegistry.registerObject("upgradeshardsoulcaverock", new RockOreObject(soulcaverock, "oremask", "upgradeshardore", new Color(0, 27, 107), "upgradeshard", 1, 1, 1), 0.0F, false);
        ObjectRegistry.registerObject("soultree", new TreeObject("soultree", "soulwoodlogitem", "soultreesappling", SoulWoodColor, 60,80,100, "soultreeleaves"), 0.0F, false);
        ObjectRegistry.registerObject("soultreesappling", new TreeSaplingObject("soultreesappling", "soultree", 20, 30, true), 1, true);
        ObjectRegistry.registerObject("lunartear", new lunartear(), 1, true);
        ObjectRegistry.registerObject("lunartearspath", new lunartearspath(), 1, true);
        //
        TorchObject torch = new TorchObject("soultorch", new Color(0x00A7FF), 240F, 0.3F);
        torch.flameHue = 190;
        ObjectRegistry.registerObject("soultorch", torch, 1, true);
        //
        TorchObject soullantern = new TorchObject("soullantern", new Color(0x00A7FF), 240.0F, 0.3F);
        soullantern.flameHue = 190;
        ObjectRegistry.registerObject("soullantern", soullantern, 10, true);
        //
        ObjectRegistry.registerObject("soulcavegrassobject", new soulcavegrassobject(), 0.0F, false);
        ObjectRegistry.registerObject("soulcrystalbig", new soulcrystalbig(), 0.0F, false);
        ObjectRegistry.registerObject("wispjarobject", new wispjarobject(), 1, false);
        ObjectRegistry.registerObject("fireflyjarobject", new fireflyjarobject(), 1, false);
        ObjectRegistry.registerObject("soulmonumentobject", new soulmonumentobject(), 100, true);
        ObjectRegistry.registerObject("oldbarrel", new InventoryObject("oldbarrel", 20, new Rectangle(8, 4, 16, 16), ToolType.PICKAXE, SoulFurnitureColor), 10, true);
        ObjectRegistry.registerObject("bigjarobject", new bigjarobject(), 10, true);
        ObjectRegistry.registerObject("chasmcrates", new RandomCrateObject("chasmcrates"), 0.0F, false);
        ObjectRegistry.registerObject("statueobject", new statueobject(), 100, true);
        ObjectRegistry.registerObject("spikeobject", new spikeobject(), 0.0F, false);
        ObjectRegistry.registerObject("magestatueobject", new magestatueobject(), 100, true);
        ObjectRegistry.registerObject("soultikitorchobject", new soultikitorchobject(), 2.0F, true);
        ObjectRegistry.registerObject("argemiaplushieobject", new argemiaplushieobject(), 10.0F, true);
        ObjectRegistry.registerObject("fairplushieobject", new fairplushieobject(), 10.0F, true);
        ObjectRegistry.registerObject("v1plushieobject", new v1plushieobject(), 10.0F, true);

        //Furniture
        BathtubObject.registerBathtub("soulwoodbathtub", "soulwoodbathtub", ToolType.PICKAXE, SoulFurnitureColor, 1);
        BedObject.registerBed("soulwoodbed", "soulwoodbed", ToolType.PICKAXE, SoulFurnitureColor, 1);
        BenchObject.registerBench("soulwoodbench", "soulwoodbench", ToolType.PICKAXE, SoulFurnitureColor, 1);
        DinnerTableObject.registerDinnerTable("soulwooddinnertable", "soulwooddinnertable", ToolType.PICKAXE, SoulFurnitureColor, 1);
        ObjectRegistry.registerObject("soulwoodbookshelf", new BookshelfObject("soulwoodbookshelf", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodcabinet", new CabinetObject("soulwoodcabinet", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodchair", new ChairObject("soulwoodchair", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodchest", new StorageBoxInventoryObject("soulwoodchest",40, SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodclock", new ClockObject("soulwoodclock", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddesk", new DeskObject("soulwooddesk", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddisplay", new DisplayStandObject("soulwooddisplay",ToolType.PICKAXE, SoulFurnitureColor, 32), 10.0F, true);
        ObjectRegistry.registerObject("soulwooddresser", new DresserObject("soulwooddresser", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodmodulartable", new ModularTableObject("soulwoodmodulartable", SoulFurnitureColor), 10.0F, true);
        ObjectRegistry.registerObject("soulwoodtoilet", new ToiletObject("soulwoodtoilet", SoulFurnitureColor), 10.0F, true);
        CandelabraObject soulwoodCandelabra = new CandelabraObject("soulwoodcandelabra", SoulFurnitureColor, 60.0F, 0.3F);
        ObjectRegistry.registerObject("soulwoodcandelabra", soulwoodCandelabra, 10.0F, true);
        ObjectRegistry.registerObject("soulcavechest", new StorageBoxInventoryObject("soulcavechest",40, SoulStoneColor), 10.0F, true);
        //BIOMES_AND_LEVELS
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
        ItemRegistry.registerItem("soulwoodlogitem", new MatItem(250), 1, true);
        ItemRegistry.registerItem("soulcoreitem", new MatItem(250, Item.Rarity.UNCOMMON), 15, true);
        ItemRegistry.registerItem("souldragonscales", new MatItem(250, Item.Rarity.EPIC), 150, true);
        ItemRegistry.registerItem("soulcaverockitem", new MatItem(500, Item.Rarity.UNCOMMON), 1, true);
        ItemRegistry.registerItem("crystalizedsouloreitem", new MatItem(250, Item.Rarity.UNCOMMON), 15, true);
        ItemRegistry.registerItem("soulmetalbar", new MatItem(100, Item.Rarity.UNCOMMON), 80, true);

        ItemRegistry.registerItem("soulgrassseeditem", new GrassSeedItem("soulcavegrass"), 1, true);
        ItemRegistry.registerItem("wispitem", new MatItem(250, Item.Rarity.UNCOMMON, "glowingbugs"), 5, true);
        ItemRegistry.registerItem("fireflyitem", new MatItem(250, Item.Rarity.UNCOMMON, "glowingbugs"), 5, true);
        ItemRegistry.registerItem("fireflyjar", new ObjectItem(getObject("fireflyjarobject")), 10, true);
        ItemRegistry.registerItem("wispjar", new ObjectItem(getObject("wispjarobject")), 10, true);

        ItemRegistry.registerItem("soulsigil", new soulsigil(), 200, false);
        ItemRegistry.registerItem("soulessence", new EssenceMatItem(250, Item.Rarity.EPIC, 2), 25.0F, true);
        //TrinketsAndOtherStuff
        ItemRegistry.registerItem("carkeys", new carkeys(), 2000, true);
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
        ItemRegistry.registerItem("soularmorboots", new soularmorboots(), 750, true);
        ItemRegistry.registerItem("soularmorchestplate", new soularmorchestplate(), 750, true);
        ItemRegistry.registerItem("soularmorcrown", new soularmorcrown(), 750, true);
        ItemRegistry.registerItem("soularmorhelmet", new soularmorhelmet(), 750, true);
        ItemRegistry.registerItem("soularmorhood", new soularmorhood(), 750, true);
        ItemRegistry.registerItem("soularmorhat", new soularmorhat(),750, true);
        //SealTrinketsItems
        ItemRegistry.registerItem("meleesoulsealtrinket", new meleesoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("summonsoulsealtrinket", new summonsoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("magicsoulsealtrinket", new magicsoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("rangesoulsealtrinket", new rangesoulsealtrinket(), 200, true);
        ItemRegistry.registerItem("soulsealtrinket", new soulsealtrinket(), 1000, true);
        ItemRegistry.registerItem("balancedsealtrinket", new balancedsealtrinket(), 2000, true);
        //Other
        ItemRegistry.registerItem("lunartearflowerhead", new HelmetArmorItem(0, (DamageType)null, 0, Item.Rarity.RARE, "lunartearflowerhead").hairDrawMode(ArmorItem.HairDrawMode.OVER_HAIR), 50.0F, true);
        ItemRegistry.registerItem("tobeblindfold", new HelmetArmorItem(0, (DamageType)null, 0, Item.Rarity.EPIC, "tobeblindfold").hairDrawMode(ArmorItem.HairDrawMode.UNDER_HAIR), 250.0F, true);
        //Overrides
        ItemRegistry.replaceItem("net", new nettoolitem(), 40.0F, true);
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
        MobRegistry.registerMob("souldragonhead", souldragonhead.class, true);
        MobRegistry.registerMob("souldragonbody", souldragonbody.class, false);
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
        SoulCaveChestRoomSet = new ChestRoomSet("soulcavefloortile", "soulstonepressureplate", WallSet.loadByStringID("soulbrick"), "soulcavechest", "soulstoneflametrap");
        OneOfTicketLootItems oneOfItems = new OneOfTicketLootItems(new Object[]{50, LootItem.offset("bonearrow", 20, 15), 100, LootItem.between("greaterhealthpotion", 2, 8), 50, LootItem.between("greatermanapotion", 2, 8), 25, LootItem.offset("soultorch", 20, 3), 25, new LootItem("travelscroll")});
        OneOfTicketLootItems oneOfPotions = new OneOfTicketLootItems(new Object[]{50, IncursionCrateLootTable.greaterPotions, 25, IncursionCrateLootTable.potions});

        //SHRINE MONUMENT LOOT
        soulcavemonumentshrineloottable = new LootTable(new LootItemList(
                new OneOfLootItems(
                        new ChanceLootItem(0.8F,"phantomfeathertrinket"),
                        new ChanceLootItem(0.8F,"soulstealertrinket"),
                        new ChanceLootItem(0.8F,"pickaxeheadtrinket"),
                        new ChanceLootItem(0.2F,"soulmetalsword"),
                        new ChanceLootItem(0.05F, "carkeys")
                )
        ));

        //CAVE CHEST LOOT
        soulcavechestloottable = new LootTable(new LootItemList(
                new OneOfLootItems(
                        new LootItem("phantomfeathertrinket"),
                        new LootItem("soulstealertrinket"),
                        new LootItem("pickaxeheadtrinket")
                ),
                LootItem.between("crystalizedsouloreitem", 8, 22),
                oneOfItems,
                oneOfPotions,
                oneOfPotions,
                oneOfPotions,
                LootItem.offset("coin", 400, 100),
                new ChanceLootItem(0.05F, "carkeys")
        ));

        //CAVE RUINS LOOT
        soulcaveruinsloottable = new LootTable(new LootItemList(
                LootItem.between("crystalizedsouloreitem", 2, 6),
                oneOfItems,
                oneOfPotions,
                oneOfPotions,
                LootItem.offset("coin", 40, 10)
        ));

        //DRAGON_LOOT
        LootItemList dragon_loot_list = new LootItemList(
                LootItem.between("soulcoreitem", 10, 20),
                LootItem.between("souldragonscales", 15, 20),
                new ChanceLootItem(0.05F, "soulscythe"),
                new ChanceLootItem(0.08F, "carkeys")
        );
        souldragonhead.lootTable = new LootTable(dragon_loot_list);

        //---CRAFTING---//
        Recipes.registerModRecipe(new Recipe(
                "soulmetalbar",
                1,
                RecipeTechRegistry.FORGE,
                new Ingredient[]{
                        new Ingredient("crystalizedsouloreitem", 3)
                }
        ).showAfter("ancientfossilbar"));

        Recipes.registerModRecipe(new Recipe(
                "soulessence",
                2,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("bloodessence", 1)
                }
        ).showAfter("slimeessence"));

        //ARMOR_AND_WEAPONS
        Recipes.registerModRecipe(new Recipe(
                "soulmetalsword",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("nightsteelboots"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalspear",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 16),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("soulmetalsword"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalrevolver",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
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
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("soulmetalrevolver"));

        Recipes.registerModRecipe(new Recipe(
                "bookofsouls",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("shadowbolt", 1),
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("soulmetalbow"));

        Recipes.registerModRecipe(new Recipe(
                "soulstatue",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 6),
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulcoreitem", 12)
                }
        ).showAfter("bookofsouls"));

        Recipes.registerModRecipe(new Recipe(
                "soulabsorbshield",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 8),
                }
        ).showAfter("soulstatue"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhelmet",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 4)
                }
        ).showAfter("soulabsorbshield"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhood",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("soularmorhelmet"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhat",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("soularmorhood"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorcrown",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 8),
                        new Ingredient("soulcoreitem", 8),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("soularmorhat"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorchestplate",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 18),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 6)
                }
        ).showAfter("soularmorcrown"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorboots",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 3)
                }
        ).showAfter("soularmorchestplate"));

        Recipes.registerModRecipe(new Recipe(
                "soulsealtrinket",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("meleesoulsealtrinket", 1),
                        new Ingredient("rangesoulsealtrinket", 1),
                        new Ingredient("magicsoulsealtrinket", 1),
                        new Ingredient("summonsoulsealtrinket", 1),
                        new Ingredient("soulessence", 5),
                        new Ingredient("souldragonscales", 6),
                        new Ingredient("soulcoreitem", 10),
                }
        ).showAfter("soularmorboots"));

        Recipes.registerModRecipe(new Recipe(
                "balancedsealtrinket",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
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
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("zephyrboots", 1),
                        new Ingredient("soulessence", 4),
                        new Ingredient("souldragonscales", 4),
                        new Ingredient("soulcoreitem", 8),
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
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wispitem", 2),
                        new Ingredient("bigjarobject", 1)
                }
        ).showAfter("fireflyjar"));

        Recipes.registerModRecipe(new Recipe(
                "magestatueobject",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulcaverockitem", 25)
                }
        ).showAfter("soulcavetiledfloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soultorch",
                20,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("torch", 20),
                        new Ingredient("soulcoreitem", 1)
                }
        ).showAfter("torch"));

        Recipes.registerModRecipe(new Recipe(
                "soultikitorchobject",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soultorch", 1),
                        new Ingredient("soulwoodlogitem", 1)
                }
        ).showAfter("tikitorch"));

        Recipes.registerModRecipe(new Recipe(
                "soullantern",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 3),
                        new Ingredient("soultorch", 1)
                }
        ).showAfter("goldlamp"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodwall",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 2)}
        ).showAfter("deadwoodfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwooddoor",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 4)}
        ).showAfter("soulwoodwall"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodfloor",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 1)}

        ).showAfter("soulwooddoor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodpath",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulwoodlogitem", 1)}

        ).showAfter("soulwoodfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulwoodtiledfloor",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
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
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 15)}
        ).showAfter("soulbrickwall"));

        Recipes.registerModRecipe(new Recipe(
                "soulbrickwall",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 5)}
        ).showAfter("deepswampstonebrickfloor"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavefloortile",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulbrickdoor"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavebrickfloortile",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulcavefloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soulcavetiledfloortile",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("soulcavebrickfloortile"));

        Recipes.registerModRecipe(new Recipe(
                "soulstonepressureplate",
                1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{new Ingredient("soulcaverockitem", 1)}
        ).showAfter("deepswampstonepressureplate"));
    }
}

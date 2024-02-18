package soulchasm;

import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.item.Item;
import necesse.inventory.item.matItem.EssenceMatItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.tileItem.GrassSeedItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.*;
import necesse.level.gameObject.furniture.*;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.plains.PlainsBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.WallSet;
import soulchasm.main.Buffs.PhantomDashersBuffs.phantomdashersactivebuff;
import soulchasm.main.Buffs.PhantomDashersBuffs.phantomdashersbuff;
import soulchasm.main.Buffs.PickaxeheadBuffs.pickaxeheadbuff;
import soulchasm.main.Buffs.PickaxeheadBuffs.pickaxeheadstackbuff;
import soulchasm.main.Buffs.SealVariantsBuffs.*;
import soulchasm.main.Buffs.*;
import soulchasm.main.Items.Armor.SetBonusBuffs.*;
import soulchasm.main.Items.Armor.*;
import soulchasm.main.Items.Materials.*;
import soulchasm.main.Items.SealVariantsItems.*;
import soulchasm.main.Items.Tools.*;
import soulchasm.main.Items.Trinkets.phantomdasherstrinket;
import soulchasm.main.Items.Trinkets.phantomfeathertrinket;
import soulchasm.main.Items.Trinkets.pickaxeheadtrinket;
import soulchasm.main.Items.Trinkets.soulstealertrinket;
import soulchasm.main.Items.*;
import soulchasm.main.Misc.BiomeMisc.soulcavernbiome;
import soulchasm.main.Misc.Incursion.soulcavernincursionbiome;
import soulchasm.main.Misc.Incursion.soulcavernincursionlevel;
import soulchasm.main.Mobs.Agressive.lostsoul;
import soulchasm.main.Mobs.Agressive.possesedstatue;
import soulchasm.main.Mobs.Agressive.soulmage;
import soulchasm.main.Mobs.Agressive.soulpillar;
import soulchasm.main.Mobs.Boss.souldragonbody;
import soulchasm.main.Mobs.Boss.souldragonhead;
import soulchasm.main.Mobs.Passive.firefly;
import soulchasm.main.Mobs.Passive.soulcavecaveling;
import soulchasm.main.Mobs.Passive.wisp;
import soulchasm.main.Mobs.Summon.carmob;
import soulchasm.main.Mobs.Summon.smallsoulsummon;
import soulchasm.main.Objects.BiomeEnviroment.*;
import soulchasm.main.Objects.OtherObjects.*;
import soulchasm.main.Objects.WoodObjects.soulwoodfloor;
import soulchasm.main.Objects.WoodObjects.soulwoodpath;
import soulchasm.main.Objects.WoodObjects.soulwoodtiledfloor;
import soulchasm.main.Projectiles.*;

import java.awt.*;
@ModEntry
public class SoulChasm {
    public static Color SoulStoneColor = new Color(7,19,23,255);
    public static Color SoulMagmaStone = new Color(29, 80, 96,255);
    public static Color SoulStoneColorLight = new Color(28, 53, 61,255);
    public static Color SoulWoodColor = new Color(35, 120, 134,255);
    public static Color SoulCrystalColor = new Color(35, 200, 232,255);
    public static Color SoulFurnitureColor = new Color(99, 187, 213, 255);
    public static ChestRoomSet SoulCaveChestRoomSet;
    public static LootTable soulcavechestloottable;
    public static LootTable soulcaveruinsloottable;
    public static LootTable soulcavemonumentshrineloottable;
    public static GameTexture[] car_mask;
    public static GameTextureSection particleFlamethrowerSection;
    public static GameTextureSection particleFireflySection;
    public static GameTextureSection particleWispSection;
    public static GameTextureSection particlePhantomBodySection;

    public void init() {
        System.out.println("Hope you have a golden day ;)");
        //TILES
        TileRegistry.registerTile("soulcavegrass", new soulcavegrass(), 1, true);
        TileRegistry.registerTile("soulcaverocktile", new soulcaverocktile(), 1, true);
        TileRegistry.registerTile("soulwoodfloor", new soulwoodfloor(), 1, true);
        TileRegistry.registerTile("soulwoodpath", new soulwoodpath(), 1, true);
        TileRegistry.registerTile("soulwoodtiledfloor", new soulwoodtiledfloor(), 1, true);
        TileRegistry.registerTile("soulcavefloortile", new soulcavefloortile(), 1, true);
        TileRegistry.registerTile("soulcavebrickfloortile", new soulcavebrickfloortile(), 1, true);
        TileRegistry.registerTile("soulcavetiledfloortile", new soulcavetiledfloortile(), 1, true);
        TileRegistry.registerTile("asphalttile", new asphalttile(), 1, true);
        TileRegistry.registerTile("meltedsouls", new meltedsouls(), 0, true);
        TileRegistry.registerTile("soulcavecracktile", new soulcavecracktile(), 0, true);
        //OBJECTS
        RockObject soulcaverock;
        ObjectRegistry.registerObject("soulcaverock", soulcaverock = new RockObject("soulcaverock", SoulStoneColor, "soulcaverockitem", 1, 5), 0.0F, false);
        soulcaverock.toolTier = 5;
        SingleRockObject.registerSurfaceRock(soulcaverock, "soulcaverocks", SoulStoneColor);
        ObjectRegistry.registerObject("soulcaverockssmall", new SingleRockSmall(soulcaverock, "soulcaverockssmall", SoulStoneColor), 0.0F, false, false);
        ObjectRegistry.registerObject("soulstonepressureplate", new MaskedPressurePlateObject("pressureplatemask", "soulcavefloortile", SoulStoneColor), 15.0F, true);
        WallObject.registerWallObjects("soulwood", "soulwoodtexture", 0, SoulWoodColor, 0.5F, 1);
        WallObject.registerWallObjects("soulbrick", "soulbricktexture", 5, SoulStoneColor, 0.5F, 1);
        ObjectRegistry.registerObject("soulstoneflametrap", new WallFlameTrapObject((WallObject)ObjectRegistry.getObject("soulbrickwall")), 50.0F, true);
        ObjectRegistry.registerObject("crystalizedsoul", new RockOreObject((RockObject)ObjectRegistry.getObject("soulcaverock"), "oremask", "crystalizedsoulore", SoulStoneColor, "crystalizedsouloreitem"), 0.0F, false);
        ObjectRegistry.registerObject("alchemyshardsoulcaverock", new RockOreObject((RockObject)ObjectRegistry.getObject("soulcaverock"), "oremask", "alchemyshardore", new Color(102, 0, 61), "alchemyshard", 1, 1, false), 0.0F, false);
        ObjectRegistry.registerObject("soultree", new soultree(), 0.0F, false);
        ObjectRegistry.registerObject("soultreesappling", new soultreesappling(), 1, true);
        ObjectRegistry.registerObject("lunartear", new lunartear(), 1, true);
        ObjectRegistry.registerObject("lunartearspath", new lunartearspath(), 1, true);
        ObjectRegistry.registerObject("soultorch", new soultorch(), 1, true);
        ObjectRegistry.registerObject("soulcavegrassobject", new soulcavegrassobject(), 0.0F, false);
        ObjectRegistry.registerObject("soulcrystalbig", new soulcrystalbig(), 0.0F, false);
        ObjectRegistry.registerObject("wispjarobject", new wispjarobject(), 1, false);
        ObjectRegistry.registerObject("fireflyjarobject", new fireflyjarobject(), 1, false);
        ObjectRegistry.registerObject("soulmonumentobject", new soulmonumentobject(), 100, true);
        ObjectRegistry.registerObject("soullanternobject", new soullanternobject(), 10, false);
        ObjectRegistry.registerObject("oldbarrel", new InventoryObject("oldbarrel", 20, new Rectangle(8, 4, 16, 16), ToolType.PICKAXE, SoulFurnitureColor), 10, true);
        ObjectRegistry.registerObject("bigjarobject", new bigjarobject(), 10, true);
        ObjectRegistry.registerObject("chasmcrates", new RandomCrateObject("chasmcrates"), 0.0F, false);
        ObjectRegistry.registerObject("statueobject", new statueobject(), 100, true);
        ObjectRegistry.registerObject("soulbossaltarobject", new soulbossaltarobject(), 0.0F, false);
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
        int soulwoodFenceID = ObjectRegistry.registerObject("soulwoodfence", new FenceObject("soulwoodfence", SoulFurnitureColor, 12, 10), 2.0F, true);
        FenceGateObject.registerGatePair(soulwoodFenceID, "soulwoodfencegate", "soulwoodfencegate", SoulFurnitureColor, 12, 10, 4.0F);
        ObjectRegistry.registerObject("soulcavechest", new StorageBoxInventoryObject("soulcavechest",40, SoulStoneColor), 10.0F, true);
        //BIOMES
        BiomeRegistry.registerBiome("soulcavernbiome", new soulcavernbiome(), 0, false, "soulcavernbiome");
        IncursionBiomeRegistry.registerBiome("soulcavernincursionbiome", new soulcavernincursionbiome());
        LevelRegistry.registerLevel("soulcavernincursionlevel", soulcavernincursionlevel.class);
        //BUFFS
        BuffRegistry.registerBuff("soulstealerbuff", new soulstealerbuff());
        BuffRegistry.registerBuff("phantomfeatherbuff", new phantomfeatherbuff());
        BuffRegistry.registerBuff("soulfirebuff", new soulfirebuff());
        BuffRegistry.registerBuff("soulabsorbshieldbuff", new soulabsorbshieldbuff());
        BuffRegistry.registerBuff("soulsealfollowerbuff", new soulsealfollowerbuff());
        BuffRegistry.registerBuff("soulbleedstackbuff", new soulbleedstackbuff());
        BuffRegistry.registerBuff("soulscythebuff", new soulscythebuff());
        BuffRegistry.registerBuff("souldeathmarkstackbuff", new souldeathmarkstackbuff());
        BuffRegistry.registerBuff("soulbowbuff", new soulbowbuff());
        BuffRegistry.registerBuff("pickaxeheadbuff", new pickaxeheadbuff());
        BuffRegistry.registerBuff("pickaxeheadstackbuff", new pickaxeheadstackbuff());
        BuffRegistry.registerBuff("phantomdashersactivebuff", new phantomdashersactivebuff());
        BuffRegistry.registerBuff("phantomdashersbuff", new phantomdashersbuff());
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
        ItemRegistry.registerItem("soulwoodlogitem", new MatItem(999), 1, true);
        ItemRegistry.registerItem("soulcoreitem", new soulcoreitem(), 15, true);
        ItemRegistry.registerItem("souldragonscales", new souldragonscales(), 150, true);
        ItemRegistry.registerItem("soulcaverockitem", new soulcaverockitem(), 1, true);
        ItemRegistry.registerItem("crystalizedsouloreitem", new crystalizedsouloreitem(), 15, true);
        ItemRegistry.registerItem("soulgrassseeditem", new GrassSeedItem("soulcavegrass"), 1, true);
        ItemRegistry.registerItem("wispitem", new wispitem(), 1, true);
        ItemRegistry.registerItem("wispjar", new wispjar(), 10, true);
        ItemRegistry.registerItem("fireflyitem", new fireflyitem(), 1, true);
        ItemRegistry.registerItem("fireflyjar", new fireflyjar(), 10, true);
        ItemRegistry.registerItem("soullantern", new soullantern(), 20, true);
        ItemRegistry.registerItem("soulmetalbar", new soulmetalbar(), 80, true);
        ItemRegistry.registerItem("soulsigil", new soulsigil(), 200, true);
        ItemRegistry.registerItem("soulessence", new EssenceMatItem(120, Item.Rarity.EPIC, 2), 25.0F, true);
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
        //MOBS
        MobRegistry.registerMob("lostsoul", lostsoul.class, true);
        MobRegistry.registerMob("carmob", carmob.class, false);
        MobRegistry.registerMob("possesedstatue", possesedstatue.class, true);
        MobRegistry.registerMob("wisp", wisp.class, false);
        MobRegistry.registerMob("firefly", firefly.class, false);
        MobRegistry.registerMob("soulpillar", soulpillar.class, true);
        MobRegistry.registerMob("soulmage", soulmage.class, true);
        MobRegistry.registerMob("smallsoulsummon", smallsoulsummon.class, false);
        MobRegistry.registerMob("soulcavecaveling", soulcavecaveling.class, true);
        MobRegistry.registerMob("souldragonhead", souldragonhead.class, true);
        MobRegistry.registerMob("souldragonbody", souldragonbody.class, false);
        //PROJECTILES
        ProjectileRegistry.registerProjectile("soulwaveprojectile", soulwaveprojectile.class, "soulwaveprojectile", "shadow_simple");
        ProjectileRegistry.registerProjectile("soularrowprojectile", soularrowprojectile.class, "soularrowprojectile", "shadow_simple");
        ProjectileRegistry.registerProjectile("souldragonfragmentprojectile", souldragonfragmentprojectile.class, "souldragonfragmentprojectile","shadow_simple");
        ProjectileRegistry.registerProjectile("soulhomingprojectile", soulhomingprojectile.class, "soulhomingprojectile","shadow_simple");
        ProjectileRegistry.registerProjectile("soulmissileprojectile", soulmissileprojectile.class, "soulmissileprojectile","shadow_simple");
        ProjectileRegistry.registerProjectile("soulflamethrower", soulflamethrower.class, "shadow_simple","shadow_simple");
        ProjectileRegistry.registerProjectile("soulspearprojectile", soulspearprojectile.class, "soulspearprojectile","shadow_simple");
        ProjectileRegistry.registerProjectile("spiritswordprojectile", spiritswordprojectile.class, "spiritswordprojectile","shadow_blank");
        ProjectileRegistry.registerProjectile("soulrevolverprojectile", soulrevolverprojectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulboomerangprojectile", soulboomerangprojectile.class, "soulboomerangprojectile", "shadow_simple");
        ProjectileRegistry.registerProjectile("soulpointywaveprojectile", soulpointywaveprojectile.class, "soulpointywaveprojectile", "shadow_simple");
        ProjectileRegistry.registerProjectile("soulbigbulletprojectile", soulbigbulletprojectile.class, null, null);
        ProjectileRegistry.registerProjectile("soulbossspikeprojectile", soulbossspikeprojectile.class, "soulbossspikeprojectile","shadow_simple");
    }
    public void initResources(){
        lostsoul.texture = GameTexture.fromFile("mobs/lostsoul");
        carmob.texture =  GameTexture.fromFile("mobs/car");
        carmob.texture_top = GameTexture.fromFile("mobs/car_top_mask");
        possesedstatue.texture = GameTexture.fromFile("mobs/possesedstatue");
        possesedstatue.glowtexture = GameTexture.fromFile("mobs/possesedstatue_glow");
        wisp.texture = GameTexture.fromFile("mobs/wisp");
        firefly.texture = GameTexture.fromFile("mobs/firefly");
        soulpillar.texture = GameTexture.fromFile("mobs/soulpillar");
        soulpillar.glow = GameTexture.fromFile("mobs/soulpillar_glow");
        soulmage.texture = GameTexture.fromFile("mobs/soulmage");
        smallsoulsummon.texture = GameTexture.fromFile("mobs/smallsoul");
        soulcavecaveling.texture = GameTexture.fromFile("mobs/soulcavecaveling");
        soulcavecaveling.texture_back = GameTexture.fromFile("mobs/soulcavecaveling_back");
        soulcavecaveling.texture_front = GameTexture.fromFile("mobs/soulcavecaveling_front");
        souldragonhead.texture = GameTexture.fromFile("mobs/souldragon");
        souldragonbody.texture = GameTexture.fromFile("mobs/souldragon");

        //TextureSections
        GameTexture flamethrowerParticleTexture = GameTexture.fromFile("particles/soulfiresparks");
        particleFlamethrowerSection = GameResources.particlesTextureGenerator.addTexture(flamethrowerParticleTexture);

        GameTexture fireflyParticleTexture = GameTexture.fromFile("particles/fireflyparticle");
        particleFireflySection = GameResources.particlesTextureGenerator.addTexture(fireflyParticleTexture);

        GameTexture wispParticleTexture = GameTexture.fromFile("particles/wispparticle");
        particleWispSection = GameResources.particlesTextureGenerator.addTexture(wispParticleTexture);

        GameTexture phantomBodyParticleTexture = GameTexture.fromFile("particles/phantombody");
        particlePhantomBodySection = GameResources.particlesTextureGenerator.addTexture(phantomBodyParticleTexture);

        GameTexture car_mask_sprites = GameTexture.fromFile("mobs/car_mask");
        int carSprites = car_mask_sprites.getHeight() / 64;
        car_mask = new GameTexture[carSprites];

        for(int i = 0; i < carSprites; ++i) {
            car_mask[i] = new GameTexture(car_mask_sprites, 0, i, 64);
        }

    }
    public void postInit() {
        ForestBiome.defaultSurfaceCritters.add(80, "firefly");
        PlainsBiome.defaultSurfaceCritters.add(80, "firefly");
        SwampBiome.surfaceCritters.add(100, "firefly");
        SoulCaveChestRoomSet = new ChestRoomSet("soulcavefloortile", "soulstonepressureplate", WallSet.loadByStringID("soulbrick"), "soulcavechest", "soulstoneflametrap");

        //SHRINE MONUMENT LOOT
        soulcavemonumentshrineloottable = new LootTable(new LootItemList(
                new OneOfLootItems(
                        new ChanceLootItem(0.8F,"soulsigil"),
                        new ChanceLootItem(0.5F,"phantomfeathertrinket"),
                        new ChanceLootItem(0.5F,"soulstealertrinket"),
                        new ChanceLootItem(0.5F,"pickaxeheadtrinket"),
                        new ChanceLootItem(0.05F,"soularmorhelmet"),
                        new ChanceLootItem(0.05F,"soularmorcrown"),
                        new ChanceLootItem(0.2F,"soulmetalsword")
                )
        ));

        //CAVE CHEST LOOT
        soulcavechestloottable = new LootTable(new LootItemList(
                LootItem.between("crystalizedsouloreitem", 6, 12),
                LootItem.between("greaterhealthpotion", 2, 6),
                LootItem.between("dynamitestick", 0, 8),
                LootItem.between("coin", 120, 80),
                new OneOfLootItems(
                        new LootItem("phantomfeathertrinket"),
                        new LootItem("soulstealertrinket"),
                        new LootItem("pickaxeheadtrinket")
                ),
                new ChanceLootItem(0.05F, "carkeys")
        ));

        //CAVE RUINS LOOT
        soulcaveruinsloottable = new LootTable(new LootItem[]{
                LootItem.between("crystalizedsouloreitem", 2, 6),
                LootItem.between("greaterhealthpotion", 0, 3),
                LootItem.between("dynamitestick", 0, 2),
                LootItem.between("coin", 5, 100)
        });

        //DRAGON_LOOT
        LootItemList dragon_loot_list = new LootItemList(
                LootItem.between("soulcoreitem", 10, 20),
                LootItem.between("souldragonscales", 15, 20),
                new ChanceLootItem(0.06F, "soulscythe")

        );
        souldragonhead.lootTable = new LootTable(dragon_loot_list);

        //SOUL_MAGE_LOOT
        soulmage.lootTable.items.add(new ChanceLootItem(0.01F, "soularmorhat"));

        //STATUE_LOOT
        LootItemList statue_loot_list = new LootItemList(
                new ChanceLootItem(0.01F,"soularmorboots"),
                new ChanceLootItem(0.01F,"soularmorchestplate"),
                new ChanceLootItem(0.01F,"soularmorhelmet")
        );
        possesedstatue.lootTable = new LootTable(statue_loot_list);

        //---CRAFTING---//
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
        ));

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
        ));

        Recipes.registerModRecipe(new Recipe(
                "asphalttile",
                50,
                RecipeTechRegistry.ALCHEMY,
                new Ingredient[]{
                        new Ingredient("anystone", 50),
                        new Ingredient("watertile", 1),
                        new Ingredient("speedpotion", 1),
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
                "soultorch",
                20,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("torch", 20),
                        new Ingredient("soulcoreitem", 1)
                }
        ).showAfter("torch"));

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

        Recipes.registerModRecipe(new Recipe(
                "soulsigil",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 4)
                }
        ).showAfter("slimeeggs"));

        //ARMOR

        Recipes.registerModRecipe(new Recipe(
                "soularmorboots",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 3)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorchestplate",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 18),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 6)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorcrown",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 8),
                        new Ingredient("soulcoreitem", 8),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhat",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhood",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 4),
                        new Ingredient("soulcoreitem", 12),
                        new Ingredient("souldragonscales", 2)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soularmorhelmet",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulmetalbar", 10),
                        new Ingredient("soulcoreitem", 4),
                        new Ingredient("souldragonscales", 4)
                }
        ).showAfter("slimeboots"));

        //Weapons

        Recipes.registerModRecipe(new Recipe(
                "soulabsorbshield",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 8),
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalbow",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("slimeboots"));

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
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalspear",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 16),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("slimeboots"));

        Recipes.registerModRecipe(new Recipe(
                "soulmetalsword",
                1,
                RecipeTechRegistry.FALLEN_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("soulessence", 5),
                        new Ingredient("soulmetalbar", 12),
                        new Ingredient("soulcoreitem", 5)
                }
        ).showAfter("slimeboots"));


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

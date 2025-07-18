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
import necesse.level.gameObject.furniture.*;
import necesse.level.gameObject.furniture.doubleBed.DoubleBedBaseObject;
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
import soulchasm.main.Misc.CarColorInterface.CarColorContainer;
import soulchasm.main.Misc.CarColorInterface.CarColorContainerForm;
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
import soulchasm.main.Mobs.Passive.ChasmCaveling;
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
import java.util.ArrayList;

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
    public static Color asphaltTileMapColor = new Color(23, 23, 23);
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
        GameLoadingScreen.drawLoadingSub("Hopefully my wonderful and carefully crafted masterpiece of a code won't crash your game ^-^ \n (that was sarcasm if it wasn't clear)");

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

        ObjectRegistry.registerObject("soultree", new TreeObject("soultree", "soulwoodlogitem", "soultreesapling", chasmWoodMapColor, 60,80,100, "soultreeleaves"), 0.0F, false);
        ObjectRegistry.registerObject("soultreesapling", new TreeSaplingObject("soultreesapling", "soultree", 1800, 2700, false, "soulcavegrass"), 5.0F, true);

        ObjectRegistry.registerObject("lunartear", new LunarTearObject(), 5.0F, true);
        FlowerPatchObject lunarpath = new FlowerPatchObject("lunartearspath", lunarTearMapColor);
        lunarpath.lightLevel = 30;
        lunarpath.lightHue = 240.0F;
        lunarpath.lightSat = 0.05F;
        ObjectRegistry.registerObject("lunartearspath", lunarpath, 5.0F, true);

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
        ObjectRegistry.registerObject("statueobject", new ChasmStatueObject(), 50.0F, true);
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
        DoubleBedBaseObject.registerDoubleBed("soulwooddoublebed", "soulwooddoublebed", chasmWoodFurnitureMapColor, -1.0F);
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
        MobRegistry.registerMob("chasmcaveling", ChasmCaveling.class, true);

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
        LootTable helmetReward = new LootTable(new LootItemList(new OneOfLootItems(new LootItem("soularmorhelmet"), new LootItem("soularmorhood"), new LootItem("soularmorhat"), new LootItem("soularmorcrown"))));
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

        //INCURSION
        BiomeRegistry.registerBiome("soulcavern", new SoulChasmBiome(), 0, null);
        IncursionBiomeRegistry.registerBiome("soulchasm", new SoulChasmIncursionBiome(), 2);
        LevelRegistry.registerLevel("soulchasmincursion", SoulChasmIncursionLevel.class);

        //INCURSION_MODS
        UniqueIncursionModifierRegistry.registerUniqueModifier("haunted", new HauntedIncursionModifier(UniqueIncursionModifierRegistry.ModifierChallengeLevel.Medium));

        //JOURNAL
        JournalEntry soulchasmIncursion = JournalRegistry.registerJournalEntry("soulchasmincursion", new JournalEntry(IncursionBiomeRegistry.getBiome("soulchasm")));
        soulchasmIncursion.addBiomeLootEntry("crystalizedsouloreitem", "alchemyshard", "upgradeshard", "soulcaverockitem", "soulwoodlogitem");
        soulchasmIncursion.addMobEntries("lostsoul", "soulmage", "magestatue", "meleestatue", "souldragonhead");

        //CONTAINERS
        CAR_COLOR_CONTAINER = ContainerRegistry.registerContainer((client, uniqueSeed, content) -> new CarColorContainerForm(client, new CarColorContainer(client.getClient(), uniqueSeed, content)), (client, uniqueSeed, content, serverObject) -> new CarColorContainer(client, uniqueSeed, content));

        //PLUSHIE
        registerPlushie("v1", V1Plushie.class, true);
        registerPlushie("fair", FairPlushie.class, false);
        registerPlushie("argemia", ArgemiaPlushie.class, true);
        registerPlushie("fumo", FumoPlushie.class, false);
        registerPlushie("dev", DevPlushie.class, true);

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

    public void initResources(){
        V1Plushie.gameTexture = GameTexture.fromFile("mobs/v1plushie");
        FairPlushie.gameTexture = GameTexture.fromFile("mobs/fairplushie");
        ArgemiaPlushie.gameTexture = GameTexture.fromFile("mobs/argemiaplushie");
        FumoPlushie.gameTexture = GameTexture.fromFile("mobs/fumoplushie");
        DevPlushie.gameTexture = GameTexture.fromFile("mobs/devplushie");

        LostSoul.texture = GameTexture.fromFile("mobs/lostsoul");

        CarMob.texture_body =  GameTexture.fromFile("mobs/car_body");
        CarMob.texture_top =  GameTexture.fromFile("mobs/car_top");
        CarMob.texture_mask = GameTexture.fromFile("mobs/car_top_mask");

        MeleeStatue.texture = GameTexture.fromFile("mobs/meleestatue");
        Wisp.texture = GameTexture.fromFile("mobs/wisp");
        Firefly.texture = GameTexture.fromFile("mobs/firefly");
        MageStatue.texture = GameTexture.fromFile("mobs/magestatue");
        GameTexture soulMageTexture = GameTexture.fromFile("mobs/soulmage");
        SoulMage.texture = new HumanTexture(soulMageTexture,soulMageTexture,soulMageTexture);
        SmallSoulSummon.texture = GameTexture.fromFile("mobs/smallsoul");
        SoulDragon.texture = GameTexture.fromFile("mobs/souldragon");
        SoulDragonBody.texture = GameTexture.fromFile("mobs/souldragon");
        SphereEffectMob.texture_ball = GameTexture.fromFile("particles/altarball");
        MeleeGhost.texture = GameTexture.fromFile("mobs/lostsoul");
        SoulStatueSummon.texture = GameTexture.fromFile("items/magestatueobject");
        SoulStatueSummon.texture_ring = GameTexture.fromFile("particles/soulstatuering");
        chasmCaveling = new HumanTexture(GameTexture.fromFile("mobs/chasmcaveling"), GameTexture.fromFile("mobs/chasmcavelingarms_front"), GameTexture.fromFile("mobs/chasmcavelingarms_back"));

        //TextureSections
        eruptionShadow = GameTexture.fromFile("particles/dragongrounderuption_shadow");
        GameTexture spinSpawnVisualTexture = GameTexture.fromFile("particles/spinspawnvisual");
        spinSpawnVisual = GameResources.particlesTextureGenerator.addTexture(spinSpawnVisualTexture);

        GameTexture meleeGhostSpawnParticle = GameTexture.fromFile("particles/meleeghostspawnparticle");
        particleGhostSpawnSection = GameResources.particlesTextureGenerator.addTexture(meleeGhostSpawnParticle);

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
        carMask = new GameTexture[carSprites];
        for(int i = 0; i < carSprites; ++i) {
            carMask[i] = new GameTexture(car_mask_sprites, 0, i, 64);
        }
        carColorContainerIcon = GameTexture.fromFile("ui/paint_bucket");

        plushieSqueak = GameSound.fromFile("plushie_squeak");
    }

    public void registerModRecipes(ArrayList<Recipe> recipes){
        for (Recipe recipe : recipes) {
            Recipes.registerModRecipe(recipe);
        }
    }

    public void postInit() {
        ForestBiome.defaultSurfaceCritters.add(80, "firefly");
        PlainsBiome.defaultSurfaceCritters.add(80, "firefly");
        SwampBiome.surfaceCritters.add(100, "firefly");

        chasmChestRoomSet = new ChestRoomSet("soulcavefloortile", "soulstonepressureplate", WallSet.loadByStringID("soulbrick"), "soulcavechest", "soulstoneflametrap");
        chasmShrineLootTable = new LootTable(new LootItemList(new OneOfLootItems(new ChanceLootItem(0.8F,"phantomfeathertrinket"), new ChanceLootItem(0.8F,"soulstealertrinket"), new ChanceLootItem(0.8F,"pickaxeheadtrinket"), new ChanceLootItem(0.2F,"soulmetalsword"), new ChanceLootItem(0.05F, "carkeys"))));

        ArrayList<Recipe> modRecipes = new ArrayList<>();

        modRecipes.add(new Recipe("soulmetalbar", 1, RecipeTechRegistry.FORGE, Recipes.ingredientsFromScript("{{crystalizedsouloreitem, 4}}")));
        modRecipes.add(new Recipe("soulessence", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{anytier2essence, 2}}")));

        modRecipes.add(new Recipe("soulmetalsword", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcoreitem, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalspear", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 16}, {soulcoreitem, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalrevolver", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {handgun, 1}, {soulmetalbar, 12}, {soulcoreitem, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulmetalbow", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcoreitem, 5}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("bookofsouls", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {book, 3}, {soulmetalbar, 4}, {soulcoreitem, 12}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soulstatue", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 6}, {soulmetalbar, 10}, {soulcoreitem, 12}}")));

        modRecipes.add(new Recipe("soularmorhelmet", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcoreitem, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorhood", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcoreitem, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorhat", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcoreitem, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorcrown", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 12}, {soulcoreitem, 20}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorchestplate", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 16}, {soulcoreitem, 30}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));
        modRecipes.add(new Recipe("soularmorboots", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulmetalbar, 8}, {soulcoreitem, 15}}"), false, (new GNDItemMap()).setInt("upgradeLevel", 100)));

        modRecipes.add(new Recipe("soulabsorbshield", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulessence, 5}, {soulmetalbar, 12}, {soulcoreitem, 8}}")));
        modRecipes.add(new Recipe("soulsealtrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{meleesoulsealtrinket, 1}, {rangesoulsealtrinket, 1}, {magicsoulsealtrinket, 1}, {summonsoulsealtrinket, 1}, {soulessence, 5}, {soulcoreitem, 20}}")));
        modRecipes.add(new Recipe("balancedsealtrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{soulsealtrinket, 1}, {balancedfoci, 1}, {soulessence, 5}, {soulmetalbar, 5}, {soulcoreitem, 20}}")));
        modRecipes.add(new Recipe("phantomdasherstrinket", 1, RecipeTechRegistry.FALLEN_ANVIL, Recipes.ingredientsFromScript("{{zephyrboots, 1}, {soulessence, 4}, {soulcoreitem, 8}}")));

        modRecipes.add(new Recipe("asphalttile", 100, RecipeTechRegistry.ALCHEMY, Recipes.ingredientsFromScript("{{anystone, 100}, {speedpotion, 2}}")));
        modRecipes.add(new Recipe("bigjarobject", 1, RecipeTechRegistry.FORGE, Recipes.ingredientsFromScript("{{sandstonetile, 5}}")));
        modRecipes.add(new Recipe("fireflyjar", 1, RecipeTechRegistry.WORKSTATION, Recipes.ingredientsFromScript("{{fireflyitem, 3}, {bigjarobject, 1}}")));
        modRecipes.add(new Recipe("wispjar", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{wispitem, 2}, {bigjarobject, 1}}")));

        modRecipes.add(new Recipe("soultorch", 8, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcoreitem, 1}, {soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soultikitorchobject", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soultorch, 1}, {soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soullantern", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulmetalbar, 3}, {soultorch, 1}}")));

        modRecipes.add(new Recipe("soulwoodwall", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 2}}")));
        modRecipes.add(new Recipe("soulwooddoor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 4}}")));
        modRecipes.add(new Recipe("soulwoodfloor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soulwoodpath", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 1}}")));
        modRecipes.add(new Recipe("soulwoodtiledfloor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulwoodlogitem, 2}}")));

        modRecipes.add(new Recipe("soulbrickdoor", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 15}}")));
        modRecipes.add(new Recipe("soulbrickwall", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 5}}")));
        modRecipes.add(new Recipe("soulcavefloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 1}}")));
        modRecipes.add(new Recipe("soulcavebrickfloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 1}}")));
        modRecipes.add(new Recipe("soulcavetiledfloortile", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 1}}")));
        modRecipes.add(new Recipe("soulstonepressureplate", 1, RecipeTechRegistry.FALLEN_WORKSTATION, Recipes.ingredientsFromScript("{{soulcaverockitem, 1}}")));

        modRecipes.add(new Recipe("soulcaverock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 4}}")));
        modRecipes.add(new Recipe("crystalizedsoul", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 4}, {crystalizedsouloreitem, 1}}")));
        modRecipes.add(new Recipe("upgradeshardsoulcaverock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 4}, {upgradeshard, 1}}")));
        modRecipes.add(new Recipe("alchemyshardsoulcaverock", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 4}, {alchemyshard, 1}}")));
        modRecipes.add(new Recipe("soulcaverocksmall", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 20}}")));
        modRecipes.add(new Recipe("soulcaverocks", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 30}}")));
        modRecipes.add(new Recipe("magestatueobject", 1, RecipeTechRegistry.LANDSCAPING, Recipes.ingredientsFromScript("{{soulcaverockitem, 50}}")));

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

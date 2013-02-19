package colonies.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.Configuration;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import colonies.eragon.src.GuiHandler;
import colonies.kzolp67.src.ColoniesTab;
import colonies.pmardle.src.ItemAncientTome;
import colonies.src.buildings.BlockAlchemistShop;
import colonies.src.buildings.BlockColoniesChest;
import colonies.src.buildings.BlockFishermanHut;
import colonies.src.buildings.BlockHouse;
import colonies.src.buildings.BlockHunterBlind;
import colonies.src.buildings.BlockLoggingCamp;
import colonies.src.buildings.BlockMine;
import colonies.src.buildings.BlockTownHall;
import colonies.src.buildings.TileEntityAlchemistShop;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityFishermanHut;
import colonies.src.buildings.TileEntityHouse;
import colonies.src.buildings.TileEntityHunterBlind;
import colonies.src.buildings.TileEntityLoggingCamp;
import colonies.src.buildings.TileEntityMine;
import colonies.src.buildings.TileEntityTownHall;
import colonies.src.citizens.EntityAlchemist;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityFisherman;
import colonies.src.citizens.EntityHunter;
import colonies.src.citizens.EntityLumberjack;
import colonies.src.citizens.EntityMiner;
import colonies.src.citizens.EntityPriestess;
import colonies.src.citizens.EntityWife;
import colonies.anglewyrm.src.GuiHandlerColoniesChest;
import colonies.anglewyrm.src.PacketHandler;
import colonies.boycat97.src.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Colonies", name = "Colonies", version = "15 Feb 2012")
@NetworkMod(
		channels = { "Colonies" },
		clientSideRequired = true,
		serverSideRequired = false,
		packetHandler = PacketHandler.class )

public class ColoniesMain 
{
	public static Block test; 	
	public static Item MeasuringTape;
	public static Block chestBlock;
	public static Block townHall;
	public static Block minerChest;
	public static Block loggingCamp;
	public static Block house;
	public static Block hunterBlind;
	public static Block fishermanHut;
	public static Block alchemistShop;
	public static Block guardHouse;
	public static Item ancientTome;
	
	//public static GuiHandler guiHandlerChest;
	public static GuiHandlerColoniesChest guiHandlerChest;
	//public static List<TileEntityTownHall> townsList;
	
	public static CreativeTabs coloniesTab = new ColoniesTab("coloniesTab");


	@Instance
	public static ColoniesMain instance;

	@SidedProxy(clientSide = "colonies.src.ClientProxy", serverSide = "colonies.src.ServerProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Initializing Colonies " + Version()); 
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		setConfig(config);
		config.save();
		MinecraftForge.EVENT_BUS.register(new ColoniesSoundManager());
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{	
		registerColoniesStuff(); // at bottom of this file for legibility
		 guiHandlerChest = new GuiHandlerColoniesChest();
		// guiHandlerChest = new GuiHandler();
		 NetworkRegistry.instance().registerGuiHandler(this, guiHandlerChest);
		proxy.registerRenderInformation(); 
		Recipes.registerRecipes();
		
		ColoniesAchievements.addAchievementLocalizations();
		AchievementPage.registerAchievementPage(ColoniesAchievements.page1);
		GameRegistry.registerCraftingHandler(new ColoniesAchievements());
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		// TODO: Add Post-Initialization code such as mod hooks
		//NetworkRegistry.instance().registerGuiHandler(this, guihBusiness);
	}

	public String Version(){
		return "PreAlpha, Revision 13";
	}

	// Configuration Settings
	// Appear here as public statics, and are set below in setConfig()
	public static int testBlockID;
	public static int measuringTapeID;
	public static int defaultChestID;
	public static int townHallID;
	public static int minerChestID;
	public static int loggingCampID;
	public static int blockHouseID;
	public static int hunterBlindID;
	public static int fishermanHutID;
	public static int alchemistShopID;
	public static int guardHouseID;
	public static int ancientTomeID;
	
	public static boolean offensiveLanguageFilter;
	public static boolean citizenGreetings;
	public static float   citizenMoveSpeed;
	
	public static String skinDefault;
	public static String skinMaleSwimming;
	public static String skinMiner;
	public static String skinMinerSwimming;
	public static String skinWife;
	public static String skinFemaleSwimming;
	public static String skinPriestess;
	public static String skinPriestessSwimming;
	public static String skinLumberjack;
	public static String skinHunter;
	public static String skinFisherman;
	public static String skinAlchemist;
	public static String skinGuard;
	public static String skinArcher;
	public static String skinSergeant;
	
	public static String guiChestBackground;
	
	private void setConfig(Configuration config)
	{
		testBlockID     = config.getBlock("testBlockID",     1100).getInt();
		measuringTapeID = config.getBlock("measuringTapeID", 1101).getInt();
		defaultChestID  = config.getBlock("defaultChestID",  1102).getInt();
		townHallID      = config.getBlock("townHallID",      1103).getInt();
		minerChestID    = config.getBlock("minerChestID",    1104).getInt();
		loggingCampID   = config.getBlock("loggingCampID",   1105).getInt();
		blockHouseID    = config.getBlock("blockHouseID",    1106).getInt();
		hunterBlindID   = config.getBlock("hunterBlindID",   1107).getInt();
		fishermanHutID  = config.getBlock("fishermanHutID",  1108).getInt();
		alchemistShopID = config.getBlock("alchemistShopID", 1109).getInt();
		guardHouseID	= config.getBlock("guardHouseID", 	 1110).getInt();
		ancientTomeID	= config.getBlock("ancientTomeID", 	 1111).getInt();
		
		offensiveLanguageFilter = config.get(Configuration.CATEGORY_GENERAL, "offensiveLanguageFilter", false).getBoolean(false);
		citizenGreetings = config.get(Configuration.CATEGORY_GENERAL, "citizenGreetings", true).getBoolean(true);
		citizenMoveSpeed = Float.parseFloat(config.get(Configuration.CATEGORY_GENERAL, "citizenMoveSpeed", "0.25f").value);
		
		skinDefault           = config.get("Skins", "skinDefault",           "/colonies/grahammarcellus/gfx/unemployedskin1.png").value;
		skinMaleSwimming      = config.get("Skins", "skinMaleSwimming",      "/colonies/anglewyrm/gfx/m-swimskin.png").value;
		skinMiner             = config.get("Skins", "skinMiner",             "/colonies/irontaxi/gfx/minerSkin1.png").value;
		skinMinerSwimming     = config.get("Skins", "skinMinerSwimming",     "/colonies/anglewyrm/gfx/miner_swim.png").value;
		skinWife              = config.get("Skins", "skinWife",              "/colonies/austensible/gfx/wife2.png").value;
		skinFemaleSwimming    = config.get("Skins", "skinFemaleSwimming",    "/colonies/anglewyrm/gfx/white_bikini.png").value;
		skinPriestess         = config.get("Skins", "skinPriestess",         "/colonies/anglewyrm/gfx/priestess.png").value;
		skinPriestessSwimming = config.get("Skins", "skinPriestessSwimming", "/colonies/anglewyrm/gfx/priestess_swimsuit.png").value;
		skinLumberjack        = config.get("Skins", "skinLumberjack",        "/colonies/anglewyrm/gfx/lumberjack.png").value;
		skinHunter            = config.get("Skins", "skinHunter",            "/colonies/kzolp67/gfx/Hunter.png").value;
		skinFisherman         = config.get("Skins", "skinFisherman",         "/colonies/irontaxi/gfx/fisherman2.png").value;
		skinAlchemist         = config.get("Skins", "skinAlchemist",         "/colonies/irontaxi/gfx/alchemist.png").value;
		skinArcher			  = config.get("Skins", "skinArcher", 			 "/colonies/boycat97/gfx/skin_archer.png").value;
		skinGuard			  = config.get("Skins", "skinGuard", 			 "/colonies/boycat97/gfx/skin_footsoldier.png").value;
		skinSergeant		  = config.get("Skins", "skinSergeant", 		 "/colonies/boycat97/gfx/skin_sergeant.png").value;

		
		guiChestBackground = config.get("GUI", "guiChestBackground", "/colonies/pmardle/gfx/Chestcontainer.png").value;
	}
	
	// Register Colonies stuff with Minecraft Forge
	private void registerColoniesStuff()
	{
		// Chest block
		chestBlock = new BlockColoniesChest(defaultChestID);
		LanguageRegistry.addName(chestBlock, "Colonies Chest");
		GameRegistry.registerBlock(chestBlock);

		GameRegistry.registerTileEntity(TileEntityColoniesChest.class, "container.colonieschest");
		LanguageRegistry.instance().addStringLocalization("container.colonieschest", "en_US", "Colonies Chest");
		proxy.registerTileEntitySpecialRenderer(TileEntityColoniesChest.class);

		minerChest = new BlockMine(minerChestID).setBlockName("Mine");
		LanguageRegistry.addName(minerChest, "Miner Chest");
		GameRegistry.registerBlock(minerChest);
		GameRegistry.registerTileEntity(TileEntityMine.class, "container.mine");
		LanguageRegistry.instance().addStringLocalization("container.mine", "en_US", "Mine");
		
		// Logging Camp
		loggingCamp = new BlockLoggingCamp(loggingCampID);
		LanguageRegistry.addName(loggingCamp, "Logging Camp");
		GameRegistry.registerBlock(loggingCamp);
		GameRegistry.registerTileEntity(TileEntityLoggingCamp.class, "container.loggingcamp");
		LanguageRegistry.instance().addStringLocalization("container.loggingcamp", "en_US", "Logging Camp");

		// House
		house = new BlockHouse(blockHouseID);
		LanguageRegistry.addName(house, "House");
		GameRegistry.registerBlock(house);
		GameRegistry.registerTileEntity(TileEntityHouse.class, "container.house");
		LanguageRegistry.instance().addStringLocalization("container.house", "en_US", "House");
		
		// Town Hall
		townHall = new BlockTownHall(townHallID);
		LanguageRegistry.addName(townHall, "Town Hall");
		GameRegistry.registerBlock(townHall);
		GameRegistry.registerTileEntity(TileEntityTownHall.class, "container.townhall");
		LanguageRegistry.instance().addStringLocalization("container.townhall", "en_US", "MyTown Town Hall");

		// Hunter Blind
		hunterBlind = new BlockHunterBlind(hunterBlindID);
		LanguageRegistry.addName(hunterBlind, "Hunter Blind");
		GameRegistry.registerBlock(hunterBlind);
		GameRegistry.registerTileEntity(TileEntityHunterBlind.class, "container.hunterBlind");
		LanguageRegistry.instance().addStringLocalization("container.hunterBlind", "en_US", "Hunter Blind");

		// Fisherman's Hut
		fishermanHut = new BlockFishermanHut(fishermanHutID);
		LanguageRegistry.addName(fishermanHut, "Fisherman's Hut");
		GameRegistry.registerBlock(fishermanHut);
		GameRegistry.registerTileEntity(TileEntityFishermanHut.class, "container.fishermanHut");
		LanguageRegistry.instance().addStringLocalization("container.fishermanHut", "en_US", "Fisherman's Hut");

		// Alchemist's Shop
		alchemistShop = new BlockAlchemistShop(alchemistShopID);
		LanguageRegistry.addName(alchemistShop, "Alchemist's Shop");
		GameRegistry.registerBlock(alchemistShop);
		GameRegistry.registerTileEntity(TileEntityAlchemistShop.class, "container.alchemistShop");
		LanguageRegistry.instance().addStringLocalization("container.alchemistShop", "en_US", "Alchemist's Shop");

		// Guard House
		guardHouse = new BlockGuardHouse(guardHouseID);
		LanguageRegistry.addName(guardHouse, "Guard House");
		GameRegistry.registerBlock(guardHouse);
		GameRegistry.registerTileEntity(TileEntityGuardHouse.class, "container.guardhouse");
		LanguageRegistry.instance().addStringLocalization("container.guardhouse", "en_US", "Guard House");
		
		// Measuring tape
		MeasuringTape = new ItemMeasuringTape(measuringTapeID).setItemName("Measuring Tape");
		LanguageRegistry.addName(MeasuringTape,"Measuring Tape");
		
		// Ancient Tome
		ancientTome = new ItemAncientTome(ancientTomeID).setItemName("Ancient Tome");
		LanguageRegistry.addName(ancientTome,"Ancient Tome");

		// Test block
		test = (TestBlock) new TestBlock(testBlockID, 3, Material.ground)
		.setBlockName("test").setHardness(0.75f).setCreativeTab(CreativeTabs.tabBlock);
		MinecraftForge.setBlockHarvestLevel(test, "shovel", 0);
		LanguageRegistry.addName(test, "Test Block");
		GameRegistry.registerBlock(test);

		// Citizens
		// the three parameters after the class are ChanceWeight, minPackSize and maxPackSize
		EntityRegistry.registerGlobalEntityID(EntityCitizen.class, "Citizen", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0xFF4444);
		LanguageRegistry.instance().addStringLocalization("entity.Citizen.name", "en_US", "Wanderer");

		// Miner
		EntityRegistry.registerGlobalEntityID(EntityMiner.class, "Miner", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0xFF8888);
		LanguageRegistry.instance().addStringLocalization("entity.Miner.name", "en_US", "Miner");

		// Lumberjack
		EntityRegistry.registerGlobalEntityID(EntityLumberjack.class, "Lumberjack", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x888800);
		LanguageRegistry.instance().addStringLocalization("entity.Lumberjack.name", "en_US", "Lumberjack");

		// Wife
		EntityRegistry.registerGlobalEntityID(EntityWife.class, "Wife", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0xFFcccc);
		LanguageRegistry.instance().addStringLocalization("entity.Wife.name", "en_US", "Wife");

		// Priestess
		EntityRegistry.registerGlobalEntityID(EntityPriestess.class, "Priestess", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x00FF00);
		LanguageRegistry.instance().addStringLocalization("entity.Priestess.name", "en_US", "Cleric");

		// Hunter
		EntityRegistry.registerGlobalEntityID(EntityHunter.class, "Hunter", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x099990);
		LanguageRegistry.instance().addStringLocalization("entity.Hunter.name", "en_US", "Hunter");

		// Fisherman
		EntityRegistry.registerGlobalEntityID(EntityFisherman.class, "Fisherman", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x099990);
		LanguageRegistry.instance().addStringLocalization("entity.Fisherman.name", "en_US", "Fisherman");

		// Alchemist
		EntityRegistry.registerGlobalEntityID(EntityAlchemist.class, "Alchemist", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x099990);
		LanguageRegistry.instance().addStringLocalization("entity.Alchemist.name", "en_US", "Alchemist");
		
		// Guard
		EntityRegistry.registerGlobalEntityID(EntityGuard.class, "Guard", ModLoader.getUniqueEntityId(), 0xCCCCFF, 0x099990);
		LanguageRegistry.instance().addStringLocalization("entity.Guard.name", "en_US", "Guard");
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.coloniesTab", "en_US", "Colonies");

		}
}

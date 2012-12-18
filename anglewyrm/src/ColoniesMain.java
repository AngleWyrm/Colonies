package colonies.anglewyrm.src;

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
import colonies.lohikaarme.src.ItemMeasuringTape;
import colonies.thephpdev.src.BlockMine;
import colonies.thephpdev.src.TileEntityMine;
import colonies.stabtokill.src.ColoniesAchievements;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;
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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Colonies", name = "Colonies", version = "21 Dec 2012")
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

	//public static List<TileEntityTownHall> townsList;


	@Instance
	public static ColoniesMain instance;

	@SidedProxy(clientSide = "colonies.anglewyrm.src.ClientProxy", serverSide = "colonies.anglewyrm.src.ServerProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Initializing Colonies " + Version()); 
		ConfigFile.load(); // on it's way out!
		//Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		//config.load();
		//setConfig(config);
		//config.save();
		MinecraftForge.EVENT_BUS.register(new ColoniesSoundManager());
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{	
		registerColoniesStuff(); // at bottom of this file for legibility
		proxy.registerRenderInformation(); 
		ColoniesAchievements.addAchievementLocalizations();
		AchievementPage.registerAchievementPage(ColoniesAchievements.page1);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		// TODO: Add Post-Initialization code such as mod hooks
		//NetworkRegistry.instance().registerGuiHandler(this, guihBusiness);
	}

	public String Version(){
		return "Pre-Alpha, Revision 5";
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
	
	public static boolean citizenGreetings;
	public static String skinDefault;
	public static String skinMaleSwimming;
	public static String skinMiner;
	
	private void setConfig(Configuration config)
	{
		testBlockID     = config.getBlock("testBlockID",     1100).getInt();
		measuringTapeID = config.getBlock("measuringTapeID", 1101).getInt();
		defaultChestID  = config.getBlock("defaultChestID",  1102).getInt();
		townHallID      = config.getBlock("townHallID",      1103).getInt();
		minerChestID    = config.getBlock("minerChestID",    1104).getInt();
		loggingCampID   = config.getBlock("loggingCampID",   1105).getInt();
		blockHouseID    = config.getBlock("blockHouseID",    1106).getInt();
		
		citizenGreetings = config.get(Configuration.CATEGORY_GENERAL, "citizenGreetings", true).getBoolean(true);
		
		skinDefault      = config.get("Skins", "skinDefault",      "/colonies/grahammarcellus/gfx/unemployedskin1.png").value;
		skinMaleSwimming = config.get("Skins", "skinMaleSwimming", "/colonies/anglewyrm/gfx/m-swimskin.png").value;
		skinMiner        = config.get("Skins", "skinMiner",        "/colonies/grahammarcellus/gfx/minerskin.png").value;
	}
	
	// Register Colonies stuff with Minecraft Forge
	private void registerColoniesStuff()
	{
		// Chest block
		chestBlock = new BlockColoniesChest(ConfigFile.parseInt("DefaultChestID"));
		LanguageRegistry.addName(chestBlock, "Colonies Chest");
		GameRegistry.registerBlock(chestBlock);

		GameRegistry.registerTileEntity(TileEntityColoniesChest.class, "container.colonieschest");
		LanguageRegistry.instance().addStringLocalization("container.colonieschest", "en_US", "Colonies Chest");
		proxy.registerTileEntitySpecialRenderer(TileEntityColoniesChest.class);

		minerChest = new BlockMine(ConfigFile.parseInt("MinerChestID")).setBlockName("Mine");
		LanguageRegistry.addName(minerChest, "Miner Chest");
		GameRegistry.registerBlock(minerChest);
		GameRegistry.registerTileEntity(TileEntityMine.class, "container.mine");
		LanguageRegistry.instance().addStringLocalization("container.mine", "en_US", "Mine");
		
		// Logging Camp
		loggingCamp = new BlockLoggingCamp(ConfigFile.parseInt("LoggingCampID"));
		LanguageRegistry.addName(loggingCamp, "Logging Camp");
		GameRegistry.registerBlock(loggingCamp);
		GameRegistry.registerTileEntity(TileEntityLoggingCamp.class, "container.loggingcamp");
		LanguageRegistry.instance().addStringLocalization("container.loggingcamp", "en_US", "Logging Camp");

		// House
		house = new BlockHouse(ConfigFile.parseInt("BlockHouseID"));
		LanguageRegistry.addName(house, "House");
		GameRegistry.registerBlock(house);
		GameRegistry.registerTileEntity(TileEntityHouse.class, "container.house");
		LanguageRegistry.instance().addStringLocalization("container.house", "en_US", "House");

		// Town Hall
		townHall = new BlockTownHall(ConfigFile.parseInt("TownHallID"));
		LanguageRegistry.addName(townHall, "Town Hall");
		GameRegistry.registerBlock(townHall);
		GameRegistry.registerTileEntity(TileEntityTownHall.class, "container.townhall");
		LanguageRegistry.instance().addStringLocalization("container.townhall", "en_US", "MyTown Town Hall");

		// Measuring tape
		MeasuringTape = new ItemMeasuringTape(ConfigFile.parseInt("MeasuringTape")).setItemName("Measuring Tape");
		LanguageRegistry.addName(MeasuringTape,"Measuring Tape");

		// Test block
		test = (TestBlock) new TestBlock(ConfigFile.parseInt("TestBlockID"), 3, Material.ground)
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


		Recipes.registerRecipes();
	}
}

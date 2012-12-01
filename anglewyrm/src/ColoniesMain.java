package colonies.anglewyrm.src;

import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Material;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Block;
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


@Mod(modid = "Colonies", name = "Colonies, a MineColony Reboot", version = "r1")
@NetworkMod(
        channels = { "Colonies" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class )

public class ColoniesMain {
	public final static TestBlock test = (TestBlock) new TestBlock(500, 0, Material.ground)
		.setBlockName("test").setHardness(0.75f).setCreativeTab(CreativeTabs.tabDecorations);
	public final static BlockColoniesChest colonieschest = (BlockColoniesChest) new BlockColoniesChest(501);
	@Instance
	public static ColoniesMain instance;

	@SidedProxy(clientSide = "colonies.anglewyrm.src.ClientProxy", serverSide = "colonies.anglewyrm.src.ServerProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Initializing Colonies"); 
		ConfigFile.load();
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
		LanguageRegistry.addName(test, "Test Block");
		MinecraftForge.setBlockHarvestLevel(test, "shovel", 0);
		GameRegistry.registerBlock(test);
		LanguageRegistry.addName(colonieschest, "Colonies chest block");
		MinecraftForge.setBlockHarvestLevel(colonieschest, "shovel", 0);
		GameRegistry.registerBlock(colonieschest);
		GameRegistry.registerTileEntity(TileEntityColoniesChest.class, "Colonies Chest TileEntity");
		LanguageRegistry.instance().addStringLocalization("Colonies Chest TileEntity" + ".name", "en_US", "Colonies Chest TileEntity");
		proxy.registerTileEntitySpecialRenderer(TileEntityColoniesChest.class);
	    // TODO: Add Initialization code such as block ID registering
		EntityRegistry.registerModEntity(EntityCitizen.class, "Citizen", 1, this, 40, 3, true);
		EntityRegistry.addSpawn(EntityCitizen.class, 10, 2, 4,
				EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills,
				BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore,
				BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland);
		proxy.registerRenderInformation();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
	    // TODO: Add Post-Initialization code such as mod hooks
	}
	
	public String Version(){
		return "Colonies r2";
	}
}



/*


package colonies.pmardle.src;


import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "Colonies", name = "Colonies", dependencies="required-after:Forge@[6.0,)")
@NetworkMod(channels = { "Colonies" }, versionBounds = "[4.2,)", clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class ColonyChest {
	public static BlockColonyChest ColonyChestBlock;
	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
	public static CommonProxy proxy;
	@Instance("Colonies")
	public static ColonyChest instance;
	public static boolean CACHE_RENDER = true;
	public static boolean OCELOTS_SITONCHESTS = true;
	private int blockId;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Version.init(event.getVersionProperties());
		event.getModMetadata().version = Version.fullVersionString();
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			blockId = cfg.get(Configuration.CATEGORY_BLOCK, "ColonyChests", 181).getInt(181);
			CACHE_RENDER = cfg.get(Configuration.CATEGORY_GENERAL, "cacheRenderingInformation", true).getBoolean(true);
			OCELOTS_SITONCHESTS = cfg.get(Configuration.CATEGORY_GENERAL, "ocelotsSitOnChests", true).getBoolean(true);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Colonies has a problem loading it's configuration");
		} finally {
			cfg.save();
		}
	}

	@Init
	public void load(FMLInitializationEvent evt) {
		ColonyChestBlock = new BlockColonyChest(blockId);
		GameRegistry.registerBlock(ColonyChestBlock, ItemColonyChest.class);
		for (ColonyChestType typ : ColonyChestType.values()) {
			GameRegistry.registerTileEntity(typ.clazz, typ.name());
			LanguageRegistry.instance().addStringLocalization(typ.name() + ".name", "en_US", typ.friendlyName);
			proxy.registerTileEntitySpecialRenderer(typ);
		}
		ColonyChestType.generateTieredRecipes(ColonyChestBlock);
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerRenderInformation();
		if (OCELOTS_SITONCHESTS)
		{
			MinecraftForge.EVENT_BUS.register(new OcelotsSitOnChestsHandler());
		}
	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) {
	}
}
*/

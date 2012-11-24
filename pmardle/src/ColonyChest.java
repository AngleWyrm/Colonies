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


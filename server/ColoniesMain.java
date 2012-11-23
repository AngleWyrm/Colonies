package colonies.server;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.EnumCreatureType;
import colonies.client.ConfigFile;
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

@Mod(modid = "Colonies", name = "Colonies, a MineColony Reboot", version = "r1")
@NetworkMod(
        channels = { "Colonies" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class )

public class ColoniesMain {
	
	@Instance
	public static ColoniesMain instance;
	
	@SidedProxy(clientSide = "colonies.client.ClientProxy", serverSide = "colonies.server.ServerProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigFile.load();
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
	    // TODO: Add Initialization code such as block ID registering
		EntityRegistry.registerModEntity(EntityCitizen.class, "Citizen", 1, this, 40, 3, true);
		EntityRegistry.addSpawn(EntityCitizen.class, 10, 2, 4, 
				EnumCreatureType.monster, BiomeGenBase.beach, BiomeGenBase.extremeHills, 
				BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, 
				BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, 
				BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
	    // TODO: Add Post-Initialization code such as mod hooks
	}
}

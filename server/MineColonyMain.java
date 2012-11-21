package server;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.SidedProxy;

@Mod(modid = "MineColony", name = "MineColony Reboot", version = "r1")
@NetworkMod(
        channels = { "MineColony" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class )

public class MineColonyMain {
	
	@Instance
	public static MineColonyMain instance;
	
	@SidedProxy(clientSide = "client.ClientProxy", serverSide = "server.CommonProxy")
	public static ServerProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
	    // TODO: Add Pre-Initialization code such as configuration loading
	}

	@Init
	public void init(FMLInitializationEvent evt)
	{
	    // TODO: Add Initialization code such as block ID registering
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
	    // TODO: Add Post-Initialization code such as mod hooks
	}
}

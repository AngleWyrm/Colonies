package Colonies.server;

import Colonies.client.ConfigFile;
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

@Mod(modid = "Colonies", name = "Colonies, a MineColony Reboot", version = "r1")
@NetworkMod(
        channels = { "Colonies" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class )

public class ColoniesMain {
	
	@Instance
	public static ColoniesMain instance;
	
	@SidedProxy(clientSide = "Colonies.client.ClientProxy", serverSide = "Colonies.server.ServerProxy")
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
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
	    // TODO: Add Post-Initialization code such as mod hooks
	}
}

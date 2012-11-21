package client;

import server.ServerProxy;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends ServerProxy
{

	@Override
    public void registerRenderInformation()
    {
        MinecraftForgeClient.preloadTexture("/minecolony.png");
    }

    @Override
    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/)
    {
       
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
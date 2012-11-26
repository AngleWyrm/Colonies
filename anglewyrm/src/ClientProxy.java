package colonies.anglewyrm.src;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;


public class ClientProxy extends ServerProxy
{

	@Override
    public void registerRenderInformation(){
        MinecraftForgeClient.preloadTexture(TESTBLOCK_PNG);
    }

    @Override
    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/){

    }

    @Override
    public World getClientWorld(){
        return FMLClientHandler.instance().getClient().theWorld;
    }
}

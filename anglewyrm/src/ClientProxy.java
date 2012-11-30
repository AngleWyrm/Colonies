package colonies.anglewyrm.src;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;


public class ClientProxy extends ServerProxy
{
	// I think these paths are old news
	public static String BLACKSMITHCHEST_PNG = "/colonies/pmardle/gfx/Blacksmithchest.png";
	public static String BUILDERCHEST_PNG =    "/colonies/pmardle/gfx/Builderchest.png";
	public static String FARMERCHEST_PNG =     "/colonies/pmardle/gfx/Farmerchest.png";
	public static String HOUSECHEST_PNG =      "/colonies/pmardle/gfx/Housechest.png";
	public static String LUMBERJACKCHEST_PNG = "/colonies/pmardle/gfx/Lumberjackchest.png";
	public static String MINERCHEST_PNG =      "/colonies/pmardle/gfx/Minerchest.png";
	public static String TOWNHALLCHEST_PNG =   "/colonies/pmardle/gfx/Townhallchest.png";
	public static String CHESTCONTAINER_PNG =  "/colonies/pmardle/gfx/Chestcontainer.png";
	
	@Override
    public void registerRenderInformation(){
        MinecraftForgeClient.preloadTexture(TESTBLOCK_PNG);
        MinecraftForgeClient.preloadTexture(MEASURING_TAPE);
    }

    @Override
    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/){

    }

    @Override
    public World getClientWorld(){
        return FMLClientHandler.instance().getClient().theWorld;
    }
}

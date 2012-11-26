package colonies.anglewyrm.src;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;


public class ClientProxy extends ServerProxy
{
	
	public static String BLACKSMITHCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Blacksmithchest.png";
	public static String BUILDERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Builderchest.png";
	public static String FARMERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Farmerchest.png";
	public static String HOUSECHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Housechest.png";
	public static String LUMBERJACKCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Lumberjackchest.png";
	public static String MINERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Minerchest.png";
	public static String TOWNHALLCHEST_PNG = "/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Townhallchest.png";
	public static String CHESTCONTAINER_PNG = "/colonies/pmardle/gfx/Chestcontainer.png";
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

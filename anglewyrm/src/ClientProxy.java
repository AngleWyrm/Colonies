package colonies.anglewyrm.src;

import colonies.vector67.src.ColoniesChestRenderHelper;
import colonies.vector67.src.TileEntityColoniesChest;
import colonies.vector67.src.TileEntityColoniesChestRenderer;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;


public class ClientProxy extends ServerProxy
{
	
	public static String BLACKSMITHCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Blacksmithchest.png";
	public static String BUILDERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Builderchest.png";
	public static String FARMERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Farmerchest.png";
	public static String HOUSECHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Housechest.png";
	public static String LUMBERJACKCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Lumberjackchest.png";
	public static String MINERCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Minerchest.png";
	public static String TOWNHALLCHEST_PNG = "/minecraft/colonies/Minecraft/bin/cpw/mods/colonies/Minecraft/bin/cpw/mods/Colonies/Chests/Townhallchest.png";
	public static String CHESTCONTAINER_PNG = "/colonies/pmardle/gfx/Chestcontainer.png";
	
	
	@Override
	public void registerRenderInformation() {
		
		
		ChestItemRenderHelper.instance = new ColoniesChestRenderHelper();
		MinecraftForgeClient.preloadTexture(TESTBLOCK_PNG);
        MinecraftForgeClient.preloadTexture(BLACKSMITHCHEST_PNG);
        MinecraftForgeClient.preloadTexture(BUILDERCHEST_PNG);
        MinecraftForgeClient.preloadTexture(FARMERCHEST_PNG);
        MinecraftForgeClient.preloadTexture(HOUSECHEST_PNG);
        MinecraftForgeClient.preloadTexture(LUMBERJACKCHEST_PNG);
        MinecraftForgeClient.preloadTexture(MINERCHEST_PNG);
        MinecraftForgeClient.preloadTexture(TOWNHALLCHEST_PNG);
        MinecraftForgeClient.preloadTexture(CHESTCONTAINER_PNG);
	}

	public void registerTileEntitySpecialRenderer(Class<TileEntityColoniesChest> colonieschesttileentity) {
		ClientRegistry.bindTileEntitySpecialRenderer(colonieschesttileentity, new TileEntityColoniesChestRenderer());
	}
	@Override
    public World getClientWorld(){
        return FMLClientHandler.instance().getClient().theWorld;
    }
}

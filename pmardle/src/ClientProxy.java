package colonies.pmardle.src;

import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import colonies.pmardle.src.CommonProxy;
import colonies.pmardle.src.ColonyChestType;
import colonies.pmardle.src.TileEntityColonyChest;

public class ClientProxy extends colonies.pmardle.src.CommonProxy {
	@Override
	public void registerRenderInformation() {
		ChestItemRenderHelper.instance = new ColonyChestRenderHelper();
		MinecraftForgeClient.preloadTexture("/pmardle/gfx/block_textures.png");
		MinecraftForgeClient.preloadTexture("/pmardle/gfx/item_textures.png");
	}

	@Override
	public void registerTileEntitySpecialRenderer(ColonyChestType typ) {
		ClientRegistry.bindTileEntitySpecialRenderer(typ.clazz, new TileEntityColonyChestRenderer());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}


	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityColonyChest) {
			return GUIChest.GUI.buildGUI(ColonyChestType.values()[ID], player.inventory, (TileEntityColonyChest) te);
		} else {
			return null;
		}
	}
}
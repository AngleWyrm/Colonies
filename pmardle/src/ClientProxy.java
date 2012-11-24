package Colonies.pmardle.src;

import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import Colonies.pmardle.src.CommonProxy;
import Colonies.pmardle.src.ColonyChestType;
import Colonies.pmardle.src.TileEntityColonyChest;

public class ClientProxy extends Colonies.pmardle.src.CommonProxy {
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
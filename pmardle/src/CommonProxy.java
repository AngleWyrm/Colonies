package colonies.pmardle.src;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;


public class CommonProxy implements IGuiHandler {
	public void registerRenderInformation()
	{

	}

	public void registerTileEntitySpecialRenderer(ColonyChestType typ)
	{

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
		TileEntity te = world.getBlockTileEntity(X, Y, Z);
		if (te != null && te instanceof TileEntityColonyChest) {
			TileEntityColonyChest icte = (TileEntityColonyChest) te;
			return new ContainerColonyChestBase(player.inventory, icte, icte.getType(), 0, 0);
		} else {
			return null;
		}
	}

	public World getClientWorld() {
		return null;
	}

}
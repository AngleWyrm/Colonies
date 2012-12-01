package colonies.anglewyrm.src;

import colonies.vector67.src.TileEntityColoniesChest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;


public class ServerProxy implements IGuiHandler
{
	public static String TESTBLOCK_PNG = "/colonies/anglewyrm/gfx/block.png";
	public static String BLACKSMITHCHEST_PNG = "/colonies/Chests/Blacksmithchest.png";
	public static String BUILDERCHEST_PNG = "/colonies/Chests/Builderchest.png";
	public static String FARMERCHEST_PNG = "/mcolonies/Chests/Farmerchest.png";
	public static String HOUSECHEST_PNG = "/colonies/Chests/Housechest.png";
	public static String LUMBERJACKCHEST_PNG = "/colonies/Chests/Lumberjackchest.png";
	public static String MINERCHEST_PNG = "/colonies/Chests/Minerchest.png";
	public static String TOWNHALLCHEST_PNG = "/colonies/pmardle/gfx/Townhallchest.png";
	public static String CHESTCONTAINER_PNG = "/colonies/pmardle/gfx/Chestcontainer.png";
	public static String MEASURING_TAPE = "/colonies/lohikaarme/gfx/LItems.png";

    public void registerRenderInformation()
    {

    }

    public void registerTileEntitySpecialRenderer(Class<TileEntityColoniesChest> renderer)
    {
        // TODO: Add special render types here
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        return null;
    }

    public World getClientWorld()
    {
        return null;
    }
}

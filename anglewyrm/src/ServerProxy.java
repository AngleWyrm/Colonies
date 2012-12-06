package colonies.anglewyrm.src;

import colonies.vector67.src.TileEntityColoniesChest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;


public class ServerProxy implements IGuiHandler
{
	public static String M_SWIMSKIN_PNG      = "/colonies/anglewyrm/gfx/m-swimskin.png";
	public static String F_SWIMSKIN_PNG      = "/colonies/anglewyrm/gfx/f-swimskin.png";
	public static String TESTBLOCK_PNG       = "/colonies/anglewyrm/gfx/block.png";
	public static String WANDERERSKIN_PNG    = "/colonies/grahammarcellus/gfx/unemployedskin1.png";
	public static String MINERSKIN_PNG       = "/colonies/grahammarcellus/gfx/minerskin.png";
	public static String WIFESKIN_PNG        = "/colonies/pmardle/gfx/FemaleBasic.png";
	public static String BLACKSMITHCHEST_PNG = "/colonies/pmardle/gfx/Blacksmithchest.png";
	public static String BUILDERCHEST_PNG    = "/colonies/pmardle/gfx/Builderchest.png";
	public static String FARMERCHEST_PNG     = "/colonies/pmardle/gfx/Farmerchest.png";
	public static String HOUSECHEST_PNG      = "/colonies/pmardle/gfx/Housechest.png";
	public static String LUMBERJACKCHEST_PNG = "/colonies/pmardle/gfx/Lumberjackchest.png";
	public static String MINERCHEST_PNG      = "/colonies/pmardle/gfx/Minerchest.png";
	public static String TOWNHALLCHEST_PNG   = "/colonies/pmardle/gfx/Townhallchest.png";
	public static String CHESTCONTAINER_PNG  = "/colonies/anglewyrm/gfx/invalidChest.png";
	public static String MEASURING_TAPE      = "/colonies/lohikaarme/gfx/LItems.png";

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

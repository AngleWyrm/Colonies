package colonies.anglewyrm.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;


public class ServerProxy implements IGuiHandler
{
	public static String TESTBLOCK_PNG = "/colonies/anglewyrm/gfx/block.png";
	public static String MEASURING_TAPE = "/colonies/lohikaarme/gfx/LItems.png";
	
    public void registerRenderInformation()
    {

    }

    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/)
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

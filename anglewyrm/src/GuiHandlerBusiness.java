package colonies.anglewyrm.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class GuiHandlerBusiness {
    //returns an instance of the Container you made earlier
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityBusiness){
                    return new ContainerBusiness(player.inventory, (TileEntityBusiness) tileEntity);
            }
            return null;
    }

    //returns an instance of the Gui you made earlier
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityBusiness){
                    return new GuiBusiness(player.inventory, (TileEntityBusiness) tileEntity);
            }
            return null;
    }
}

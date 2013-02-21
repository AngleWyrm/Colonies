package colonies.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import colonies.eragon.src.BlockColoniesChestGui;
import colonies.src.buildings.ContainerColoniesChest;
import colonies.src.buildings.TileEntityColoniesChest;
//..
public class GuiHandlerResearchBlock implements IGuiHandler {
    //returns an instance of the Container you made earlier
 
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityResearchBlock){
                return new ContainerResearchBlock(player.inventory, (TileEntityResearchBlock) tileEntity);
        }
        return null;
	}

	@Override
  
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
	       
        TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
       
       
        if(tile_entity instanceof TileEntityResearchBlock){

                return new GuiResearchBlock(player.inventory, (TileEntityResearchBlock) tile_entity);
        }

return null;
}



}

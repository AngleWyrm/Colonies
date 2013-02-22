package colonies.anglewyrm.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import colonies.lohikaarme.src.GuiHouse;
import colonies.lohikaarme.src.GuiTownHall;
import colonies.lohikaarme.src.GuiTownName;
import colonies.src.buildings.ContainerColoniesChest;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityTownHall;

public class GuiHandlerColoniesChest implements IGuiHandler {
    //returns an instance of the Container you made earlier
 
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityColoniesChest){
                return new ContainerColoniesChest(player.inventory, (TileEntityColoniesChest) tileEntity);
        }
        return null;
	}
	//ID has same value than GuiID in BlockColoniesChest. Make new case for new gui and if it is for chest block change value of the GuiID to value ask in new case at extend class.
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
            switch(ID){
            case 0:
            	
            	if (world.getBlockTileEntity(x,y,z) instanceof TileEntityTownHall) {
            		return new GuiTownHall((TileEntityTownHall)world.getBlockTileEntity(x,y,z), player.inventory);
            	} else if (world.getBlockTileEntity(x,y,z) instanceof TileEntityColoniesChest) {
            		return new GuiColoniesChest((TileEntityColoniesChest)world.getBlockTileEntity(x,y,z), player.inventory);
            	}
            	
            case 1:return new GuiHouse((TileEntityColoniesChest)world.getBlockTileEntity(x,y,z),player.inventory);
            case 10:return new GuiTownName((TileEntityColoniesChest)world.getBlockTileEntity(x,y,z),player.inventory); 
            }
            return null;
    }

}

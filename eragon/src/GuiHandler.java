package colonies.eragon.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import colonies.src.buildings.TileEntityColoniesChest;
import cpw.mods.fml.common.network.IGuiHandler;
 
public class GuiHandler implements IGuiHandler{
 
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
       
                TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
               
                if(tile_entity instanceof TileEntityColoniesChest){
               
                        return new ContainerColoniesChest((TileEntityColoniesChest) tile_entity, player.inventory);
                }
               
               
                return null;
        }
       
       
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
       
                TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
               
               
                if(tile_entity instanceof TileEntityColoniesChest){
       
                        return new BlockColoniesChestGui(player.inventory, (TileEntityColoniesChest) tile_entity);
                }
       
                return null;
        }
}
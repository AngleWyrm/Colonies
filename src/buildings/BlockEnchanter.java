package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnchanter extends BlockColoniesChest {
	
	public BlockEnchanter(int id) {
		super(id);
		tileEntity = new TileEntityEnchanterChest();
		setBlockName("Enchanter Chest");
		setCreativeTab(ColoniesMain.coloniesTab);
	}


	@Override
	public String getTextureFile() {
		return ClientProxy.ENCHANTERCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityEnchanterChest();
        return tileEntity;
     }

    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teEnchanter){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teEnchanter);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

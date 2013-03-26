package colonies.src.buildings;

import java.util.LinkedList;

import colonies.src.ClientProxy;
import colonies.src.citizens.EntityCitizen;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityHouse extends TileEntityColoniesChest {

	public static TileEntityHouse house = new TileEntityHouse();
	
	public TileEntityHouse() {
		super();
	}

	@Override
    public String getInvName(){
        return "House";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.HOUSECHEST_PNG;
	}
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);
          TileEntityTownHall.playerTown.homesList.offer(this);
          TileEntityTownHall.playerTown.maxPopulation+=2;
    }

}

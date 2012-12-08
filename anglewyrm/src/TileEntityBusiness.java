package colonies.anglewyrm.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityBusiness extends TileEntity implements IInventory 
{
	public  TileEntityBusiness(){
		inventory = new ItemStack[1];
	}

	private ItemStack[] inventory;
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
    public int getInventoryStackLimit() {
		// Meh, I don't particularly like a generic stack size limit dictated by the container type
		// it seems to have so little to do with the items and the container size.
		// but whatever for now.
        return 64;
    }
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		
		// obey container's max allowable items/stack
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }              
	}
	
    @Override
    public ItemStack decrStackSize(int slot, int quantity) {
    	ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
        	if (stack.stackSize <= quantity) {
        		setInventorySlotContents(slot, null);
            } else {
              	stack = stack.splitStack(quantity);
                if (stack.stackSize == 0) {
                  	setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
    	ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
        	setInventorySlotContents(slot, null);
        }
        return stack;
    }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {	
	}

	@Override
	public void closeChest() {
	}
	
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	super.readFromNBT(tagCompound);
           
        NBTTagList tagList = tagCompound.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
        	NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
            	inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
                           
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < inventory.length; i++) {
                    ItemStack stack = inventory[i];
                    if (stack != null) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setByte("Slot", (byte) i);
                            stack.writeToNBT(tag);
                            itemList.appendTag(tag);
                    }
            }
            tagCompound.setTag("Inventory", itemList);
    }

	@Override
	public String getInvName() {
		return "Inventory";
	}
}

package colonies.src.buildings;

import net.minecraft.src.*;

public class ContainerColoniesChest extends Container {

        protected TileEntityColoniesChest tileEntity;
        private int numrows;
        
        public ContainerColoniesChest (InventoryPlayer inventoryPlayer, TileEntityColoniesChest te){
                tileEntity = te;
                tileEntity.openChest();
                numrows = te.getSizeInventory()/9;
                
                //the Slot constructor takes the IInventory and the slot number in that it binds to
                //and the x-y coordinates it resides on-screen
                for(int var4 = 0;var4 < numrows; ++var4){
                	for (int var5 = 0; var5 < 9; ++var5){
                      addSlotToContainer(new Slot(tileEntity,var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
                    }
                }
                //commonly used vanilla code that adds the player's inventory
                bindPlayerInventory(inventoryPlayer);
        }

//        public ContainerColoniesChest(IInventory par1IInventory, IInventory par2IInventory){
          
//        }
        
        @Override
        public boolean canInteractWith(EntityPlayer player) {
                return tileEntity.isUseableByPlayer(player);
        }


        protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
                for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                                8 + j * 18, 84 + i * 18));
                        }
                }

                for (int i = 0; i < 9; i++) {
                        addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
                }
        }
        
        @Override
        public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
                ItemStack stack = null;
                Slot slotObject = (Slot) inventorySlots.get(slot);

                //null checks and checks if the item can be stacked (maxStackSize > 1)
                if (slotObject != null && slotObject.getHasStack()) {
                        ItemStack stackInSlot = slotObject.getStack();
                        stack = stackInSlot.copy();

                        //merges the item into player inventory since its in the tileEntity
                        //this assumes only 1 slot, for inventories with > 1 slots, check out the Chest Container.
                        if (slot == 0) {
                                if (!mergeItemStack(stackInSlot, 1,
                                                inventorySlots.size(), true)) {
                                        return null;
                                }
                        //places it into the tileEntity is possible since its in the player inventory
                        } else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
                                return null;
                        }

                        if (stackInSlot.stackSize == 0) {
                                slotObject.putStack(null);
                        } else {
                                slotObject.onSlotChanged();
                        }
                }

                return stack;
        }
        public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
        {
            super.onCraftGuiClosed(par1EntityPlayer);
            tileEntity.closeChest();
        }

}
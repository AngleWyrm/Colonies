package colonies.pmardle.src;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerColonyChestBase extends Container {
  private ColonyChestType type;
  private EntityPlayer player;
  private IInventory chest;

  public ContainerColonyChestBase(IInventory playerInventory, IInventory chestInventory, ColonyChestType type, int xSize, int ySize) {
    chest = chestInventory;
    player = ((InventoryPlayer) playerInventory).player;
    this.type = type;
    chestInventory.openChest();
    layoutContainer(playerInventory, chestInventory, type, xSize, ySize);
  }

  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    return chest.isUseableByPlayer(player);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer p, int i)
  {
    ItemStack itemstack = null;
    Slot slot = (Slot) inventorySlots.get(i);
    if (slot != null && slot.getHasStack())
    {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (i < type.size)
      {
        if (!mergeItemStack(itemstack1, type.size, inventorySlots.size(), true))
        {
          return null;
        }
      } else if (!mergeItemStack(itemstack1, 0, type.size, false))
      {
        return null;
      }
      if (itemstack1.stackSize == 0)
      {
        slot.putStack(null);
      } else
      {
        slot.onSlotChanged();
      }
    }
    return itemstack;
  }

  @Override
  public void onCraftGuiClosed(EntityPlayer entityplayer)
  {
    super.onCraftGuiClosed(entityplayer);
    chest.closeChest();
  }

  protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, ColonyChestType type, int xSize, int ySize) {
    for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++)
    {
      for (int chestCol = 0; chestCol < type.getRowLength(); chestCol++)
      {
        addSlotToContainer(new Slot(chestInventory, chestCol + chestRow * type.getRowLength(), 12 + chestCol * 18, 8 + chestRow * 18));
      }

    }

    int leftCol = (xSize - 162) / 2 + 1;
    for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
    {
      for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
      {
        addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
      }

    }

    for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
    {
      addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
    }
  }

  public EntityPlayer getPlayer() {
    return player;
  }
}
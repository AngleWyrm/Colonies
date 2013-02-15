package colonies.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.ItemFood;

public class InventoryCitizen implements IInventory{


    /**
     * An array of 36 item stacks indicating a citizen's inventory.
     */
    public ItemStack[] mainInventory = new ItemStack[36];

    /** An array of 4 item stacks containing the currently worn armor pieces. */
    public ItemStack[] armorInventory = new ItemStack[4];

    /** The index of the currently held item (0-8). */
    public int currentItem = 0;
    @SideOnly(Side.CLIENT)

    /** The current ItemStack. */
    private ItemStack currentItemStack;

    /** The citizen whose inventory this is. */
    public EntityCitizen citizen;
    private ItemStack itemStack;

    // given a block ID
    // returns the total number of items of this type in all three inventories (main, armor, in-hand)
    public int countItems(int searchedForID){
    	int sum = 0;
    	// check main
    	int slot = this.getInventorySlotContainItem(searchedForID);
    	while(slot >= 0){
    		sum += this.getStackInSlot(slot).stackSize;
    		slot = this.getInventorySlotContainItem(searchedForID, ++slot);
    	}
    	// TODO: check armor slots and in-hand slot
    	return sum;
    }
    
    public boolean eatFood(){
    	if(citizen == null) return false;
    	
    	// search for and consume a food item
    	for (int index = 0; index < this.mainInventory.length; ++index){
    		if(this.mainInventory[index] != null){
    			Item item = this.mainInventory[index].getItem();
    			if((item != null) && (item instanceof ItemFood)){
    				// found a food stack in main inventory
    				citizen.hunger += ((ItemFood)item).getHealAmount();
    				consumeInventoryItem(index);
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Set true whenever the inventory changes. Nothing sets it false so you will have to write your own code to check
     * it and reset the value.
     */
    public boolean inventoryChanged = false;

    public InventoryCitizen(EntityCitizen _citizen)
    {
        this.citizen = _citizen;
    }

    /**
     * Returns the item stack currently held by the citizen.
     */
    public ItemStack getCurrentItem()
    {
        return this.currentItem < 9 && this.currentItem >= 0 ? this.mainInventory[this.currentItem] : null;
    }

    public static int func_70451_h()
    {
        return 9;
    }

    // Given an itemID, return slot location or -1
    // Optional second parameter gives starting index
    private int getInventorySlotContainItem(int _itemID){
    	return getInventorySlotContainItem(_itemID, 0);
    }
    private int getInventorySlotContainItem(int _itemID, int _slot)
    {
    	if((_slot < 0)||(_slot > this.getSizeInventory())) return -1;
    	
        for (int index = _slot; index < this.mainInventory.length; ++index)
        {
            if (this.mainInventory[index] != null && this.mainInventory[index].itemID == _itemID)
            {
                return index;
            }
        }

        return -1;
    }

    @SideOnly(Side.CLIENT)
    private int getInventorySlotContainItemAndDamage(int par1, int par2)
    {
        for (int var3 = 0; var3 < this.mainInventory.length; ++var3)
        {
            if (this.mainInventory[var3] != null && this.mainInventory[var3].itemID == par1 && this.mainInventory[var3].getItemDamage() == par2)
            {
                return var3;
            }
        }

        return -1;
    }

    /**
     * stores an itemstack in the users inventory
     */
    private int storeItemStack(ItemStack par1ItemStack)
    {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2)
        {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].itemID == par1ItemStack.itemID && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], par1ItemStack))
            {
                return var2;
            }
        }

        return -1;
    }

    /**
     * Returns the first item stack that is empty.
     */
    public int getFirstEmptyStack()
    {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1)
        {
            if (this.mainInventory[var1] == null)
            {
                return var1;
            }
        }

        return -1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Sets a specific itemID as the current item being held (only if it exists on the hotbar)
     */
    public void setCurrentItem(int par1, int par2, boolean par3, boolean par4)
    {
        boolean var5 = true;
        this.currentItemStack = this.getCurrentItem();
        int var7;

        if (par3)
        {
            var7 = this.getInventorySlotContainItemAndDamage(par1, par2);
        }
        else
        {
            var7 = this.getInventorySlotContainItem(par1);
        }

        if (var7 >= 0 && var7 < 9)
        {
            this.currentItem = var7;
        }
        else
        {
            if (par4 && par1 > 0)
            {
                int var6 = this.getFirstEmptyStack();

                if (var6 >= 0 && var6 < 9)
                {
                    this.currentItem = var6;
                }

                this.func_70439_a(Item.itemsList[par1], par2);
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Switch the current item to the next one or the previous one
     */
    public void changeCurrentItem(int par1)
    {
        if (par1 > 0)
        {
            par1 = 1;
        }

        if (par1 < 0)
        {
            par1 = -1;
        }

        for (this.currentItem -= par1; this.currentItem < 0; this.currentItem += 9)
        {
            ;
        }

        while (this.currentItem >= 9)
        {
            this.currentItem -= 9;
        }
    }

    /**
     * Clear this citizen's inventory, using the specified ID and metadata as filters or -1 for no filter.
     */
    public int clearInventory(int par1, int par2)
    {
        int var3 = 0;
        int var4;
        ItemStack var5;

        for (var4 = 0; var4 < this.mainInventory.length; ++var4)
        {
            var5 = this.mainInventory[var4];

            if (var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2))
            {
                var3 += var5.stackSize;
                this.mainInventory[var4] = null;
            }
        }

        for (var4 = 0; var4 < this.armorInventory.length; ++var4)
        {
            var5 = this.armorInventory[var4];

            if (var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2))
            {
                var3 += var5.stackSize;
                this.armorInventory[var4] = null;
            }
        }

        return var3;
    }

    @SideOnly(Side.CLIENT)
    public void func_70439_a(Item par1Item, int par2)
    {
        if (par1Item != null)
        {
            int var3 = this.getInventorySlotContainItemAndDamage(par1Item.shiftedIndex, par2);

            if (var3 >= 0)
            {
                this.mainInventory[var3] = this.mainInventory[this.currentItem];
            }

            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.getInventorySlotContainItemAndDamage(this.currentItemStack.itemID, this.currentItemStack.getItemDamageForDisplay()) == this.currentItem)
            {
                return;
            }

            this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.shiftedIndex], 1, par2);
        }
    }

    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    private int storePartialItemStack(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.itemID;
        int var3 = par1ItemStack.stackSize;
        int var4;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            var4 = this.getFirstEmptyStack();

            if (var4 < 0)
            {
                return var3;
            }
            else
            {
                if (this.mainInventory[var4] == null)
                {
                    this.mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
                }

                return 0;
            }
        }
        else
        {
            var4 = this.storeItemStack(par1ItemStack);

            if (var4 < 0)
            {
                var4 = this.getFirstEmptyStack();
            }

            if (var4 < 0)
            {
                return var3;
            }
            else
            {
                if (this.mainInventory[var4] == null)
                {
                    this.mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());

                    if (par1ItemStack.hasTagCompound())
                    {
                        this.mainInventory[var4].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
                    }
                }

                int var5 = var3;

                if (var3 > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize)
                {
                    var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
                }

                if (var5 > this.getInventoryStackLimit() - this.mainInventory[var4].stackSize)
                {
                    var5 = this.getInventoryStackLimit() - this.mainInventory[var4].stackSize;
                }

                if (var5 == 0)
                {
                    return var3;
                }
                else
                {
                    var3 -= var5;
                    this.mainInventory[var4].stackSize += var5;
                    this.mainInventory[var4].animationsToGo = 5;
                    return var3;
                }
            }
        }
    }

    /**
     * removed one item of specified itemID from inventory (if it is in a stack, the stack size will reduce with 1)
     */
    public boolean consumeInventoryItem(int _itemID)
    {
        int slot = this.getInventorySlotContainItem(_itemID);

        if (slot < 0)
        {
            return false;
        }
        else
        {
            if (--this.mainInventory[slot].stackSize <= 0)
            {
                this.mainInventory[slot] = null;
            }

            return true;
        }
    }

    /**
     * Get if a specifiied item id is inside the inventory.
     */
    public boolean hasItem(int par1)
    {
        int var2 = this.getInventorySlotContainItem(par1);
        return var2 >= 0;
    }
    
    // given an array of possible item stacks,
    // return whether any of those items are present in inventory
    public boolean hasItemOfSet(ItemStack[] _choices){
    	if(_choices == null) return false;
    	
    	for(ItemStack stack : _choices){
    		if(hasItem(stack.itemID)){
    			return true;
    		}
    	}   	
    	return false;
    }
    

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    public boolean addItemStackToInventory(ItemStack par1ItemStack)
    {
        int var2;

        if (par1ItemStack.isItemDamaged())
        {
            var2 = this.getFirstEmptyStack();

            if (var2 >= 0)
            {
                this.mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
                this.mainInventory[var2].animationsToGo = 5;
                par1ItemStack.stackSize = 0;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            do
            {
                var2 = par1ItemStack.stackSize;
                par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
            }
            while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < var2);

                return par1ItemStack.stackSize < var2;
        }
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        ItemStack[] var3 = this.mainInventory;

        if (par1 >= this.mainInventory.length)
        {
            var3 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }

        if (var3[par1] != null)
        {
            ItemStack var4;

            if (var3[par1].stackSize <= par2)
            {
                var4 = var3[par1];
                var3[par1] = null;
                return var4;
            }
            else
            {
                var4 = var3[par1].splitStack(par2);

                if (var3[par1].stackSize == 0)
                {
                    var3[par1] = null;
                }

                return var4;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        ItemStack[] var2 = this.mainInventory;

        if (par1 >= this.mainInventory.length)
        {
            var2 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }

        if (var2[par1] != null)
        {
            ItemStack var3 = var2[par1];
            var2[par1] = null;
            return var3;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        ItemStack[] var3 = this.mainInventory;

        if (par1 >= var3.length)
        {
            par1 -= var3.length;
            var3 = this.armorInventory;
        }

        var3[par1] = par2ItemStack;
    }

    /**
     * Gets the strength of the current item (tool) against the specified block, 1.0f if not holding anything.
     */
    public float getStrVsBlock(Block par1Block)
    {
        float var2 = 1.0F;

        if (this.mainInventory[this.currentItem] != null)
        {
            var2 *= this.mainInventory[this.currentItem].getStrVsBlock(par1Block);
        }

        return var2;
    }

    /**
     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
     * for crafting).
     */
    public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
    {
        int var2;
        NBTTagCompound var3;

        for (var2 = 0; var2 < this.mainInventory.length; ++var2)
        {
            if (this.mainInventory[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        for (var2 = 0; var2 < this.armorInventory.length; ++var2)
        {
            if (this.armorInventory[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        return par1NBTTagList;
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with the correct items.
     */
    public void readFromNBT(NBTTagList par1NBTTagList)
    {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];

        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);

            if (var5 != null)
            {
                if (var4 >= 0 && var4 < this.mainInventory.length)
                {
                    this.mainInventory[var4] = var5;
                }

                if (var4 >= 100 && var4 < this.armorInventory.length + 100)
                {
                    this.armorInventory[var4 - 100] = var5;
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.mainInventory.length + 4;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        ItemStack[] var2 = this.mainInventory;

        if (par1 >= var2.length)
        {
            par1 -= var2.length;
            var2 = this.armorInventory;
        }

        return var2[par1];
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.inventory";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Return damage vs an entity done by the current held weapon, or 1 if nothing is held
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        ItemStack var2 = this.getStackInSlot(this.currentItem);
        return var2 != null ? var2.getDamageVsEntity(par1Entity) : 1;
    }

    /**
     * Returns whether the current item (tool) can harvest from the specified block (actually get a result).
     */
    public boolean canHarvestBlock(Block par1Block)
    {
    	return false;
    	/*
    	 * Material.isHarbestable(Block) does not appear to be in v1.4.5 source code
        if (par1Block.blockMaterial.isHarvestable())
        {
            return true;
        }
        else
        {
            ItemStack var2 = this.getStackInSlot(this.currentItem);
            return var2 != null ? var2.canHarvestBlock(par1Block) : false;
        }
        */
    }

    /**
     * returns a citizen armor item (as itemstack) contained in specified armor slot.
     */
    public ItemStack armorItemInSlot(int par1)
    {
        return this.armorInventory[par1];
    }

    /**
     * Based on the damage values and maximum damage values of each armor item, returns the current armor value.
     */
    public int getTotalArmorValue()
    {
        int var1 = 0;

        for (int var2 = 0; var2 < this.armorInventory.length; ++var2)
        {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor)
            {
                int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
                var1 += var3;
            }
        }

        return var1;
    }

    /**
     * Damages armor in each slot by the specified amount.
     */
    public void damageArmor(int par1)
    {
        par1 /= 4;

        if (par1 < 1)
        {
            par1 = 1;
        }

        for (int var2 = 0; var2 < this.armorInventory.length; ++var2)
        {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor)
            {
                this.armorInventory[var2].damageItem(par1, this.citizen);

                if (this.armorInventory[var2].stackSize == 0)
                {
                    this.armorInventory[var2] = null;
                }
            }
        }
    }

    /**
     * Drop all armor and main inventory items.
     */
    public void dropAllItems()
    {
        int var1;

        for (var1 = 0; var1 < this.mainInventory.length; ++var1)
        {
            if (this.mainInventory[var1] != null)
            {
                this.citizen.dropPlayerItemWithRandomChoice(this.mainInventory[var1], true);
                this.mainInventory[var1] = null;
            }
        }

        for (var1 = 0; var1 < this.armorInventory.length; ++var1)
        {
            if (this.armorInventory[var1] != null)
            {
                this.citizen.dropPlayerItemWithRandomChoice(this.armorInventory[var1], true);
                this.armorInventory[var1] = null;
            }
        }
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        this.inventoryChanged = true;
    }

    public void setItemStack(ItemStack par1ItemStack)
    {
        this.itemStack = par1ItemStack;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.citizen.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(this.citizen) <= 64.0D;
    }

    /**
     * Returns true if the specified ItemStack exists in the inventory.
     */
    public boolean hasItemStack(ItemStack par1ItemStack)
    {
        int var2;

        for (var2 = 0; var2 < this.armorInventory.length; ++var2)
        {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(par1ItemStack))
            {
                return true;
            }
        }

        for (var2 = 0; var2 < this.mainInventory.length; ++var2)
        {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].isItemEqual(par1ItemStack))
            {
                return true;
            }
        }

        return false;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Copy the ItemStack contents from another InventoryCitizen instance
     */
    public void copyInventory(InventoryCitizen otherCitizen)
    {
        int index;

        for (index = 0; index < this.mainInventory.length; ++index)
        {
            this.mainInventory[index] = ItemStack.copyItemStack(otherCitizen.mainInventory[index]);
        }

        for (index = 0; index < this.armorInventory.length; ++index)
        {
            this.armorInventory[index] = ItemStack.copyItemStack(otherCitizen.armorInventory[index]);
        }
    }
	
}

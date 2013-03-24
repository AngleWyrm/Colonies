package colonies.src.buildings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import colonies.src.ClientProxy;
import colonies.src.Point;
import colonies.src.Utility;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityLumberjack;
import colonies.src.citizens.EntityWife;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityColoniesChest extends TileEntity implements IInventory {
	/**Name of the town chest belongs to*/
    public String townname;
	private ItemStack[] chestContents = new ItemStack[36];
	private LinkedList<EntityCitizen> occupants;
	private int maxOccupancy = 2;
	protected EntityCitizen jobPositions[] = {null, null};
	
	// TODO: This is probably better handled as createNewWorker()
	// public EntityCitizen workerType = new EntityCitizen(this.worldObj);
	
	public TileEntityColoniesChest(){
		super();
		occupants = new LinkedList<EntityCitizen>();
	}
	
	
	public boolean applyForJob(EntityCitizen candidate){
		if(candidate.worldObj.isRemote) return false;
		
		// find a job opening
		for(EntityCitizen availablePosition : jobPositions){
			if(availablePosition == null){
				// found empty job.
				
				// is candidate disqualified?
				if(!candidate.isMale) return false;
							
				EntityCitizen newCitizen = createNewWorker(candidate.worldObj);
				candidate.setNewJob(newCitizen);
				
				newCitizen.employer = this;
				availablePosition = newCitizen;
				
				Utility.chatMessage("Citizen #" + candidate.ssn + " hired as "+newCitizen.getJobTitle()+ " #"+newCitizen.ssn);
				return true;
			}// else position already occupied
		}
		return false;
	}
	// Override for various building types
	protected EntityCitizen createNewWorker(World theWorld){
		return new EntityCitizen(theWorld);
	}	
	
	// Normally this function appears in the Block class,
	// but there's a freakish requirement that the worldObj exist for the block
	// which is incompatible with GUI rendering.
	// So a duplicate appears within these TileEntity Chest clases for the renderer to use
	public String getTextureFile(){
		return ClientProxy.CHESTCONTAINER_PNG;
	}
	
	public Point getPoint(){
		return new Point(this.xCoord, this.yCoord, this.zCoord);
	}

    /** Determines if the check for adjacent chests has taken place. */
    public boolean adjacentChestChecked = false;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityColoniesChest adjacentChestZNeg;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityColoniesChest adjacentChestXPos;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityColoniesChest adjacentChestXNeg;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityColoniesChest adjacentChestZPosition;

    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;

    /** The angle of the lid last tick */
    public float prevLidAngle;

    /** The number of players currently using this chest */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    private byte facing;
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 27;
    }
    public byte getFacing() {
        return this.facing;
      }
    
    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.chestContents[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int index, int numItemsToRemove)
    {
        if (this.chestContents[index] != null)
        {
            ItemStack var3;

            if (this.chestContents[index].stackSize <= numItemsToRemove)
            {
                var3 = this.chestContents[index];
                this.chestContents[index] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.chestContents[index].splitStack(numItemsToRemove);

                if (this.chestContents[index].stackSize == 0)
                {
                    this.chestContents[index] = null;
                }

                this.onInventoryChanged();
                return var3;
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
        if (this.chestContents[par1] != null)
        {
            ItemStack var2 = this.chestContents[par1];
            this.chestContents[par1] = null;
            return var2;
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
        this.chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.colonieschest";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);       

        NBTTagList occupantsTagList = par1NBTTagCompound.getTagList("occupants");
        this.occupants = new LinkedList<EntityCitizen>();

        for (int var3 = 0; var3 < occupantsTagList.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)occupantsTagList.tagAt(var3);

            this.occupants.add(EntityCitizen.loadEntityCitizenFromNBT(var4));
        }
        
        
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.chestContents.length)
            {
                this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
        townname = par1NBTTagCompound.getString("townname");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
               
        
        NBTTagList occupantsTagList = new NBTTagList(); 

        for (int var3 = 0; var3 < this.occupants.size(); ++var3)
        {
            if (this.occupants.get(var3) != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                this.occupants.get(var3).writeEntityToNBT(var4);
                occupantsTagList.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("occupants", occupantsTagList);
        
        NBTTagList var2 = new NBTTagList(); 

        for (int var3 = 0; var3 < this.chestContents.length; ++var3)
        {
            if (this.chestContents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.chestContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
        
        par1NBTTagCompound.setString("townname", townname);
        
        super.writeToNBT(par1NBTTagCompound);
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    /**
     * Causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjacent chest check
     */
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }

    private void func_90009_a(TileEntityColoniesChest par1TileEntityChest, int par2)
    {
        if (par1TileEntityChest.isInvalid())
        {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked)
        {
            switch (par2)
            {
                case 0:
                    if (this.adjacentChestZPosition != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 1:
                    if (this.adjacentChestXNeg != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 2:
                    if (this.adjacentChestZNeg != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 3:
                    if (this.adjacentChestXPos != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }

    /**
     * Performs the check for adjacent chests to determine if this chest is double or not.
     */
    public void checkForAdjacentChests()
    {
        if (!this.adjacentChestChecked)
        {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPosition = null;

            if (this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof TileEntityColoniesChest)
            {
                this.adjacentChestXNeg = (TileEntityColoniesChest)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }

            if (this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof TileEntityColoniesChest)
            {
                this.adjacentChestXPos = (TileEntityColoniesChest)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }

            if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof TileEntityColoniesChest)
            {
                this.adjacentChestZNeg = (TileEntityColoniesChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }

            if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof TileEntityColoniesChest)
            {
                this.adjacentChestZPosition = (TileEntityColoniesChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }

            if (this.adjacentChestZNeg != null)
            {
                this.adjacentChestZNeg.func_90009_a(this, 0);
            }

            if (this.adjacentChestZPosition != null)
            {
                this.adjacentChestZPosition.func_90009_a(this, 2);
            }

            if (this.adjacentChestXPos != null)
            {
                this.adjacentChestXPos.func_90009_a(this, 1);
            }

            if (this.adjacentChestXNeg != null)
            {
                this.adjacentChestXNeg.func_90009_a(this, 3);
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

    	// TODO: Validate chest belongs to a town,
    	if(TileEntityTownHall.playerTown!=null){
    		// check places of employment
    		// check homes
    	}
    	       
        this.checkForAdjacentChests();
        ++this.ticksSinceSync;
        float var1;

        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numUsingPlayers = 0;
            var1 = 5.0F;
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord - var1), (double)((float)this.yCoord - var1), (double)((float)this.zCoord - var1), (double)((float)(this.xCoord + 1) + var1), (double)((float)(this.yCoord + 1) + var1), (double)((float)(this.zCoord + 1) + var1)));
            Iterator var3 = var2.iterator();

            
        }

        this.prevLidAngle = this.lidAngle;
        var1 = 0.1F;
        double var11;

        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
        {
            double var8 = (double)this.xCoord + 0.5D;
            var11 = (double)this.zCoord + 0.5D;

            if (this.adjacentChestZPosition != null)
            {
                var11 += 0.5D;
            }

            if (this.adjacentChestXPos != null)
            {
                var8 += 0.5D;
            }

            this.worldObj.playSoundEffect(var8, (double)this.yCoord + 0.5D, var11, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F)
        {
            float var9 = this.lidAngle;

            if (this.numUsingPlayers > 0)
            {
                this.lidAngle += var1;
            }
            else
            {
                this.lidAngle -= var1;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float var10 = 0.5F;

            if (this.lidAngle < var10 && var9 >= var10 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
            {
                var11 = (double)this.xCoord + 0.5D;
                double var6 = (double)this.zCoord + 0.5D;

                if (this.adjacentChestZPosition != null)
                {
                    var6 += 0.5D;
                }

                if (this.adjacentChestXPos != null)
                {
                    var11 += 0.5D;
                }

                this.worldObj.playSoundEffect(var11, (double)this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public void receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
        }
    }

    public void openChest()
    {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
    }

    public void closeChest()
    {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
        this.evictOccupants();
    }
    
    private void evictOccupants(){
    	if(occupants == null) return;
    	EntityCitizen evictedTennant;
    	while(!occupants.isEmpty()){
    		evictedTennant = occupants.getFirst();
    		evictedTennant.residence = null;
    		occupants.removeFirst();
    	}
    }
    
	public boolean hasVacancy(EntityCitizen candidate) {
		// gender bias may need further consideration for employment scenarios
		if(occupants.size() >= maxOccupancy){
			Utility.chatMessage("house full " + occupants.size());
			return false;
		}
		
		for(EntityCitizen occupant : occupants){
			if(occupant.isMale == candidate.isMale ){
				Utility.chatMessage("House already contains a " + (occupant.isMale? "male":"female"));
				return false;
			}
		}
		Utility.chatMessage("appropriate housing found " + occupants.size());
		return true;
	}
	public void moveIn(EntityCitizen newOccupant) {
		if(occupants == null) return;
		occupants.add(newOccupant);
	}


	public void fireEmployees() {
		for(EntityCitizen employee : jobPositions){
			if(employee != null){
				employee.employer = null;
				
				if(employee.isMale){
					employee.setNewJob(new EntityCitizen(employee.worldObj));
				}
				else{
					employee.setNewJob(new EntityWife(employee.worldObj));
				}
				
				employee = null;
			}
		}
	}

	public int addItemsToInventory(ItemStack stack) {
		// loop through inventory, topping off similar stacks or filling empty slots
		for(int index = 0; index < getSizeInventory(); ++index){
			ItemStack currentStack = getStackInSlot(index);
			if(currentStack == null){
				// empty slot, drop stack here
				setInventorySlotContents(index, stack);
				return 0; // ASSUMPTION: stack fits into empty slot
			} // else not null
			// TODO: top off partial stacks with same ID
		}
		return -1; // no room for this stack
	}

}

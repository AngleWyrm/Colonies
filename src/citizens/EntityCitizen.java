package colonies.src.citizens;

import java.util.HashMap;
import java.util.LinkedList;

import colonies.anglewyrm.src.EntityAITransferInventoryToEmployer;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityTownHall;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;
import paulscode.sound.Vector3D;

public class EntityCitizen extends EntityCreature implements IMob // TODO: Make EntityLiving 
{
	public boolean isMale = true;
	public static int ssnPool = 0;
	public int ssn;
	public String name;
	public InventoryCitizen inventory;
	public InventoryCitizen desiredInventory;
	public boolean wantsSomething = false;
	public float hunger = 20;
	
	public boolean hasHomeTown;
	public TileEntityTownHall homeTown;
	public TileEntityColoniesChest residence;
	public boolean firstVisit = true; // TODO: replace with check for housing availability
	
	// Employment Mechanics
	public TileEntityColoniesChest employer;
	public static LinkedList<EntityCitizen> jobTypes = new LinkedList<EntityCitizen>();
	private EntityCitizen[] skillPoints = new EntityCitizen[10];
	private int expertise = 0;

	//private HashMap <Integer, PathNavigator> paths;
	
	public EntityCitizen(World par1World) {
		super(par1World);
		ssn = ++ssnPool;
		texture = ColoniesMain.skinDefault;
		moveSpeed = ColoniesMain.citizenMoveSpeed;
		
		inventory = new InventoryCitizen(this);
		desiredInventory = new InventoryCitizen(this);
		desiredInventory.addItemStackToInventory(new ItemStack(Item.bread, 2));

		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIEatSomething(this));
	    tasks.addTask(1, new EntityAIFindShelterFromRain(this, 0.4f));
	    tasks.addTask(2, new EntityAIGatherDroppedItems(this, null)); // Testing, might move to specific citizens
	    tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
	    tasks.addTask(3, new EntityAIMaintainInventoryLevels(this));
	    tasks.addTask(4, new EntityAIJoinTown(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(5, new EntityAIGoHomeAtNight(this));
		tasks.addTask(5, new EntityAISeekEmployment(this));
		tasks.addTask(6, new EntityAITransferInventoryToEmployer(this));
	    tasks.addTask(6, new EntityAIVisit(this));
	    tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
	    tasks.addTask(7, new EntityAIGoHomeAtNight(this));

	    targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityCitizen){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
	    
	    // initialize skill points
	    for(EntityCitizen skill : skillPoints){
	    	skill = this;
	    }

	    //this.paths = new HashMap<Integer, PathNavigator>();
	}
	
	public void stopNavigating(){
		PathNavigate path = getNavigator();
		if(path != null){
			path.setPath(null, 0f);
		}
	}
	
	public ItemStack getItemFromChest(TileEntityColoniesChest chest, ItemStack desiredItem){
		if(chest == null || desiredItem == null) return null;
		ItemStack gatheredItems = null;
		for(int index = 0; index < chest.getSizeInventory(); index++){
			gatheredItems = chest.getStackInSlot(index);
			if((gatheredItems != null) && (gatheredItems.itemID == desiredItem.itemID)){
				// found item in chest
				gatheredItems = chest.decrStackSize(index, desiredItem.stackSize);
				this.inventory.addItemStackToInventory(gatheredItems);
				desiredItem.stackSize -= gatheredItems.stackSize;
				// TODO: Consolidate inventory
			}
		}
		return gatheredItems; // can return null
	}
	
	public void onLivingUpdate()
	{
        super.onLivingUpdate();
        
        // If not playing Peaceful mode, tick down hunger
        if(this.worldObj.difficultySetting != 0){
        	hunger = hunger - 0.0005f; // 0.0005f is about 1/2 hunger/minute
        	
        	// Starvation
        	if(hunger < 0){
        		hunger = 0;
        		this.damageEntity(DamageSource.starve, 1);
        		if(this.health <= 0 && !this.dead){
        			this.onDeath(DamageSource.starve);
        		}
        	}
        }

		// citizen status special effects
        // CLIENT SIDE ONLY
        // May move all special effects to a class module later       
        if(worldObj.isRemote) return;
        
        // TODO: get AI to update wantsSomething
        if(wantsSomething){ // updated by EntityAIMaintainInventoryLevels
        	//Utility.chatMessage("want #" + ssn); // is executed, not clear by whom
        	worldObj.spawnParticle("reddust", posX, posY + 2.5, posZ, 0.2,0.9,0.2);
		}
    }

	// Sounds
	Boolean citizenGreetings = true;
	protected String getLivingSound(){
		if(citizenGreetings){
			if(Utility.getLootCategory()==3){
				return "colonies.m-hello";
			}
		}
		return "";
    }
    protected String getHurtSound(){
        return "colonies.m-hurt";
    }
    protected String getDeathSound(){
    	if(ColoniesMain.offensiveLanguageFilter) return "";
        return "colonies.f-damnit";
    }
    
    protected void playStepSound(int par1, int par2, int par3, int par4){
        this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 1.0F);
    }
    /**
     * Args: itemstack, flag
     */
    public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack, boolean par2)
    {
        if (par1ItemStack == null)
        {
            return null;
        }
        else
        {
            EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896D + (double)this.getEyeHeight(), this.posZ, par1ItemStack);
            var3.delayBeforeCanPickup = 40;
            float var4 = 0.1F;
            float var5;

            if (par2)
            {
                var5 = this.rand.nextFloat() * 0.5F;
                float var6 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
                var3.motionX = (double)(-MathHelper.sin(var6) * var5);
                var3.motionZ = (double)(MathHelper.cos(var6) * var5);
                var3.motionY = 0.20000000298023224D;
            }
            else
            {
                var4 = 0.3F;
                var3.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
                var3.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
                var3.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * var4 + 0.1F);
                var4 = 0.02F;
                var5 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
                var4 *= this.rand.nextFloat();
                var3.motionX += Math.cos((double)var5) * (double)var4;
                var3.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                var3.motionZ += Math.sin((double)var5) * (double)var4;
            }

            this.worldObj.spawnEntityInWorld(var3);
            return var3;
        }
    }
    // Mob Loot for default Citizen
    protected int getDropItemId() {
    	int lootID=1;
    	switch(Utility.getLootCategory()){
    	case 1: // Common
    			switch(Utility.getLootCategory(3)){
    			case 1: return Item.appleRed.itemID;
    			case 2: return Item.shovelStone.itemID;
    			default:return Item.bakedPotato.itemID;
    			}
    	case 2: // Uncommon
    		return Item.swordWood.itemID;
    	case 3: // Rare
    		return Item.goldNugget.itemID;
    	default: // Legendary
    		return Item.emerald.itemID;
    	}
    }

	public String getTexture() {
		if (this.isInWater()){
			return ColoniesMain.skinMaleSwimming;
		}
		// TODO: return skin based on expertise in this job
		return ColoniesMain.skinDefault;
    }

	public int getTotalArmorValue() {
        return 10;
    }

	public int getMaxHealth(){
		return 20;
	}

	protected boolean isAIEnabled() {
        return true;
    }
	
	
	
	@Override
	public boolean canDespawn() {
		return false;
	}
	
	// TODO: This is not being called upon death.
	@Override
	public void onDeath(DamageSource _damageSource){
		// if this citizen belongs to a town, leave that town before dying
		if(this.homeTown != null){
			homeTown.leaveTown(this);
			this.homeTown = null;
		}
		if(_damageSource == DamageSource.starve){
			Utility.chatMessage("Citizen starved");
			this.worldObj.playSoundAtEntity(this, this.getDeathSound(), 0.15F, 1.0F);
		}
		super.onDeath(_damageSource);
	}

	public void respecSkillPoints()
	{	
		// add a point of current skill, lose a point of oldest skill
		for(int index = 9; index > 1; index--){
			skillPoints[index] = skillPoints[index-1];
		}
		expertise++;
	}
	
	public void setNewJob(EntityCitizen newJob) {
		// Replace this citizen with the provided new citizen
		
		// TODO: copy inventory, etc
		newJob.setPosition(this.posX, this.posY, this.posZ);
		
		TileEntityTownHall town = this.homeTown;

		this.homeTown.leaveTown(this);
		this.setDead();
		
		newJob.worldObj.spawnEntityInWorld(newJob);
		newJob.homeTown = town;
		newJob.homeTown.adoptTown(newJob); // may have to subvert this method
	}

	public String getJobTitle() {
		// TODO: String localization
		return "Wanderer";
	}

	public static EntityCitizen loadEntityCitizenFromNBT(NBTTagCompound var4) {	
		
		EntityCitizen entityCit = new EntityCitizen(Minecraft.getMinecraft().theWorld);
		entityCit.readEntityFromNBT(var4);
		
		return entityCit;
	
	}

}

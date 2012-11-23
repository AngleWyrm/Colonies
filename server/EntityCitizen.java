package colonies.server;

import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import colonies.client.ConfigFile;

public class EntityCitizen extends EntityMob {
	public EntityCitizen(World par1World) {
		super(par1World);
		this.texture = "/Citizen.png";
		this.moveSpeed = Float.parseFloat(ConfigFile.settings.getProperty("CitizenMoveSpeed"));
		
		this.tasks.addTask(0, new EntityAISwimming(this));
	    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
	    this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	    this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));

	    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}
	
	public void onLivingUpdate(){
		if (!this.worldObj.isRemote){
			if (this.worldObj.isDaytime()){
	        	// Daytime behaviors
	        }
	        else {
	        	// Night time behaviors
	        }
		}
        super.onLivingUpdate();
    }
	
	// Sounds
	protected String getLivingSound(){
        return "mob.zombie.say";
    }

    protected String getHurtSound(){
        return "mob.zombie.hurt";
    }
    
    protected String getDeathSound(){
        return "mob.zombie.death";
    }
    
    protected void playStepSound(int par1, int par2, int par3, int par4){
        this.worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
    }
	
    // Mob Loot
    protected int getDropItemId() {
        return Item.ingotGold.shiftedIndex;
    }
    
    protected void dropRareDrop(int par1){
        switch (this.rand.nextInt(2)){
            case 0:
                this.dropItem(Item.ingotIron.shiftedIndex, 1);
                break;
            case 1:
                this.dropItem(Item.helmetSteel.shiftedIndex, 1);
                break;
        }
    }
    
    protected void dropFewItems(boolean par1, int par2){
    	if(this.rand.nextInt(3) == 0) {
    		this.dropItem(Item.appleRed.shiftedIndex, 1);
    	}
    }
    
    
	public String getTexture() {
		return "/Citizen.png";
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
}

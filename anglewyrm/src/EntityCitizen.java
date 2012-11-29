package colonies.anglewyrm.src;

import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import java.util.Random;


public class EntityCitizen extends EntityMob {
	public EntityCitizen(World par1World) {
		super(par1World);
		this.texture = "/colonies/anglewyrm/gfx/PolkadotGirl.png";
		this.moveSpeed = Float.parseFloat(ConfigFile.get("CitizenMoveSpeed"));

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
        return "mob.cow.say";
    }

    protected String getHurtSound(){
        return "mob.cow.hurt";
    }

    protected String getDeathSound(){
        return "mob.cow.death";
    }

    protected void playStepSound(int par1, int par2, int par3, int par4){
        this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 1.0F);
    }

    // Mob Loot for default Citizen
    protected int getDropItemId() {
    	int lootID=1;
    	switch(Utility.getLootCategory()){
    	case 1: // Common
    			switch(Utility.getLootCategory(3)){
    			case 1:
    			case 2:
    			default:
    			}
    		break;
    	case 2: // Uncommon
    		break;
    	case 3: // Rare
    		break;
    	default: // Legendary
    	}
    	return lootID;
    }

	public String getTexture() {
		return "/colonies/anglewyrm/gfx/PolkadotGirl.png";
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

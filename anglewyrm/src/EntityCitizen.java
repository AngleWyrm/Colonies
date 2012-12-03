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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import colonies.vector67.src.BlockColoniesChest;
import colonies.anglewyrm.src.VacanciesQueue;

public class EntityCitizen extends EntityMob {
	public EntityCitizen(World par1World) {
		super(par1World);
		this.texture = "/colonies/grahammarcellus/gfx/minerskin.png";
		this.moveSpeed = Float.parseFloat(ConfigFile.get("CitizenMoveSpeed"));

		this.tasks.addTask(0, new EntityAISwimming(this));
	    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
	    this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	    this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));

	    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	    
	    this.job = jobs.unemployed;
	    this.skills = new HashMap<jobs, Integer>(10);
	    this.skills.put(jobs.unemployed, 10);
	    
	    this.citizenGreetings = Boolean.valueOf( ConfigFile.get("citizenGreetings") );
	}
	BlockColoniesChest home;
	public static enum jobs {unemployed, miner, farmer, builder, lumberjack, fisherman }
	public jobs job;
	public HashMap<jobs, Integer> skills;
	
	public void onLivingUpdate()
	{
		// Call baseAI for all citizens in range
		if (!this.worldObj.isRemote){
			BaseAI.onLivingUpdate(this);
		}
        super.onLivingUpdate();
    }

	// Sounds
	boolean citizenGreetings = true;
	protected String getLivingSound(){
		if(citizenGreetings){
			return "colonies.m-hello";
		}
		return "";
    }

    protected String getHurtSound(){
        return "colonies.f-ohyeah";
    }

    protected String getDeathSound(){
        return "colonies.f-damnit";
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
		return "/colonies/grahammarcellus/gfx/minerskin.png";
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

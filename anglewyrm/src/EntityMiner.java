package colonies.anglewyrm.src;

import java.util.HashMap;
import colonies.anglewyrm.src.Utility;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import colonies.anglewyrm.src.EntityCitizen.jobs;

public class EntityMiner extends EntityCitizen{
	public EntityMiner(World world){ 
		super(world);
		this.texture = ServerProxy.MINERSKIN_PNG;
		this.skills = new HashMap<jobs, Integer>(10);
	    this.skills.put(jobs.unemployed, 10);
	}
	
	public String getTexture() {
		return ServerProxy.MINERSKIN_PNG;
    }

	protected String getLivingSound(){
		if(citizenGreetings){
			if(Utility.getLootCategory()==2){ // only play on Uncommon
				return "colonies.m-hello";
			}
		}
		return "";
    }
	
    // Mob Loot for default Citizen
    protected int getDropItemId() {
    	int lootID=1;
    	switch(Utility.getLootCategory()){
    	case 1: // Common
    			switch(Utility.getLootCategory(3)){
    			case 1: return Item.appleRed.shiftedIndex;
    			case 2: return Item.pickaxeStone.shiftedIndex;
    			default:return Item.pickaxeSteel.shiftedIndex;
    			}
    	case 2: // Uncommon
    		return Item.coal.shiftedIndex;
    	case 3: // Rare
    		return Item.goldNugget.shiftedIndex;
    	default: // Legendary
    		return Item.ingotGold.shiftedIndex;
    	}
    }

	public void onLivingUpdate()
	{
		// custom miner behaviors
		super.onLivingUpdate();
		
		// sparkle test
		   worldObj.spawnParticle("reddust", 
				   posX + (rand.nextDouble() - 0.5D) * (double)width, 
				   (posY + rand.nextDouble() * (double)height) - 0.25D, 
				   posZ + (rand.nextDouble() - 0.5D) * (double)width, 
				   Utility.rng.nextFloat()+0.35, Utility.rng.nextFloat()+0.35, Utility.rng.nextFloat()+0.35);
    }
	
}

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

public class EntityWife extends EntityCitizen{
	public EntityWife(World world){ 
		super(world);
		this.texture = ConfigFile.getSkin("skinWife");
		this.skills = new HashMap<jobs, Integer>(10);
	    this.skills.put(jobs.unemployed, 10);
	}
	
	public String getTexture() {
		if(this.isInWater()){
			return ConfigFile.getSkin("skinFemaleSwimming");
		}
		return ConfigFile.getSkin("skinWife");
    }

	protected String getLivingSound(){
		if(citizenGreetings){
			if(Utility.getLootCategory()==3){
				return "colonies.f-hello";
			}
		}
		return "";
	}
    protected String getHurtSound(){
        return "colonies.f-ohyeah";
    }

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
    }

	// Mob Loot for default Citizen
    protected int getDropItemId() {
    	int lootID=1;
    	switch(Utility.getLootCategory()){
    	case 1: // Common
    			switch(Utility.getLootCategory(3)){
    			case 1: return Item.appleRed.shiftedIndex;
    			case 2: return Item.bone.shiftedIndex;
    			default:return Item.rottenFlesh.shiftedIndex;
    			}
    	case 2: // Uncommon
    		return Item.bread.shiftedIndex;
    	case 3: // Rare
    		return Item.bucketMilk.shiftedIndex;
    	default: // Legendary
    		return Item.emerald.shiftedIndex;
    	}
    }
	
}

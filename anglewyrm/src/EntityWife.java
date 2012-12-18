package colonies.anglewyrm.src;

import java.util.HashMap;

import net.minecraft.src.Item;
import net.minecraft.src.World;

public class EntityWife extends EntityCitizen{
	public EntityWife(World world){ 
		super(world);
		this.texture = ColoniesMain.skinWife;
		this.skills = new HashMap<jobs, Integer>(10);
	    this.skills.put(jobs.unemployed, 10);
	    
		// TODO: would like Wives to panic if attacked

	}
	
	public String getTexture() {
		if(this.isInWater()){
			return ColoniesMain.skinFemaleSwimming;
		}
		return ColoniesMain.skinWife;
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

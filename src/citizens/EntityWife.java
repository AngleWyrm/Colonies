package colonies.src.citizens;

import java.util.HashMap;

import colonies.src.ColoniesMain;
import colonies.src.Utility;


import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityWife extends EntityCitizen{
	public EntityWife(World world){ 
		super(world);
		this.isMale = false;
		this.texture = ColoniesMain.skinWife;
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityWife){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
	    
		// TODO: would like Wives to panic if attacked

	}
	
	public String getTexture() {
		if(this.isInWater()){
			return ColoniesMain.skinFemaleSwimming;
		}
		return ColoniesMain.skinWife;
    }
	
	public String getJobTitle(){
		return "Wife";
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
    			case 1: return Item.appleRed.itemID;
    			case 2: return Item.bone.itemID;
    			default:return Item.rottenFlesh.itemID;
    			}
    	case 2: // Uncommon
    		return Item.bread.itemID;
    	case 3: // Rare
    		return Item.bucketMilk.itemID;
    	default: // Legendary
    		return Item.emerald.itemID;
    	}
    }
	
}

package colonies.src.citizens;

import java.util.HashMap;

import colonies.src.ColoniesMain;
import colonies.src.Utility;


import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityPriestess extends EntityCitizen{
	public EntityPriestess(World world){ 
		super(world);
		this.isMale = false;
		this.texture = ColoniesMain.skinPriestess;
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityPriestess){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
	    
	    // TODO: Would like miners to go hostile with a pickaxe if attacked
	}
	
	public String getTexture() {
		if(this.isInWater()){
			return ColoniesMain.skinPriestessSwimming;
		}
		return ColoniesMain.skinPriestess;
    }
	
	public String getJobTitle(){
		return "Priestess";
	}
	
    protected String getHurtSound(){
        return "colonies.f-ohyeah";
    }

	protected String getLivingSound(){
		if(citizenGreetings){
			if(Utility.getLootCategory()==3){
				return "colonies.f-hello";
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
    			case 1: return Item.appleRed.itemID;
    			case 2: return Item.arrow.itemID;
    			default:return Item.bow.itemID;
    			}
    	case 2: // Uncommon
    		return Item.emerald.itemID;
    	case 3: // Rare
    		return Item.ingotIron.itemID;
    	default: // Legendary
    		return Item.ingotGold.itemID;
    	}
    }

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		// sparkle test
		// CLIENT SIDE ONLY
		if(worldObj.isRemote) return;
		
		worldObj.spawnParticle("reddust", 
		   posX + (rand.nextDouble() - 0.5D) * (double)width, 
		   (posY + rand.nextDouble() * (double)height) - 0.25D, 
		   posZ + (rand.nextDouble() - 0.5D) * (double)width, 
		   Utility.rng.nextFloat()+0.35, Utility.rng.nextFloat()+0.35, Utility.rng.nextFloat()+0.35);
    }
	
}

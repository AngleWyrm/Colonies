package colonies.src;

import java.util.HashMap;


import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;

public class EntityAlchemist extends EntityCitizen 
{
	public EntityAlchemist(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinAlchemist;

		// desiredInventory.addItemStackToInventory(new ItemStack(Item.fishingRod));

	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityAlchemist){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMaleSwimming;
		}
		return ColoniesMain.skinAlchemist;
	}

	protected String getLivingSound() {
		if (citizenGreetings){
			if (Utility.getLootCategory()>=3) { // Rare or above
				return "colonies.m-hello";
			}
		}
		return "";
	}

	// Mob Loot for default Citizen
	protected int getDropItemId() {
		int lootID=1;
		switch(Utility.getLootCategory()) {
			case 1: // Common
				return Item.appleRed.shiftedIndex;
			case 2: // Uncommon
				return Item.book.shiftedIndex;
			case 3: // Rare
				return Item.flintAndSteel.shiftedIndex;
			default: // Legendary
				return Item.ingotGold.shiftedIndex;
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
}
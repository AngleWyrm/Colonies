package colonies.src.citizens;

import java.util.HashMap;

import colonies.src.ColoniesMain;
import colonies.src.Utility;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
	
	public String getJobTitle(){
		return "Alchemist";
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
				return Item.appleRed.itemID;
			case 2: // Uncommon
				return Item.book.itemID;
			case 3: // Rare
				return Item.flintAndSteel.itemID;
			default: // Legendary
				return Item.ingotGold.itemID;
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
}
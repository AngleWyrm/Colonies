package colonies.src.citizens;

import java.util.HashMap;

import colonies.src.ColoniesMain;
import colonies.src.Utility;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import paulscode.sound.Vector3D;

public class EntityFisherman extends EntityCitizen {
	
	private Vector3D closestMinerChest;
	
	public EntityFisherman(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinFisherman;

		desiredInventory.addItemStackToInventory(new ItemStack(Item.fishingRod));
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityFisherman){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);

		
		// TODO: Would like miners to go hostile with a pickaxe if attacked
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMaleSwimming;
		}
		return ColoniesMain.skinFisherman;
	}
	
	public String getJobTitle(){
		return "Fisherman";
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
				return Item.fishRaw.itemID;
			case 2: // Uncommon
				return Item.fishingRod.itemID;
			case 3: // Rare
				return Item.goldNugget.itemID;
			default: // Legendary
				return Item.ingotGold.itemID;
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	
}
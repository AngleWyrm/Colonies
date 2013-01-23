package colonies.anglewyrm.src;

import java.util.HashMap;

import net.minecraft.src.Block;
import net.minecraft.src.BlockWood;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;
import colonies.boycat97.src.EntityAIGoToWork;
import colonies.src.ColoniesMain;

public class EntityLumberjack extends EntityCitizen {
	
	private Vector3D closestMinerChest;
	
	public EntityLumberjack(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinLumberjack;

		desiredInventory.addItemStackToInventory(new ItemStack(Item.axeSteel,1));
		desiredInventory.addItemStackToInventory(new ItemStack(Item.axeStone,2));
		desiredInventory.addItemStackToInventory(new ItemStack(Block.sapling,10));
		
		this.tasks.addTask(1, new EntityAIGoToWork(this,17, 0.4f));
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityLumberjack){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
		
		// TODO: Would like miners to go hostile with a pickaxe if attacked
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMinerSwimming;
		}
		return ColoniesMain.skinLumberjack;
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
				switch(Utility.getLootCategory(3)) {
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

	public void onLivingUpdate() {

		super.onLivingUpdate();
		
		
	}
	
}

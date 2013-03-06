package colonies.boycat97.src;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import paulscode.sound.Vector3D;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import colonies.src.citizens.EntityCitizen;

public class EntityGuard extends EntityCitizen {
	
	protected EnumGuardRank currentRank;	
	
	public enum EnumGuardRank {
		FootSoldier,
		Archer,
		Seargent	
	};
	
	public EntityGuard(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinGuard;
		this.currentRank = EnumGuardRank.FootSoldier;

		//TODO: Figure out all the items that are required for desires.
		//desiredInventory.addItemStackToInventory(new ItemStack(Item.fishingRod));
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityGuard){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);

	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMaleSwimming;
		} else			
			return this.texture;
	}

	// TODO: custom voices
	protected String getLivingSound() {
		if (ColoniesMain.citizenGreetings){
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
				return Item.bread.itemID;
			case 2: // Uncommon
				return Item.swordWood.itemID;
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

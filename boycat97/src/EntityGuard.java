package colonies.boycat97.src;

import java.util.HashMap;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;
import colonies.anglewyrm.src.ColoniesMain;
import colonies.anglewyrm.src.EntityCitizen;
import colonies.anglewyrm.src.Utility;
import colonies.anglewyrm.src.EntityCitizen.jobs;

public class EntityGuard extends EntityCitizen {
	
	protected EnumGuardRank currentRank;	
	
	public EntityGuard(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinGuard;
		this.currentRank = EnumGuardRank.FootSoldier;

		//TODO: Figure out all the items that are required for desires.
		//desiredInventory.addItemStackToInventory(new ItemStack(Item.fishingRod));

		this.skills = new HashMap<jobs, Integer>(10);
		this.skills.put(jobs.unemployed, 10);

	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMaleSwimming;
		} else			
			return ColoniesMain.skinGuard;
	}

//	protected String getLivingSound() {
//		if (citizenGreetings){
//			if (Utility.getLootCategory()>=3) { // Rare or above
//				return "colonies.m-hello";
//			}
//		}
//		return "";
//	}

	// Mob Loot for default Citizen
//	protected int getDropItemId() {
//		int lootID=1;
//		
//		switch(Utility.getLootCategory()) {
//			case 1: // Common
//				return Item.fishRaw.shiftedIndex;
//			case 2: // Uncommon
//				return Item.fishingRod.shiftedIndex;
//			case 3: // Rare
//				return Item.goldNugget.shiftedIndex;
//			default: // Legendary
//				return Item.ingotGold.shiftedIndex;
//		}
//	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	
}

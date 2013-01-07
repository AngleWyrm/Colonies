package colonies.kzolp67.src;

import java.util.HashMap;

import colonies.anglewyrm.src.ColoniesMain;
import colonies.anglewyrm.src.EntityCitizen;
import colonies.anglewyrm.src.Utility;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;

public class EntityHunter extends EntityCitizen {
	
	private Vector3D closestHunterChest;
	
	public EntityHunter(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinHunter;

		desiredInventory.addItemStackToInventory(new ItemStack(Item.bow,1));
		desiredInventory.addItemStackToInventory(new ItemStack(Item.arrow,64));

		this.skills = new HashMap<jobs, Integer>(10);
		this.skills.put(jobs.unemployed, 10);
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinHunter;
		}
		return ColoniesMain.skinHunter;
	}

	// Mob Loot for default Citizen
	protected int getDropItemId() {
		int lootID=1;
		switch(Utility.getLootCategory()) {
			case 1: // Common
				switch(Utility.getLootCategory(3)) {
					case 1: return Item.bow.shiftedIndex;
					default:return Item.arrow.shiftedIndex;
				}
			case 2: // Uncommon
				return Item.porkRaw.shiftedIndex;
			case 3: // Rare
				return Item.beefRaw.shiftedIndex;
			default: // Legendary
				return Item.chickenRaw.shiftedIndex;
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	
}

package colonies.src.citizens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IEntitySelector;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import colonies.src.Point;
import colonies.src.Utility;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.network.Player;
import net.minecraft.src.Entity;


public class EntityAIGatherDroppedItems extends EntityAIBase 
{
	
	private  EntityCitizen citizen;
	private ItemStack itemsLookingFor;
	private World taskEntityWorld;
	private boolean foundItem;
	private ItemStack[] itemsToPickUp;
	
	private final IEntitySelector field_82643_g;
	
	private Point destination;
	
	public EntityAIGatherDroppedItems(EntityCitizen _citizen, ItemStack[] _itemsToPickUp)
	{
		this.setMutexBits(0); // for testing wander, may change later
		itemsToPickUp = _itemsToPickUp;
		this.citizen = _citizen;
		this.foundItem = false;
		this.field_82643_g = (IEntitySelector)null;
	}

	@Override
	public boolean shouldExecute() {
		
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false; // must belong to a town
		if(citizen.inventory.isFull()) return false; // need room to pick up stuff
		
		return true;
	}
	
	@Override 
	public boolean continueExecuting()
	{
		//Look for item
		List<EntityItem> foundItems = this.getNearbyEntities(taskEntityWorld, 6.0D);
			
		placeInInventory(foundItems);
		return true;
	}
	
	private List<EntityItem> getNearbyEntities(World world, double range) {	
		//Scan for items in a set bounding box
		List<EntityItem> items = citizen.worldObj.selectEntitiesWithinAABB(EntityItem.class, citizen.boundingBox.expand(range, range, range), this.field_82643_g);		
		
		return items;
	}
	
	
	private void placeInInventory(List<EntityItem> itemsToRetrieve)
	{
		//looking through items to make sure they are on the ground and available to be retrieved
		for( EntityItem itemRetrieved : itemsToRetrieve) {
			// Do we want this item?
			if(shouldPickUpThisItem(itemRetrieved.item)){
				if (this.citizen.inventory.addItemStackToInventory(itemRetrieved.item) && itemRetrieved.onGround ){
					itemRetrieved.setDead();
				} // else pick up failed, move on to next item
			} // else shouldn't pick up this item, move on to next item
		} // done with retrieved item list
		
	}

	private boolean shouldPickUpThisItem(ItemStack testItem) {
		if(itemsToPickUp == null) return true; // no special set defined, so pick up everything
		
		// scan items to pick up, check for a match
		for(ItemStack pickMeUp : itemsToPickUp){
			if(testItem.itemID == pickMeUp.itemID) return true;
		} // else no match
		return false;
	}
	
}
package colonies.boycat97.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IEntitySelector;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityLumberjack;
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
	
	private final IEntitySelector field_82643_g;
	
	private Point destination;
	
	public EntityAIGatherDroppedItems(EntityCitizen _citizen)
	{
		this.setMutexBits(4);
		this.citizen = _citizen;
		this.foundItem = false;
		this.field_82643_g = (IEntitySelector)null;
	}

	@Override
	public boolean shouldExecute() {
		
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false; // must belong to a town
		
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
		//TODO: Determine what items they would like to have over just picking up everything.
		
		//looking through items to make sure they are on the ground and available to be retrieved
		for( EntityItem itemRetrieved : itemsToRetrieve) {
			if (this.citizen.inventory.addItemStackToInventory(itemRetrieved.item) && itemRetrieved.onGround ){
				itemRetrieved.setDead();
			} else {
				//if item was not retrievable move to next one.
				continue;
			}
		}
		
	}
	
	
	
}

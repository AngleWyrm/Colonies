package colonies.src.citizens;

import java.util.LinkedList;

import colonies.src.Point;
import colonies.src.Utility;


import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ItemStack;

// AI is given a set of stock levels to maintain in their personal inventory
// They will check their place of employment to gather shortages
public class EntityAIMaintainInventoryLevels extends EntityAIBase
{
	EntityCitizen citizen;
	static int INVENTORY_CHECK_FREQUENCY = 100; // about 5 seconds between scans
	int updateCounter = INVENTORY_CHECK_FREQUENCY;
	ItemStack objectOfDesire;
	Point destination;
	
	public EntityAIMaintainInventoryLevels(EntityCitizen _citizen){
		citizen = _citizen;	
		this.setMutexBits(1);
	}
   
	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false;
		if(!citizen.worldObj.isDaytime()) return false; // don't work during night
		
		// check for multiple wants quickly if nearby assigned chest
		if(destination == null){
			// TODO: will have this updated by joining/leaving jobs and building/destroying chests
			destination = new Point(citizen.homeTown.xCoord,  citizen.homeTown.yCoord, citizen.homeTown.zCoord);
		}
		
		if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) < 3){
			return wantsSomething();
		} // else too far away (would need to path there)
		
		// using memory of want, and updating want infrequently
		if(--updateCounter > 0){
			return citizen.wantsSomething;
		}
		else{
			updateCounter = INVENTORY_CHECK_FREQUENCY;
			return wantsSomething();
		}
	}
	
	public void startExecuting(){
		// TODO: select destination from employer, home, townhall
		destination.set(citizen.homeTown.xCoord, citizen.homeTown.yCoord, citizen.homeTown.zCoord);
		citizen.getNavigator().tryMoveToXYZ(citizen.homeTown.xCoord, citizen.homeTown.yCoord+1, citizen.homeTown.zCoord, 0.35f);
	}
	
    public boolean continueExecuting()
    {
    	// if arrived, get supplies and return false
    	Point p = new Point();
    	double range;
    	p.set(citizen.posX, citizen.posY, citizen.posZ);
    	range = p.getDistance(citizen.homeTown.getPoint());
    	if(range < 3.0){
    		// arrived at location, transfer supplies
    		citizen.stopNavigating();
    		destination = null;
    		
    		if(citizen.getItemFromChest(citizen.homeTown, objectOfDesire) != null){ 
    			Utility.chatMessage("Citizen #" +citizen.ssn + " got supplies");
    		}
    		citizen.wantsSomething = false;
    		objectOfDesire = null;
    		destination = null;
    		return false;
    	}// else still pathing to location
    	
    	boolean shouldContinue = !this.citizen.getNavigator().noPath();
    	// other reasons to bail, such as status changes
    	return shouldContinue;
    }

	
	// Returns true if citizen has an item in desiredInventory,
	// but the citizen's inventory has less of that item.
	private boolean wantsSomething()
	{
		// check main inventory
		for(int slot = 0; slot < citizen.desiredInventory.getSizeInventory(); slot++)
		{
			ItemStack thisDesire = citizen.desiredInventory.getStackInSlot(slot);
			if(thisDesire == null) continue;
			
			if(citizen.desiredInventory.countItems(thisDesire.itemID) > citizen.inventory.countItems(thisDesire.itemID)){
				//Utility.chatMessage(citizen.ssn + " wants something");
				objectOfDesire = thisDesire;
				citizen.wantsSomething = true;
				return true;
			}
		}
		// Utility.chatMessage("Citizen is content");
		objectOfDesire = null;
		citizen.wantsSomething = false;
		return false;
	}
}


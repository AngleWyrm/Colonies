package colonies.anglewyrm.src;

import java.util.LinkedList;

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
	
	public EntityAIMaintainInventoryLevels(EntityCitizen _citizen){
		citizen = _citizen;	
		this.setMutexBits(1);
	}
   
	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task
		if(citizen == null) return false;
		if(!citizen.hasHomeTown) return false;
		
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
		citizen.getNavigator().tryMoveToXYZ(citizen.homeTown.xCoord, citizen.homeTown.yCoord+1, citizen.homeTown.zCoord, 0.35f);
	}
	
    public boolean continueExecuting()
    {
    	// if arrived, get supplies and return false
    	Point p = new Point(citizen.posX, citizen.posY, citizen.posZ);
    	double range = p.getDistance(citizen.homeTown.getPoint());
    	if(range < 3.0){
    		// arrived at location, transfer supplies
    		citizen.getItemFromChest(citizen.homeTown, objectOfDesire); 
    		Utility.chatMessage("Citizen #" +citizen.ssn + " got supplies");
    		citizen.wantsSomething = false;
    		objectOfDesire = null;
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


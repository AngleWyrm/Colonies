package colonies.anglewyrm.src;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import colonies.src.Point;
import colonies.src.citizens.EntityCitizen;


public class EntityAITransferInventoryToEmployer extends EntityAIBase {

	private EntityCitizen citizen;
	private Point destination;
	
	public EntityAITransferInventoryToEmployer(EntityCitizen _citizen){
		setMutexBits(1);
		citizen = _citizen;
	}
	
	@Override
	public boolean shouldExecute() 
	{
		if(citizen.worldObj.isRemote) return false;     // not a real citizen
		if(!citizen.worldObj.isDaytime()) return false; // don't work at night
		if(citizen.employer == null) return false;      // must have chest to drop off
		if(!citizen.inventory.isFull()) return false;   // still has room
		
		return true;
	}
	
	@Override
	public void startExecuting(){
		destination = new Point(citizen.employer);
		citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y, destination.z, 0.35f);
	}
	
	@Override
	public boolean continueExecuting(){
		// are we there yet?
		if(destination.getDistance(citizen) > 3f){
			// not there yet, so try again later (if path works)
			return !citizen.getNavigator().noPath();
		} // else close enough!
		
		citizen.stopNavigating();
		destination = null;
		
		if(citizen.employer == null){ // chest is gone, drop everything
			citizen.inventory.dropAllItems();
			return false;
		} // else chest exists
			
		// Dump cargo into employer chest
		for(int index = 0; index < citizen.inventory.getSizeInventory(); ++index){
			int desiredCount, extraOnHand;
			ItemStack stack = citizen.inventory.getStackInSlot(index);
			if(stack == null) continue;
			desiredCount = citizen.desiredInventory.countItems(stack.itemID);
			extraOnHand = citizen.inventory.countItems(stack.itemID) - desiredCount;
			if(extraOnHand > 0){
				// have too many, transfer (or drop) some
				if(citizen.employer.addItemsToInventory(stack) == 0){
					// dropped off goods
					citizen.inventory.mainInventory[index] = null;
					continue;
				} // else failed to add items to chest
				citizen.dropPlayerItemWithRandomChoice(citizen.inventory.mainInventory[index], true);
			} // else no extra to drop off
		} // next citizen.inventory slot
		
		return false;
	}

}

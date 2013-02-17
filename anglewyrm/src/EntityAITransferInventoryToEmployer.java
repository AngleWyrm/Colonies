package colonies.anglewyrm.src;

import colonies.src.Point;
import colonies.src.citizens.EntityCitizen;
import net.minecraft.src.EntityAIBase;

public class EntityAITransferInventoryToEmployer extends EntityAIBase{

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
		
		// Dump cargo into employer chest
		// Until chest is full
		// or down to minimum desired levels of stuff
		
		return false;
	}

}

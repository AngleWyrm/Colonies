package colonies.anglewyrm.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import colonies.anglewyrm.src.Point;

public class EntityAIPlantSapling extends EntityAIBase 
{
	EntityCitizen citizen;
	Point destination;
	
	EntityAIPlantSapling(EntityCitizen _citizen)
	{
		citizen = _citizen;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() 
	{
		if(citizen == null) return false;
		
		// If citizen has a sapling in inventory, this task can be performed
		if(citizen.inventory.hasItem(Block.sapling.blockID)){
			return true;
		}
		// Otherwise this task should not be attempted
		return false;
	}

	@Override 
	public boolean continueExecuting()
	{
		if(destination == null){ // suitable destination not yet established
			//   continue looking for a good spot
			Point candidate = new Point();
			for(int i = 0; i < 10; ++i){
				// choose a spot 5-10m in a random direction
				candidate.set(citizen.posX, citizen.posY, citizen.posZ);
				candidate.polarTranslation(Utility.rng.nextRadian(), Math.PI/2, 5 + Utility.rng.nextInt(5));
				Utility.terrainAdjustment(citizen.worldObj, candidate);
				
				// if a plantable ground that can see sky, we're good
				//    set navigator, destination and return true
			}
			// still not found, continue searching later (return true) 
		} // else a destination has already been established during a previous update tick
		
		if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) <= 3){
			// remove a sapling from inventory
			// plant a sapling at destination
			destination = null;
			return false; // mission accomplished, quit executing this task
		} // else not there yet, or can't get there
		
		return !citizen.getNavigator().noPath();
	}
}

package colonies.anglewyrm.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.MathHelper;
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
			Utility.chatMessage("looking for a spot to plant sapling");
			Point candidate = new Point();
			for(int i = 0; i < 10; ++i){
				// choose a spot 5-10m away from citizen in a random direction
				candidate.polarTranslation(Utility.rng.nextRadian(), Math.PI/2, 5 + Utility.rng.nextInt(5));
				candidate.plus(citizen.posX, citizen.posY, citizen.posZ);
				Utility.terrainAdjustment(citizen.worldObj, candidate);
				
				// if we found dirt that can see sky, we're good; set navigator and return true
				if(citizen.worldObj.canBlockSeeTheSky((int)candidate.x, (int)candidate.y, (int)candidate.z)){
					Utility.chatMessage("candidate sees sky " + candidate.toRoundedString());
					if(citizen.worldObj.getBlockId((int)candidate.x, (int)candidate.y-1, (int)candidate.z) == Block.dirt.blockID){
						Utility.chatMessage("IS DIRT!");
						destination = candidate;
						citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y, destination.z, 0.35f);
						return true;
					}
					Utility.chatMessage("Not dirt?");
					
				} // else candidate didn't meet criteria, try another place
			} // still not found, continue searching later
			return true;
		} // else a destination has already been established during a previous update tick
		
		if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) <= 3) // close enough, plant sapling
		{
			// remove a sapling from inventory
			if(citizen.inventory.consumeInventoryItem(Block.sapling.blockID)){ // had a sapling (and used it up)
				citizen.worldObj.setBlockWithNotify((int)destination.x, (int)destination.y, (int)destination.z, Block.sapling.blockID);			
			} // else didn't have a sapling
			
			// mission accomplished; quit this task
			destination = null;
			return false;
		} // else not there yet, or can't get there
		
		return !citizen.getNavigator().noPath();
	}
}

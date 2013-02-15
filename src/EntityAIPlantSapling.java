package colonies.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.MathHelper;

public class EntityAIPlantSapling extends EntityAIBase 
{
	EntityCitizen citizen;
	Point destination;
	
	public EntityAIPlantSapling(EntityCitizen _citizen)
	{
		citizen = _citizen;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() 
	{
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false; // must belong to a town
		if(!citizen.worldObj.isDaytime()) return false; // only plant during day
		
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
			// Utility.chatMessage("looking for a spot to plant sapling");
			Point candidate = new Point();
			int blockID = 0; 
			for(int i = 0; i < 10; ++i){
				// choose a spot 5-10m away from citizen in a random direction
				candidate.polarTranslation(Utility.rng.nextRadian(), Math.PI/2, 5 + Utility.rng.nextInt(5));
				candidate.plus(citizen.posX, citizen.posY, citizen.posZ);
				
				// move destination away from logging camp if necessary
				if(candidate.getDistance(citizen.homeTown.xCoord, citizen.homeTown.yCoord, citizen.homeTown.zCoord) < 10){
					double theta = Math.atan2(candidate.y - citizen.homeTown.yCoord, candidate.x - citizen.homeTown.xCoord);
					candidate.polarTranslation(theta, Math.PI/2, 10);
				}			
				Utility.terrainAdjustment(citizen.worldObj, candidate);
				
				// if we found dirt that can see sky, we're good; set navigator and return true
				if(citizen.worldObj.canBlockSeeTheSky((int)candidate.x, (int)candidate.y, (int)candidate.z)){
					// Utility.chatMessage("candidate sees sky " + candidate.toRoundedString());
					blockID = citizen.worldObj.getBlockId((int)candidate.x, (int)candidate.y-1, (int)candidate.z);
					if(blockID == Block.grass.blockID || blockID == Block.dirt.blockID){
						// Utility.chatMessage("Found a suitable place to plant a sapling");
						destination = candidate;
						citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y, destination.z, 0.35f);
						return true;
					}
					// Utility.chatMessage("Not dirt/grass?");
					
				} // else candidate didn't meet criteria, try another place
			} // still not found, continue searching later
			return true;
		} // else a destination has already been established during a previous update tick
		
		if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) <= 3) // close enough, plant sapling
		{
			// remove a sapling from inventory
			if(citizen.inventory.consumeInventoryItem(Block.sapling.blockID)){ // had a sapling (and used it up)
				citizen.worldObj.setBlockWithNotify((int)destination.x, (int)destination.y, (int)destination.z, Block.sapling.blockID);			
				Utility.chatMessage("Citizen #" + citizen.ssn + " planted sapling");
			} // else didn't have a sapling
			destination = null;
			return false;
		} // else not there yet, or can't get there
		
		// Can we get there from here?
		if(citizen.getNavigator().noPath()){ // nope, cancel this attempt
			// Utility.chatMessage("Unable to path to selected location");
			destination = null;
			return false;
		}
		// else still travelling to destination
		return true;
	}
}

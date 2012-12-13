package colonies.anglewyrm.src;

import java.util.Iterator;

import colonies.vector67.src.BlockColoniesChest;

import net.minecraft.src.EntityAIBase;

// Join a town, and move to town hall
// movement suggests mutex bit 1
//
public class EntityAIJoinTown extends EntityAIBase {
	
	EntityCitizen citizen;

	public EntityAIJoinTown(EntityCitizen this_guy) {
		this.citizen = this_guy;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() 
	{
		if(ColoniesMain.townsList.isEmpty()) return false; // no town to join
		
		if( citizen.homeTown == null)
		{
			Utility.Debug("Citizen with no town");
			
			// Find closest town hall
			BlockTownHall closestTown = ColoniesMain.townsList.get(0);
			Iterator<BlockTownHall> iter = ColoniesMain.townsList.iterator();
			while(iter.hasNext()){
				BlockTownHall test = iter.next();
				if(distanceToBlock(test) < distanceToBlock(closestTown)){
					closestTown = test;
				}
			}
			citizen.homeTown = closestTown;
			
			Utility.Debug("Citizen moved into:" + citizen.homeTown.townName);
			return true;
		}
		return false; // already has a homeTown
	}

	private float distanceToBlock(BlockColoniesChest block){
		float distance = 1;
		
		// Quick estimate, no pathing: sqrt(dx^2 + dy^2 + dz^2)
		Utility.Debug("range check");
		
		return distance;
	}
}

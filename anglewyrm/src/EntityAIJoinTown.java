package colonies.anglewyrm.src;

import java.util.Iterator;

import colonies.vector67.src.BlockColoniesChest;

import net.minecraft.src.EntityAIBase;

// Join a town
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
			TileEntityTownHall closestTown = ColoniesMain.townsList.get(0);
			Iterator<TileEntityTownHall> iter = ColoniesMain.townsList.iterator();
			while(iter.hasNext()){
				TileEntityTownHall test = iter.next();
				if(distanceToBlock(test) < distanceToBlock(closestTown)){
					closestTown = test;
				}
			}
			citizen.homeTown = closestTown;		
			Utility.Debug("Citizen moved into: " + citizen.homeTown.townName);
			return true;
		}
		return false; // already has a homeTown
	}

	private double distanceToBlock(TileEntityTownHall tile){
		double distance = 1;
		Utility.Debug("range check");
		distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return distance;
	}
}

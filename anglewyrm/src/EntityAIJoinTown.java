package colonies.anglewyrm.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.util.Iterator;
import java.util.PriorityQueue;

import colonies.vector67.src.BlockColoniesChest;
import colonies.anglewyrm.src.TileEntityTownHall;
import net.minecraft.src.EntityAIBase;

// Join a town
public class EntityAIJoinTown extends EntityAIBase {
	public static double TOO_FAR_AWAY = 150;
	EntityCitizen citizen;

	public EntityAIJoinTown(EntityCitizen this_guy) {
		this.citizen = this_guy;
		// this.setMutexBits(1); // this is for later, when pathing to town
	}

	// Node class for priority queue
	class NodeTownDistance implements Comparable {
		TileEntityTownHall town;
		double distance;
		
		NodeTownDistance(TileEntityTownHall _town, double _distance){
			town = _town;
			distance = _distance;
		}
		
		@Override
		public int compareTo(Object other) {
			if(this.distance > ((NodeTownDistance) other).distance) return 1;
			if(this.distance < ((NodeTownDistance)other).distance) return -1;
			return 0;
		}
	}
	
	@Override
	public boolean shouldExecute() 
	{
		if( citizen.homeTown != null) return false;

		// check for town to join
		if((TileEntityTownHall.townsList==null)||(TileEntityTownHall.townsList.isEmpty())) return false; // no town to join
		Utility.Debug("Citizen with no town");
		
		// Make a list of towns, sorted by distance
		PriorityQueue<NodeTownDistance> towns = new PriorityQueue<NodeTownDistance>();
		for(TileEntityTownHall t: TileEntityTownHall.townsList){
			NodeTownDistance tmp = new NodeTownDistance(t, distanceToBlock(t));
			towns.add(tmp);
		}
		
		// apply to each town within walking distance
		while(!towns.isEmpty()){
			NodeTownDistance application = towns.poll();
			if(application.distance >= TOO_FAR_AWAY){
				Utility.Debug(application.town.townName + " is too far away; " + application.distance);
				return false;
			}
			if(application.town.adoptTown(citizen)){
				Utility.Debug(application.town.townName + " accepted applicant; pop:" + application.town.citizensList.size());
				return true;
			}
			else{
				Utility.Debug(application.town.townName + " rejected applicant; pop:" + application.town.citizensList.size());
			}
			// move on to next town in list
		}
		Utility.Debug("No towns remaining in list");
		return false;
	}

	private double distanceToBlock(TileEntityTownHall tile){
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}

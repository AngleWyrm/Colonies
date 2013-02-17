package colonies.src.citizens;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.util.Iterator;
import java.util.PriorityQueue;

import colonies.src.Point;
import colonies.src.Utility;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityTownHall;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ModLoader;

// Join a town
public class EntityAIJoinTown extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 40;
	private EntityCitizen citizen;
	Point destination;

	public EntityAIJoinTown(EntityCitizen _citizen) {
		citizen = _citizen;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task in the background
		if(!citizen.worldObj.isDaytime()) return false; // don't work at night
		if(TileEntityTownHall.playerTown == null) return false;  // no player town
		if( citizen.homeTown != null){
			if(!citizen.firstVisit) return false; // already visited town hall
			return true; // else not there yet
		} // else no home town
		
		// apply for citizenship
		if(TileEntityTownHall.playerTown.adoptTown(citizen)){
			// Utility.Debug("Application for citizenship accepted");
			return true;
		}
		else{
			// Utility.Debug("Application for citizenship declined");
			return false;
		}
	}
	
    public void startExecuting()
    {
    	if(citizen.homeTown == null) return;
    	destination = new Point(citizen.homeTown);
   		citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y+1, destination.z, 0.25f);
    }

    public boolean continueExecuting()
    {
    	if(citizen.homeTown == null || destination == null){
    		citizen.stopNavigating();
    		destination = null;
    		return false;
    	}
    	
   		// Arrive at town hall
   		if(destination.getDistance(citizen) < 3d){
			destination = null;
			citizen.stopNavigating();
			
			// assign housing
			citizen.firstVisit = false; // TODO: replace with housing availability check
			
			if(citizen.homeTown.homesList != null && !citizen.homeTown.homesList.isEmpty()){
				// move into first house with vacancy and complimentary gender
				for(TileEntityColoniesChest testHouse : citizen.homeTown.homesList){
					if(testHouse.hasVacancy(citizen)){
						citizen.residence = testHouse;
						citizen.residence.moveIn(citizen);
						break;
					}
				}
			}
			if(citizen.residence == null){
				// couldn't find a suitable residence, use town hall
				citizen.residence = citizen.homeTown;
				citizen.residence.moveIn(citizen);
			}
			return false; // moved in, quit process
		} // else still traveling to town hall
    
    	return !this.citizen.getNavigator().noPath();
    }
}
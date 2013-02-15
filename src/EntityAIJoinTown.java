package colonies.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.util.Iterator;
import java.util.PriorityQueue;

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
		if( citizen.homeTown != null && !citizen.firstVisit ) return false;
		if( TileEntityTownHall.playerTown == null ) return false;
		
		// apply for citizenship if necessary
		if( citizen.homeTown == null ){
			if( TileEntityTownHall.playerTown.adoptTown(citizen) ){
				Utility.Debug("Application for citizenship accepted");
				return true;
			}
			else{
				Utility.Debug("Application for citizenship declined");
				return false;
			}
		}		
		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    		this.citizen.getNavigator()
        		.tryMoveToXYZ(TileEntityTownHall.playerTown.xCoord, 
        	 			  TileEntityTownHall.playerTown.yCoord+1, 
        				  TileEntityTownHall.playerTown.zCoord, 0.25f);
    	}// else fubar
    }

    public boolean continueExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    		Utility.Debug("Continuing Journey");
    		
    		// Arrive at town hall
    		if(distanceToBlock(TileEntityTownHall.playerTown) < 2d){
    			destination = null;
    			citizen.stopNavigating();
    			
    			// assign housing
    			citizen.firstVisit = false; // TODO: replace with housing availability check
    			
    			if(TileEntityTownHall.playerTown.homesList != null && !TileEntityTownHall.playerTown.homesList.isEmpty()){
    				// move into first house with vacancy and complimentary gender
    				for(TileEntityColoniesChest testHouse : TileEntityTownHall.playerTown.homesList){
    					if(testHouse.hasVacancy(citizen)){
    						citizen.residence = testHouse;
    						citizen.residence.moveIn(citizen);
    						break;
    					}
    				}
    			}
    			if(citizen.residence == null){
    				// couldn't find a suitable residence, use town hall
    				citizen.residence = TileEntityTownHall.playerTown;
    				citizen.residence.moveIn(citizen);
    			}
    			return false;
    		} // else still travelling to town hall
    	}
    	boolean pathOK = !this.citizen.getNavigator().noPath();
    	if(pathOK){
    		Utility.Debug("...I can see it from here!");
    	}
    	else{
    		Utility.Debug("Oh no! I can't get there from here!");
    	}
    	return pathOK;
    }
   
	private double distanceToBlock(TileEntityTownHall tile){
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}
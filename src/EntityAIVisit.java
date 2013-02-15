package colonies.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ModLoader;

// Join a town
public class EntityAIVisit extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 64;
	private EntityCitizen citizen;
	private Point destination;
	private TileEntityColoniesChest destinationBlock;
	

	public EntityAIVisit(EntityCitizen _citizen) {
		this.citizen = _citizen;
		this.setMutexBits(1); 
	}

	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task in the background
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false;
		if(TileEntityTownHall.playerTown == null) return false;
		
		if(Utility.rng.nextFloat() > 0.10f ) return false; // sometimes just doesn't wanna	

		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown == null) return;   	
	   	// Utility.Debug("Going to visit a friend");
	   	
	   	// select destination
	   	LinkedList<TileEntityColoniesChest> choices = new LinkedList<TileEntityColoniesChest>();
	   	choices.add(TileEntityTownHall.playerTown);
	   	if(!TileEntityTownHall.playerTown.employersList.isEmpty()){
	   		for(TileEntityColoniesChest c: TileEntityTownHall.playerTown.employersList){
	   			choices.add(c);
	   		}
	   	}
	   	if(!TileEntityTownHall.playerTown.homesList.isEmpty()){
	   		for(TileEntityColoniesChest c: TileEntityTownHall.playerTown.homesList){
	   			choices.add(c);
	   		}
	   	}
	   	destination = new Point(choices.get(Utility.rng.nextInt(choices.size())));
	   	
	   	// if this destination is too close, scrap this attempt
	   	if(destination.getDistance(citizen) < 4f){
	   		destination = null;
	   		return;
	   	}
	   	
		this.citizen.getNavigator()
    		.tryMoveToXYZ(destination.x, destination.y, destination.z, 0.25f);
    }

    public boolean continueExecuting()
    {
    	if(destination == null) return false;
    	
    	if(TileEntityTownHall.playerTown == null){
    		citizen.stopNavigating();
    		destination = null;
    		return false;
    	}
    	
   		// Utility.Debug("Heading to neighbor's");
    	if(destination.getDistance(citizen) < 3d){
    			destination = null;
    			citizen.stopNavigating();
    			Utility.chatMessage("Citizen #" + citizen.ssn + " Visited a building");
    			return false;
    	}

    	if(this.citizen.getNavigator().noPath()){
    		destination = null;
    		citizen.stopNavigating();
    		return false;
    	}
    	return true;
    }
}

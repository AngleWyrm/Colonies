package colonies.anglewyrm.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import colonies.src.BlockColoniesChest;
import colonies.src.TileEntityColoniesChest;
import colonies.anglewyrm.src.TileEntityTownHall;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ModLoader;

// Join a town
public class EntityAIVisit extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 64;
	private EntityCitizen citizen;
	private TileEntityColoniesChest destination;
	

	public EntityAIVisit(EntityCitizen _citizen) {
		this.citizen = _citizen;
		this.setMutexBits(1); 
	}

	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task in the background
		if( !citizen.hasHomeTown  ) return false;
		if( TileEntityTownHall.playerTown == null ) return false;
		if( distanceToBlock(destination) < 6d) return false; // too close
		if(Utility.rng.nextFloat() > 0.0015f ) return false; // sometimes just doesn't wanna	
		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
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
    	   	destination = choices.get(Utility.rng.nextInt(choices.size()));
    	   	
    		this.citizen.getNavigator()
        		.tryMoveToXYZ(destination.xCoord, destination.yCoord, destination.zCoord, 0.25f);
    	}
    }

    public boolean continueExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    		Utility.Debug("Heading to neighbor's");
    		if(distanceToBlock(destination) < 3d){
    			destination = null;
    			Utility.chatMessage("Citizen #" + citizen.ssn + " Visited a building");
    			return false;
    		}
    	}

    	if(this.citizen.getNavigator().noPath()){
    		destination = null;
    		return false;
    	}
    	return true;
    }
   
	private double distanceToBlock(TileEntityColoniesChest tile){
		if(tile==null) return 999;
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}

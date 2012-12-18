package colonies.anglewyrm.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.util.Iterator;
import java.util.PriorityQueue;

import colonies.vector67.src.BlockColoniesChest;
import colonies.anglewyrm.src.TileEntityTownHall;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ModLoader;

// Join a town
public class EntityAIJoinTown extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 40;
	private EntityCitizen citizen;

	public EntityAIJoinTown(EntityCitizen _citizen) {
		this.citizen = _citizen;
		this.setMutexBits(1); 
	}

	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task in the background
		if( citizen.hasHomeTown && !citizen.firstVisit ) return false;
		if( TileEntityTownHall.playerTown == null ) return false;
		
		double distance = distanceToBlock(TileEntityTownHall.playerTown);
		Utility.Debug("Distance to town hall:" + distance);
		if( distance > TOO_FAR_AWAY ) return false;
		
		// apply for citizenship if necessary
		if( !citizen.hasHomeTown ){
			if( TileEntityTownHall.playerTown.adoptTown(citizen) ){
				Utility.Debug("Application for citizenship accepted");
			}
			else{
				Utility.Debug("Application for citizenship declined");
				return false;
			}
		}
		
		// Mission complete?
		if( distance < 3d){
			Utility.Debug("Citizen visited Town Hall");
			citizen.firstVisit = false;
			return false;
		}
		
		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    	   	Utility.Debug("Attempting Journey");
    		this.citizen.getNavigator()
        		.tryMoveToXYZ(TileEntityTownHall.playerTown.xCoord, 
        	 			  TileEntityTownHall.playerTown.yCoord+1, 
        				  TileEntityTownHall.playerTown.zCoord, 0.25f);
    	}
    }

    public boolean continueExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    		Utility.Debug("Continuing Journey");
    		if(distanceToBlock(TileEntityTownHall.playerTown) < 4d){
    			Utility.Debug("Journey Finished!");
				Minecraft.getMinecraft().thePlayer.addChatMessage("A new citizen arrived in town!");    			
    			citizen.firstVisit = false;
    			return false;
    		}
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

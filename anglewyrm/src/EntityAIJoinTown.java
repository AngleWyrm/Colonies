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
public class EntityAIJoinTown extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 150;
	public EntityCitizen citizen;

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
		if( distanceToBlock(TileEntityTownHall.playerTown) > TOO_FAR_AWAY ) return false;
		
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
		
		return true;
	}
	
    public void startExecuting()
    {
        this.citizen.getNavigator()
        	.tryMoveToXYZ(TileEntityTownHall.playerTown.xCoord, 
        	 			  TileEntityTownHall.playerTown.yCoord, 
        				  TileEntityTownHall.playerTown.zCoord, 0.2f);
    }

    public boolean continueExecuting()
    {
        return !this.citizen.getNavigator().noPath();
    }
   
	private double distanceToBlock(TileEntityTownHall tile){
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}

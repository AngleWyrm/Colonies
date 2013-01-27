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

// Go home at night
public class EntityAIGoHomeAtNight extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 30;
	private EntityCitizen citizen;
	private TileEntityColoniesChest destination;
	

	public EntityAIGoHomeAtNight(EntityCitizen _citizen) {
		this.citizen = _citizen;
		this.setMutexBits(1); 
	}

	@Override
	public boolean shouldExecute() 
	{
		// reasons to idle this task in the background
		if( citizen.worldObj.isDaytime() ) return false;
		if( TileEntityTownHall.playerTown == null ) return false;
		if((destination != null) && (distanceToBlock(destination) < 5d)) return false;

		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown == null) return;
    	
    	Utility.Debug("Going home for the night");   	
   	   	// select destination
    	if(citizen.residence != null){
    		destination = citizen.residence;
    	}
    	else{
    		destination = TileEntityTownHall.playerTown;
    	}
    	   	
   		this.citizen.getNavigator()
   			.tryMoveToXYZ(destination.xCoord, destination.yCoord, destination.zCoord, 0.25f);
    }

    public boolean continueExecuting()
    {
    	if(destination == null) return false;
    	
    	if(distanceToBlock(destination) < 5d){
			Utility.Debug("Bed time");
			// Minecraft.getMinecraft().thePlayer.addChatMessage("Sleeping");    			
			return false;
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
   
	private double distanceToBlock(TileEntityColoniesChest tile){
		if(tile==null) return 999;
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}
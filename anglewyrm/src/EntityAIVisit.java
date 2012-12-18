package colonies.anglewyrm.src;
//
// TODO: Possibly add a navigate to town hall phase
//
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;
import colonies.vector67.src.BlockColoniesChest;
import colonies.anglewyrm.src.TileEntityTownHall;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ModLoader;
import colonies.vector67.src.TileEntityColoniesChest;

// Join a town
public class EntityAIVisit extends EntityAIBase 
{
	public static double TOO_FAR_AWAY = 30;
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
		if(Utility.rng.nextFloat() > 0.75f ) return false; // sometimes just doesn't wanna	
		return true;
	}
	
    public void startExecuting()
    {
    	if(TileEntityTownHall.playerTown != null){
    	   	Utility.Debug("Going to visit a friend");
    	   	
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
    			Utility.Debug("Tea party!");
				//Minecraft.getMinecraft().thePlayer.addChatMessage("Visiting");    			
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
   
	private double distanceToBlock(TileEntityColoniesChest tile){
		if(tile==null) return 999;
		double distance = tile.getDistanceFrom(citizen.posX, citizen.posY, citizen.posZ);
		return Math.sqrt(distance);
	}
}

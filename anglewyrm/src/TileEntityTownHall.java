package colonies.anglewyrm.src;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IInventory;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest 
{
	// Town variables
	public int maxPopulation = 0;    // citizen count
	public double townPerimeter = 20; // meters
	private int spawnDelay = 600;    // count of calls to update function
	public String townName;
	
	public static TileEntityTownHall playerTown; // to be replace by a list later on

	public LinkedList<EntityCitizen>       citizensList;
	public LinkedList<TileEntityColoniesChest>  homesList;
	public LinkedList<TileEntityColoniesChest>  employersList;
	
	public TileEntityTownHall() {
		super();
		setTownName("MyTown");
		citizensList = new LinkedList<EntityCitizen>();
		employersList = new LinkedList<TileEntityColoniesChest>();
		homesList = new LinkedList<TileEntityColoniesChest>();
	}
	
	public boolean adoptTown(EntityCitizen newCitizen){
		if((citizensList==null)||(newCitizen==null)){
			Utility.Debug("null list and/or citizen");
			return false;
		}
		if(citizensList.size() >= maxPopulation){
			Utility.Debug(townName + " full: " + citizensList.size() + "/" + maxPopulation);
			return false;
		}
		if(citizensList.contains(newCitizen)){
			Utility.Debug("Already a resident!?");
			return false;
		}
		
		newCitizen.hasHomeTown = true;
		if(citizensList.offer(newCitizen)){
			Utility.Debug("Citizen joined town");
		}
		else{
			Utility.Debug("[ERR] citizenList refused offer");
			return false;
		}
		return true;
	}
	
	// One citizen leaves town membership
	public boolean leaveTown(EntityCitizen oldCitizen){
		if((citizensList==null)||(oldCitizen==null)) return false;
		if(!citizensList.contains(oldCitizen)) return false;
		citizensList.remove(citizensList.indexOf(oldCitizen));
		oldCitizen.hasHomeTown = false;
		Utility.Debug("Citizen left town");
  		Minecraft.getMinecraft().thePlayer.addChatMessage("A Citizen left " 
   				+ TileEntityTownHall.playerTown.townName + " (pop: " 
   				+ TileEntityTownHall.playerTown.citizensList.size() + "/"
   				+ TileEntityTownHall.playerTown.maxPopulation + ")");
  		
  		// TODO: free up houseing
 		return true;
	}
	
	// Empties town of all residents
	public boolean evacuateTown(){
		if(citizensList==null) return false;
		
		Utility.Debug("Evacuating " + townName);
		maxPopulation = 0;
		
		// remove citizens from town
		while(!citizensList.isEmpty()){
			EntityCitizen tmp = citizensList.getFirst();
			tmp.hasHomeTown = false;
			citizensList.removeFirst();
			Utility.Debug("Citizen left town");
		}
		
		if(playerTown==this){
			playerTown = null;
			Utility.Debug("playerTown removed");
		}
		else{
			Utility.Debug("another town removed");
		}
		return true;
	}
	
	public void setTownName(String newName){
		townName = newName;
	}
	
	@Override
    public String getInvName(){
        // return "container.townhall";
		return townName + " (pop: " + citizensList.size() + ")";
    }
	
	@Override
	public void updateEntity(){
        super.updateEntity();
        
        // DEBUG: workaround for double-chest placement bug
        if(this != playerTown) return;
        
        // Spawner system
        if(citizensList == null) return;
        if(citizensList.size() >= maxPopulation) return;
        
        if(--spawnDelay <= 0){
        	spawnDelay = 500;
        	Utility.Debug(townName + " spawner triggered");
 
        	// Choose citizen type to spawn
           	EntityCitizen newGuy;
           	if(Utility.rng.nextBoolean()){
           		newGuy = new EntityCitizen(worldObj);
           	}
           	else{
           		newGuy = new EntityWife(worldObj);
           	}
        	
        	// pick a random direction at the town perimeter
        	Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
        	Point q = new Point();
        	Utility.Debug(p.toString());
        	q.polarTranslation(Utility.rng.nextRadian(), (float)(Math.PI/2.2), 14d);
        	p.plus(q);
        	Utility.Debug(p.toString());
        	
        	// TODO: Validate and adjust ground level for mob landing

        	// spawn mob
            newGuy.setLocationAndAngles(Math.floor(p.x), Math.floor(p.y), Math.floor(p.z), Utility.rng.nextFloat()*360.0f, 0.0f);
            this.worldObj.spawnEntityInWorld(newGuy);
        }
        
	}
}

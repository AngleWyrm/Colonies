package colonies.anglewyrm.src;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.src.IInventory;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest 
{
	// Town variables
	public int maxPopulation = 4;
	public String townName;
	public LinkedList<BlockColoniesChest>        homesList;
	public LinkedList<BlockColoniesChest>        employersList;
	public LinkedList<EntityCitizen>             citizensList;
	public static LinkedList<TileEntityTownHall> townsList;
	private int spawnDelay = 500; // measured in updates
	
	public TileEntityTownHall() {
		super();
		citizensList = new LinkedList<EntityCitizen>();
		employersList = new LinkedList<BlockColoniesChest>();
		homesList = new LinkedList<BlockColoniesChest>();
		if(townsList == null){ // first town hall so start the towns list
			townsList = new LinkedList<TileEntityTownHall>();
		}
		setTownName("MyTown #" + (townsList.size()+1) );
		townsList.add(this);
	}
	
	public boolean adoptTown(EntityCitizen newCitizen){
		if((citizensList==null)||(newCitizen==null)) return false;
		if(citizensList.size() >= maxPopulation) return false;
		if(citizensList.contains(newCitizen)) return false;
		
		newCitizen.homeTown = this;
		citizensList.add(newCitizen);
		return true;
	}
	
	public boolean abandonTown(EntityCitizen oldCitizen){
		if((citizensList==null)||(oldCitizen==null)) return false;
		if(!citizensList.contains(oldCitizen)) return false;
		citizensList.remove(citizensList.indexOf(oldCitizen));
		oldCitizen.homeTown = null;
		return true;
	}
	
	public boolean evacuateTown(){
		if(citizensList==null) return false;
		
		// remove citizens from town
		while(!citizensList.isEmpty()){
			EntityCitizen tmp = citizensList.getFirst();
			citizensList.removeFirst();
			tmp.homeTown = null;
		}
		// remove town from town list
		if(townsList.contains(this)){
			townsList.remove(townsList.indexOf(this));
		}else{
			return false;
		}
		return true;
	}
	
	public void setTownName(String newName){
		townName = newName;
	}
	
	@Override
    public String getInvName(){
        return "container.townhall";
    }
	
	@Override
	public void updateEntity(){
        super.updateEntity();
        
        if(citizensList==null) return;
        if(maxPopulation <= citizensList.size()) return;
        
        if(--spawnDelay <= 0){
        	spawnDelay = 500;
        	Utility.Debug(townName + " spawner triggered, pop: " + citizensList.size());
        	EntityCitizen newGuy;
        	if(Utility.rng.nextInt(2)>0){
        		newGuy = new EntityCitizen(this.worldObj);
        	}else{
        		newGuy = new EntityWife(this.worldObj);
        	}        	
            newGuy.setLocationAndAngles(this.xCoord + 3, this.yCoord + 1, this.zCoord + 3, Utility.rng.nextFloat()*360.0f, 0.0f);
            this.worldObj.spawnEntityInWorld(newGuy);
        }
        
	}
}

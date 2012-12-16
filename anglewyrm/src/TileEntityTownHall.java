package colonies.anglewyrm.src;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.src.IInventory;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest 
{
	// Town variables
	public int maxPopulation = 10;
	public double townPerimeter = 30;
	public String townName;
	public LinkedList<BlockColoniesChest>        homesList;
	public LinkedList<BlockColoniesChest>        employersList;
	public LinkedList<EntityCitizen>             citizensList;
	public static LinkedList<TileEntityTownHall> townsList;
	private int spawnDelay = 1000; // measured in updates
	
	public TileEntityTownHall() {
		super();
		citizensList = new LinkedList<EntityCitizen>();
		citizensList.clear();
		employersList = new LinkedList<BlockColoniesChest>();
		employersList.clear();
		homesList = new LinkedList<BlockColoniesChest>();
		homesList.clear();
		if(townsList == null){ // first town hall so start the towns list
			townsList = new LinkedList<TileEntityTownHall>();
			townsList.clear();
		}
		if(townsList.isEmpty()){
			setTownName("FirstTown");
			maxPopulation = 0;
		}
		else{
			setTownName("MyTown#" + townsList.size() );
		}
		townsList.offer(this);
	}
	
	public boolean adoptTown(EntityCitizen newCitizen){
		if((citizensList==null)||(newCitizen==null)){
			Utility.Debug("null list and/or citizen");
			return false;
		}
		if(citizensList.size() >= maxPopulation){
			Utility.Debug(townName + " full: " + citizensList.size());
			return false;
		}
		if(citizensList.contains(newCitizen)){
			Utility.Debug("Already a resident!?");
			return false;
		}
		
		newCitizen.hasHomeTown = true;
		citizensList.offer(newCitizen);
		return true;
	}
	
	public boolean abandonTown(EntityCitizen oldCitizen){
		if((citizensList==null)||(oldCitizen==null)) return false;
		if(!citizensList.contains(oldCitizen)) return false;
		citizensList.remove(citizensList.indexOf(oldCitizen));
		oldCitizen.hasHomeTown = false;
		return true;
	}
	
	public boolean evacuateTown(){
		if(citizensList==null) return false;
		Utility.Debug("Evacuating " + townName + " (pop:"+citizensList.size()+")");
		// remove citizens from town
		while(!citizensList.isEmpty()){
			EntityCitizen tmp = citizensList.getFirst();
			citizensList.removeFirst();
			tmp.hasHomeTown = false;
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
        // return "container.townhall";
		return townName + " pop: " + citizensList.size();
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
        	Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
        	Point q = new Point();
        	Utility.Debug(p.toString());
        	q.polarTranslation(Utility.rng.nextRadian(), (float)(Math.PI/2.2), townPerimeter);
        	p.plus(q);
        	Utility.Debug(p.toString());
            newGuy.setLocationAndAngles(p.x, p.y, p.z, Utility.rng.nextFloat()*360.0f, 0.0f);
            this.worldObj.spawnEntityInWorld(newGuy);
        }
        
	}
}

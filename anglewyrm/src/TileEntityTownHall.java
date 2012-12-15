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
	public LinkedList<BlockColoniesChest> homesList;
	public LinkedList<BlockColoniesChest> employersList;
	public LinkedList<EntityCitizen>      citizens;
	private int spawnDelay = 500; // measured in updates
	
	public TileEntityTownHall() {
		super();
		citizens = new LinkedList<EntityCitizen>();
		employersList = new LinkedList<BlockColoniesChest>();
		homesList = new LinkedList<BlockColoniesChest>();
		if(ColoniesMain.townsList!=null){
			setTownName("MyTown #" + (ColoniesMain.townsList.size()+1) );
		}
	}
	
	public boolean adoptTown(EntityCitizen newCitizen){
		if((citizens==null)||(newCitizen==null)) return false;
		
		// verify this citizen is not already a member
		for(EntityCitizen c: citizens){
			if(c == newCitizen) return false;
		}
		citizens.add(newCitizen);
		return true;
	}
	
	public boolean abandonTown(EntityCitizen oldCitizen){
		if((citizens==null)||(oldCitizen==null)) return false;
		
		if(citizens.remove(oldCitizen)){
			return true;
		}
		return false;
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
        
        if(citizens==null) return;
        if(maxPopulation <= citizens.size()) return;
        
        if(--spawnDelay <= 0){
        	spawnDelay = 500;
        	Utility.Debug(townName + " spawner triggered, pop: " + citizens.size());
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

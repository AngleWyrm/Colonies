package colonies.anglewyrm.src;

import java.util.List;
import net.minecraft.src.IInventory;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest 
{
	// Town variables
	public int population = 0;
	public int maxPopulation = 4;
	public String townName;
	public List<BlockColoniesChest> homesList;
	public List<BlockColoniesChest> employersList;
	public List<EntityCitizen>      citizens;

	public TileEntityTownHall() {
		super();
	}
	
	public boolean adoptTown(EntityCitizen newCitizen)
	{
		// verify this citizen is not already a member
		for(EntityCitizen c: citizens){
			if(c == newCitizen) return false;
		}
		citizens.add(newCitizen);
		++population;
		return true;
	}
	
	public boolean abandonTown(EntityCitizen oldCitizen){
		if(citizens.remove(oldCitizen)){
			--population;
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
        
        // TODO: Spawner code goes here
        if(population >= maxPopulation) return;
        
        
	}
}

package colonies.anglewyrm.src;

import java.util.List;
import net.minecraft.src.IInventory;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest implements IInventory{

	public TileEntityTownHall() {
		super();
	}
	
	public static String townName;
	public static List<BlockColoniesChest> homesList;
	public static List<BlockColoniesChest> employersList;
	
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
	}
}

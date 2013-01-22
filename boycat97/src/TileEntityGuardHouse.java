package colonies.boycat97.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityGuardHouse extends TileEntityColoniesChest {
	
	public static TileEntityGuardHouse guardHouse = new TileEntityGuardHouse();
	
	public TileEntityGuardHouse() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.guardhouse";
    }
}

package colonies.anglewyrm.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityHouse extends TileEntityColoniesChest {

	public static TileEntityHouse house = new TileEntityHouse();
	
	private EntityCitizen[] owners;
	
	public TileEntityHouse() {
		super();
	}

	@Override
    public String getInvName(){
        return "House";
    }

}

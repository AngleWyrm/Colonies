package colonies.src.buildings;

import colonies.src.ClientProxy;
import net.minecraft.inventory.IInventory;

public class TileEntityHouse extends TileEntityColoniesChest {

	public static TileEntityHouse house = new TileEntityHouse();
	
	public TileEntityHouse() {
		super();
	}

	@Override
    public String getInvName(){
        return "House";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.HOUSECHEST_PNG;
	}


}

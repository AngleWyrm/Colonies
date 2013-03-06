package colonies.boycat97.src;

import net.minecraft.inventory.IInventory;
import colonies.src.ClientProxy;
import colonies.src.buildings.TileEntityColoniesChest;

public class TileEntityGuardHouse extends TileEntityColoniesChest {
	
	public static TileEntityGuardHouse guardHouse = new TileEntityGuardHouse();
	
	public TileEntityGuardHouse() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.guardhouse";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.GUARDHOUSE_PNG;
	}

}

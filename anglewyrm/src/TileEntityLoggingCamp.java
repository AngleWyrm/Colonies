package colonies.anglewyrm.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityLoggingCamp extends TileEntityColoniesChest implements IInventory{

	public TileEntityLoggingCamp() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.loggingcamp";
    }

}

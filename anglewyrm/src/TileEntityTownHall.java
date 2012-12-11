package colonies.anglewyrm.src;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityTownHall extends TileEntityColoniesChest implements IInventory{

	public TileEntityTownHall() {
		super();
	}
	
	@Override
    public String getInvName()
    {
        return "container.townhall";
    }
}

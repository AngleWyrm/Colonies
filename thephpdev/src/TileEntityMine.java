package colonies.thephpdev.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityMine extends TileEntityColoniesChest {

	public TileEntityMine() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.mine";
    }

}

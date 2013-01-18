package colonies.anglewyrm.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityAlchemistShop extends TileEntityColoniesChest {

	public static TileEntityAlchemistShop alchemistShop = new TileEntityAlchemistShop();
	
	public TileEntityAlchemistShop() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.alchemistShop";
    }

}

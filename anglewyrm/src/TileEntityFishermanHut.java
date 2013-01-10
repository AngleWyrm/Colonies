package colonies.anglewyrm.src;

import net.minecraft.src.IInventory;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityFishermanHut extends TileEntityColoniesChest {

	public static TileEntityFishermanHut fishermanHut = new TileEntityFishermanHut();
	
	public TileEntityFishermanHut() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.FishermanHut";
    }

}

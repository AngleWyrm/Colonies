package colonies.src.tileentity;

import net.minecraft.src.IInventory;
import colonies.src.ClientProxy;
import colonies.src.TileEntityColoniesChest;

public class TileEntityFishermanHut extends TileEntityColoniesChest {

	public static TileEntityFishermanHut fishermanHut = new TileEntityFishermanHut();
	
	public TileEntityFishermanHut() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.fishermanHut";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.FISHERMANHUT_PNG;
	}


}

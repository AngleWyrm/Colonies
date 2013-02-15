package colonies.src;

import net.minecraft.src.IInventory;

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

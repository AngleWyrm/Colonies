package colonies.kzolp67.src;

import net.minecraft.src.IInventory;
import colonies.src.ClientProxy;
import colonies.vector67.src.TileEntityColoniesChest;

public class TileEntityHunterBlind extends TileEntityColoniesChest {

	public static TileEntityHunterBlind hunterBlind = new TileEntityHunterBlind();
	
	public TileEntityHunterBlind() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.hunterBlind";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.HUNTERBLIND_PNG;
	}


}
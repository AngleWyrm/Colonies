package colonies.src.buildings;

import net.minecraft.src.IInventory;
import colonies.src.ClientProxy;

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
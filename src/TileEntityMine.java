package colonies.src;

import net.minecraft.src.IInventory;

public class TileEntityMine extends TileEntityColoniesChest {

	public TileEntityMine() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.mine";
    }

	@Override
	public String getTextureFile(){
		return ClientProxy.MINERCHEST_PNG;
	}


}

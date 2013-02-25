package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityEnchanter;
import net.minecraft.src.IInventory;
import net.minecraft.src.World;

public class TileEntityEnchanterChest extends TileEntityColoniesChest {

	public TileEntityEnchanterChest() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.enchanter";
    }

	@Override
	public String getTextureFile(){
		return ClientProxy.ENCHANTERCHEST_PNG;
	}

    @Override
    public EntityCitizen createNewWorker(World theWorld){
    	return new EntityEnchanter(theWorld);
    }

}

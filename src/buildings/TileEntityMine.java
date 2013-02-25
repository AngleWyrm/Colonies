package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityMiner;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

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

    @Override
    public EntityCitizen createNewWorker(World theWorld){
    	return new EntityMiner(theWorld);
    }

}

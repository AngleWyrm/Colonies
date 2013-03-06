package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityFisherman;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

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

    @Override
    public EntityCitizen createNewWorker(World theWorld){
    	return new EntityFisherman(theWorld);
    }

}

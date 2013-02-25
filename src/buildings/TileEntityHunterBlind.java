package colonies.src.buildings;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import colonies.src.ClientProxy;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityHunter;

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

    @Override
    public EntityCitizen createNewWorker(World theWorld){
    	return new EntityHunter(theWorld);
    }

}
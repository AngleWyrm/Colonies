package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.Utility;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityLumberjack;
import net.minecraft.src.IInventory;
import net.minecraft.src.World;

public class TileEntityLoggingCamp extends TileEntityColoniesChest {

	private EntityLumberjack[] jobPositions = {null, null};
	
	public TileEntityLoggingCamp() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.loggingcamp";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.LOGGINGCAMP_PNG;
	}

	@Override
	public EntityCitizen createNewWorker(World theWorld){
		return new EntityLumberjack(theWorld);
	}
}

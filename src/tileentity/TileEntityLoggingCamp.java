package colonies.src.tileentity;

import net.minecraft.src.IInventory;
import colonies.src.ClientProxy;
import colonies.src.TileEntityColoniesChest;

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

	public boolean applyForJob(EntityCitizen _candidate){
		if(_candidate.worldObj.isRemote) return false;
		
		// find a job opening
		for(EntityLumberjack availablePosition : jobPositions){
			if(availablePosition == null){
				// foundd empty job.
				// TODO: Does candidate qualify for position?
				
				availablePosition = replaceCitizen(_candidate);
				return true;
			}
		}
		return false;
	}
	
	private EntityLumberjack replaceCitizen(EntityCitizen _oldCitizen){
		EntityLumberjack newCitizen = new EntityLumberjack(_oldCitizen.worldObj);
		newCitizen.setPosition(_oldCitizen.posX, _oldCitizen.posY, _oldCitizen.posZ);
		// TODO: copy inventory, etc
		_oldCitizen.setDead();
		newCitizen.worldObj.spawnEntityInWorld(newCitizen);
		return newCitizen;
	}

}

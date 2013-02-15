package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.Utility;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityLumberjack;
import net.minecraft.src.IInventory;

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
	public boolean applyForJob(EntityCitizen _candidate){
		if(_candidate.worldObj.isRemote) return false;
		
		// find a job opening
		for(EntityLumberjack availablePosition : jobPositions){
			if(availablePosition == null){
				// foundd empty job.
				
				// is candidate qualified?
				if(!_candidate.isMale) return false;
				
				EntityLumberjack newCitizen = new EntityLumberjack(_candidate.worldObj);
				_candidate.employer = this;
				_candidate.setNewJob(newCitizen);				
				availablePosition = newCitizen;
				Utility.chatMessage("Citizen #" + _candidate.ssn + " hired as Lumberjack #"+newCitizen.ssn);
				return true;
			}// else position already occupied
		}
		return false;
	}
}

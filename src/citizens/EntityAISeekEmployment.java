package colonies.src.citizens;

import colonies.src.Utility;
import colonies.src.buildings.TileEntityTownHall;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISeekEmployment extends EntityAIBase 
{
	EntityCitizen citizen;

	public EntityAISeekEmployment(EntityCitizen _citizen){
		citizen = _citizen;
	}
	
	@Override
	public boolean shouldExecute()
	{
		// reasons to idle this process
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false;
		if(citizen.homeTown.employersList == null) return false;
		if(citizen.homeTown.employersList.isEmpty()) return false;
		if(!citizen.worldObj.isDaytime()) return false; // don't work at night
		if(citizen.firstVisit) return false; // wait until citizen visits town hall
		if(citizen.worldObj.isRemote) return false; // is this a real citizen?
		
		// TODO: if already employed, then usually skip this
		if(citizen.employer != null) return false;
		
		return citizen.isMale; // Temporary construct
	}
	
	@Override
	public void startExecuting(){	
		// apply for jobs in list
		boolean gotJob;
		gotJob = citizen.homeTown.getNextJob(citizen);
		
		// If didn't get a job, convert to plain citizen
		if(!gotJob){
			if(citizen.isMale){
				// convert to default wanderer citizen
				citizen.setNewJob(new EntityCitizen(citizen.worldObj));
			}
			else{
				// convert to default wife
				citizen.setNewJob(new EntityWife(citizen.worldObj));
			}
		}
	}
}

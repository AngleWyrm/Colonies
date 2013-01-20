package colonies.anglewyrm.src;

import net.minecraft.src.EntityAIBase;

public class EntityAISeekEmployment extends EntityAIBase 
{
	EntityCitizen citizen;

	EntityAISeekEmployment(EntityCitizen _citizen){
		citizen = _citizen;
	}
	
	@Override
	public boolean shouldExecute()
	{
		// reasons to idle this process
		if(TileEntityTownHall.playerTown == null) return false;
		if(TileEntityTownHall.playerTown.employersList == null) return false;
		if(TileEntityTownHall.playerTown.employersList.isEmpty()) return false;
		
		// for now, fill first available job in employers list
		EntityCitizen newJob = TileEntityTownHall.playerTown.getNextJob();
		if(newJob != null){
			citizen.setNewJob(newJob);
		}
		
		return false;
	}
	

}

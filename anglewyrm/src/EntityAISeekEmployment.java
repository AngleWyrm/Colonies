package colonies.anglewyrm.src;

import colonies.src.buildings.TileEntityTownHall;
import colonies.src.citizens.EntityCitizen;
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
		
		// TODO: if already employed, then usually skip this
		if(citizen.employer != null) return false;
		
		return true;
	}
	
	@Override
	public boolean continueExecuting(){
		
		// assemble list of available jobs
		// sort that list according to citizen's skill
		// apply for jobs in list
		
		return false;
	}
	

}
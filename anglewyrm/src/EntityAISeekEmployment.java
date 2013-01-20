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
		
		
		return false;
	}
	

}

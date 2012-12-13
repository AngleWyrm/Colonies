package colonies.anglewyrm.src;

import net.minecraft.src.EntityAIBase;

// Join a town, and move to town hall
// movement suggests mutex bit 1
//
public class EntityAIJoinTown extends EntityAIBase {
	
	EntityCitizen citizen;

	public EntityAIJoinTown(EntityCitizen this_guy) {
		this.citizen = this_guy;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() 
	{
		if( citizen.homeTown == null){
			Utility.Debug("Citizen with no town");
			// TODO: Don't just 'move in', travel to town hall
			citizen.homeTown = ColoniesMain.townsList.get(0);
			Utility.Debug("Citizen moved into:" + citizen.homeTown.townName);
			return true;
		}
		return false; // already has a homeTown
	}

}

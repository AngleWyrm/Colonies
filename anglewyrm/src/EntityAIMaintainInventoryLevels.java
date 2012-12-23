package colonies.anglewyrm.src;

import java.util.LinkedList;

import net.minecraft.src.EntityAIBase;
import net.minecraft.src.ItemStack;

// AI is given a set of stock levels to maintain in their personal inventory
// They will check their place of employment to gather shortages
public class EntityAIMaintainInventoryLevels extends EntityAIBase 
{
	EntityCitizen citizen;
	int updateCounter = 100; // about 5 seconds between inventory scans 
	
	public EntityAIMaintainInventoryLevels(EntityCitizen _citizen){
		citizen = _citizen;
		
	}
   
	@Override
	public boolean shouldExecute() {
		return false;
	}
}

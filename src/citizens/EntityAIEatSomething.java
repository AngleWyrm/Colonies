package colonies.src.citizens;

import colonies.src.Utility;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;

public class EntityAIEatSomething extends EntityAIBase
{
    private EntityCitizen citizen;

    public EntityAIEatSomething(EntityCitizen _citizen)
    {
        this.citizen = _citizen;
        this.setMutexBits(4);
    }

    public boolean shouldExecute()
    {
    	// hunger doesn't apply to peaceful mode
    	if(citizen == null) return false;
    	if(citizen.worldObj.difficultySetting == 0) return false;
    	if(citizen.hunger > 16) return false; // not hungry
    	
    	// if has edible item in pack then yes
    	// might postpone the search until ContinueExecuting() to minimize CPU load
    	return true;
    }

    public boolean continueExecuting()
    {
    	if(citizen == null) return false;
    	
    	if(citizen.inventory.eatFood()){
    		Utility.chatMessage("Ate something " + citizen.hunger);    	
    	}
    	else{
    		// Utility.chatMessage("Nothing to eat " + citizen.hunger);
    	}

    	// if still hungry, try again
        return (citizen.hunger < 16);
    }

    public void startExecuting()
    {
    }
}
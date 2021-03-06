package colonies.vector67.src;

import java.util.Iterator;
import java.util.List;

import colonies.src.citizens.EntityMiner;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;

public class EntityAIMine extends EntityAIBase {
	private boolean haspickaxe;
	private EntityMiner miner;
	
	public EntityAIMine(EntityMiner var1miner)
    {
		miner = var1miner;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
    public boolean shouldExecute()
    {
        // Check a bunch of things like do we have a pickaxe and other things we might need.
		if(miner.hasHomeTown&&miner.hasPickaxe())
		{
			return true;
		}
		return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	// Same logic as shouldExecute
        return this.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
     
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        
    }

}

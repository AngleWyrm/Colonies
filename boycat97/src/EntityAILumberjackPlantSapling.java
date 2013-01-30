package colonies.boycat97.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;

public class EntityAILumberjackPlantSapling extends EntityAIGoToWork {

	public EntityAILumberjackPlantSapling(EntityLiving par1EntityLiving, int BlockID, float _movementSpeed) {
		super(par1EntityLiving, BlockID, _movementSpeed);
	}
	
	@Override
	public void startExecuting() {
		
		//set the bottom and top block IDs so the Lumberjack knows where to place the sapling.
		int bottomBlockID = this.taskEntityWorld.getBlockId(MathHelper.floor_double(this.taskEntity.posX+1), MathHelper.floor_double(this.taskEntity.posY-2), MathHelper.floor_double(this.taskEntity.posZ));
		int topBlockID = this.taskEntityWorld.getBlockId(MathHelper.floor_double(this.taskEntity.posX+1), MathHelper.floor_double(this.taskEntity.posY-1), MathHelper.floor_double(this.taskEntity.posZ));
		
		//when they are the right setup for a sapling place it.
		if (  ( topBlockID == 0 || topBlockID == Block.grass.blockID) && bottomBlockID == Block.dirt.blockID) 
		{
			this.placeSapling();
			super.startExecuting();
		} else {
			 this.lookForWorkLocation();
		}
		
	}
	
	
	private void placeSapling() 
	{		
		this.taskEntityWorld.setBlockWithNotify(MathHelper.floor_double(this.taskEntity.posX+1), MathHelper.floor_double(this.taskEntity.posY), MathHelper.floor_double(this.taskEntity.posZ), Block.sapling.blockID);
	}
	
	
	@Override
	public boolean continueExecuting()
	{
		return super.continueExecuting();
	}

}

package colonies.boycat97.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import colonies.anglewyrm.src.EntityLumberjack;
import colonies.anglewyrm.src.Utility;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.network.Player;

public class EntityAILumberjack extends EntityAIGoToWork
{
	
	private enum task {
		chopping,
		placingSapling,
		walking
	}
	
	private task currentTask;

	public EntityAILumberjack(EntityLiving par1EntityLiving, int BlockID, float _movementSpeed) {
		super(par1EntityLiving, BlockID, _movementSpeed);
		this.currentTask = task.walking;
		
	}
	
	public boolean continueExecuting()
	{
		
		if (this.currentTask.equals(task.chopping) && Math.abs(this.targetBlockX - this.taskEntity.posX) < 2) {
			if (findPossibleBlockTarget() != null )
			{
				this.collectingWood();
			} else {
				this.currentTask = task.walking;
			}
			
			return false;
		}
		
		if (findPossibleBlockTarget() != null  && 
		this.taskEntityWorld.getBlockId(MathHelper.floor_double(this.targetBlockX), MathHelper.floor_double(this.targetBlockY), MathHelper.floor_double(this.targetBlockZ)) == 17 ) 
		{	
			
			this.currentTask = task.chopping;
			
			if ( Math.abs(this.targetBlockX - this.taskEntity.posX) < 2) {
				this.collectingWood();
				this.taskEntity.setCurrentItemOrArmor(0, new ItemStack(Item.axeWood, 1));
			}
			
			return true;
			
		} else {
			
			return super.continueExecuting();
		}	
		
	}
	
	private void collectingWood() 
	{
		this.currentTask = task.chopping;
		Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects((int)this.targetBlockX, (int)this.targetBlockY, (int)this.targetBlockZ, 16, 16);
		Block.wood.harvestBlock(this.taskEntityWorld, Minecraft.getMinecraft().thePlayer, MathHelper.floor_double(this.targetBlockX), MathHelper.floor_double(this.targetBlockY), MathHelper.floor_double(this.targetBlockZ), 0);
		//Utility.chatMessage("Chopping some wood...");
	}	
	
	@Override
	public void startExecuting()
	{
		if (!this.currentTask.equals(task.chopping)) {
		 super.startExecuting();
		}
	}
	
	
	@Override
	public boolean shouldExecute() {
		
	   return super.shouldExecute();
        
	} 
	
}

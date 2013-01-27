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

public class EntityAILumberjackChopTree extends EntityAIGoToWork
{

	public EntityAILumberjackChopTree(EntityLiving par1EntityLiving, int BlockID, float _movementSpeed) {
		super(par1EntityLiving, BlockID, _movementSpeed);
		
	}
	
	public boolean continueExecuting()
	{
		//if we are looking for work then find tree and start chopping
		if (lookForWorkLocation() != null  && 
		this.taskEntityWorld.getBlockId(MathHelper.floor_double(this.targetBlockX), MathHelper.floor_double(this.targetBlockY), MathHelper.floor_double(this.targetBlockZ)) == 17 ) 
		{	
			
			//make sure the entity is close to the wood
			if ( Math.abs(this.targetBlockX - this.taskEntity.posX) <= 2) {
				
				this.choppingWood();
				//this.taskEntity.setCurrentItemOrArmor(0, new ItemStack(Item.axeWood, 1));
			}
			
			return false;
			
		} 
		
		return super.continueExecuting();
	}
	
	private void choppingWood() 
	{
		
		//show the animation of the block being hit.
		Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects((int)this.targetBlockX, (int)this.targetBlockY, (int)this.targetBlockZ, 16, 16);
		
		//break a piece of wood off of the tree.
		Block.wood.harvestBlock(this.taskEntityWorld, Minecraft.getMinecraft().thePlayer, MathHelper.floor_double(this.targetBlockX), MathHelper.floor_double(this.targetBlockY), MathHelper.floor_double(this.targetBlockZ), 0);
	
	}	
	 
	
	public void startExecuting() 
	{
		
		this.continueExecuting();
		
	}
	
}

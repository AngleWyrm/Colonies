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
	
	private boolean isATree(int x, int y, int z)
	{
		Vec3 blockFound = this.lookForBlockNearBy(Block.leaves.blockID, x, y, z, 1, 1, 1);
		if (blockFound != null ) {			
			return true;
		} else {		
			return false;
		}
	}
	
	
	public boolean shouldExecute()
	{
		//block coordinates
		int xCoord = MathHelper.floor_double(this.targetBlockX);
		int yCoord = MathHelper.floor_double(this.targetBlockY);
		int zCoord = MathHelper.floor_double(this.targetBlockZ);		
		
		//if we are looking for a spot for wood find a tree and start chopping	 			
		if ( this.isATree(xCoord, yCoord, zCoord) ) {
			
			//make sure the entity is close to the wood				
			if ( Math.abs(this.targetBlockX - this.taskEntity.posX) <= 3   ) {	
				
						
				if ( this.taskEntityWorld.getBlockId(xCoord, yCoord, zCoord) != 0 ) {								
						
						this.choppingWood(xCoord, yCoord, zCoord);
						
						if (this.taskEntityWorld.getBlockId(xCoord, yCoord+1, zCoord) == Block.wood.blockID) {
							yCoord++;							
						} else if (this.taskEntityWorld.getBlockId(xCoord, yCoord-1, zCoord) == Block.wood.blockID) {
							yCoord--;							
						} else if (this.taskEntityWorld.getBlockId(xCoord+1, yCoord, zCoord) == Block.wood.blockID) { 
							xCoord++;
						} else if (this.taskEntityWorld.getBlockId(xCoord-1, yCoord, zCoord) == Block.wood.blockID) { 
							xCoord--;
						} else if (this.taskEntityWorld.getBlockId(xCoord, yCoord, zCoord+1) == Block.wood.blockID) { 
							zCoord++;
						} else if (this.taskEntityWorld.getBlockId(xCoord, yCoord, zCoord-1) == Block.wood.blockID) { 
							zCoord--;
						}
						
						this.targetBlockY = yCoord;
						this.targetBlockZ = zCoord;
						this.targetBlockX = xCoord;
						
				}
															
					
				return false;					
				
			} else {
				return super.shouldExecute();
			}
		}
		
		return true;			
		
	}
	
	public boolean continueExecuting()
	{
		return super.shouldExecute();		
	}
	
	private void choppingWood(int xCoord, int yCoord,int zCoord) 
	{
		
		//show the animation of the block being hit.
		Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(xCoord, yCoord, zCoord, 16, 16);
		
		//break a piece of wood off of the tree.
		Block.wood.harvestBlock(this.taskEntityWorld, Minecraft.getMinecraft().thePlayer, xCoord, yCoord, zCoord, 0);
	
		this.taskEntityWorld.setBlock(xCoord, yCoord, zCoord, 0);
	}	
	 
	
	public void startExecuting() 
	{
		super.startExecuting();
		this.shouldExecute();
		
	}
	
}

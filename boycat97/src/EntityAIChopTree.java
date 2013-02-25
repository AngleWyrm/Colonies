package colonies.boycat97.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import colonies.src.Point;
import colonies.src.Utility;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityLumberjack;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.network.Player;


public class EntityAIChopTree extends EntityAIBase
{
	private EntityCitizen citizen;   
    protected int BlockID;
    protected World taskEntityWorld; 
    protected float movementSpeed;
    private static ItemStack[] axeTypes = {new ItemStack(Item.axeSteel,1), new ItemStack(Item.axeStone, 1), new ItemStack(Item.axeWood,1)};
    
    Point destination;
	
	public EntityAIChopTree(EntityCitizen _entityCitizen) {
		this.movementSpeed = 0.25f;
        this.citizen = _entityCitizen;
        this.taskEntityWorld = this.citizen.worldObj;
        this.BlockID = Block.wood.blockID;
        this.setMutexBits(1);
               
	}	
	
	@Override
	public boolean shouldExecute()
	{
		if(citizen == null) return false;
		if(citizen.homeTown == null) return false; // must belong to a town
		if(!citizen.worldObj.isDaytime()) return false; // only chop during day

		if(!citizen.inventory.hasItemOfSet(axeTypes)) return false; // needs an axe

		return true;
	}	
	
	@Override 
	public boolean continueExecuting()
	{
		
		if(destination == null) { // suitable destination not yet established
			
			Point candidate = new Point();
			int blockID = 0; 
			
			for(int i = 0; i < 100; ++i) {
				
				//choose a spot 5-10m away from citizen in a random direction
//				candidate.polarTranslation(Utility.rng.nextRadian(), Math.PI/2, 5 + Utility.rng.nextInt(5));
//				candidate.plus(citizen.posX, citizen.posY, citizen.posZ);
				
				Vec3 tempVec = this.lookForWorkLocation();				
				
				if ( tempVec != null ) {
					candidate.x = tempVec.xCoord;
					candidate.y = tempVec.yCoord;
					candidate.z = tempVec.zCoord;
				}
				
				// move destination away from logging camp if necessary
				if(candidate.getDistance(citizen.homeTown.xCoord, citizen.homeTown.yCoord, citizen.homeTown.zCoord) < 10){
					double theta = Math.atan2(candidate.y - citizen.homeTown.yCoord, candidate.x - citizen.homeTown.xCoord);
					candidate.polarTranslation(theta, Math.PI/2, 10);
				}			
				//Utility.terrainAdjustment(citizen.worldObj, candidate);			
				
				blockID = citizen.worldObj.getBlockId((int)candidate.x, (int)candidate.y, (int)candidate.z);					
				if( blockID == Block.wood.blockID ) //TODO: look for a tree here with leaves.
				{
					destination = candidate;
					
					citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y, destination.z, this.movementSpeed);
					
					return true;
				}
			}
			return false;		
			
		} // else a destination has already been established during a previous update tick		
		
		if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) <= 6) // close enough, chop tree
		{
			this.choppingWood();
			this.lookingForTheRestOfTheTree();
			
			return true;
		} // else not there yet, or can't get there		
		
		// Can we get there from here?
		if(citizen.getNavigator().noPath()) { // nope, cancel this attempt
			destination = null;
			return false;
		}				
			
		return true;
	}
		
	public Vec3 lookForWorkLocation()
    {
        for (int i = 0; i < 100; ++i)
        {
            int x = MathHelper.floor_double(this.citizen.posX + Utility.rng.nextInt(30) - 15);
            int y = MathHelper.floor_double(this.citizen.boundingBox.minY + Utility.rng.nextInt(10) - 5);
            int z = MathHelper.floor_double(this.citizen.posZ + Utility.rng.nextInt(30) - 15);

            if (this.taskEntityWorld.blockExists(x, y, z))
            {
            	if ( this.taskEntityWorld.getBlockId(x, y, z) == this.BlockID )
            		return this.taskEntityWorld.getWorldVec3Pool().getVecFromPool(x, y, z);
            }
        }

        return null;
        
    }
	
	private void choppingWood() 
	{
				
		//show the animation of the block being hit.
		Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects((int)destination.x, (int)destination.y, (int)destination.z, 5, 16);
		
		//break a piece of wood off of the tree.
		Block.wood.harvestBlock(this.taskEntityWorld, Minecraft.getMinecraft().thePlayer, (int)destination.x, (int)destination.y, (int)destination.z, 10);
		this.taskEntityWorld.setBlockWithNotify((int)destination.x, (int)destination.y, (int)destination.z, 0);	
		
	}	
	
	private void lookingForTheRestOfTheTree () 
	{
		//TODO: figure out how to chop wood multiple times so that its being broken up correctly.
		//set the variables that allow for immediate scanning for wood.
		int scanTime = 0;
		int MaxScanTime = 6; 
				
		while (destination != null && scanTime <= MaxScanTime) {
			if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y-scanTime, (int)destination.z) == Block.wood.blockID) {
				destination.y -= scanTime;
			}  else if (this.taskEntityWorld.getBlockId((int)destination.x+scanTime, (int)destination.y+scanTime, (int)destination.z) == Block.wood.blockID) {  
				destination.x += scanTime;
				destination.y += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x+scanTime, (int)destination.y, (int)destination.z) == Block.wood.blockID) { 
				destination.x += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x-scanTime, (int)destination.y+scanTime, (int)destination.z) == Block.wood.blockID) {  
				destination.x -= scanTime;
				destination.y += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x-scanTime, (int)destination.y, (int)destination.z) == Block.wood.blockID) { 
				destination.x -= scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y, (int)destination.z+scanTime) == Block.wood.blockID) { 
				destination.z += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y+scanTime, (int)destination.z+scanTime) == Block.wood.blockID) {  
				destination.z += scanTime;
				destination.y += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y, (int)destination.z-scanTime) == Block.wood.blockID) { 
				destination.z -= scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y+scanTime, (int)destination.z-scanTime) == Block.wood.blockID) {  
				destination.z -= scanTime;
				destination.y += scanTime;
			} else if (this.taskEntityWorld.getBlockId((int)destination.x, (int)destination.y+scanTime, (int)destination.z) == Block.wood.blockID) {
				destination.y -= scanTime;
			} else if (scanTime == MaxScanTime ) {
				destination = null;
			} else {
				scanTime++;
			}
		}
		
	}
}

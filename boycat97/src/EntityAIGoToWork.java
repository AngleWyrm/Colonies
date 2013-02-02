package colonies.boycat97.src;

import colonies.anglewyrm.src.Utility;
import net.minecraft.src.Block;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityAITarget;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathEntity;
import net.minecraft.src.PathPoint;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class EntityAIGoToWork extends EntityAIBase {

	protected EntityLiving taskEntity;    
    protected int BlockID;
    protected World taskEntityWorld; 
    protected float movementSpeed;
    
    protected double targetBlockX;
    protected double targetBlockY;
    protected double targetBlockZ;        
    
    
    public EntityAIGoToWork(EntityLiving par1EntityLiving, int BlockID, float _movementSpeed)
    {
    	this.movementSpeed = _movementSpeed;
        this.taskEntity = par1EntityLiving;
        this.taskEntityWorld = this.taskEntity.worldObj;
        this.BlockID = BlockID;
        this.setMutexBits(1);
    }
        
    public Vec3 lookForWorkLocation()
    {
        for (int i = 0; i < 100; ++i)
        {
            int x = MathHelper.floor_double(this.taskEntity.posX + Utility.rng.nextInt(30) - 15);
            int y = MathHelper.floor_double(this.taskEntity.boundingBox.minY + Utility.rng.nextInt(10) - 5);
            int z = MathHelper.floor_double(this.taskEntity.posZ + Utility.rng.nextInt(30) - 15);

            if (this.taskEntityWorld.blockExists(x, y, z))
            {
            	if ( this.taskEntityWorld.getBlockId(x, y, z) == this.BlockID )
            		return this.taskEntityWorld.getWorldVec3Pool().getVecFromPool(x, y, z);
            }
        }

        return null;
    }
    
    public boolean isSuitableTarget(Block block)
    {
    	//TODO: Figure out how to determine who wants what block area, suitable for them.
    	return true;
    }
    
    
	@Override
	public boolean shouldExecute() {
		
	   //Am I currently interested in the location
       if (this.taskEntity.getLookVec() == Vec3.createVectorHelper(this.targetBlockX, this.targetBlockY, this.targetBlockZ) && 
    		   this.taskEntityWorld.getBlockId(MathHelper.floor_double(this.targetBlockX), MathHelper.floor_double(this.targetBlockY), MathHelper.floor_double(this.targetBlockZ)) == this.BlockID ){
            return false;
        }
        else
        {
        	//Look for some other block of the same type.
            Vec3 v = this.lookForWorkLocation();

            if (v == null)
            {
                return false;
            }
            else 
            {
                this.targetBlockX = v.xCoord;
                this.targetBlockY = v.yCoord;
                this.targetBlockZ = v.zCoord;
                
                return true;
            }
        }
        
	}
	
	public boolean continueExecuting()
    {
        return !this.taskEntity.getNavigator().noPath();
    }
	
	public void startExecuting()
    {
		 
        this.taskEntity.getNavigator().tryMoveToXYZ(this.targetBlockX, this.targetBlockY, this.targetBlockZ, this.movementSpeed);
       
    }

}

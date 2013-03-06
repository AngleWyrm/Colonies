package colonies.boycat97.src;

import colonies.src.Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
    

    protected Vec3 lookForBlockNearBy(int _blockID, int j, int k, int l, int i1, int j1, int k1)
    {
        Vec3 vec3d = Vec3.createVectorHelper(j, k, l);
        Vec3 vec3d1 = null;
        double d = 999999999D;
        
        int l1;

        if (i1 > j1 && i1 > k1)
        {
            l1 = i1;
        }
        else if (j1 > k1)
        {
            l1 = j1;
        }
        else
        {
            l1 = k1;
        }

        int i5 = 0;

        for (int j5 = 0; j5 <= l1; j5++)
        {
            int k5 = Math.min(i1, j5);
            int l5 = Math.min(j1, j5);
            int i6 = Math.min(k1, j5);

            if (j5 <= k1)
            {
                int i4 = l - j5;

                for (int i3 = k - l5; i3 <= k + l5; i3++)
                {
                    for (int i2 = j - k5; i2 <= j + k5; i2++)
                    {
                        if (this.taskEntityWorld.getBlockId(i2, i3, i4) == _blockID)
                        {
                            Vec3 vec3d2 = Vec3.createVectorHelper(i2, i3, i4);

                            if (vec3d1 == null || vec3d2.distanceTo(vec3d) < d)
                            {
                                vec3d1 = vec3d2;
                                d = vec3d1.distanceTo(vec3d);
                            }
                        }

                        i5++;
                    }
                }
            }

            if (j5 > 0)
            {
                if (j5 <= k1)
                {
                    int j4 = l + j5;

                    for (int j3 = k - l5; j3 <= k + l5; j3++)
                    {
                        for (int j2 = j - k5; j2 <= j + k5; j2++)
                        {
                            if (this.taskEntityWorld.getBlockId(j2, j3, j4) == _blockID)
                            {
                                Vec3 vec3d3 = Vec3.createVectorHelper(j2, j3, j4);

                                if (vec3d1 == null || vec3d3.distanceTo(vec3d) < d)
                                {
                                    vec3d1 = vec3d3;
                                    d = vec3d1.distanceTo(vec3d);
                                }
                            }

                            i5++;
                        }
                    }

                    i6--;
                }

                if (j5 <= j1)
                {
                    for (int k3 = k - j5; k3 <= k + j5; k3 += 2 * j5)
                    {
                        for (int k4 = l - i6; k4 <= l + i6; k4++)
                        {
                            for (int k2 = j - k5; k2 <= j + k5; k2++)
                            {
                                if (this.taskEntityWorld.getBlockId(k2, k3, k4) == _blockID)
                                {
                                    Vec3 vec3d4 = Vec3.createVectorHelper(k2, k3, k4);

                                    if (vec3d1 == null || vec3d4.distanceTo(vec3d) < d)
                                    {
                                        vec3d1 = vec3d4;
                                        d = vec3d1.distanceTo(vec3d);
                                    }
                                }

                                i5++;
                            }
                        }
                    }

                    l5--;
                }

                if (j5 <= i1)
                {
                    for (int l2 = j - j5; l2 <= j + j5; l2 += 2 * j5)
                    {
                        for (int l4 = l - i6; l4 <= l + i6; l4++)
                        {
                            for (int l3 = k - l5; l3 <= k + l5; l3++)
                            {
                                if (this.taskEntityWorld.getBlockId(l2, l3, l4) == _blockID)
                                {
                                    Vec3 vec3d5 = Vec3.createVectorHelper(l2, l3, l4);

                                    if (vec3d1 == null || vec3d5.distanceTo(vec3d) < d)
                                    {
                                        vec3d1 = vec3d5;
                                        d = vec3d1.distanceTo(vec3d);
                                    }
                                }

                                i5++;
                            }
                        }
                    }
                }
            }

            if (d < 999999999D)
            {

                return vec3d1;
            }
        }

        return null;
    }
        
    public Vec3 lookForWorkLocation()
    {
        for (int i = 0; i < 10; ++i)
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

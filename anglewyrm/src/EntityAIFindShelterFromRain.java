package colonies.anglewyrm.src;

import java.util.Random;

import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class EntityAIFindShelterFromRain extends EntityAIBase
{
    private EntityCitizen citizen;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private float movementSpeed;
    private World theWorld;

    public EntityAIFindShelterFromRain(EntityCitizen _citizen, float _movementSpeed)
    {
        this.citizen = _citizen;
        this.movementSpeed = _movementSpeed;
        this.theWorld = citizen.worldObj;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (!this.theWorld.isRaining()){
            return false;
        }
        else if (!this.theWorld.canBlockSeeTheSky(
        		MathHelper.floor_double(this.citizen.posX), 
        		(int)this.citizen.boundingBox.minY, 
        		MathHelper.floor_double(this.citizen.posZ))){
            return false;
        }
        else
        {
            Vec3 var1 = this.findPossibleShelter();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.shelterX = var1.xCoord;
                this.shelterY = var1.yCoord;
                this.shelterZ = var1.zCoord;
                return true;
            }
        }
    }

    public boolean continueExecuting()
    {
        return !this.citizen.getNavigator().noPath();
    }

    public void startExecuting()
    {
        this.citizen.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    private Vec3 findPossibleShelter()
    {
        for (int var2 = 0; var2 < 10; ++var2)
        {
            int x = MathHelper.floor_double(this.citizen.posX + Utility.rng.nextInt(30) - 15);
            int y = MathHelper.floor_double(this.citizen.boundingBox.minY + Utility.rng.nextInt(10) - 5);
            int z = MathHelper.floor_double(this.citizen.posZ + Utility.rng.nextInt(30) - 15);

            if (!this.theWorld.canBlockSeeTheSky(x, y, z) && this.citizen.getBlockPathWeight(x, y, z) < 0.0F)
            {
                return this.theWorld.getWorldVec3Pool().getVecFromPool(x, y, z);
            }
        }

        return null;
    }
}

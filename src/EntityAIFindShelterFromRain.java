package colonies.src;

import java.util.Random;


import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class EntityAIFindShelterFromRain extends EntityAIBase
{
    EntityCitizen citizen;
    Point destination;
  
    public EntityAIFindShelterFromRain(EntityCitizen _citizen, float _movementSpeed)
    {
        this.citizen = _citizen;
        this.setMutexBits(1);
        
    }

    public boolean shouldExecute()
    {
    	if(citizen == null )    return false;
        if (!citizen.worldObj.isRaining()) return false;
        
        // Am I already under cover?
        if (!citizen.worldObj.canBlockSeeTheSky((int)citizen.posX, (int)citizen.posY, (int)citizen.posZ)){
            return false;
        }
        return true;
    }

    public boolean continueExecuting()
    {
    	// if destination not yet established, search for shelter
    	if(destination == null){
    		Point candidate = new Point();
    		for(int i = 0; i < 10; ++i){
    			candidate.polarTranslation(Utility.rng.nextRadian(), Math.PI/2, Utility.rng.nextInt(20));
    			candidate.plus(citizen.posX, citizen.posY, citizen.posZ);
    			Utility.terrainAdjustment(citizen.worldObj, candidate);
    			if(!citizen.worldObj.canBlockSeeTheSky((int)candidate.x, (int)candidate.y, (int)candidate.z)){
    				destination = candidate;
    				citizen.getNavigator().tryMoveToXYZ(destination.x, destination.y, destination.z, 0.35f);
    			} // else try another spot
    		} // else still not found, search more later
    		return true;
    	} // else already has a destination
    	
    	if(this.citizen.getNavigator().noPath()){
    		destination = null;
    		return false;
    	}
    	
    	// are we there yet?
    	if(destination.getDistance(citizen.posX, citizen.posY, citizen.posZ) < 2 ){
        	Utility.chatMessage("Citizen #"+ citizen.ssn + " \"I wish this rain would stop\"");
    		destination = null;
    		return false;
    	}
    	return true;
    }
}

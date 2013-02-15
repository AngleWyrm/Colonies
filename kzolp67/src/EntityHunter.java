package colonies.kzolp67.src;

import java.util.HashMap;

import colonies.src.ColoniesMain;
import colonies.src.EntityCitizen;
import colonies.src.Utility;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;

public class EntityHunter extends EntityCitizen {
	
	private Vector3D closestHunterChest;
	
	public EntityHunter(World world) { 
		super(world);		
		this.texture = ColoniesMain.skinHunter;

		desiredInventory.addItemStackToInventory(new ItemStack(Item.bow,1));
		desiredInventory.addItemStackToInventory(new ItemStack(Item.arrow,64));
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityHunter){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinHunter;
		}
		return ColoniesMain.skinHunter;
	}
   
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.arrow.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        {
            var3 = this.rand.nextInt(3 + par2);

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.arrow.shiftedIndex, 1);
            }
        }

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var3)
        {
            this.dropItem(Item.bow.shiftedIndex, 1);
        }
    }
    

	public void onLivingUpdate() {
		
        if (this.worldObj.isDaytime())
        {
        	
        }
		
		super.onLivingUpdate();
	}
	
}

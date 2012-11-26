package colonies.lohikaarme.src;

import java.io.File;
import java.io.IOException;

import colonies.anglewyrm.src.ServerProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class ItemMeasuringTape extends Item{
  
  private int isCounting=1;
  private int x1,y1,z1;
	public ItemMeasuringTape(int par1){
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
		setIconIndex(0);
		setIconCoord(0,0);
	}
	
	@Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
	  switch(isCounting){
	    case 2:isCounting=3;
		       x1=x;
		       y1=y;
		       z1=z;
		       player.addChatMessage("Placed");
		       break;
	    case 4:isCounting=1;
			   int dx=Math.abs(x-x1)+1;
			   int dy=Math.abs(y-y1)+1;
			   int dz=Math.abs(z-z1)+1;
			   if(dx==1&&dy==1&&dz==1){
				 player.addChatMessage("Same Block");
			   }
			   if(dx!=1&&dy==1&&dz==1){
				 player.addChatMessage("Line of "+dx);  
			   }
			   if(dx==1&&dy!=1&&dz==1){
				 player.addChatMessage("Line of "+dy);  
			   }
			   if(dx==1&&dy==1&&dz!=1){
				 player.addChatMessage("Line of "+dz);  
			   }
			   if(dx!=1&&dy!=1&&dz==1){
				 player.addChatMessage("Area of "+dx+" X "+dy);  
			   }
			   if(dx==1&&dy!=1&&dz!=1){
				 player.addChatMessage("Area of "+dz+" X "+dy);
			   }
			   if(dx!=1&&dy==1&&dz!=1){
				 player.addChatMessage("Area of "+dx+" X "+dz);
			   }
			   if(dx!=1&&dy!=1&&dz!=1){
			     player.addChatMessage("Volume of "+dx+" X "+dz+" X "+dy);
			   }
			   break;
	   default:isCounting++;
	     	   break;
	  }
      return true;
    }
    
	@Override
	public String getTextureFile(){
		return ServerProxy.MEASURING_TAPE;
	}
}

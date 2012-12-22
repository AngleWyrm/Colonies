package colonies.kzolp67.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import colonies.anglewyrm.src.ServerProxy;
import colonies.anglewyrm.src.Utility;

public class ItemCalculator extends Item{
  
	public ItemCalculator(int par1){
		super(par1);
		setMaxStackSize(1);
		setIconIndex(1);
		setIconCoord(14,0);
		setCreativeTab(CreativeTabs.tabTools);
	}
	

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
		       
    	Utility.chatMessage("Test");
		return true; 
	       
    }
    
	@Override
	public String getTextureFile(){
		return ServerProxy.ITEMS_PNG;
	}
}

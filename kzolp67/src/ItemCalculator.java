package colonies.kzolp67.src;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import colonies.anglewyrm.src.ServerProxy;

public class ItemCalculator extends Item{
  
	public ItemCalculator(int par1){
		super(par1);
		setMaxStackSize(1);
		setIconIndex(1);
		setIconCoord(14,0);
		setCreativeTab(CreativeTabs.tabTools);
	}
    
	@Override
	public String getTextureFile(){
		return ServerProxy.ITEMS_PNG;
	}
}

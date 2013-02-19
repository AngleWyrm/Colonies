package colonies.pmardle.src;

import colonies.src.ColoniesMain;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemAncientTome extends Item{
  
  private int isCounting=1;
  private int x1,y1,z1;
	public ItemAncientTome(int par1){
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(ColoniesMain.coloniesTab);
		setIconIndex(0);
		setIconCoord(12,11);
	}
	

	}


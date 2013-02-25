package colonies.src;
//..
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemResearchedTome extends Item{
  
  private int isCounting=1;
  private int x1,y1,z1;
	public ItemResearchedTome(int par1){
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(ColoniesMain.coloniesTab);
		setIconIndex(0);
		setIconCoord(12,11);
	}
	

	}


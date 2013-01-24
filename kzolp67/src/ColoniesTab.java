package colonies.kzolp67.src;

import colonies.src.ColoniesMain;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;

public class ColoniesTab extends CreativeTabs {

	public ColoniesTab(String label) {
	    super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
	    return new ItemStack(ColoniesMain.townHall);
	}
	
}

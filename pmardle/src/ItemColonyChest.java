package Colonies.pmardle.src;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemColonyChest extends ItemBlock {

	public ItemColonyChest(int id) {
		super(id);
        setMaxDamage(0);
        setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
	  return ColonyChestType.validateMeta(i);
	}
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return ColonyChestType.values()[itemstack.getItemDamage()].name();
	}
}
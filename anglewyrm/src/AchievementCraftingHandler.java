package colonies.anglewyrm.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;
import colonies.anglewyrm.src.BlockTownHall;
import colonies.stabtokill.src.ColoniesAchievements;

public class AchievementCraftingHandler implements ICraftingHandler
{
	// Crafting achievements
	@Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
    {
			player.addChatMessage("Achievement Test");
            if (item.itemID == ColoniesMain.townHallID)
            {
                    player.addStat(ColoniesAchievements.TownStarted, 1);
            }
    }

	// Smelting achievements
	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
	}
}
package colonies.stabtokill.src;

import net.minecraft.src.Achievement;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.AchievementPage;
import colonies.anglewyrm.src.ColoniesMain;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 
 * @author stabtokill
 *
 */
public class ColoniesAchievements implements ICraftingHandler
{



	public static final Achievement TownStarted = new Achievement(50, "TownStarted", 1, 1, ColoniesMain.townHall, (Achievement)null).setIndependent().registerAchievement();	
	public static final Achievement ToTheDepts = new Achievement(51, "ToTheDepts", 3, 2, ColoniesMain.minerChest, TownStarted).setIndependent().registerAchievement();
	public static final Achievement Prosperity = new Achievement(52, "Prosperity", -1, 2, ColoniesMain.townHall, TownStarted).registerAchievement();
	
	public static AchievementPage page1 = new AchievementPage("Colonies", TownStarted, ToTheDepts, Prosperity);


	private static void addAchievementName(String ach, String name) {
		LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
	}

	private static void addAchievementDesc(String ach, String desc) {
		LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", "en_US", desc);
	}

	public static void addAchievementLocalizations() {
		addAchievementName("TownStarted", "First Town Started");
		addAchievementDesc("TownStarted", "Made A Town");
		addAchievementName("ToTheDepts", "Miner box");
		addAchievementDesc("ToTheDepts", "Made A Helpful Miner");
		addAchievementName("Prosperity", "The Start of Work");
		addAchievementDesc("Prosperity", "Having A Complete Set of Basic Workers");
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack block, IInventory craftMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub

	}

}

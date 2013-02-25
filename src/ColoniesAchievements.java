package colonies.src;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;


/**
 * 
 * @author stabtokill
 *
 */
public class ColoniesAchievements implements ICraftingHandler
{

	public static Achievement TownStarted = (new Achievement(50, "TownStarted", 0, 0, ColoniesMain.townHall, null)).setIndependent().registerAchievement();	
	public static Achievement ToTheDepts = (new Achievement(51, "ToTheDepts", 3, 1, ColoniesMain.minerChest, TownStarted)).registerAchievement();
	public static Achievement Prosperity = (new Achievement(52, "Prosperity", -1, 2, ColoniesMain.chestBlock, TownStarted)).registerAchievement();
	
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
	
	private boolean chestZMinerItMade = false;
	private boolean chestZBuilderItMade = false;
	private boolean chestZBlSmithItMade = false;
	private boolean chestZLumItMade = false;
	private boolean chestZFarmsItMade = false;
	
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		
		
		// System.out.println("ColoniesAchievements, crafting, are being called??");
		if (item.itemID == ColoniesMain.townHallID)
		{
			player.addStat(TownStarted, 1);
		}
		if (item.itemID == ColoniesMain.minerChestID)
		{
			player.addStat(ToTheDepts, 1);
			chestZMinerItMade = true;    
		}
		//is this a valid way of doing it?
		if((chestZMinerItMade) && (chestZBuilderItMade) && (chestZBlSmithItMade) && (chestZLumItMade) && (chestZFarmsItMade))
		{
			player.addStat(Prosperity, 1);
		}
		/*
		if (item.itemID == ColoniesMain.loggingCampID)
		{
			chestZLumItMade = true;
		}
		if(item.itemID == ColoniesMain.builderChestID)
		{
			chestZBuilderItMade = true;
		}

		if(item.itemID == ColoniesMain.blackSmithID)
		{
			chestZBlSmithItMade = true; 
		}
		if(item.itemID == ColoniesMain.farmerID)
		{
			chestZFarmsItMade = true;
		}
        */
		

	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
   
	}

}

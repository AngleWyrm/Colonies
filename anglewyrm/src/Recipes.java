package colonies.anglewyrm.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes 
{
	public static void registerRecipes() 
	{
		GameRegistry.addRecipe(new ItemStack(ColoniesMain.MeasuringTape),
			"II", 'I',Item.ingotIron);

		GameRegistry.addRecipe(new ItemStack(ColoniesMain.minerChest), new Object [] { 
			"WWW", "WPW", "WWW", 'W', Block.planks, 'P', Item.pickaxeWood} );

		GameRegistry.addRecipe( new ItemStack(ColoniesMain.townHall), new Object[]{
			"BIB","ICI","BIB", 'B',Item.book, 'I',Item.ingotIron, 'C',BlockContainer.chest});

		GameRegistry.addRecipe( new ItemStack(ColoniesMain.loggingCamp), new Object[]{
			"SXS","XCX","SXS", 'S',Block.sapling, 'X',Item.axeStone, 'C',BlockContainer.chest});
		
		GameRegistry.addRecipe( new ItemStack(ColoniesMain.house), new Object[]{
			"TBT","DCD","TBT", 'T',Block.torchWood, 'B',Item.bed, 'C',BlockContainer.chest, 'D',Block.doorWood});
		
		GameRegistry.addRecipe( new ItemStack(ColoniesMain.hunterBlind), new Object[]{
			"TLT","LCL","TLT", 'T',Item.beefRaw, 'L',Item.leather, 'C',BlockContainer.chest});
	}	
}

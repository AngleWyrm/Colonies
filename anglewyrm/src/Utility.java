package colonies.anglewyrm.src;

import java.util.Random;

public class Utility 
{
	// TODO: get world instance, and put world.getSeed() in Random() c'tor
	public static Random rng = new Random();

	// Given a category count, (usually four)
	// return a random category in the range [1..numCategories]
	// 1=Most Common, 2=Less Likely 3=Even Less So, and so on
	public static int getLootCategory(){
		return getLootCategory(4);
	}
	public static int getLootCategory(int numCategories){
		return (int) Math.round(Math.sqrt(2.0) * Math.sqrt(-1*Math.log(rng.nextFloat())));
	}
	
	// Disable-able replacement for System.out.println()
	public static void Debug(String text)
	{
		System.out.println(text);
	}
}

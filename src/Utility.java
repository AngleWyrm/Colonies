package colonies.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Utility 
{
	public static Minecraft game;
	
	public static class RNG extends Random{
		public float nextRadian(){
			return nextFloat()*2.0f * (float)Math.PI;
		}
	}
	// TODO: get world instance, and put world.getSeed() in Random() c'tor
	public static RNG rng = new RNG();

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
		//System.out.println(text);
	}
	
	@SideOnly(Side.CLIENT)
	public static void chatMessage(String txt){
		game = Minecraft.getMinecraft();	
		
		if(game == null || txt == null || game.thePlayer == null) return;
		
  		game.thePlayer.addChatMessage(txt); 				 
	}
	
	public static Point terrainAdjustment(World world, Point p){
		if(world == null || p == null) return null;
		
		// If this ain't air, go up until it is
		while(!world.isAirBlock((int)p.x, (int)p.y, (int)p.z)){
			++p.y;
			if(p.y >= 126) return p; // fail safe
			if(!world.isAirBlock((int)p.x, (int)p.y+1, (int)p.z)) ++p.y;
		}
		// else is air, if air beneath, go down until it ain't
		while(world.isAirBlock((int)p.x, (int)p.y-1, (int)p.z)){
			--p.y;
			if(p.y <= 5) return p; // fail safe
		}
		return p;
	}
}

package colonies.anglewyrm.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;



public class Schematic extends World
{
	private static WorldSettings worldSettings = new WorldSettings(0, EnumGameType.CREATIVE, false, false, WorldType.FLAT);
	

	public Schematic(ISaveHandler par1iSaveHandler, String par2Str,
			WorldProvider par3WorldProvider, WorldSettings par4WorldSettings,
			Profiler par5Profiler) {
		super(par1iSaveHandler, par2Str, par3WorldProvider, par4WorldSettings,
				par5Profiler);
	}

	// return type will change to schematic
	public void loadSchematic(Path filePath)
	{
		try{
			BufferedReader reader = Files.newBufferedReader(filePath, Charset.forName("US-ASCII"));
		}
		catch(IOException x){
			System.err.format("[Colonies]IOException: %s%n", x);
		}
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntityByID(int var1) {
		// TODO Auto-generated method stub
		return null;
	}
}

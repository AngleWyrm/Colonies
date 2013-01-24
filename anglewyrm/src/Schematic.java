package colonies.anglewyrm.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import net.minecraft.src.Entity;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.Profiler;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

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

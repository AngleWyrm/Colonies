// Create, read and write configuration file
// stored in user.dir/../config/MineColony.cfg
package client;

import java.io.File;

public class ConfigFile {

	public static void CreateDefaultConfig() {
			String configFilePath;
			configFilePath = File.separator +".." + File.separator + "config";
			// test for and possibly create config folder
			// test for and possibly create MineColony.cfg
			// catch exceptions and post to stream
			// failure to create means use defaults in-game
	}
}

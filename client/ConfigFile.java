// 
// data is in a public static hash table called ConfigFile.settings
//   ConfigFile.settings.getProperty("myNewBlockID");
//   ConfigFile.settings.setProperty("myNewBlockID", "1500");
//
// config file location is ../config/MineColony.cfg
//
package client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigFile {
	public static Properties settings;
	
	public static void CreateDefaultConfig() {
	
	     // Default key/value pairs in ConfigFile.settings
	     settings.setProperty("testBlockID", "3500");

	     // TODO: Store settings in config file
	     Path configFilePath = FileSystems.getDefault().getPath(".."+File.separator+"config", "MineColony.cfg");

	}
	
	
	public static void LoadConfig() {
	     Path configFilePath = FileSystems.getDefault().getPath(".."+File.separator+"config", "MineColony.cfg");
	     if( Files.exists(configFilePath) ){
	    	 // TODO: load configuration settings
	     }
	     else{
	    	 CreateDefaultConfig();
	     }
	}


}

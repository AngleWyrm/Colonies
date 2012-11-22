// 
// data is in a public static hash table called ConfigFile.settings
//   ConfigFile.settings.getProperty("myNewBlockID");
//   ConfigFile.settings.setProperty("myNewBlockID", "1500");
//
// config file location is ../config/MineColony.cfg
//
package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.minecraft.client.Minecraft;

public class ConfigFile {
	
	public static Properties settings = new Properties();
	private static String configFilePath="Colony.cfg";
			
	public static void createDefaultConfiguration() {
	   // Default key/value pairs in ConfigFile.settings
	   settings.setProperty("MineColony", "Reboot");

	   //test purposes
	   settings.setProperty("townhallID","1000");
	   //

           save();
	}
	
	
	public static void load() {
	    File configFile = new File(configFilePath);
	  
	    try{
    	      if(configFile.exists() ){
    	        FileInputStream in = new FileInputStream(configFile);	
    	        settings.load(in);
    	      }
    	      else {
    	        createDefaultConfiguration();
   	      }

	   }
	   catch (Exception e) {
             System.err.println("[MineColony] " + e.getMessage());
             System.err.flush();
           }
         }

	
         public static void save() {
           File configFile = new File(configFilePath);
           try{
             configFile.createNewFile();
             FileOutputStream out = new FileOutputStream(configFile);
             settings.store(out, "MineColony configuration file");
             load();
           }
           catch (FileNotFoundException e) {
             System.err.println("[MineColony] " + e.getMessage()+" save,1");
             System.err.flush();
           }
           catch (IOException e){
             System.err.println("[MineColony] " + e.getMessage()+" save,2");
             System.err.flush();
           }
         }
}
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

public class ConfigFile {
	public static Properties settings = new Properties();
	
	public static void createDefaultConfiguration() {
	
	     // Default key/value pairs in ConfigFile.settings
	     settings.setProperty("MineColony", "Reboot");

	     save();
	}
	
	
	public static void load() {
	     String configFilePath = ".."+File.separator+"config"+File.separator+"MineColony.cfg";
	     File configFile = new File(configFilePath);
	     
    	 if( configFile.exists() ){
    		 try {
    			 FileInputStream in = new FileInputStream(configFile);	
    			 settings.load(in);    		 
    		 } 
    		 catch (Exception e) {
    			 System.err.println("[MineColony] " + e.getMessage());
    			 System.err.flush();
    		 }
    	 }
    	 else {
    		 createDefaultConfiguration();
    	 }
    }

	
	public static void save() {
	     String configFilePath = ".."+File.separator+"config"+File.separator+"MineColony.cfg";
	     File configFile = new File(configFilePath);

	     try{
	    	 FileOutputStream out = new FileOutputStream(configFile);
	    	 settings.store(out, "MineColony configuration file");
	     }
	     catch (Exception e) {
	    	 System.err.println("[MineColony] " + e.getMessage());
	    	 System.err.flush();
	     }
	}


}

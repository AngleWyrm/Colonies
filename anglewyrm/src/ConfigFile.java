//
// data is in a public static hash table called ConfigFile.settings
//   ConfigFile.settings.getProperty("myNewBlockID");
//   ConfigFile.settings.setProperty("myNewBlockID", "1500");
//
// config file location is ../config/Colonies.cfg
//

package colonies.anglewyrm.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigFile 
{
	public static Properties settings = new Properties();

	public static void set(String key, String value){
		settings.setProperty(key, value);
	}
	
	public static String get(String key){
		String tmp = new String();
		tmp = settings.getProperty(key);
		if(tmp == null)
		{
			System.err.println("[Colonies] key not found in config: " + key);
			tmp = "0";
		}
		return tmp;
	}
	
	public static int parseInt(String key){
		String tmp = new String();
		tmp = settings.getProperty(key);
		if(tmp == null)
		{
			System.err.println("[Colonies] key not found in config: " + key);
			return 0;
		}
		return Integer.parseInt(tmp);
	}

	public static void createDefaultConfiguration() {

	     // Default key/value pairs in ConfigFile.settings
	     settings.setProperty("Colonies", "MineColony Reboot");
	     settings.setProperty("CitizenMoveSpeed", "0.25");
	     
	     // Item ID numbers
	     settings.setProperty("TestBlockID", "1100");
	     settings.setProperty("MeasuringTape","1101");
	     settings.setProperty("CitizenID", "1102");

	     save();
	}


	public static void load() {
	     String configFilePath = "config"+File.separator+"Colonies.cfg";
	     File configFile = new File(configFilePath);

    	 // Developer convenience work-around:
	     // After adding new entries to the config file,
	     // just force createDefaultConfiguration by commenting out
	     // the other if header statement below:
	     if( configFile.exists() ){
	   //if(false){
    		 try {
    			 FileInputStream in = new FileInputStream(configFile);
    			 settings.load(in);
    			 System.out.println("[Colonies] config file loaded: " + configFilePath);
    		 }
    		 catch (Exception e) {
    			 System.err.println("[Colonies] " + e.getMessage());
    			 System.err.flush();
    		 }
    	 }
    	 else {
    		 createDefaultConfiguration();
    	 }
    }


	public static void save() {

	    // validate or create ../config folder
		// might not exist if this is the first mod the player installed
		String configFilePath = "config";
	    File configDir = new File(configFilePath);

	    if( !configDir.isDirectory()){
	    	try{
	    		configDir.mkdir();
	    	}
	    	catch (Exception e) {
	    		System.err.println("[Colonies] " + e.getMessage());
	    		System.err.flush();
	    	}
	    }


 	    configFilePath += File.separator+"Colonies.cfg";
	    File configFile = new File(configFilePath);

	    try{
	    	 FileOutputStream out = new FileOutputStream(configFile);
	    	 settings.store(out, "Colonies configuration file");
	    }
	    catch (Exception e) {
	    	 System.err.println("[Colonies] " + e.getMessage());
	    	 System.err.flush();
	    }
	    
		System.out.println("[Colonies] ConfigFile saved: " + configFilePath);
	}


}

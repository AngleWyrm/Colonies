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
		tmp = settings.getProperty(key, "1");
		if(tmp == null)
		{
			System.err.println("[Colonies] key not found in config: " + key);
		}
		return tmp;
	}
	public static String getSkin(String key){
		return settings.getProperty(key, "SKIN_NOT_FOUND");
/*		String filename = new String();
		filename = "/colonies/skins/" + settings.getProperty(key, "SKIN_NOT_FOUND") + ".png";
		System.out.println("[Colonies] getSkin returned:"+filename);
		return filename;
*/
	}
	
	public static int parseInt(String key){
		String tmp = new String();
		tmp = settings.getProperty(key, "1");
		if(tmp == null)
		{
			System.err.println("[Colonies] key not found in config: " + key);
			return 1;
		}
		return Integer.parseInt(tmp);
	}

	public static void createDefaultConfiguration() 
	{
	     // Default key/value pairs in ConfigFile.settings
		 set("Version", ColoniesMain.instance.Version());
	     save();
	     
	     // Item ID numbers
	     // This section may become depreciated
	     set("TestBlockID", "1100");
	     set("MeasuringTape","1102");
	     set("DefaultChestID", "1103");
	     set("TownHallID","1104");
	     set("BlockBusinessID", "1105");
	     set("MinerChestID","1106");
	     set("LoggingCampID", "1107");
	     set("BlockHouseID","1108");
	     
	     set("citizenGreetings", "true");
	     set("CitizenMoveSpeed", "0.25");
	     
	     // Citizen skins
	     set("skinDefault","/colonies/grahammarcellus/gfx/unemployedskin1.png");
	     set("skinMaleSwimming", "/colonies/anglewyrm/gfx/m-swimskin.png");
	     set("skinMiner", "/colonies/grahammarcellus/gfx/minerskin.png");
	     set("skinMinerSwimming", "/colonies/anglewyrm/gfx/miner_swim.png");
	     set("skinWife", "/colonies/anglewyrm/gfx/daisy_duke.png");
	     set("skinFemaleSwimming","/colonies/anglewyrm/gfx/white_bikini.png");
	     set("skinPriestess","/colonies/anglewyrm/gfx/priestess.png");
	     set("skinPriestessSwimming", "/colonies/anglewyrm/gfx/priestess_swimsuit.png");
	     set("skinLumberjack","/colonies/anglewyrm/gfx/lumberjack.png");
	     
	     System.out.println("[Colonies] Config file regenerated.");
	     save();
	}

	public static void load() 
	{
  		 String configFilePath = "config"+File.separator+"Colonies.cfg";
	     File configFile = new File(configFilePath);

    	 // Developer convenience work-around:
	     // After adding new entries to the config file,
	     // just force createDefaultConfiguration by commenting out
	     // the other if header statement below:
	     if( configFile.exists() ){
	     // if(false){
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
	     
	     // Validate config file version against game version
	     // if they differ, recreate the config file.
	     if(! get("Version").equals(ColoniesMain.instance.Version())){
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

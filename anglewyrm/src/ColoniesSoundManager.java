package colonies.anglewyrm.src;

import java.io.File;
import java.net.URL;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

	public class ColoniesSoundManager 
	{
	   @ForgeSubscribe
	    public void onSound(SoundLoadEvent event)
	    {
	        try
	        {
	            event.manager.soundPoolSounds.addSound("colonies/hello.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/hello.wav"));            
	            event.manager.soundPoolSounds.addSound("colonies/damnit.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/damnit.wav"));            
	            event.manager.soundPoolSounds.addSound("colonies/ohyeah.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/ohyeah.wav"));            
	       
	        }
	        catch (Exception e)
	        {
	            System.err.println("Failed to register one or more sounds.");
	        }
	    }
	}
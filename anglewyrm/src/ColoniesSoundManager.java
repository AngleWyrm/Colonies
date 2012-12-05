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
	        	event.manager.soundPoolSounds.addSound("colonies/m-hello.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/m-hello.wav"));
	        	event.manager.soundPoolSounds.addSound("colonies/f-hello.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/f-hello.wav"));            
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt1.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/m-hurt1.wav"));
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt2.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/m-hurt2.wav"));
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt3.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/m-hurt3.wav"));
	            event.manager.soundPoolSounds.addSound("colonies/f-damnit.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/f-damnit.wav"));            
	            event.manager.soundPoolSounds.addSound("colonies/f-ohyeah.wav", ColoniesMain.class.getResource("/colonies/anglewyrm/snd/f-ohyeah.wav"));            
	       
	        }
	        catch (Exception e)
	        {
	            System.err.println("Failed to register one or more sounds.");
	        }
	    }
	}
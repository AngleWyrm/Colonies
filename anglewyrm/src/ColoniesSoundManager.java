package colonies.anglewyrm.src;

import colonies.src.ColoniesMain;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ColoniesSoundManager 
	{
	   @ForgeSubscribe
	   @SideOnly(Side.CLIENT)
	    public void onSound(SoundLoadEvent event)
	    {
	        try
	        {
	        	event.manager.soundPoolSounds.addSound("colonies/m-hello.ogg", ColoniesMain.class.getResource("/colonies/snd/m-hello.ogg"));
	        	event.manager.soundPoolSounds.addSound("colonies/f-hello.ogg", ColoniesMain.class.getResource("/colonies/snd/f-hello.ogg"));            
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt1.ogg", ColoniesMain.class.getResource("/colonies/snd/m-hurt1.ogg"));
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt2.ogg", ColoniesMain.class.getResource("/colonies/snd/m-hurt2.ogg"));
	        	event.manager.soundPoolSounds.addSound("colonies/m-hurt3.ogg", ColoniesMain.class.getResource("/colonies/snd/m-hurt3.ogg"));
	            event.manager.soundPoolSounds.addSound("colonies/f-damnit.ogg", ColoniesMain.class.getResource("/colonies/snd/f-damnit.ogg"));            
	            event.manager.soundPoolSounds.addSound("colonies/f-ohyeah.ogg", ColoniesMain.class.getResource("/colonies/snd/f-ohyeah.ogg"));            
	       
	        }
	        catch (Exception e)
	        {
	            System.err.println("Failed to register one or more sounds.");
	        }
	    }
	}
package colonies.anglewyrm.src;

public class BaseAI 
{
	public static void onLivingUpdate(EntityCitizen me)
	{
		// check for home
		if(me.home==null){
			// find nearest town to move into
		}
		
		if (me.worldObj.isDaytime()){
	    	// Daytime behaviors
	    }
	    else {
	    	// Night time behaviors
	    }
	}
}

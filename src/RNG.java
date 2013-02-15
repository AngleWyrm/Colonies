package colonies.src;

import java.util.Random;

public class RNG extends Random{
	public float nextRadian(){
		return nextFloat()*2.0f * (float)Math.PI;
	}
}

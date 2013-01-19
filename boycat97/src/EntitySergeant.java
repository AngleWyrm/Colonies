package colonies.boycat97.src;

import java.util.HashMap;

import colonies.anglewyrm.src.ColoniesMain;
import colonies.anglewyrm.src.EntityCitizen.jobs;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;

public class EntitySergeant extends EntityGuard {
	
	public EntitySergeant(World world) {
		super(world);
		
		this.texture = ColoniesMain.skinSergeant;
		this.currentRank = EnumGuardRank.Seargent;		
		
		
		this.skills = new HashMap<jobs, Integer>(10);
		this.skills.put(jobs.unemployed, 10);
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMaleSwimming;
		} else			
			return ColoniesMain.skinSergeant;
	}
	
}

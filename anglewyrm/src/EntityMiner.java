package colonies.anglewyrm.src;

import java.util.HashMap;

import paulscode.sound.Vector3D;
import colonies.anglewyrm.src.Utility;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAIMoveTowardsTarget;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathEntity;
import net.minecraft.src.PathNavigate;
import net.minecraft.src.PathPoint;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import colonies.anglewyrm.src.EntityCitizen.jobs;
import colonies.thephpdev.src.BlockMiner;

public class EntityMiner extends EntityCitizen {
	
	private Vector3D closestMinerChest;
	
	public EntityMiner(World world) { 
		super(world);
        //this.targetTasks.addTask(1, new EntityAI
		
		this.texture = ConfigFile.getSkin("skinMiner");
		this.skills = new HashMap<jobs, Integer>(10);
		this.skills.put(jobs.unemployed, 10);

		// TODO: Would like miners to go hostile with a pickaxe if attacked
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ConfigFile.getSkin("skinMinerSwimming");
		}
		return ConfigFile.getSkin("skinMiner");
	}

	protected String getLivingSound() {
		if (citizenGreetings){
			if (Utility.getLootCategory()==3) {
				return "colonies.m-hello";
			}
		}
		return "";
	}

	// Mob Loot for default Citizen
	protected int getDropItemId() {
		int lootID=1;
		switch(Utility.getLootCategory()) {
			case 1: // Common
				switch(Utility.getLootCategory(3)) {
					case 1: return Item.appleRed.shiftedIndex;
					case 2: return Item.pickaxeStone.shiftedIndex;
					default:return Item.pickaxeSteel.shiftedIndex;
				}
			case 2: // Uncommon
				return Item.coal.shiftedIndex;
			case 3: // Rare
				return Item.goldNugget.shiftedIndex;
			default: // Legendary
				return Item.ingotGold.shiftedIndex;
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		int minerBlockID = ConfigFile.parseInt("MinerChestID");
		PathEntity path = pathToBlock(minerBlockID);
		navigateToChest(path);
	}
	
	private Vector3D getPositionRelativeToSelf(int x, int y, int z) {
		Vector3D ret = new Vector3D();
		ret.x = (int)(MathHelper.floor_double(posX) + Math.floor(x));
		ret.y = (int)(MathHelper.floor_double(posY) + Math.floor(y));
		ret.z = (int)(MathHelper.floor_double(posZ) + Math.floor(z));
		return ret;
	}
	
	private boolean isVectorSame(Vector3D v1, Vector3D v2) {
		if (v1 == null || v2 == null)
			return false;
		return (v1.x == v2.x && v1.y == v2.y && v1.z == v2.z); 
	}
	
	private boolean shouldNavigateToChest() {
		if (getNavigator().getPath() == null) return false;
		return (getNavigator().getPath().getCurrentPathLength() > 0.5);
	}
	
	private Vector3D getPointFromPathPoint(PathPoint p) {
		Vector3D point = new Vector3D();
		point.x = p.xCoord;
		point.y = p.yCoord;
		point.z = p.zCoord;
		return point;
	}
	
	private Vector3D getScanRadius() {
		Vector3D scanRadius = new Vector3D();
		scanRadius.x = 20;
		scanRadius.y = 5;
		scanRadius.z = 20;
		return scanRadius;
	}
	
	private void navigateToChest(PathEntity path) {
		if (path != null) {
			Vector3D chest = getPointFromPathPoint(path.getFinalPathPoint());
			if (shouldNavigateToChest()) {
				if (getNavigator().getPath() != null && isVectorSame(chest, closestMinerChest)) {
					getNavigator().onUpdateNavigation();
				} else {
					closestMinerChest = chest;
					getNavigator().setPath(path, 0.3f);
				}
			} else if (getNavigator().getPath() != null) {
				getNavigator().clearPathEntity();
			}
		}
	}
	
	private PathEntity pathToBlock(int blockID) {
		Vector3D scanRadius = getScanRadius();
		PathEntity ret = null;
		for (        int x = (int) -scanRadius.x; x < scanRadius.x; x++) {
			for (    int y = (int) -scanRadius.y; y < scanRadius.y; y++) {
				for (int z = (int) -scanRadius.z; z < scanRadius.z; z++) {
					Vector3D targetBlock = getPositionRelativeToSelf(x, y, z); 
					if (worldObj.getBlockId((int)targetBlock.x, (int)targetBlock.y, (int)targetBlock.z) == blockID) {
						if (!isVectorSame(targetBlock, closestMinerChest)) {
							PathEntity path = getNavigator().getPathToXYZ(targetBlock.x, targetBlock.y, targetBlock.z);
							if (path == null)
								continue;
							if (ret == null || ret.getCurrentPathLength() > path.getCurrentPathLength()) {
								ret = path;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	
}

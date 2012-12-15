package colonies.anglewyrm.src;

import java.util.HashMap;

import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAIMoveIndoors;
import net.minecraft.src.EntityAIMoveTwardsRestriction;
import net.minecraft.src.EntityAIOpenDoor;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityAIWatchClosest;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathEntity;
import net.minecraft.src.PathNavigate;
import net.minecraft.src.PathPoint;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class EntityCitizen extends EntityMob 
{
	public String name;
	public TileEntityColoniesChest home;
	public TileEntityTownHall homeTown;
	public static enum jobs {unemployed, miner, farmer, builder, lumberjack, fisherman }
	public jobs job;
	public HashMap<jobs, Integer> skills;
	private HashMap <Integer, PathNavigator> paths;
	
	public EntityCitizen(World par1World) {
		super(par1World);
		this.texture = ServerProxy.WANDERERSKIN_PNG;
		this.moveSpeed = Float.parseFloat(ConfigFile.get("CitizenMoveSpeed"));

		this.tasks.addTask(0, new EntityAISwimming(this));
	    this.tasks.addTask(1, new EntityAIFindShelterFromRain(this, 0.4f));
	    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
	    this.tasks.addTask(3, new EntityAIJoinTown(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	    this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));

	    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	    
	    this.job = jobs.unemployed;
	    this.skills = new HashMap<jobs, Integer>(10);
	    this.skills.put(jobs.unemployed, 10);
	    this.paths = new HashMap<Integer, PathNavigator>();
	    
	    this.citizenGreetings = Boolean.valueOf( ConfigFile.get("citizenGreetings") );
	}
	
	public void onLivingUpdate()
	{
		// Call baseAI for all citizens in range
		if (!this.worldObj.isRemote){
		}
        super.onLivingUpdate();
    }

	// Sounds
	Boolean citizenGreetings = true;
	protected String getLivingSound(){
		if(citizenGreetings){
			if(Utility.getLootCategory()==3){
				return "colonies.m-hello";
			}
		}
		return "";
    }
    protected String getHurtSound(){
        return "colonies.m-hurt";
    }
    protected String getDeathSound(){
        return "colonies.f-damnit";
    }

    protected void playStepSound(int par1, int par2, int par3, int par4){
        this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 1.0F);
    }

    // Mob Loot for default Citizen
    protected int getDropItemId() {
    	int lootID=1;
    	switch(Utility.getLootCategory()){
    	case 1: // Common
    			switch(Utility.getLootCategory(3)){
    			case 1: return Item.appleRed.shiftedIndex;
    			case 2: return Item.shovelStone.shiftedIndex;
    			default:return Item.bakedPotato.shiftedIndex;
    			}
    	case 2: // Uncommon
    		return Item.swordWood.shiftedIndex;
    	case 3: // Rare
    		return Item.goldNugget.shiftedIndex;
    	default: // Legendary
    		return Item.emerald.shiftedIndex;
    	}
    }

	public String getTexture() {
		if (this.isInWater()){
			return ConfigFile.getSkin("skinMaleSwimming");
		}
		return ConfigFile.getSkin("skinDefault");
    }

	public int getTotalArmorValue() {
        return 10;
    }

	public int getMaxHealth(){
		return 20;
	}

	protected boolean isAIEnabled() {
        return true;
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
	
	private boolean shouldNavigatePath(PathEntity path) {
		if (path == null) return false;
		return (path.getCurrentPathLength() > 0.5);
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
	
	protected void navigateToBlock(int blockID) {
		PathNavigator path = paths.get(Integer.valueOf(blockID));
		
		if (path == null) {
			path = pathToBlock(blockID);
			paths.put(Integer.valueOf(blockID), path);
		}
		
		doNavigation(path);
	}
	
	protected void navigateToBlock(PathNavigator path) {
		doNavigation(path);
	}
	
	private void doNavigation(PathNavigator path) {
		if (path == null)
			return;
		Vector3D chest = path.getLocation();
		if (shouldNavigatePath(path.getPath())) {
			if (getNavigator().getPath() != null && isVectorSame(getPointFromPathPoint(getNavigator().getPath().getFinalPathPoint()), chest)) {
				getNavigator().onUpdateNavigation();
			} else {
				getNavigator().setPath(path.getPath(), 0.3f);
			}
		} else if (getNavigator().getPath() != null) {
			getNavigator().clearPathEntity();
		}
	}
	
	protected PathNavigator pathToBlock(int blockID) {
		Vector3D scanRadius = getScanRadius();
		PathNavigator ret = null;
		for (        int x = (int) -scanRadius.x; x < scanRadius.x; x++) {
			for (    int y = (int) -scanRadius.y; y < scanRadius.y; y++) {
				for (int z = (int) -scanRadius.z; z < scanRadius.z; z++) {
					Vector3D targetBlock = getPositionRelativeToSelf(x, y, z); 
					if (worldObj.getBlockId((int)targetBlock.x, (int)targetBlock.y, (int)targetBlock.z) == blockID) {
						PathNavigator path = new PathNavigator(getNavigator(), targetBlock.x, targetBlock.y, targetBlock.z);
						if (ret == null || ret.getLength() > path.getLength()) {
							ret = path;
						}
					}
				}
			}
		}
		return ret;
	}
	
	protected Vector3D makeVector(double x, double y, double z) {
		Vector3D ret = new Vector3D();
		ret.x = (float) x;
		ret.y = (float) y;
		ret.z = (float) z;
		return ret;
	}
	
	protected class PathNavigator {
		private Vector3D location;
		private PathEntity path;
		private int length;
		private PathNavigate nav;
		
		/**
		 * Initializes an empty class
		 */
		public PathNavigator(PathNavigate nav) {
			length = 0;
			location = null;
			path = null;
			this.nav = nav;
		}
		
		/**
		 * Initializes the class with the ending location
		 * of the block
		 * @param x X location of ending block
		 * @param y Y location of ending block
		 * @param z Z location of ending block
		 */
		public PathNavigator(PathNavigate nav, double x, double y, double z) {
			this.nav = nav;
			calculatePath(x, y, z);
		}
		
		/**
		 * Calculates the path to the ending block that was
		 * set earlier in the constructor or the setLocation
		 * method.
		 */
		public void calculatePath() {
			if (location != null) {
				calculatePath(location.x, location.y, location.z);
			}
		}
		
		/**
		 * Uses the path finding algorithm to get the path
		 * to the ending block represented by the variables
		 * x, y, and z.
		 * @param x X location of ending block
		 * @param y Y location of ending block
		 * @param z Z location of ending block
		 */
		public void calculatePath(double x, double y, double z) {
			path = getNavigator().getPathToXYZ(x, y, z);
			if (hasPath()) {
				length = path.getCurrentPathLength();
			}
			location = new Vector3D();
			location.x = (float)x;
			location.y = (float)y;
			location.z = (float)z;
		}
		
		/**
		 * Sets the location of the ending block without doing path calculation
		 * @param x X location of block
		 * @param y Y location of block
		 * @param z Z location of block
		 */
		public void setLocation(double x, double y, double z) {
			location = new Vector3D();
			location.x = (float)x;
			location.y = (float)y;
			location.z = (float)z;
		}
		
		/**
		 * Sets all the variables using the PathEntity class
		 * @param path The PathEntity to use for calculations
		 */
		public void setPath(PathEntity path) {
			this.path = path;
			if (hasPath()) {
				this.length = path.getCurrentPathLength();
			}
			location = new Vector3D();
			location.x = path.getFinalPathPoint().xCoord;
			location.y = path.getFinalPathPoint().yCoord;
			location.z = path.getFinalPathPoint().zCoord;
		}
		
		/**
		 * Returns the path to the ending block. May be NULL
		 */
		public PathEntity getPath() {
			return path;
		}
		
		/**
		 * Returns the location of the ending block. May be NULL
		 */
		public Vector3D getLocation() {
			return location;
		}
		
		/**
		 * Returns the distance to the target with the path created
		 */
		public int getLength() {
			return length;
		}
		
		/**
		 * Returns the direct distance to the target
		 */
		public double getRawLength() {
			return Math.sqrt((location.x * location.x) + (location.y * location.y) + (location.z * location.z));
		}
		
		/**
		 * Returns true if the path has been calculated successfully
		 */
		public boolean hasPath() {
			return path != null;
		}
		
		/**
		 * Returns true if there is a location set
		 */
		public boolean hasLocation() {
			return location != null;
		}
		
		/**
		 * Returns the x location of the ending block
		 * @return
		 */
		public int getEndX() {
			if (location == null) return 0;
			return MathHelper.floor_double(location.x);
		}
		
		/**
		 * Returns the y location of the ending block
		 * @return
		 */
		public int getEndY() {
			if (location == null) return 0;
			return MathHelper.floor_double(location.y);
		}
		
		/**
		 * Returns the z location of the ending block
		 * @return
		 */
		public int getEndZ() {
			if (location == null) return 0;
			return MathHelper.floor_double(location.z);
		}
	}
	
}

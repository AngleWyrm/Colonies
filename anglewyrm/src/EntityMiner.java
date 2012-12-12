package colonies.anglewyrm.src;

import java.util.HashMap;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import paulscode.sound.Vector3D;
import colonies.vector67.src.TileEntityColoniesChest;

public class EntityMiner extends EntityCitizen {
	
	private Vector3D closestMinerChest;
	private boolean hasPickaxe;
	
	public EntityMiner(World world) { 
		super(world);
        //this.targetTasks.addTask(1, new EntityAI
		
		this.texture = ConfigFile.getSkin("skinMiner");
		this.skills = new HashMap<jobs, Integer>(10);
		this.skills.put(jobs.unemployed, 10);
		this.hasPickaxe = false;

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
		PathNavigator nav = pathToBlock(minerBlockID);
		if (nav == null) return;
		if (nav.getLength() < 1.5) {
			if (!hasPickaxe && nav.hasLocation()) {
				hasPickaxe = getPickaxeFromChest(nav.getEndX(), nav.getEndY(), nav.getEndZ());
			}
		} else {
			navigateToBlock(nav);
		}
	}
	
	private boolean getPickaxeFromChest(int x, int y, int z) {
		TileEntity entity = worldObj.getBlockTileEntity(x, y, z);
		if (entity instanceof TileEntityColoniesChest) {
			TileEntityColoniesChest chest = (TileEntityColoniesChest)entity;
			int invSize = chest.getSizeInventory();
			for (int i = 0; i < invSize; i++) {
				ItemStack stack = chest.getStackInSlot(i);
				if (stack != null && isPickaxe(stack.getItem())) {
					((TileEntityColoniesChest)entity).setInventorySlotContents(i, ((TileEntityColoniesChest)entity).decrStackSize(0, stack.stackSize-1));
					entity.onInventoryChanged();
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isPickaxe(Item item) {
		return (   item == Item.pickaxeWood
				|| item == Item.pickaxeStone
				|| item == Item.pickaxeSteel
				|| item == Item.pickaxeDiamond
				|| item == Item.pickaxeGold);
	}
	
}

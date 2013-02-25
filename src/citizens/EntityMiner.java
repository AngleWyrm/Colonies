package colonies.src.citizens;

import java.util.Date;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import paulscode.sound.Vector3D;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.vector67.src.EntityAIMine;

public class EntityMiner extends EntityCitizen{
	
	public InventoryCitizen inventory = new InventoryCitizen(this);
	private Vector3D closestMinerChest;
	private boolean hasPickaxe;
	private Item pickaxe;
	private static ItemStack defaultHeldItem;
	private long lastSearch;
	
	static {
		defaultHeldItem = null;
	}
	
	public EntityMiner(World world) { 
		super(world);
		
		desiredInventory.addItemStackToInventory(new ItemStack(Item.pickaxeSteel,1));
		desiredInventory.addItemStackToInventory(new ItemStack(Item.pickaxeStone,2));
		desiredInventory.addItemStackToInventory(new ItemStack(Block.torchWood,10));
		// System.out.println("here miner");
		this.targetTasks.addTask(1, new EntityAIMine(this));
		
		this.texture = ColoniesMain.skinMiner;
	    
	    // add this type of employment to the jobTypes if necessary
	    boolean alreadyInList = false;
	    for(EntityCitizen job : jobTypes){
	    	if(job instanceof EntityMiner){
	    		alreadyInList = true;
	    		break;
	    	}
	    }
	    if(!alreadyInList) jobTypes.add(this);

	    this.hasPickaxe = false;
		this.pickaxe = null;
		this.lastSearch = ticksExisted;

		// TODO: Would like miners to go hostile with a pickaxe if attacked
	}
	
	public String getTexture() {
		if (this.isInWater()) {
			return ColoniesMain.skinMinerSwimming;
		}
		return ColoniesMain.skinMiner;
	}

	public String getJobTitle(){
		return "Miner";
	}
	
	protected String getLivingSound() {
		if (citizenGreetings){
			if (Utility.getLootCategory()==3) {
				return "colonies.m-hello";
			}
		}
		return "";
	}

	// Mob Loot for Miner
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
		int minerBlockID = ColoniesMain.minerChestID;
		if (ticksExisted - lastSearch >= 40) {
			lastSearch = ticksExisted;
			/*PathNavigator nav; // = pathToBlock(minerBlockID);
			if (nav == null) return;
			if (nav.getLength() < 1.5) {
				if (nav.hasLocation()) {
					if (!hasPickaxe) {
						getPickaxeFromChest(nav.getEndX(), nav.getEndY(), nav.getEndZ());
					}
				}
			} else {
				navigateToBlock(nav);
			}
			*/
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
					setPickaxe(stack.getItem());
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
	public boolean hasPickaxe(){
			return hasPickaxe;
	}
	public ItemStack getHeldItem() {
		if (pickaxe == null) return null;
		return new ItemStack(pickaxe, 1);
	}
	
	private void removePickaxe() {
		this.hasPickaxe = false;
		this.pickaxe = null;
		this.defaultHeldItem = null;
	}
	
	private void setPickaxe(Item pickaxe) {
		this.hasPickaxe = true;
		this.pickaxe = pickaxe;
		this.defaultHeldItem = new ItemStack(pickaxe, 1);
	}
	
}

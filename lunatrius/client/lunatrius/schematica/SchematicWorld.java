package colonies.lunatrius.client.lunatrius.schematica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;


public class SchematicWorld extends World {
	private static final AnvilSaveHandler anvilSaveHandler = new AnvilSaveHandler(Minecraft.getMinecraftDir(), "mods/saves-schematica-dummy", false);
	private static final WorldSettings worldSettings = new WorldSettings(0, EnumGameType.CREATIVE, false, false, WorldType.FLAT);
	private static final Comparator<ItemStack> blockListComparator = new Comparator<ItemStack>() {
		@Override
		public int compare(ItemStack itemStackA, ItemStack itemStackB) {
			return (itemStackA.itemID * 16 + itemStackA.getItemDamage()) - (itemStackB.itemID * 16 + itemStackB.getItemDamage());
		}
	};

	protected static final List<Integer> blockListIgnoreID = new ArrayList<Integer>();
	protected static final List<Integer> blockListIgnoreMetadata = new ArrayList<Integer>();
	protected static final Map<Integer, Integer> blockListMapping = new HashMap<Integer, Integer>();

	private final Settings settings = Settings.instance();
	private ItemStack icon;
	private int[][][] blocks;
	private int[][][] metadata;
	private final List<TileEntity> tileEntities = new ArrayList<TileEntity>();
	private final List<ItemStack> blockList = new ArrayList<ItemStack>();
	private short width;
	private short length;
	private short height;

	public SchematicWorld() {
		super(anvilSaveHandler, "", null, worldSettings, null);
		this.icon = Settings.defaultIcon.copy();
		this.blocks = null;
		this.metadata = null;
		this.tileEntities.clear();
		this.width = 0;
		this.length = 0;
		this.height = 0;
	}

	public SchematicWorld(String icon, int[][][] blocks, int[][][] metadata, List<TileEntity> tileEntities, short width, short height, short length) {
		this();
		try {
			String[] parts = icon.split(":");
			if (parts.length == 1) {
				this.icon = new ItemStack(Integer.parseInt(parts[0]), 1, 0);
			} else if (parts.length == 2) {
				this.icon = new ItemStack(Integer.parseInt(parts[0]), 1, Integer.parseInt(parts[1]));
			}
		} catch (Exception e) {
			Settings.logger.log(e);
			this.icon = Settings.defaultIcon.copy();
		}
		this.blocks = blocks.clone();
		this.metadata = metadata.clone();
		if (tileEntities != null) {
			this.tileEntities.addAll(tileEntities);
		}
		this.width = width;
		this.length = length;
		this.height = height;

		generateBlockList();
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
		if (tagCompound.hasKey("Icon")) {
			this.icon.readFromNBT(tagCompound.getCompoundTag("Icon"));
		} else {
			this.icon = Settings.defaultIcon.copy();
		}

		byte localBlocks[] = tagCompound.getByteArray("Blocks");
		byte localMetadata[] = tagCompound.getByteArray("Data");

		boolean extra = false;
		byte extraBlocks[] = null;
		if ((extra = tagCompound.hasKey("Add")) == true) {
			extraBlocks = tagCompound.getByteArray("Add");
		}

		this.width = tagCompound.getShort("Width");
		this.length = tagCompound.getShort("Length");
		this.height = tagCompound.getShort("Height");

		this.blocks = new int[this.width][this.height][this.length];
		this.metadata = new int[this.width][this.height][this.length];

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				for (int z = 0; z < this.length; z++) {
					this.blocks[x][y][z] = (localBlocks[x + (y * this.length + z) * this.width]) & 0xFF;
					this.metadata[x][y][z] = (localMetadata[x + (y * this.length + z) * this.width]) & 0xFF;
					if (extra) {
						this.blocks[x][y][z] |= ((extraBlocks[x + (y * this.length + z) * this.width]) & 0xFF) << 8;
					}
				}
			}
		}

		this.tileEntities.clear();

		NBTTagList tileEntitiesList = tagCompound.getTagList("TileEntities");

		for (int i = 0; i < tileEntitiesList.tagCount(); i++) {
			TileEntity tileEntity = TileEntity.createAndLoadEntity((NBTTagCompound) tileEntitiesList.tagAt(i));
			if (tileEntity != null) {
				tileEntity.worldObj = this;
				this.tileEntities.add(tileEntity);
			}
		}

		refreshChests();

		generateBlockList();
	}

	public void writeToNBT(NBTTagCompound tagCompound) {
		NBTTagCompound tagCompoundIcon = new NBTTagCompound();
		this.icon.writeToNBT(tagCompoundIcon);
		tagCompound.setCompoundTag("Icon", tagCompoundIcon);

		tagCompound.setShort("Width", this.width);
		tagCompound.setShort("Length", this.length);
		tagCompound.setShort("Height", this.height);

		byte localBlocks[] = new byte[this.width * this.length * this.height];
		byte localMetadata[] = new byte[this.width * this.length * this.height];
		byte extraBlocks[] = new byte[this.width * this.length * this.height];
		boolean extra = false;

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				for (int z = 0; z < this.length; z++) {
					localBlocks[x + (y * this.length + z) * this.width] = (byte) this.blocks[x][y][z];
					localMetadata[x + (y * this.length + z) * this.width] = (byte) this.metadata[x][y][z];
					if ((extraBlocks[x + (y * this.length + z) * this.width] = (byte) (this.blocks[x][y][z] >> 8)) > 0) {
						extra = true;
					}
				}
			}
		}

		tagCompound.setString("Materials", "Alpha");
		tagCompound.setByteArray("Blocks", localBlocks);
		tagCompound.setByteArray("Data", localMetadata);
		if (extra) {
			tagCompound.setByteArray("Add", extraBlocks);
		}
		tagCompound.setTag("Entities", new NBTTagList());

		NBTTagList tileEntitiesList = new NBTTagList();
		for (TileEntity tileEntity : this.tileEntities) {
			NBTTagCompound tileEntityTagCompound = new NBTTagCompound();
			tileEntity.writeToNBT(tileEntityTagCompound);
			tileEntitiesList.appendTag(tileEntityTagCompound);
		}

		tagCompound.setTag("TileEntities", tileEntitiesList);
	}

	private void generateBlockList() {
		this.blockList.clear();

		int x, y, z, itemID, itemDamage;
		ItemStack itemStack = null;

		for (x = 0; x < this.width; x++) {
			for (y = 0; y < this.height; y++) {
				for (z = 0; z < this.length; z++) {
					itemID = this.blocks[x][y][z];
					itemDamage = this.metadata[x][y][z];

					if (itemID == 0 || blockListIgnoreID.contains(itemID)) {
						continue;
					}

					if (blockListIgnoreMetadata.contains(itemID)) {
						itemDamage = 0;
					}

					if (blockListMapping.containsKey(itemID)) {
						itemID = blockListMapping.get(itemID);
					}

					if (itemID == Block.wood.blockID || itemID == Block.leaves.blockID) {
						itemDamage &= 0x03;
					}

					if (itemID == Block.stoneSingleSlab.blockID || itemID == Block.woodSingleSlab.blockID) {
						itemDamage &= 0x07;
					}

					if (itemID >= 256) {
						itemDamage = 0;
					}

					if (itemID - 256 == Block.cocoaPlant.blockID) {
						itemDamage = 0x03;
					}

					if (itemID == Item.skull.shiftedIndex) {
						itemDamage = this.metadata[x][y][z];
					}

					itemStack = null;
					for (ItemStack block : this.blockList) {
						if (block.itemID == itemID && block.getItemDamage() == itemDamage) {
							itemStack = block;
							itemStack.stackSize++;
							break;
						}
					}

					if (itemStack == null) {
						this.blockList.add(new ItemStack(itemID, 1, itemDamage));
					}
				}
			}
		}
		Collections.sort(this.blockList, blockListComparator);
	}

	@Override
	public int getBlockId(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
			return 0;
		}
		return (this.blocks[x][y][z]) & 0xFFF;
	}

	@Override
	public TileEntity getBlockTileEntity(int x, int y, int z) {
		for (int i = 0; i < this.tileEntities.size(); i++) {
			if (this.tileEntities.get(i).xCoord == x && this.tileEntities.get(i).yCoord == y && this.tileEntities.get(i).zCoord == z) {
				return this.tileEntities.get(i);
			}
		}
		return null;
	}

	@Override
	public int getLightBrightnessForSkyBlocks(int var1, int var2, int var3, int var4) {
		return 15;
	}

	@Override
	public float getBrightness(int var1, int var2, int var3, int var4) {
		return 1.0f;
	}

	@Override
	public float getLightBrightness(int x, int y, int z) {
		return 1.0f;
	}

	@Override
	public int getBlockMetadata(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
			return 0;
		}
		return this.metadata[x][y][z];
	}

	@Override
	public Material getBlockMaterial(int x, int y, int z) {
		return getBlock(x, y, z) != null ? getBlock(x, y, z).blockMaterial : Material.air;
	}

	@Override
	public boolean isBlockOpaqueCube(int x, int y, int z) {
		if (this.settings.renderingLayer != -1 && this.settings.renderingLayer != y) {
			return false;
		}
		return getBlock(x, y, z) != null && getBlock(x, y, z).isOpaqueCube();
	}

	@Override
	public boolean isBlockNormalCube(int x, int y, int z) {
		return getBlockMaterial(x, y, z).isOpaque() && getBlock(x, y, z) != null && getBlock(x, y, z).renderAsNormalBlock();
	}

	@Override
	public boolean isAirBlock(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
			return true;
		}
		return this.blocks[x][y][z] == 0;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int var1, int var2) {
		return BiomeGenBase.forest;
	}

	@Override
	public int getHeight() {
		return this.height + 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean extendedLevelsInChunkCache() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesBlockHaveSolidTopSurface(int var1, int var2, int var3) {
		return false;
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		return null;
	}

	@Override
	public Entity getEntityByID(int var1) {
		return null;
	}

	@Override
	public boolean blockExists(int x, int y, int z) {
		return false;
	}

	public void setBlockMetadata(int x, int y, int z, byte metadata) {
		this.metadata[x][y][z] = metadata;
	}

	public Block getBlock(int x, int y, int z) {
		return Block.blocksList[getBlockId(x, y, z)];
	}

	public void setTileEntities(List<TileEntity> tileEntities) {
		this.tileEntities.clear();
		this.tileEntities.addAll(tileEntities);
	}

	public List<TileEntity> getTileEntities() {
		return this.tileEntities;
	}

	public List<ItemStack> getBlockList() {
		return this.blockList;
	}

	public void refreshChests() {
		TileEntity tileEntity;
		for (int i = 0; i < this.tileEntities.size(); i++) {
			tileEntity = this.tileEntities.get(i);

			if (tileEntity instanceof TileEntityChest) {
				checkForAdjacentChests((TileEntityChest) tileEntity);
			}
		}
	}

	private void checkForAdjacentChests(TileEntityChest tileEntityChest) {
		tileEntityChest.adjacentChestChecked = true;
		tileEntityChest.adjacentChestZNeg = null;
		tileEntityChest.adjacentChestXPos = null;
		tileEntityChest.adjacentChestXNeg = null;
		tileEntityChest.adjacentChestZPosition = null;

		if (getBlockId(tileEntityChest.xCoord - 1, tileEntityChest.yCoord, tileEntityChest.zCoord) == Block.chest.blockID) {
			tileEntityChest.adjacentChestXNeg = (TileEntityChest) getBlockTileEntity(tileEntityChest.xCoord - 1, tileEntityChest.yCoord, tileEntityChest.zCoord);
		}

		if (getBlockId(tileEntityChest.xCoord + 1, tileEntityChest.yCoord, tileEntityChest.zCoord) == Block.chest.blockID) {
			tileEntityChest.adjacentChestXPos = (TileEntityChest) getBlockTileEntity(tileEntityChest.xCoord + 1, tileEntityChest.yCoord, tileEntityChest.zCoord);
		}

		if (getBlockId(tileEntityChest.xCoord, tileEntityChest.yCoord, tileEntityChest.zCoord - 1) == Block.chest.blockID) {
			tileEntityChest.adjacentChestZNeg = (TileEntityChest) getBlockTileEntity(tileEntityChest.xCoord, tileEntityChest.yCoord, tileEntityChest.zCoord - 1);
		}

		if (getBlockId(tileEntityChest.xCoord, tileEntityChest.yCoord, tileEntityChest.zCoord + 1) == Block.chest.blockID) {
			tileEntityChest.adjacentChestZPosition = (TileEntityChest) getBlockTileEntity(tileEntityChest.xCoord, tileEntityChest.yCoord, tileEntityChest.zCoord + 1);
		}
	}

	public void flip() {
		int tmp;
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				for (int z = 0; z < (this.length + 1) / 2; z++) {
					tmp = this.blocks[x][y][z];
					this.blocks[x][y][z] = this.blocks[x][y][this.length - 1 - z];
					this.blocks[x][y][this.length - 1 - z] = tmp;

					if (z == this.length - 1 - z) {
						this.metadata[x][y][z] = flipMetadataZ(this.metadata[x][y][z], this.blocks[x][y][z]);
					} else {
						tmp = this.metadata[x][y][z];
						this.metadata[x][y][z] = flipMetadataZ(this.metadata[x][y][this.length - 1 - z], this.blocks[x][y][z]);
						this.metadata[x][y][this.length - 1 - z] = flipMetadataZ(tmp, this.blocks[x][y][this.length - 1 - z]);
					}
				}
			}
		}

		TileEntity tileEntity;
		for (int i = 0; i < this.tileEntities.size(); i++) {
			tileEntity = this.tileEntities.get(i);
			tileEntity.zCoord = this.length - 1 - tileEntity.zCoord;
			tileEntity.blockMetadata = this.metadata[tileEntity.xCoord][tileEntity.yCoord][tileEntity.zCoord];

			if (tileEntity instanceof TileEntitySkull && tileEntity.blockMetadata == 0x1) {
				TileEntitySkull skullTileEntity = (TileEntitySkull) tileEntity;
				int angle = skullTileEntity.func_82119_b();
				int base = 0;
				if (angle <= 7) {
					base = 4;
				} else {
					base = 12;
				}

				skullTileEntity.func_82116_a((2 * base - angle) & 15);
			}
		}

		refreshChests();
	}

	private int flipMetadataZ(int blockMetadata, int blockId) {
		if (blockId == Block.torchWood.blockID || blockId == Block.torchRedstoneActive.blockID || blockId == Block.torchRedstoneIdle.blockID) {
			switch (blockMetadata) {
			case 0x3:
				return 0x4;
			case 0x4:
				return 0x3;
			}
		} else if (blockId == Block.rail.blockID) {
			switch (blockMetadata) {
			case 0x4:
				return 0x5;
			case 0x5:
				return 0x4;
			case 0x6:
				return 0x9;
			case 0x7:
				return 0x8;
			case 0x8:
				return 0x7;
			case 0x9:
				return 0x6;
			}
		} else if (blockId == Block.railDetector.blockID || blockId == Block.railPowered.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x4:
				return (byte) (0x5 | (blockMetadata & 0x8));
			case 0x5:
				return (byte) (0x4 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.stairCompactCobblestone.blockID || blockId == Block.stairCompactPlanks.blockID || blockId == Block.stairsBrick.blockID || blockId == Block.stairsNetherBrick.blockID || blockId == Block.stairsStoneBrickSmooth.blockID || blockId == Block.stairsSandStone.blockID || blockId == Block.stairsWoodSpruce.blockID || blockId == Block.stairsWoodBirch.blockID || blockId == Block.stairsWoodJungle.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x2:
				return (byte) (0x3 | (blockMetadata & 0x4));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0x4));
			}
		} else if (blockId == Block.lever.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x3:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x3 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.doorWood.blockID || blockId == Block.doorSteel.blockID) {
			if ((blockMetadata & 0x8) == 0x8) {
				return (byte) (blockMetadata ^ 0x1);
			}
			switch (blockMetadata & 0x3) {
			case 0x1:
				return (byte) ((0x3 | (blockMetadata & 0xC)));
			case 0x3:
				return (byte) ((0x1 | (blockMetadata & 0xC)));
			}
		} else if (blockId == Block.stoneButton.blockID || blockId == Block.woodenButton.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x3:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x3 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.signPost.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x8;
			case 0x1:
				return 0x7;
			case 0x2:
				return 0x6;
			case 0x3:
				return 0x5;
			case 0x4:
				return 0x4;
			case 0x5:
				return 0x3;
			case 0x6:
				return 0x2;
			case 0x7:
				return 0x1;
			case 0x8:
				return 0x0;
			case 0x9:
				return 0xF;
			case 0xA:
				return 0xE;
			case 0xB:
				return 0xD;
			case 0xC:
				return 0xC;
			case 0xD:
				return 0xB;
			case 0xE:
				return 0xA;
			case 0xF:
				return 0x9;
			}
		} else if (blockId == Block.ladder.blockID || blockId == Block.signWall.blockID || blockId == Block.stoneOvenActive.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.dispenser.blockID || blockId == Block.chest.blockID || blockId == Block.enderChest.blockID) {
			switch (blockMetadata) {
			case 0x2:
				return 0x3;
			case 0x3:
				return 0x2;
			}
		} else if (blockId == Block.pumpkin.blockID || blockId == Block.pumpkinLantern.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x2;
			case 0x2:
				return 0x0;
			}
		} else if (blockId == Block.bed.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x2 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.redstoneRepeaterActive.blockID || blockId == Block.redstoneRepeaterIdle.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x2 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.trapdoor.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x1;
			case 0x1:
				return 0x0;
			}
		} else if (blockId == Block.pistonBase.blockID || blockId == Block.pistonStickyBase.blockID || blockId == Block.pistonExtension.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x2:
				return (byte) (0x3 | (blockMetadata & 0x8));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.vine.blockID) {
			return (byte) ((blockMetadata & 0xA) | ((blockMetadata & 0x1) << 2) | ((blockMetadata & 0x4) >> 2));
		} else if (blockId == Block.fenceGate.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x2 | (blockMetadata & 0x4));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0x4));
			}
		} else if (blockId == Block.tripWireSource.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x2 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x1 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.cocoaPlant.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x2 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x1 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.anvil.blockID) {
			switch (blockMetadata & 0x03) {
			case 0x1:
				return 0x3 | (blockMetadata & 0xC);
			case 0x3:
				return 0x1 | (blockMetadata & 0xC);
			case 0x0:
				return 0x2 | (blockMetadata & 0xC);
			case 0x2:
				return 0x0 | (blockMetadata & 0xC);
			}
		} else if (blockId == Block.skull.blockID) {
			System.out.println(blockMetadata);
			switch (blockMetadata) {
			case 0x2:
				return 0x3;
			case 0x3:
				return 0x2;
			case 0x4:
				return 0x5;
			case 0x5:
				return 0x4;

			default:
				break;
			}
		}

		return blockMetadata;
	}

	public void rotate() {
		int[][][] localBlocks = new int[this.length][this.height][this.width];
		int[][][] localMetadata = new int[this.length][this.height][this.width];

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				for (int z = 0; z < this.length; z++) {
					localBlocks[z][y][x] = this.blocks[this.width - 1 - x][y][z];
					localMetadata[z][y][x] = rotateMetadata(this.metadata[this.width - 1 - x][y][z], this.blocks[this.width - 1 - x][y][z]);
				}
			}
		}

		this.blocks = localBlocks;
		this.metadata = localMetadata;

		TileEntity tileEntity;
		int coord;
		for (int i = 0; i < this.tileEntities.size(); i++) {
			tileEntity = this.tileEntities.get(i);
			coord = tileEntity.xCoord;
			tileEntity.xCoord = tileEntity.zCoord;
			tileEntity.zCoord = this.width - 1 - coord;
			tileEntity.blockMetadata = this.metadata[tileEntity.xCoord][tileEntity.yCoord][tileEntity.zCoord];

			if (tileEntity instanceof TileEntitySkull && tileEntity.blockMetadata == 0x1) {
				TileEntitySkull skullTileEntity = (TileEntitySkull) tileEntity;
				skullTileEntity.func_82116_a((skullTileEntity.func_82119_b() + 12) & 15);
			}

		}

		refreshChests();

		short tmp = this.width;
		this.width = this.length;
		this.length = tmp;
	}

	private int rotateMetadata(int blockMetadata, int blockId) {
		if (blockId == Block.torchWood.blockID || blockId == Block.torchRedstoneActive.blockID || blockId == Block.torchRedstoneIdle.blockID) {
			switch (blockMetadata) {
			case 0x1:
				return 0x4;
			case 0x2:
				return 0x3;
			case 0x3:
				return 0x1;
			case 0x4:
				return 0x2;
			}
		} else if (blockId == Block.rail.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x1;
			case 0x1:
				return 0x0;
			case 0x2:
				return 0x4;
			case 0x3:
				return 0x5;
			case 0x4:
				return 0x3;
			case 0x5:
				return 0x2;
			case 0x6:
				return 0x9;
			case 0x7:
				return 0x6;
			case 0x8:
				return 0x7;
			case 0x9:
				return 0x8;
			}
		} else if (blockId == Block.railDetector.blockID || blockId == Block.railPowered.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x0:
				return (byte) (0x1 | (blockMetadata & 0x8));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0x8));
			case 0x2:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x3:
				return (byte) (0x5 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x3 | (blockMetadata & 0x8));
			case 0x5:
				return (byte) (0x2 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.stairCompactCobblestone.blockID || blockId == Block.stairCompactPlanks.blockID || blockId == Block.stairsBrick.blockID || blockId == Block.stairsNetherBrick.blockID || blockId == Block.stairsStoneBrickSmooth.blockID || blockId == Block.stairsSandStone.blockID || blockId == Block.stairsWoodSpruce.blockID || blockId == Block.stairsWoodBirch.blockID || blockId == Block.stairsWoodJungle.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0x4));
			case 0x1:
				return (byte) (0x2 | (blockMetadata & 0x4));
			case 0x2:
				return (byte) (0x0 | (blockMetadata & 0x4));
			case 0x3:
				return (byte) (0x1 | (blockMetadata & 0x4));
			}
		} else if (blockId == Block.lever.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x1:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x2:
				return (byte) (0x3 | (blockMetadata & 0x8));
			case 0x3:
				return (byte) (0x1 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x2 | (blockMetadata & 0x8));
			case 0x5:
				return (byte) (0x6 | (blockMetadata & 0x8));
			case 0x6:
				return (byte) (0x5 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.doorWood.blockID || blockId == Block.doorSteel.blockID) {
			if ((blockMetadata & 0x8) == 0x8) {
				return blockMetadata;
			}
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.stoneButton.blockID || blockId == Block.woodenButton.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x1:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x2:
				return (byte) (0x3 | (blockMetadata & 0x8));
			case 0x3:
				return (byte) (0x1 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x2 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.signPost.blockID) {
			return (byte) ((blockMetadata + 0xC) % 0x10);
			/*
			 * switch (blockMetadata) { case 0x0: return 0xC; case 0x1: return
			 * 0xD; case 0x2: return 0xE; case 0x3: return 0xF; case 0x4: return
			 * 0x0; case 0x5:
			 * return 0x1; case 0x6: return 0x2; case 0x7: return 0x3; case 0x8:
			 * return 0x4; case 0x9: return 0x5; case 0xA: return 0x6; case 0xB:
			 * return 0x7;
			 * case 0xC: return 0x8; case 0xD: return 0x9; case 0xE: return 0xA;
			 * case 0xF: return 0xB; }
			 */
		} else if (blockId == Block.ladder.blockID || blockId == Block.signWall.blockID || blockId == Block.stoneOvenActive.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.dispenser.blockID || blockId == Block.chest.blockID || blockId == Block.enderChest.blockID) {
			switch (blockMetadata) {
			case 0x2:
				return 0x4;
			case 0x3:
				return 0x5;
			case 0x4:
				return 0x3;
			case 0x5:
				return 0x2;
			}
		} else if (blockId == Block.pumpkin.blockID || blockId == Block.pumpkinLantern.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x3;
			case 0x1:
				return 0x0;
			case 0x2:
				return 0x1;
			case 0x3:
				return 0x2;
			}
		} else if (blockId == Block.bed.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.redstoneRepeaterActive.blockID || blockId == Block.redstoneRepeaterIdle.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.trapdoor.blockID) {
			switch (blockMetadata) {
			case 0x0:
				return 0x2;
			case 0x1:
				return 0x3;
			case 0x2:
				return 0x1;
			case 0x3:
				return 0x0;
			}
		} else if (blockId == Block.pistonBase.blockID || blockId == Block.pistonStickyBase.blockID || blockId == Block.pistonExtension.blockID) {
			switch (blockMetadata & 0x7) {
			case 0x0:
				return (byte) (0x0 | (blockMetadata & 0x8));
			case 0x1:
				return (byte) (0x1 | (blockMetadata & 0x8));
			case 0x2:
				return (byte) (0x4 | (blockMetadata & 0x8));
			case 0x3:
				return (byte) (0x5 | (blockMetadata & 0x8));
			case 0x4:
				return (byte) (0x3 | (blockMetadata & 0x8));
			case 0x5:
				return (byte) (0x2 | (blockMetadata & 0x8));
			}
		} else if (blockId == Block.vine.blockID) {
			return (byte) ((blockMetadata >> 1) | ((blockMetadata & 0x1) << 3));
		} else if (blockId == Block.fenceGate.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0x4));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0x4));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0x4));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0x4));
			}
		} else if (blockId == Block.tripWireSource.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.cocoaPlant.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return (byte) (0x3 | (blockMetadata & 0xC));
			case 0x1:
				return (byte) (0x0 | (blockMetadata & 0xC));
			case 0x2:
				return (byte) (0x1 | (blockMetadata & 0xC));
			case 0x3:
				return (byte) (0x2 | (blockMetadata & 0xC));
			}
		} else if (blockId == Block.wood.blockID) {
			switch (blockMetadata & 0xC) {
			case 0x4:
				return (byte) (0x8 | (blockMetadata & 0x3));
			case 0x8:
				return (byte) (0x4 | (blockMetadata & 0x3));
			}
		} else if (blockId == Block.anvil.blockID) {
			switch (blockMetadata & 0x3) {
			case 0x0:
				return 0x3 | (blockMetadata & 0xC);
			case 0x1:
				return 0x0 | (blockMetadata & 0xC);
			case 0x2:
				return 0x1 | (blockMetadata & 0xC);
			case 0x3:
				return 0x2 | (blockMetadata & 0xC);
			}
		} else if (blockId == Block.skull.blockID) {
			switch (blockMetadata) {
			case 0x5:
				return 0x2;
			case 0x2:
				return 0x4;
			case 0x4:
				return 0x3;
			case 0x3:
				return 0x5;
			}
		}

		return blockMetadata;
	}

	public int width() {
		return this.width;
	}

	public int length() {
		return this.length;
	}

	public int height() {
		return this.height;
	}
}

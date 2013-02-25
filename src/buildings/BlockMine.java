package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMine extends BlockColoniesChest {
	
	public BlockMine(int id) {
		super(id);
		setBlockName("Mine");
		tileEntity = new TileEntityMine();
		setCreativeTab(ColoniesMain.coloniesTab);
		setTextureFile(ClientProxy.MINERCHEST_PNG);
	}
	
	private void dropItems(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = Utility.rng.nextFloat() * 0.8F + 0.1F;
				float ry = Utility.rng.nextFloat() * 0.8F + 0.1F;
				float rz = Utility.rng.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world,
						x + rx, y + ry, z + rz,
						new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.item.setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = Utility.rng.nextGaussian() * factor;
				entityItem.motionY = Utility.rng.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = Utility.rng.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public String getTextureFile() {
		return ClientProxy.MINERCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityMine();
        return tileEntity;
     }

    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teMine){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teMine);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

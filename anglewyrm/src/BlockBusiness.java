package colonies.anglewyrm.src;

import colonies.vector67.src.TileEntityColoniesChest;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockBusiness extends BlockContainer 
{
	protected BlockBusiness(int id, Material material) {
		super(id, material);
		setBlockName("BlockBusiness");
		setHardness(1.0F);
		setResistance(5.0f);
	    setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z,
    	EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
        	return false;
        }
        //opens gui, to be implemented later
        //player.openGui();
        return true;
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
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBusiness();
	}
}

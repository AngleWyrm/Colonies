package colonies.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityEnchantmentTable;
import net.minecraft.src.World;
import colonies.eragon.src.ContainerColoniesChest;
import colonies.src.ColoniesMain;
import colonies.src.buildings.BlockColoniesChest;
import colonies.src.buildings.ColoniesChestRenderHelper;


public class BlockResearchBlock extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random researchRand = new Random();

    /** True if this is an active furnace, false if idle */
    private final boolean isActive;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepResearchBlockInventory = false;

    protected BlockResearchBlock(int par1, boolean par2)
    {
        super(par1, Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
        this.isActive = par2;
        this.blockIndexInTexture = 0;
    }
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ColoniesMain.researchBlock.blockID;
    }

     
    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
	@Override
	public String getTextureFile() {
		return ("/colonies/gfx/ResearchBench.PNG");
	}


    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        for (int var6 = par2 - 2; var6 <= par2 + 2; ++var6)
        {
            for (int var7 = par4 - 2; var7 <= par4 + 2; ++var7)
            {
                if (var6 > par2 - 2 && var6 < par2 + 2 && var7 == par4 - 1)
                {
                    var7 = par4 + 2;
                }

                if (par5Random.nextInt(16) == 0)
                {
                    for (int var8 = par3; var8 <= par3 + 1; ++var8)
                    {
                        if (par1World.getBlockId(var6, var8, var7) == Block.bookShelf.blockID)
                        {
                            if (!par1World.isAirBlock((var6 - par2) / 2 + par2, var8, (var7 - par4) / 2 + par4))
                            {
                                break;
                            }

                            par1World.spawnParticle("enchantmenttable", (double)par2 + 0.5D, (double)par3 + 2.0D, (double)par4 + 0.5D, (double)((float)(var6 - par2) + par5Random.nextFloat()) - 0.5D, (double)((float)(var8 - par3) - par5Random.nextFloat() - 1.0F), (double)((float)(var7 - par4) + par5Random.nextFloat()) - 0.5D);
                        }
                    }
                }
            }
        }
    }
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean isOpaqueCube()
    {
    	return false;
    }
	
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.getBlockTextureFromSide(par1);
    }
    
    @Override
    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
   public int getBlockTextureFromSide(int par1)
    {
	   return par1 == 0 ? this.blockIndexInTexture + 17 : (par1 == 1 ? this.blockIndexInTexture : this.blockIndexInTexture + 16);
    }
   /**
    * Returns a new instance of a block's tile entity class. Called on placing the block.
    */

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityResearchBlock var10 = (TileEntityResearchBlock)par1World.getBlockTileEntity(par2, par3, par4);

            if (var10 != null)
            {
            	openGUI(par5EntityPlayer, par1World, par2, par3, par4);
  
            }

            return true;
        }
    }


    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
   public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
   {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
       keepResearchBlockInventory = true;

        if (par0)
        {
            par1World.setBlockWithNotify(par2, par3, par4, ColoniesMain.researchBlock.blockID);
        }
        else
        {
            par1World.setBlockWithNotify(par2, par3, par4, ColoniesMain.researchBlock.blockID);
        }

       keepResearchBlockInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5);

        if (var6 != null)
        {
            var6.validate();
            par1World.setBlockTileEntity(par2, par3, par4, var6);
        }
    }

   @Override
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityResearchBlock();
    }

    /**
     * Called when the block is placed in the world.
     */

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepResearchBlockInventory)
        {
            TileEntityResearchBlock var7 = (TileEntityResearchBlock)par1World.getBlockTileEntity(par2, par3, par4);

            if (var7 != null)
            {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
                {
                    ItemStack var9 = var7.getStackInSlot(var8);

                    if (var9 != null)
                    {
                        float var10 = this.researchRand.nextFloat() * 0.8F + 0.1F;
                        float var11 = this.researchRand.nextFloat() * 0.8F + 0.1F;
                        float var12 = this.researchRand.nextFloat() * 0.8F + 0.1F;

                        while (var9.stackSize > 0)
                        {
                            int var13 = this.researchRand.nextInt(21) + 10;

                            if (var13 > var9.stackSize)
                            {
                                var13 = var9.stackSize;
                            }

                            var9.stackSize -= var13;
                            EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

                            if (var9.hasTagCompound())
                            {
                                var14.item.setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }

                            float var15 = 0.05F;
                            var14.motionX = (double)((float)this.researchRand.nextGaussian() * var15);
                            var14.motionY = (double)((float)this.researchRand.nextGaussian() * var15 + 0.2F);
                            var14.motionZ = (double)((float)this.researchRand.nextGaussian() * var15);
                            par1World.spawnEntityInWorld(var14);
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    public void openGUI(EntityPlayer player, World theWorld, int x, int y, int z)
    {
    	player.openGui(ColoniesMain.instance, 0, theWorld, x, y, z);
    }
}

package colonies.vector67.src;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.anglewyrm.src.BlockTownHall;
import colonies.anglewyrm.src.ClientProxy;
import colonies.anglewyrm.src.ColoniesMain;
import colonies.anglewyrm.src.TileEntityTownHall;
import colonies.anglewyrm.src.Utility;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import colonies.anglewyrm.src.Point;

public class BlockColoniesChest extends BlockContainer {

	//private Random random = new Random();
	public BlockColoniesChest(int id){
		super(id, Material.wood);
		setBlockName("ColoniesChest");
		setHardness(1.0F);
	    setRequiresSelfNotify();
	    setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	    setCreativeTab(CreativeTabs.tabDecorations);
	}
	@Override
	  public String getTextureFile() {
	    return ClientProxy.CHESTCONTAINER_PNG;
	  }
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 22;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.blockID)
        {
            this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.blockID)
        {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        }
        else if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.blockID)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.blockID)
        {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        }
        else
        {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World theWorld, int x, int y, int z)
    {
        super.onBlockAdded(theWorld, x, y, z);
        int var5 = theWorld.getBlockId(x, y, z - 1);
        int var6 = theWorld.getBlockId(x, y, z + 1);
        int var7 = theWorld.getBlockId(x - 1, y, z);
        int var8 = theWorld.getBlockId(x + 1, y, z);
    }

    // to be over-ridden by the various building types
    public boolean addBlockToTown(TileEntityColoniesChest _teChest){
    	return false;
    }
    public boolean removeChestFromTown(TileEntityColoniesChest _teChest){
    	return false;
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World theWorld, int x, int y, int z, EntityLiving par5EntityLiving)
    {
    	// TODO: override in subclasses
    	addBlockToTown((TileEntityColoniesChest) theWorld.getBlockTileEntity(x, y, z));
    	
        int var6 = theWorld.getBlockId(x, y, z - 1);
        int var7 = theWorld.getBlockId(x, y, z + 1);
        int var8 = theWorld.getBlockId(x - 1, y, z);
        int var9 = theWorld.getBlockId(x + 1, y, z);
        byte var10 = 0;
        int var11 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var11 == 0)
        {
            var10 = 2;
        }

        if (var11 == 1)
        {
            var10 = 5;
        }

        if (var11 == 2)
        {
            var10 = 3;
        }

        if (var11 == 3)
        {
            var10 = 4;
        }

        if (var6 != this.blockID && var7 != this.blockID && var8 != this.blockID && var9 != this.blockID)
        {
            theWorld.setBlockMetadataWithNotify(x, y, z, var10);
        }
        else
        {
            if ((var6 == this.blockID || var7 == this.blockID) && (var10 == 4 || var10 == 5))
            {
                if (var6 == this.blockID)
                {
                    theWorld.setBlockMetadataWithNotify(x, y, z - 1, var10);
                }
                else
                {
                    theWorld.setBlockMetadataWithNotify(x, y, z + 1, var10);
                }

                theWorld.setBlockMetadataWithNotify(x, y, z, var10);
            }

            if ((var8 == this.blockID || var9 == this.blockID) && (var10 == 2 || var10 == 3))
            {
                if (var8 == this.blockID)
                {
                    theWorld.setBlockMetadataWithNotify(x - 1, y, z, var10);
                }
                else
                {
                    theWorld.setBlockMetadataWithNotify(x + 1, y, z, var10);
                }

                theWorld.setBlockMetadataWithNotify(x, y, z, var10);
            }
        }
    }
    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return 1;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return 4;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World theWorld, int x, int y, int z)
    {
    	// RULE: Town hall placed first
    	if(!(this instanceof BlockTownHall)){
    		if(TileEntityTownHall.playerTown == null){
				Utility.chatMessage("Place a town hall first");
				return false;
    		}
    		else{
    			// RULE: place only within town
    			Point here = new Point(x,y,z);
    			double distanceToTown = here.getDistance(TileEntityTownHall.playerTown.getPoint());
    			if(Math.floor(distanceToTown) > Math.floor(TileEntityTownHall.playerTown.townPerimeter)){
    				Utility.chatMessage("Too far from Town ("
    						+ (int)distanceToTown + " > " 
    						+ (int)TileEntityTownHall.playerTown.townPerimeter + ")");
    				return false;
    			}
    		}
    	}
    	

    	
    	
        int var5 = 0;

        if (theWorld.getBlockId(x - 1, y, z) == this.blockID)
        {
            ++var5;
        }

        if (theWorld.getBlockId(x + 1, y, z) == this.blockID)
        {
            ++var5;
        }

        if (theWorld.getBlockId(x, y, z - 1) == this.blockID)
        {
            ++var5;
        }

        if (theWorld.getBlockId(x, y, z + 1) == this.blockID)
        {
            ++var5;
        }

        return var5 > 1 ? false : (this.isThereANeighborChest(theWorld, x - 1, y, z) ? false : (this.isThereANeighborChest(theWorld, x + 1, y, z) ? false : (this.isThereANeighborChest(theWorld, x, y, z - 1) ? false : !this.isThereANeighborChest(theWorld, x, y, z + 1))));
    }
    
    private boolean isThereANeighborChest(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockId(par2, par3, par4) != this.blockID ? false : (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID ? true : (par1World.getBlockId(par2 + 1, par3, par4) == this.blockID ? true : (par1World.getBlockId(par2, par3, par4 - 1) == this.blockID ? true : par1World.getBlockId(par2, par3, par4 + 1) == this.blockID)));
    }
    
    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World theWorld, int x, int y, int z, int par5, int par6)
    {
    	removeChestFromTown((TileEntityColoniesChest) theWorld.getBlockTileEntity(x, y, z));
    	
    	
        TileEntityColoniesChest var7 = (TileEntityColoniesChest)theWorld.getBlockTileEntity(x, y, z);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = Utility.rng.nextFloat() * 0.8F + 0.1F;
                    float var11 = Utility.rng.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = Utility.rng.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; theWorld.spawnEntityInWorld(var14))
                    {
                        int var13 = Utility.rng.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(theWorld, (double)((float)x + var10), (double)((float)y + var11), (double)((float)z + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)Utility.rng.nextGaussian() * var15);
                        var14.motionY = (double)((float)Utility.rng.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)Utility.rng.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.item.setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(theWorld, x, y, z, par5, par6);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World theWorld, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        Object chest = (TileEntityColoniesChest)theWorld.getBlockTileEntity(x, y, z);

        if (chest == null)
        {
            return true;
        }
        else if (theWorld.isBlockSolidOnSide(x, y + 1, z, DOWN))
        {
            return true;
        }
        else if (isOcelotBlockingChest(theWorld, x, y, z))
        {
            return true;
        }
        else if (theWorld.getBlockId(x - 1, y, z) == this.blockID && (theWorld.isBlockSolidOnSide(x - 1, y + 1, z, DOWN) || isOcelotBlockingChest(theWorld, x - 1, y, z)))
        {
            return true;
        }
        else if (theWorld.getBlockId(x + 1, y, z) == this.blockID && (theWorld.isBlockSolidOnSide(x + 1, y + 1, z, DOWN) || isOcelotBlockingChest(theWorld, x + 1, y, z)))
        {
            return true;
        }
        else if (theWorld.getBlockId(x, y, z - 1) == this.blockID && (theWorld.isBlockSolidOnSide(x, y + 1, z - 1, DOWN) || isOcelotBlockingChest(theWorld, x, y, z - 1)))
        {
            return true;
        }
        else if (theWorld.getBlockId(x, y, z + 1) == this.blockID && (theWorld.isBlockSolidOnSide(x, y + 1, z + 1, DOWN) || isOcelotBlockingChest(theWorld, x, y, z + 1)))
        {
            return true;
        }
        else
        {
            if (theWorld.getBlockId(x - 1, y, z) == this.blockID)
            {
                chest = new InventoryLargeChest("container.chestDouble", (TileEntityColoniesChest)theWorld.getBlockTileEntity(x - 1, y, z), (IInventory)chest);
            }

            if (theWorld.getBlockId(x + 1, y, z) == this.blockID)
            {
                chest = new InventoryLargeChest("container.chestDouble", (IInventory)chest, (TileEntityColoniesChest)theWorld.getBlockTileEntity(x + 1, y, z));
            }

            if (theWorld.getBlockId(x, y, z - 1) == this.blockID)
            {
                chest = new InventoryLargeChest("container.chestDouble", (TileEntityColoniesChest)theWorld.getBlockTileEntity(x, y, z - 1), (IInventory)chest);
            }

            if (theWorld.getBlockId(x, y, z + 1) == this.blockID)
            {
                chest = new InventoryLargeChest("container.chestDouble", (IInventory)chest, (TileEntityColoniesChest)theWorld.getBlockTileEntity(x, y, z + 1));
            }

            if (theWorld.isRemote)
            {
                return true;
            }
            else
            {
            	// Choose first line for default chest
            	// and second line for custom chest GUI (WIP)
                player.displayGUIChest((IInventory)chest);
                // player.openGui(ColoniesMain.instance, 0, theWorld, x, y, z);
                return true;
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityColoniesChest();
    }

    /**
     * Looks for a sitting ocelot within certain bounds. Such an ocelot is considered to be blocking access to the
     * chest.
     */
    public static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3)
    {
        Iterator var4 = par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)par1, (double)(par2 + 1), (double)par3, (double)(par1 + 1), (double)(par2 + 2), (double)(par3 + 1))).iterator();
        EntityOcelot var6;

        do
        {
            if (!var4.hasNext())
            {
                return false;
            }

            EntityOcelot var5 = (EntityOcelot)var4.next();
            var6 = (EntityOcelot)var5;
        }
        while (!var6.isSitting());

        return true;
    }
}
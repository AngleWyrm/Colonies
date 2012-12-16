package colonies.anglewyrm.src;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;

public class BlockTownHall extends BlockColoniesChest 
{
	public TileEntityTownHall tileEntity;
	
	public BlockTownHall(int id) {
		super(id);
		setBlockName("block.townhall");
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.TOWNHALLCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World par1World){
    	// this town hall placed in world
    	tileEntity = new TileEntityTownHall();
        return tileEntity;
     }
    
    @Override
    public void breakBlock(World theWorld, int par2, int par3, int par4, int par5, int par6)
    {
    	tileEntity.evacuateTown();
    	super.breakBlock(theWorld, par2, par3, par4, par5, par6);
    }
 }

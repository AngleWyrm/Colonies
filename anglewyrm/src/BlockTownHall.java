package colonies.anglewyrm.src;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;

public class BlockTownHall extends BlockColoniesChest 
{
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
    	return new TileEntityTownHall();
     }
    
    @Override
    public void breakBlock(World theWorld, int x, int y, int z, int par5, int par6)
    {
    	// Get block's associated tile entity,
    	// and if it's a good town hall, evacuate the citizens
    	TileEntity myTileEntity = theWorld.getBlockTileEntity(x, y, z);
    	if((myTileEntity != null) && (myTileEntity instanceof TileEntityTownHall)){
    		((TileEntityTownHall) myTileEntity).evacuateTown();
    	}
    	super.breakBlock(theWorld, x, y, z, par5, par6);
    }
 }

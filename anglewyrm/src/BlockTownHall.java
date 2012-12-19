package colonies.anglewyrm.src;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

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
 
    public void onBlockPlacedBy(World theWorld, int x, int y, int z, EntityLiving par5EntityLiving)
    {
    	super.onBlockPlacedBy(theWorld, x, y, z, par5EntityLiving);
    	TileEntity te = theWorld.getBlockTileEntity(x, y, z);
    	if(te != null){
    		if(te instanceof TileEntityTownHall){
    			((TileEntityTownHall)te).maxPopulation = 4;
    			((TileEntityTownHall) te).playerTown = (TileEntityTownHall) te;
    			Utility.Debug("Player town placed");
    		}
    		else{
    			Utility.Debug("Found entity not a town hall");
    		}
    	}
    	else{
    		Utility.Debug("Null tileEntity when placing town");
    	}
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

    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teTownHall){
    	// If we got here, then we're trying to add a town hall to a list of buildings
    	// TODO: support a list of town halls
    	// for now, just bail
    	return false;
    }

 }

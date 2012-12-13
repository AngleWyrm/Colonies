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
		setBlockName("BlockTownHall");
		townName = new String("MyTown");
		
		// DEBUG: testing the towns list
		System.out.println("[Colonies]Towns List contains "+ColoniesMain.townsList.size()+" town halls");
	}
	public static String townName;
	public static List<BlockColoniesChest> homesList;
	public static List<BlockColoniesChest> employersList;
	
	// This might end up in TileEntityTownHall
	// when we create a GUI to name a town in-game
	public void setTownName(String newName){
		townName = newName;
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.TOWNHALLCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World par1World){
    	// this town hall placed in world
    	ColoniesMain.townsList.add(this);
        return new TileEntityTownHall();
     }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	// remove this town hall from global townsList
    	Iterator iter = ColoniesMain.townsList.iterator();
    	while(iter.hasNext()){
    		if (iter.next() == this)
    		{
    			iter.remove();
    			Utility.Debug(this.townName + " removed from townslist");
    			break;
    		}
    	}
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
 }

package colonies.anglewyrm.src;

import java.util.List;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import colonies.vector67.src.BlockColoniesChest;

public class BlockTownHall extends BlockColoniesChest 
{
	public BlockTownHall(int id, List<BlockTownHall> townsList) {
		super(id);
		setBlockName("BlockTownHall");
		townsList.add(this);
		
		// DEBUG: testing the towns list
		System.out.println("[Colonies]Towns List contains "+townsList.size()+" town halls");
	}
	public static VacanciesQueue forRent;
	
	@Override
	public String getTextureFile() {
		return ClientProxy.TOWNHALLCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World par1World){
        return new TileEntityTownHall();
    }

}

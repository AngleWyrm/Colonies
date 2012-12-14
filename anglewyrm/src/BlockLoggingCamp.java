package colonies.anglewyrm.src;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;

public class BlockLoggingCamp extends BlockColoniesChest {

	public TileEntityLoggingCamp tileEntity;
	
	public BlockLoggingCamp(int id) {
		super(id);
		setBlockName("block.loggingcamp");
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.LOGGINGCAMP_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityLoggingCamp();
        return tileEntity;
     }
}

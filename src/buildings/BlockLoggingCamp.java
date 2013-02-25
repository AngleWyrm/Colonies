package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLoggingCamp extends BlockColoniesChest {

	public BlockLoggingCamp(int id) {
		super(id);
		tileEntity = new TileEntityLoggingCamp();
		setBlockName("block.loggingcamp");
		setCreativeTab(ColoniesMain.coloniesTab);
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
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teLoggingCamp){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teLoggingCamp);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

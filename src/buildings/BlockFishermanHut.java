package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFishermanHut extends BlockColoniesChest {

	public BlockFishermanHut(int id) {
		super(id);
		tileEntity = new TileEntityFishermanHut();
		setBlockName("block.fishermanHut");
		setCreativeTab(ColoniesMain.coloniesTab);
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.FISHERMANHUT_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityFishermanHut();
        return tileEntity;
     }
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teFishermanHut){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teFishermanHut);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}
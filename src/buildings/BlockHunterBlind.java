package colonies.src.buildings;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;

public class BlockHunterBlind extends BlockColoniesChest {

	public BlockHunterBlind(int id) {
		super(id);
		tileEntity = new TileEntityHunterBlind();
		setBlockName("block.hunterblind");
		setCreativeTab(ColoniesMain.coloniesTab);
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.HUNTERBLIND_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityHunterBlind();
        return tileEntity;
     }
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teHunterBlind){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teHunterBlind);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

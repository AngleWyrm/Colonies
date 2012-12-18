package colonies.anglewyrm.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class BlockHouse extends BlockColoniesChest {

	public TileEntityHouse tileEntity;
	
	public BlockHouse(int id) {
		super(id);
		setBlockName("block.house");
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.HOUSECHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityHouse();
        return tileEntity;
     }
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teHouse){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teHouse);
    		Minecraft.getMinecraft().thePlayer.addChatMessage("New housing available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

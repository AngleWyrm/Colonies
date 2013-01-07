package colonies.kzolp67.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.anglewyrm.src.ClientProxy;
import colonies.anglewyrm.src.TileEntityTownHall;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class BlockHunterBlind extends BlockColoniesChest {

	public TileEntityHunterBlind tileEntity;
	
	public BlockHunterBlind(int id) {
		super(id);
		setBlockName("block.loggingCamp");
		setCreativeTab(CreativeTabs.tabDecorations);
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
    		Minecraft.getMinecraft().thePlayer.addChatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}

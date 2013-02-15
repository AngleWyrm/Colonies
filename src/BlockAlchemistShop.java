package colonies.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockAlchemistShop extends BlockColoniesChest {

	public BlockAlchemistShop(int id) {
		super(id);
		tileEntity = new TileEntityAlchemistShop();
		setBlockName("block.alchemistShop");
		setCreativeTab(ColoniesMain.coloniesTab);
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.LOGGINGCAMP_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityAlchemistShop();
        return tileEntity;
     }
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teShop){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teShop);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }

}
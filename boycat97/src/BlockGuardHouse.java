package colonies.boycat97.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import colonies.src.buildings.BlockColoniesChest;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityTownHall;

public class BlockGuardHouse extends BlockColoniesChest {
	
	public BlockGuardHouse(int id) {
		super(id);
		tileEntity = new TileEntityGuardHouse();
		setBlockName("block.guardhouse");
		setCreativeTab(ColoniesMain.coloniesTab);
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.GUARDHOUSE_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World theWorld){
    	tileEntity = new TileEntityGuardHouse();
        return tileEntity;
     }
    
    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teGuardHouse){
    	if(TileEntityTownHall.playerTown != null){
    		TileEntityTownHall.playerTown.employersList.offer(_teGuardHouse);
    		Utility.chatMessage("New jobs available in " + TileEntityTownHall.playerTown.townName);
    		return true;
    	}
    	return false;
    }
	
}

package colonies.anglewyrm.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.vector67.src.BlockColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class BlockHouse extends BlockColoniesChest {

	public TileEntityHouse tileEntity;
	
	public BlockHouse(int id) {
		super(id);
		setBlockName("block.house");
		setCreativeTab(CreativeTabs.tabDecorations);
		GuiID = 1;
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
    		if(TileEntityTownHall.playerTown.homesList == null) return false;
    		if(TileEntityTownHall.playerTown.citizensList == null) return false;
    		
    		// DEBUG: Workaround for double-chest placement bug
    		
    		// TODO: use list
    		TileEntityTownHall.playerTown.homesList.offer(_teHouse);
    		TileEntityTownHall.playerTown.maxPopulation += 1;
       		Minecraft.getMinecraft().thePlayer.addChatMessage("New housing available in " 
       				+ TileEntityTownHall.playerTown.townName + " (pop: " 
       				+ TileEntityTownHall.playerTown.citizensList.size() + "/"
       				+ TileEntityTownHall.playerTown.maxPopulation + ")");
       	    		return true;
    	}
    	return false;
    }
    
    @Override
    public boolean removeChestFromTown(TileEntityColoniesChest _teChest){
    	if(TileEntityTownHall.playerTown != null){
    		if(TileEntityTownHall.playerTown.homesList == null) return false;
    		if(TileEntityTownHall.playerTown.citizensList == null) return false;
    		if(TileEntityTownHall.playerTown.citizensList.size() <=0) return false;
    	    		
    		// TODO: Use list
    		TileEntityTownHall.playerTown.maxPopulation -= 2;
    		if(TileEntityTownHall.playerTown.maxPopulation <=0) return false;
    		while(TileEntityTownHall.playerTown.citizensList.size() > TileEntityTownHall.playerTown.maxPopulation){
    			TileEntityTownHall.playerTown.leaveTown(TileEntityTownHall.playerTown.citizensList.getLast());
    		}
    		
    		Minecraft.getMinecraft().thePlayer.addChatMessage("Housing reduced for " 
				+ TileEntityTownHall.playerTown.townName + " (pop: " 
				+ TileEntityTownHall.playerTown.citizensList.size() + "/"
				+ TileEntityTownHall.playerTown.maxPopulation + ")");
    	}
    	
    	return false;
    }

}

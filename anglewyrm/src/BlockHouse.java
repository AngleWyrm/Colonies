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
    		// DEBUG: Workaround for double-chest placement bug
    		
    		// TODO: use list
    		TileEntityTownHall.playerTown.employersList.offer(_teHouse);
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
    		// DEBUG: Workaround for double-chest placement bug
    		
    		// TODO: Use list
    		TileEntityTownHall.playerTown.maxPopulation -= 2;
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

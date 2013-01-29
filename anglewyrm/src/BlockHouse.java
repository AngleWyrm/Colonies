package colonies.anglewyrm.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.src.BlockColoniesChest;
import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.TileEntityColoniesChest;

public class BlockHouse extends BlockColoniesChest {

	public BlockHouse(int id) {
		super(id);
		tileEntity = new TileEntityHouse();
		setBlockName("block.house");
		setCreativeTab(ColoniesMain.coloniesTab);
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
    		TileEntityTownHall.playerTown.maxPopulation += 2;
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
    	TileEntityTownHall players = TileEntityTownHall.playerTown;
    	if(players != null){
    		if(players.homesList == null) return false;
    		if(players.citizensList == null) return false;
    		if(players.citizensList.size() <=0) return false;
    	    		
    		// TODO: Use list
    		players.maxPopulation -= 2;
//    		if(TileEntityTownHall.playerTown.maxPopulation <=0) return false;
    		
    		int size1 = players.homesList.size();
    		for(int i=0;i<size1;i++){
    		  if(_teChest.xCoord==players.homesList.get(i).xCoord){
    			if(_teChest.yCoord==players.homesList.get(i).yCoord){
    			  if(_teChest.zCoord==players.homesList.get(i).zCoord){
    				players.homesList.remove(i);
    				players.homesList.remove(i);//removes a ghost!
    				break;
    			  }
    			}
    		  }
    		}
    		
    		while(players.citizensList.size() >players.maxPopulation){
    			players.leaveTown(players.citizensList.getLast());
    		}
    		
    		Minecraft.getMinecraft().thePlayer.addChatMessage("Housing reduced for " 
				+ players.townName + " (pop: " 
				+ players.citizensList.size() + "/"
				+ players.maxPopulation + ")");
    	}
    	
    	return false;
    }

}

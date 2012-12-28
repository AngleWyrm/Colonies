package colonies.vector67.src;

import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.TileEntityRenderer;
import colonies.anglewyrm.src.ColoniesMain;
import colonies.anglewyrm.src.TileEntityHouse;
import colonies.anglewyrm.src.Utility;

import com.google.common.collect.Maps;

public class ColoniesChestRenderHelper extends ChestItemRenderHelper{
		
	private Map<Integer, TileEntityColoniesChest> itemRenders = Maps.newHashMap();

	public ColoniesChestRenderHelper() {
		itemRenders.put(0, (TileEntityColoniesChest) ColoniesMain.chestBlock.createTileEntity(null, 0));
		itemRenders.put(1, (TileEntityColoniesChest) ColoniesMain.house.createTileEntity(null, 0));
	}
	
	// FIXME: This is causing problems with rendering in the GUI
	public void renderChest(Block block, int i, float f) {
		if(block==null) return; // prevent null pointer exceptions
		/*
		// Test each type of chest
		if(block.blockID==ColoniesMain.blockHouseID){ 
		  // Utility.Debug("rendering House"); // This happens quite regularly
		  if(TileEntityHouse.house != null){
			  TileEntityRenderer.instance.renderTileEntityAt(TileEntityHouse.house, 0.0D, 0.0D, 0.0D, 0.0F);
			  return;
		  }
		  return; // is house, but base class not constructed yet
		}
		*/
		
		super.renderChest(block,i,f); // should only be called as a last resort
	}
}

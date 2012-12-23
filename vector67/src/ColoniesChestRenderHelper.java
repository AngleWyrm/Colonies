package colonies.vector67.src;

import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.TileEntityRenderer;
import colonies.anglewyrm.src.ColoniesMain;

import com.google.common.collect.Maps;

public class ColoniesChestRenderHelper extends ChestItemRenderHelper{
		
	private Map<Integer, TileEntityColoniesChest> itemRenders = Maps.newHashMap();

	public ColoniesChestRenderHelper() {
		itemRenders.put(0, (TileEntityColoniesChest) ColoniesMain.chestBlock.createTileEntity(null, 0));
	}
	
	// FIXME: This is causing problems with rendering in the GUI
	public void renderChest(Block block, int i, float f) {
		if(block==null) return; // prevent null pointer exceptions
		
		// Test each type of chest
		if(block.blockID==ColoniesMain.blockHouseID){ // <-- Don't know if this works right
		  // Pretty sure this isn't getting the right thing	
		  TileEntityRenderer.instance.renderTileEntityAt(itemRenders.get(i), 0.0D, 0.0D, 0.0D, 0.0F);
		  return;
		}
		super.renderChest(block,i,f); // should only be called as a last resort
	}
}

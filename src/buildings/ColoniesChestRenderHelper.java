package colonies.src.buildings;

import java.util.Map;

import net.minecraft.block.Block;

import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import colonies.src.ColoniesMain;

import com.google.common.collect.Maps;

public class ColoniesChestRenderHelper extends ChestItemRenderHelper{
		
	private Map<Integer, TileEntityColoniesChest> itemRenders = Maps.newHashMap();

	public ColoniesChestRenderHelper() {
		itemRenders.put(0, (TileEntityColoniesChest) ColoniesMain.chestBlock.createTileEntity(null, 0));
		itemRenders.put(1, (TileEntityColoniesChest) ColoniesMain.house.createTileEntity(null, 0));
		itemRenders.put(2, (TileEntityColoniesChest) ColoniesMain.townHall.createTileEntity(null, 0));
	}
	
	// FIXME: This is causing problems with rendering in the GUI
	public void renderChest(Block block, int i, float f) {
		if(block==null) return; // prevent null pointer exceptions
		
		if(block instanceof BlockColoniesChest){
			TileEntityRenderer.instance.renderTileEntityAt(((BlockColoniesChest) block).getChestType(), 0.0D, 0.0D, 0.0D, 0.0F);
			return;
		}
		
		// otherwise render other game and mod chests
		super.renderChest(block,i,f); // should only be called as a last resort
	}
}

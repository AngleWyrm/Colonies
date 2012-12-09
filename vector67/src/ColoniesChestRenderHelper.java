package colonies.vector67.src;

import java.util.Map;

import colonies.anglewyrm.src.ColoniesMain;

import com.google.common.collect.Maps;

import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityRenderer;

public class ColoniesChestRenderHelper extends ChestItemRenderHelper{
		
	private Map<Integer, TileEntityColoniesChest> itemRenders = Maps.newHashMap();

	public ColoniesChestRenderHelper() {
		itemRenders.put(0, (TileEntityColoniesChest) ColoniesMain.chestBlock.createTileEntity(null, 0));
	}
	
	public void renderChest(Block block, int i, float f) {
		super.renderChest(block,i,f);
		if(block.blockID==ColoniesMain.chestBlock.blockID){
		  TileEntityRenderer.instance.renderTileEntityAt(itemRenders.get(i), 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}
}

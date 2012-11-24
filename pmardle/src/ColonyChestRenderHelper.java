package colonies.pmardle.src;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.TileEntityRenderer;

public class ColonyChestRenderHelper extends ChestItemRenderHelper {
	private Map<Integer, TileEntityColonyChest> itemRenders = Maps.newHashMap();

	public ColonyChestRenderHelper() {
		for (ColonyChestType typ : ColonyChestType.values())
		{
			itemRenders.put(typ.ordinal(), (TileEntityColonyChest) ColonyChest.ColonyChestBlock.createTileEntity(null, typ.ordinal()));
		}
	}
	@Override
	public void renderChest(Block block, int i, float f) {
		if (block==ColonyChest.ColonyChestBlock) {
			TileEntityRenderer.instance.renderTileEntityAt(itemRenders.get(i), 0.0D, 0.0D, 0.0D, 0.0F);
		} else {
			super.renderChest(block, i, f);
		}
	}
}
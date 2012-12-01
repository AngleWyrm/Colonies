package colonies.anglewyrm.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class TestBlock extends Block {

	public TestBlock (int id, int texture, Material material) {
		super(id, texture, material);
	}
	
	@Override
	public String getTextureFile () {
		return ServerProxy.TOWNHALLCHEST_PNG;
	}
}
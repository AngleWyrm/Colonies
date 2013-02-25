package colonies.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

// This is just a simple block from the forums
// Someone suggested making a road block so this is it.
// May delete later if we go another route
public class TestBlock extends Block {

	public TestBlock (int id, int texture, Material material) {
		super(id, texture, material);
		setCreativeTab(ColoniesMain.coloniesTab);
	}
	
	@Override
	public String getTextureFile () {
		return ServerProxy.BLOCK_PNG;
	}
}
package colonies.anglewyrm.src;

import colonies.vector67.src.TileEntityColoniesChest;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockBuilding extends BlockContainer 
{
	protected BlockBuilding(int id, Material material) {
		super(id, material);
		setBlockName("BlockBuilding");
		setHardness(1.0F);
	    setRequiresSelfNotify();
	    setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	    setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBuilding(); //NOTE: do I need to pass the world?
	}
}

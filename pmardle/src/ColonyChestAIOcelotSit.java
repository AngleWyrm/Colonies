package colonies.pmardle.src;

import net.minecraft.src.EntityAIOcelotSit;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.World;

public class ColonyChestAIOcelotSit extends EntityAIOcelotSit {
	public ColonyChestAIOcelotSit(EntityOcelot par1EntityOcelot, float par2) {
		super(par1EntityOcelot, par2);
	}

	@Override
	protected boolean isSittableBlock(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) == ColonyChest.ColonyChestBlock.blockID) {
			return true;
		}
		return super.isSittableBlock(world, x, y, z);
	}
}
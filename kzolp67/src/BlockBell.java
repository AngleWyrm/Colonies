package colonies.kzolp67.src;

import colonies.src.ColoniesMain;
import net.minecraft.block.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.block.material.Material;

public class BlockBell extends Block
{
    protected BlockBell(int par1, int par2)
    {
        super(par1, par2, Material.ground);
        this.setCreativeTab(ColoniesMain.coloniesTab);
    }
    
}
package colonies.hostile;
//
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBiped;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderBarbarian extends RenderBiped
{
    public RenderBarbarian(ModelBiped par1ModelBiped, float par2)
    {
        super(par1ModelBiped, par2);
    }

    public void renderBarbarian(EntityBarbarian par1EntityBarbarian, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityBarbarian, par2, par4, par6, par8, par9);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderBarbarian((EntityBarbarian)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderBarbarian((EntityBarbarian)par1Entity, par2, par4, par6, par8, par9);
    }
}

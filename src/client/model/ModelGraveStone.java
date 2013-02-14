package colonies.src.model;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelGraveStone extends ModelBase
{
  //fields
    ModelRenderer BasePlate;
    ModelRenderer MainStone;
    ModelRenderer Top1;
    ModelRenderer Top2;
    ModelRenderer Top3;
    ModelRenderer TippyTop;
  
  public ModelGraveStone()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      BasePlate = new ModelRenderer(this, 0, 0);
      BasePlate.addBox(-8F, 0F, -5F, 16, 1, 10);
      BasePlate.setRotationPoint(0F, 23F, 3F);
      BasePlate.setTextureSize(128, 128);
      BasePlate.mirror = true;
      setRotation(BasePlate, 0F, 0F, 0F);
      MainStone = new ModelRenderer(this, 54, 0);
      MainStone.addBox(-6F, 0F, -2F, 12, 10, 4);
      MainStone.setRotationPoint(0F, 13F, 3F);
      MainStone.setTextureSize(128, 128);
      MainStone.mirror = true;
      setRotation(MainStone, 0F, 0F, 0F);
      Top1 = new ModelRenderer(this, 88, 0);
      Top1.addBox(-5F, 0F, -2F, 10, 2, 4);
      Top1.setRotationPoint(0F, 11F, 3F);
      Top1.setTextureSize(128, 128);
      Top1.mirror = true;
      setRotation(Top1, 0F, 0F, 0F);
      Top2 = new ModelRenderer(this, 0, 20);
      Top2.addBox(-4F, 0F, -2F, 8, 1, 4);
      Top2.setRotationPoint(0F, 10F, 3F);
      Top2.setTextureSize(128, 128);
      Top2.mirror = true;
      setRotation(Top2, 0F, 0F, 0F);
      Top3 = new ModelRenderer(this, 28, 20);
      Top3.addBox(-3F, 0F, -2F, 6, 1, 4);
      Top3.setRotationPoint(0F, 9F, 3F);
      Top3.setTextureSize(128, 128);
      Top3.mirror = true;
      setRotation(Top3, 0F, 0F, 0F);
      TippyTop = new ModelRenderer(this, 52, 20);
      TippyTop.addBox(-2F, 0F, -2F, 4, 1, 4);
      TippyTop.setRotationPoint(0F, 8F, 3F);
      TippyTop.setTextureSize(128, 128);
      TippyTop.mirror = true;
      setRotation(TippyTop, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    BasePlate.render(f5);
    MainStone.render(f5);
    Top1.render(f5);
    Top2.render(f5);
    Top3.render(f5);
    TippyTop.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}

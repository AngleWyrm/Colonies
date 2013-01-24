package colonies.kzolp67.src;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelBell extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
  
  public ModelBell()
  {
    textureWidth = 64;
    textureHeight = 69;
    
      Shape1 = new ModelRenderer(this, 0, 58);
      Shape1.addBox(0F, 0F, 0F, 10, 1, 10);
      Shape1.setRotationPoint(-5F, 18F, -5F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      Shape2 = new ModelRenderer(this, 0, 43);
      Shape2.addBox(0F, 0F, 0F, 12, 2, 12);
      Shape2.setRotationPoint(-6F, 16F, -6F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      Shape3 = new ModelRenderer(this, 0, 30);
      Shape3.addBox(0F, 0F, 0F, 10, 2, 10);
      Shape3.setRotationPoint(-5F, 14F, -5F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      Shape4 = new ModelRenderer(this, 0, 19);
      Shape4.addBox(0F, 0F, 0F, 8, 2, 8);
      Shape4.setRotationPoint(-4F, 12F, -4F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      Shape5 = new ModelRenderer(this, 0, 10);
      Shape5.addBox(0F, 0F, 0F, 6, 2, 6);
      Shape5.setRotationPoint(-3F, 10F, -3F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      Shape6 = new ModelRenderer(this, 0, 4);
      Shape6.addBox(0F, 0F, 0F, 4, 1, 4);
      Shape6.setRotationPoint(-2F, 9F, -2F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      Shape7 = new ModelRenderer(this, 0, 0);
      Shape7.addBox(0F, 0F, 0F, 2, 1, 2);
      Shape7.setRotationPoint(-1F, 8F, -1F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
  }

}

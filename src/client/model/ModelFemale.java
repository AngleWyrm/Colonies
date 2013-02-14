package colonies.src.model;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelFemale extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer Bun;
    ModelRenderer Knot;
    ModelRenderer TopTail;
    ModelRenderer MiddleTail;
    //ModelRenderer LeftChest;
    //ModelRenderer RightChest;
    ModelRenderer UpperChest;
    ModelRenderer LowerChest;
  
  public ModelFemale()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -8F, -4F, 8, 8, 8);
      head.setRotationPoint(0F, 0F, 0F);
      head.setTextureSize(64, 64);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 16, 16);
      body.addBox(-4F, 0F, -2F, 8, 12, 4);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(64, 64);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 40, 16);
      rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
      rightarm.setRotationPoint(-5F, 2F, 0F);
      rightarm.setTextureSize(64, 64);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 40, 16);
      leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
      leftarm.setRotationPoint(5F, 2F, 0F);
      leftarm.setTextureSize(64, 64);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 0, 16);
      rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
      rightleg.setRotationPoint(-2F, 12F, 0F);
      rightleg.setTextureSize(64, 64);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 0, 16);
      leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
      leftleg.setRotationPoint(2F, 12F, 0F);
      leftleg.setTextureSize(64, 64);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      Bun = new ModelRenderer(this, 32, 0);
      Bun.addBox(0F, 0F, 0F, 4, 4, 1);
      Bun.setRotationPoint(-2F, -7F, 4F);
      Bun.setTextureSize(64, 64);
      Bun.mirror = true;
      setRotation(Bun, 0F, 0F, 0F);
      Knot = new ModelRenderer(this, 34, 0);
      Knot.addBox(0F, 0F, 0F, 2, 2, 2);
      Knot.setRotationPoint(-1F, -5F, 5F);
      Knot.setTextureSize(64, 64);
      Knot.mirror = true;
      setRotation(Knot, 0F, 0F, 0F);
      TopTail = new ModelRenderer(this, 34, 0);
      TopTail.addBox(0F, 0F, 0F, 2, 4, 1);
      TopTail.setRotationPoint(-1F, -3F, 6F);
      TopTail.setTextureSize(64, 64);
      TopTail.mirror = true;
      setRotation(TopTail, 0F, 0F, 0F);
      MiddleTail = new ModelRenderer(this, 35, 0);
      MiddleTail.addBox(0F, 0F, 0F, 2, 4, 1);
      MiddleTail.setRotationPoint(-1F, 0F, 7F);
      MiddleTail.setTextureSize(64, 64);
      MiddleTail.mirror = true;
      setRotation(MiddleTail, 0F, 0F, 0F);
      //LeftChest = new ModelRenderer(this, 0, 50);
      //LeftChest.addBox(0F, 0F, 0F, 3, 7, 1);
      //LeftChest.setRotationPoint(2.65F, 3.7F, -1.9F);
      //LeftChest.setTextureSize(64, 64);
      //LeftChest.mirror = true;
      //setRotation(LeftChest, -0.0872665F, 1.809191F, 0.3755949F);
      //RightChest = new ModelRenderer(this, 0, 50);
      //RightChest.addBox(0F, 0F, 0F, 3, 7, 1);
      //RightChest.setRotationPoint(-3.8F, 3.8F, -2.1F);
      //RightChest.setTextureSize(64, 64);
      //RightChest.mirror = true;
      //setRotation(RightChest, 0.1031331F, 1.302218F, 0.3756064F);
      UpperChest = new ModelRenderer(this, 0, 32);
      UpperChest.addBox(0F, 0F, 0F, 6, 4, 4);
      UpperChest.setRotationPoint(-3F, 2F, -2F);
      UpperChest.setTextureSize(64, 64);
      UpperChest.mirror = true;
      setRotation(UpperChest, -0.7853982F, 0F, 0F);
      LowerChest = new ModelRenderer(this, 0, 40);
      LowerChest.addBox(0F, 0F, -23F, 6, 7, 2);
      LowerChest.setRotationPoint(-3F, -3.65F, 16.5F);
      LowerChest.setTextureSize(64, 64);
      LowerChest.mirror = true;
      setRotation(LowerChest, 0.3839724F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    Bun.render(f5);
    Knot.render(f5);
    TopTail.render(f5);
    MiddleTail.render(f5);
    //LeftChest.render(f5);
    //RightChest.render(f5);
    UpperChest.render(f5);
    LowerChest.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity me)
  {
   super.setRotationAngles(f, f1, f2, f3, f4, f5, me);
   
   // Model animation calculations
   rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
   leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
   leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
   rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
   }

}

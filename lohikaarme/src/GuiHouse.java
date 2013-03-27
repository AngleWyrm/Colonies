package colonies.lohikaarme.src;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import colonies.anglewyrm.src.GuiColoniesChest;
import colonies.src.ColoniesMain;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.citizens.EntityCitizen;

public class GuiHouse extends GuiColoniesChest{
	
   private int view = 0;

	public GuiHouse(TileEntityColoniesChest teChest,InventoryPlayer _playerInventory) {
		super(teChest, _playerInventory);
	}
	
	
	@Override
	public void initGui(){
	  super.initGui();
	  
	  int xButton = (width-xSize) / 2;
	  int yButton = ((height-ySize)/2)-20;
	  
	  controlList.add(new GuiButton(1,xButton,yButton, 40, 20,"Info"));
	  controlList.add(new GuiButton(0, xButton+40, yButton, 60, 20, "House Inv."));
	  
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
	  if(view==0){
		super.drawScreen(par1, par2, par3);
	  }
	  else{	    	
		this.drawDefaultBackground();
		int var4 = this.guiLeft;
		int var5 = this.guiTop;
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);	
		for (int var44 = 0; var44 < this.controlList.size(); ++var44)
		{
		  GuiButton var55 = (GuiButton)this.controlList.get(var44);
		  var55.drawButton(this.mc, par1, par2);
		}
				
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var4, (float)var5, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		short var6 = 240;
		short var7 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int var8;
		int var9;
				
		this.drawGuiContainerForegroundLayer(par1, par2);
				
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	  }
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
    {
     if (view == 1){
            int picture = mc.renderEngine.getTexture(ColoniesMain.guiChestBackground);
            this.mc.renderEngine.bindTexture(picture);
            
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);        
            int x = (width - xSize) / 2;      
            int y = (height - ySize) / 2;
            this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);        
            this.drawTexturedModalRect(x, y + getInventoryRows() * 18 + 17, 0, 126, this.xSize, 96);
            
    	} 
        else{
    		 int picture = mc.renderEngine.getTexture("/gui/container.png");
    		 this.mc.renderEngine.bindTexture(picture);
    		 
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             this.mc.renderEngine.bindTexture(picture);
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.getInventoryRows() * 18 + 17);
             this.drawTexturedModalRect(x, y + this.getInventoryRows() * 18 + 17, 0, 126, this.xSize, 96);
    	}
        
    }
	
	 @Override
	    protected void drawGuiContainerForegroundLayer(int x, int y) 
	    { 	

	    	if (view == 0) {
	    	  super.drawGuiContainerForegroundLayer(x, y);
	    	}
	    	else{
	    	  LinkedList<EntityCitizen> loop = chestInventory.getOccupants();
	    	  for(int i=0;i<loop.size();i++){
	            fontRenderer.drawString(loop.get(i).name+" dff", 8, 6+(10*i), 0x404040); 
	    	  }
	    	}
	    }
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			view = guiButton.id;
		}		
	}
}

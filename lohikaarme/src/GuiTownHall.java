package colonies.lohikaarme.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.inventory.Slot;
import colonies.anglewyrm.src.GuiColoniesChest;
import colonies.src.ColoniesMain;
import colonies.src.Utility;
import colonies.src.buildings.TileEntityColoniesChest;
import colonies.src.buildings.TileEntityTownHall;

public class GuiTownHall extends GuiColoniesChest {
	
	private GuiButton infoButton = null;
	private GuiButton townHallInv = null;
	
	private static TileEntityTownHall tileEntityTownHall = null;
	
	private int currentView = 0;
	
	private int inventoryRows = 0;
	
	
	public GuiTownHall(TileEntityTownHall teChest, InventoryPlayer _playerInventory) {
		super(teChest, _playerInventory);
		
		this.tileEntityTownHall = teChest;
		
		this.inventoryRows = chestInventory.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 7;
	}
	
	
	@Override
	public void initGui(){
	  super.initGui();
	  
      int buttonHeight = 20;
      int xButton = ((width - xSize) / 2);
	  int yButton = ((height - ySize) / 2) - buttonHeight;
	  
	  int id = 0;
	  
	  infoButton = new GuiButton(id++, xButton, yButton, 40, buttonHeight, "Info");	  
	  controlList.add(infoButton);
	  
	  townHallInv = new GuiButton(id++, xButton+40, yButton, 60, buttonHeight, "Town Inv.");
	  controlList.add(townHallInv);
      
	 
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
    {
		
		if ( this.currentView == this.infoButton.id) {	    	
            // drawString parameters: string, x, y, color
			this.drawDefaultBackground();
			int var4 = this.guiLeft;
			int var5 = this.guiTop;
			this.drawGuiContainerBackgroundLayer(par3, par1, par2);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);	
			this.infoButton.drawButton(this.mc, par1, par2);
			//TODO: highlight the tab that is currently selected.
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
            
    	} else if ( this.currentView == this.townHallInv.id) { 
    		super.drawScreen(par1, par2, par3);
    	}
    }
	
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if (guiButton.enabled) {
			this.currentView = guiButton.id;
		}		
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) 
    { 	

    	if ( this.currentView == this.infoButton.id) { //info view
    	
            // drawString parameters: string, x, y, color 
    		if ( tileEntityTownHall != null &&  this.tileEntityTownHall.playerTown != null ) {
    			fontRenderer.drawString(this.tileEntityTownHall.playerTown.getInvName(), 8, 6, 0x404040);
    		}
        
    	} else if ( this.currentView == this.townHallInv.id) { // inventory view

            // Localization: draws "Inventory" or your regional equivalent
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 62, 0x404040);
    	}
    }
   
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
    {
    	
    	if ( this.currentView == this.infoButton.id) {  //info view
        	//stores the background image in a variable to be rendered
            int picture = mc.renderEngine.getTexture(ColoniesMain.guiChestBackground);
            this.mc.renderEngine.bindTexture(picture);
            
            //draws the image of the container
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);        
            int x = (width - xSize) / 2;      
            int y = (height - ySize) / 2;
            this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);        
            this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
            
    	} else if ( this.currentView == this.townHallInv.id) { //inventory view
    		 int picture = mc.renderEngine.getTexture("/gui/container.png");
    		 this.mc.renderEngine.bindTexture(picture);
    		 
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             this.mc.renderEngine.bindTexture(picture);
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             //this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
             this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
             this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    	}
        
    }

	public void setTileEntityTownHall (TileEntityTownHall _tileEntityTownHall)
	{
		this.tileEntityTownHall = _tileEntityTownHall;
	}

}

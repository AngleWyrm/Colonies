package colonies.anglewyrm.src;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import colonies.src.buildings.ContainerColoniesChest;
import colonies.src.buildings.TileEntityColoniesChest;

public class GuiColoniesChest extends GuiContainer 
{
	protected TileEntityColoniesChest chestInventory;
	private IInventory playerInventory;
	private int inventoryRows = 0;

    public GuiColoniesChest (TileEntityColoniesChest teChest,InventoryPlayer _playerInventory) {
            //the container is instantiated and passed to the superclass for handling
            super(new ContainerColoniesChest(_playerInventory, teChest));
            chestInventory = teChest;
            this.inventoryRows = chestInventory.getSizeInventory() / 9;
            this.ySize = 114 + this.inventoryRows * 7;
    }
    /*
    public GuiColoniesChest (IInventory _chestInventory, IInventory _playerInventory)
    {
        super(new ContainerChest(_playerInventory,_chestInventory));
    	System.out.println("2");
        this.chestInventory = _chestInventory;
        this.playerInventory = _playerInventory;
        this.allowUserInput = true;
        
        // chests are nine slots wide
        this.inventoryRows = chestInventory.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 7;
    }
    */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) 
    {
            // drawString parameters: string, x, y, color
            fontRenderer.drawString(StatCollector.translateToLocal(chestInventory.getInvName()), 8, 6, 0x404040);

            // Localization: draws "Inventory" or your regional equivalent
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"),8,ySize - 62, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
    {
        /*int var4 = this.mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96); 
    	
    	    int texture = mc.renderEngine.getTexture(ColoniesMain.guiChestBackground);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(texture);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            //this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
            this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);*/
           
        int picture = mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(picture);
        int x = (width - xSize) / 2;      
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
        
    }
}
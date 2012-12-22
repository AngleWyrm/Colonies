package colonies.anglewyrm.src;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import colonies.vector67.src.TileEntityColoniesChest;

public class GuiColoniesChest extends GuiContainer 
{
	private IInventory chestInventory;
	private IInventory playerInventory;
	private int inventoryRows = 0;

    public GuiColoniesChest (InventoryPlayer _playerInventory, TileEntityColoniesChest teChest) {
            //the container is instanciated and passed to the superclass for handling
            super(new ContainerColoniesChest(_playerInventory, teChest));
    }
    public GuiColoniesChest (IInventory _chestInventory, IInventory _playerInventory)
    {
        super(new ContainerChest(_chestInventory, _playerInventory));
        this.chestInventory = _chestInventory;
        this.playerInventory = _playerInventory;
        this.allowUserInput = false; 
        
        // chests are nine slots wide
        this.inventoryRows = chestInventory.getSizeInventory() / 9;
        this.ySize = 222 + this.inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) 
    {
            // drawString parameters: string, x, y, color
            fontRenderer.drawString("Tiny", 8, 6, 0x404040);

            // Localization: draws "Inventory" or your regional equivalent
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
    {
            int texture = mc.renderEngine.getTexture(ColoniesMain.guiChestBackground);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(texture);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            //this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
            this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);

    }
}
package colonies.pmardle.src;

import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;

import org.lwjgl.opengl.GL11;

import colonies.pmardle.src.ContainerColonyChestBase;
import colonies.pmardle.src.ColonyChestType;
import colonies.pmardle.src.TileEntityColonyChest;

public class GUIChest extends GuiContainer {
	public enum GUI {
		TOWNHALL(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.TOWNHALL),
		MINER(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.MINER),
		LUMBERJACK(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.LUMBERJACK),
		BUILDER(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.BUILDER),
		BLACKSMITH(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.BLACKSMITH),
		HOUSE(184,202,"/pmardle/gfx/Chestcontainer.png", ColonyChestType.HOUSE),
		BAKER(184,202,"/pmardle/gfx/Bakercontainer.png", ColonyChestType.BAKER),
		FARMER(184,202,"/pmardle/gfx/Farmercontainer.png", ColonyChestType.FARMER);

		private int xSize;
		private int ySize;
		private String guiTexture;
		private ColonyChestType mainType;

		private GUI(int xSize, int ySize, String guiTexture, ColonyChestType mainType) {
			this.xSize=xSize;
			this.ySize=ySize;
			this.guiTexture=guiTexture;
			this.mainType=mainType;
		}

		protected Container makeContainer(IInventory player, IInventory chest) {
			return new ContainerColonyChestBase(player,chest, mainType, xSize, ySize);
		}

		public static GUIChest buildGUI(ColonyChestType type, IInventory playerInventory, TileEntityColonyChest chestInventory) {
			return new GUIChest(values()[chestInventory.getType().ordinal()],playerInventory,chestInventory);
		}
	}

	public int getRowLength() {
		return type.mainType.getRowLength();
	}
	private GUI type;

	private GUIChest(GUI type, IInventory player, IInventory chest) {
		super(type.makeContainer(player,chest));
		this.type=type;
		this.xSize=type.xSize;
		this.ySize=type.ySize;
		this.allowUserInput=false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        int tex = mc.renderEngine.getTexture(type.guiTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(tex);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
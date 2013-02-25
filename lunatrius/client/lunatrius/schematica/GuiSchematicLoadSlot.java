package colonies.lunatrius.client.lunatrius.schematica;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiSchematicLoadSlot extends GuiSlot {
	private final Settings settings = Settings.instance();
	private final FontRenderer fontRenderer = this.settings.minecraft.fontRenderer;
	private final RenderEngine renderEngine = this.settings.minecraft.renderEngine;

	private final GuiSchematicLoad guiSchematicLoad;

	protected int selectedIndex = -1;

	public GuiSchematicLoadSlot(GuiSchematicLoad guiSchematicLoad) {
		super(Settings.instance().minecraft, guiSchematicLoad.width, guiSchematicLoad.height, 16, guiSchematicLoad.height - 40, 24);
		this.guiSchematicLoad = guiSchematicLoad;
	}

	@Override
	protected int getSize() {
		return this.guiSchematicLoad.schematicFiles.size();
	}

	@Override
	protected void elementClicked(int index, boolean par2) {
		GuiSchematicEntry schematic = this.guiSchematicLoad.schematicFiles.get(index);
		if (schematic.isDirectory()) {
			this.guiSchematicLoad.changeDirectory(schematic.getName());
			this.selectedIndex = -1;
		} else {
			this.selectedIndex = index;
		}
	}

	@Override
	protected boolean isSelected(int index) {
		return index == this.selectedIndex;
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	protected void drawContainerBackground(Tessellator tessellator) {
	}

	@Override
	protected void drawSlot(int index, int x, int y, int par4, Tessellator tessellator) {
		if (index < 0 || index >= this.guiSchematicLoad.schematicFiles.size()) {
			return;
		}

		GuiSchematicEntry schematic = this.guiSchematicLoad.schematicFiles.get(index);
		String schematicName = schematic.getName();

		if (schematic.isDirectory()) {
			schematicName += "/";
		} else {
			schematicName = schematicName.replaceAll("(?i)\\.schematic$", "");
		}

		drawItemStack(x, y, schematic.getItemStack());

		this.guiSchematicLoad.drawString(this.settings.minecraft.fontRenderer, schematicName, x + 24, y + 6, 0x00FFFFFF);
	}

	private void drawItemStack(int x, int y, ItemStack itemStack) {
		drawItemStackSlot(x, y);

		if (itemStack != null) {
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.enableGUIStandardItemLighting();
			Settings.renderItem.renderItemIntoGUI(this.fontRenderer, this.renderEngine, itemStack, x + 2, y + 2);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	private void drawItemStackSlot(int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ForgeHooksClient.bindTexture("/gui/slot.png", 0);
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.addVertexWithUV(x + 1 + 0, y + 1 + 18, 0, 0 * 0.0078125F, 18 * 0.0078125F);
		var10.addVertexWithUV(x + 1 + 18, y + 1 + 18, 0, 18 * 0.0078125F, 18 * 0.0078125F);
		var10.addVertexWithUV(x + 1 + 18, y + 1 + 0, 0, 18 * 0.0078125F, 0 * 0.0078125F);
		var10.addVertexWithUV(x + 1 + 0, y + 1 + 0, 0, 0 * 0.0078125F, 0 * 0.0078125F);
		var10.draw();
	}
}

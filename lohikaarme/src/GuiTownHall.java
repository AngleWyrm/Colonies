package colonies.lohikaarme.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import colonies.anglewyrm.src.GuiColoniesChest;
import colonies.src.buildings.TileEntityColoniesChest;

public class GuiTownHall extends GuiColoniesChest {

	public GuiTownHall(TileEntityColoniesChest teChest,InventoryPlayer _playerInventory) {
		super(teChest, _playerInventory);
	}
	
	
	@Override
	public void initGui(){
	  super.initGui();
	 controlList.add(new GuiButtonColonies(0, this.width / 2 + 30, this.height / 4 - 5, 50, 12, "Info"));
	}

}

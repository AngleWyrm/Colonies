package colonies.lohikaarme.src;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import colonies.anglewyrm.src.GuiColoniesChest;
import colonies.vector67.src.TileEntityColoniesChest;

public class GuiTownHall extends GuiColoniesChest {

	public GuiTownHall(TileEntityColoniesChest teChest,InventoryPlayer _playerInventory) {
		super(teChest, _playerInventory);
	}
	
	
	@Override
	public void initGui(){
	  super.initGui();
	}

}

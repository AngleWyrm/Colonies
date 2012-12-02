package colonies.anglewyrm.src;

import colonies.vector67.src.BlockColoniesChest;

public class TownHall extends BlockColoniesChest 
{
	public TownHall(int id) {
		super(id);
		setBlockName("TownHall");
	}
	public static VacanciesQueue forRent;
	
	@Override
	  public String getTextureFile() {
	    return ClientProxy.TOWNHALLCHEST_PNG;
	  }
}

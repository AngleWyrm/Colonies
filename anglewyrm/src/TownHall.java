package colonies.anglewyrm.src;

import java.util.List;

import colonies.vector67.src.BlockColoniesChest;

public class TownHall extends BlockColoniesChest 
{
	public TownHall(int id, List<TownHall> townsList) {
		super(id);
		setBlockName("TownHall");
		townsList.add(this);
		
		// DEBUG: testing the towns list
		System.out.println("[Colonies]Towns List contains "+townsList.size()+" town halls");
	}
	public static VacanciesQueue forRent;
	
	@Override
	  public String getTextureFile() {
	    return ClientProxy.TOWNHALLCHEST_PNG;
	  }
}

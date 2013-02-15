package colonies.src.buildings;

import colonies.src.ClientProxy;
import colonies.src.citizens.EntityAlchemist;
import colonies.src.citizens.EntityCitizen;
import net.minecraft.src.IInventory;
import net.minecraft.src.World;

public class TileEntityAlchemistShop extends TileEntityColoniesChest {

	public static TileEntityAlchemistShop alchemistShop = new TileEntityAlchemistShop();
	
	public TileEntityAlchemistShop() {
		super();
	}

	@Override
    public String getInvName(){
        return "container.alchemistShop";
    }
	
	@Override
	public String getTextureFile(){
		return ClientProxy.ALCHEMISTCHEST_PNG;
	}

    @Override
    public EntityCitizen createNewWorker(World theWorld){
    	return new EntityAlchemist(theWorld);
    }

}

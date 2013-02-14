package colonies.anglewyrm.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import colonies.src.BlockColoniesChest;
import colonies.src.ClientProxy;
import colonies.src.ColoniesMain;
import colonies.src.TileEntityColoniesChest;

public class BlockTownHall extends BlockColoniesChest 
{
	public BlockTownHall(int id) {
		super(id);
		tileEntity = new TileEntityTownHall();
		setBlockName("block.townhall");
		setCreativeTab(ColoniesMain.coloniesTab);
		setTickRandomly(true); // for city limits effects
		GuiID = 0;
	}
	
	@Override
	public TileEntityColoniesChest getChestType(){
		return tileEntity;
	}
	
	@Override
	public String getTextureFile() {
		return ClientProxy.TOWNHALLCHEST_PNG;
	}
	
    @Override
    public TileEntity createNewTileEntity(World par1World){
    	return new TileEntityTownHall();
     }

    public void onBlockPlacedBy(World theWorld, int x, int y, int z, EntityLiving par5EntityLiving)
    {   	
    	if(theWorld.isRemote) return;
    	super.onBlockPlacedBy(theWorld, x, y, z, par5EntityLiving);
    	
    	// TODO: this tileEntity variable appears to be redundant
    	//       there is likely already a tile entity associated with block
    	tileEntity = (TileEntityTownHall)theWorld.getBlockTileEntity(x, y, z);
    	if(tileEntity != null){
			if(par5EntityLiving instanceof EntityPlayer){
  			  ((EntityPlayer) par5EntityLiving).openGui(ColoniesMain.instance, 10, theWorld, x, y, z);
  			}
    		((TileEntityTownHall) tileEntity).maxPopulation = 4;
    		TileEntityTownHall.playerTown = ((TileEntityTownHall)tileEntity);
    		Utility.chatMessage("player town placed");
    	}
    	else{
    			Utility.Debug("Found entity not a town hall");
    		}
    }
    
    @Override
    public void breakBlock(World theWorld, int x, int y, int z, int par5, int par6)
    {
    	// Get block's associated tile entity,
    	// and if it's a good town hall, evacuate the citizens
    	TileEntity myTileEntity = theWorld.getBlockTileEntity(x, y, z);
    	if((myTileEntity != null) && (myTileEntity instanceof TileEntityTownHall)){
    		((TileEntityTownHall) myTileEntity).evacuateTown();
    	}
    	super.breakBlock(theWorld, x, y, z, par5, par6);
    }

    @Override
    public boolean addBlockToTown(TileEntityColoniesChest _teTownHall){
    	// If we got here, then we're trying to add a town hall to a list of buildings
    	// TODO: support a list of town halls
    	// for now, just bail
    	return false;
    }
    
    public int tickRate(){
    	return 1;
    }
    
    public void updateTick(World world, int x, int y, int z, Random rng){
    	super.updateTick(world, x,y,z, rng);
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rng)
    {
    	super.randomDisplayTick(world, x, y, z, rng);

    	// player town border markers
    	// CLIENT SIDE ONLY
    	if(world.isRemote) return;
    	
    	Point p = new Point();
    	for(int angle = 0; angle < 32; ++angle){
    		p.set(0, 0, 0);
    		p.polarTranslation((float)angle/32.0 * 2*Math.PI, Math.PI/2, 14d);
    		p.plus(x, y, z);
    		this.terrainAdjustment(world, p);
    		world.spawnParticle("reddust", p.x, p.y+0.5, p.z, -0.5,0.5,0.8);
    	}
    }
	public Point terrainAdjustment(World world, Point p){
		// If this ain't air, go up until it is
		while(!world.isAirBlock((int)p.x, (int)p.y, (int)p.z)){
			++p.y;
			if(p.y >= 126) return p; // failsafe
			if(!world.isAirBlock((int)p.x, (int)p.y+1, (int)p.z)) ++p.y;
		}
		// else is air, if air beneath, go down until it ain't
		while(world.isAirBlock((int)p.x, (int)p.y-1, (int)p.z)){
			--p.y;
			if(p.y <= 5) return p; // failsafe
		}
		return p;
	}

 }

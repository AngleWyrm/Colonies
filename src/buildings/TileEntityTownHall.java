package colonies.src.buildings;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import colonies.lohikaarme.src.GuiTownHall;
import colonies.src.ClientProxy;
import colonies.src.Point;
import colonies.src.Utility;
import colonies.src.citizens.EntityAlchemist;
import colonies.src.citizens.EntityCitizen;
import colonies.src.citizens.EntityFisherman;
import colonies.src.citizens.EntityLumberjack;
import colonies.src.citizens.EntityMiner;
import colonies.src.citizens.EntityPriestess;
import colonies.src.citizens.EntityWife;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IInventory;
import net.minecraft.src.NBTTagCompound;

public class TileEntityTownHall extends TileEntityColoniesChest 
{
	// Town variables
	public int maxPopulation = 0;    // citizen count
	public double townPerimeter = 14; // meters
	private int spawnDelay = 600;    // count of calls to update function
	public String townName;
	
	public static TileEntityTownHall playerTown; // to be replace by a list later on

	public LinkedList<EntityCitizen>       		citizensList;
	public LinkedList<TileEntityColoniesChest>  homesList;
	public LinkedList<TileEntityColoniesChest>  employersList;
	
	
	private boolean notreturnedlist = false;
	private Integer[][] homelistvalues;
	
	
	public TileEntityTownHall() {
		super();
		setTownName("MyTown");
		
		citizensList = new LinkedList<EntityCitizen>();
		employersList = new LinkedList<TileEntityColoniesChest>();
		homesList = new LinkedList<TileEntityColoniesChest>();
	}
	
	public boolean adoptTown(EntityCitizen newCitizen){
		if((citizensList==null)||(newCitizen==null)){
			// Utility.Debug("null list and/or citizen");
			return false;
		}
		if(citizensList.size() >= maxPopulation){
			Utility.chatMessage("Sorry, town full: " + getInvName());
			return false;
		}
		if(citizensList.contains(newCitizen)){
			Utility.chatMessage("[ERROR] Already a resident!?");
			return false;
		}
		
		if(citizensList.offer(newCitizen)){
	 		Utility.chatMessage("A Citizen joined "	+ playerTown.getInvName());
			newCitizen.homeTown = this;
			return true;
 		}
		Utility.Debug("[ERROR] citizenList refused offer");
		return false;
	}

	// One citizen leaves town membership
	public boolean leaveTown(EntityCitizen oldCitizen){
		if((citizensList==null)||(oldCitizen==null)) return false;
		if(!citizensList.contains(oldCitizen)) return false;
		citizensList.remove(citizensList.indexOf(oldCitizen));
		oldCitizen.homeTown = null;
  		Utility.chatMessage("A Citizen left " + getInvName());
  		
  		// TODO: free up houseing
  		// TODO: free up job
 		return true;
	}
	
	// Empties town of all residents
	public boolean evacuateTown(){
		if(citizensList==null) return false;
		
		Utility.Debug("Evacuating " + townName);
		maxPopulation = 0;
		
		// remove citizens from town
		while(!citizensList.isEmpty()){
			EntityCitizen tmp = citizensList.getFirst();
			tmp.hasHomeTown = false;
			citizensList.removeFirst();
			Utility.Debug("Citizen left town");
		}
		
		if(playerTown==this){
			playerTown = null;
			Utility.Debug("playerTown removed");
		}
		else{
			Utility.Debug("another town removed");
		}
		return true;
	}
	
	public void setTownName(String newName){
		townName = newName;
	}
	
	@Override
    public String getInvName(){
		String townLabel = new String();
		if(townName != null) townLabel += townName;
		townLabel += " (Pop: ";
		if(citizensList != null){
			townLabel += citizensList.size();
			townLabel += "/";
			townLabel += maxPopulation;
			townLabel += ")";
		}
		else{
			townLabel += "0)";
		}
		return townLabel;
    }
	
	@Override
	public void updateEntity(){
        super.updateEntity();
        
        // DEBUG: workaround for double-chest placement bug
        if(this != playerTown) return;
             	
        // Spawner system
        if(citizensList == null) return;
        if(citizensList.size() >= maxPopulation) return;
        
        if(--spawnDelay <= 0){
        	spawnDelay = 500;
        	// Utility.Debug(townName + " spawner triggered");
 
        	// Choose citizen type to spawn (default wanderer or wife)
           	EntityCitizen newGuy;
           	if(isLessFemales()){
           		newGuy = new EntityWife(worldObj);
           	}
           	else{
           		newGuy = new EntityCitizen(worldObj);
           	}
        	
        	// pick a random direction at the town perimeter
        	Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
        	Point q = new Point();
        	Utility.Debug(p.toString());
        	q.polarTranslation(Utility.rng.nextRadian(), (float)(Math.PI/2.2), 14d);
        	p.plus(q);
        	this.terrainAdjustment(p);

        	// spawn mob
            newGuy.setLocationAndAngles(Math.floor(p.x), Math.floor(p.y), Math.floor(p.z), Utility.rng.nextFloat()*360.0f, 0.0f);
            worldObj.spawnEntityInWorld(newGuy);
        }
        
        /*
        // Load/Save
        if(notreturnedlist){
          for(int i=0;i<homelistvalues.length;i++){
      		TileEntityColoniesChest var1 = (TileEntityColoniesChest)worldObj.getBlockTileEntity(homelistvalues[i][0],homelistvalues[i][1],homelistvalues[i][2]);
      		playerTown.homesList.add((TileEntityColoniesChest)var1);;
          }
        }
        */
 
	}
	private boolean isLessFemales(){
		if(citizensList == null || citizensList.isEmpty()) return false;
		
		int males = 0, females = 0;
		for(EntityCitizen me: citizensList){
			if(me.isMale){
				++males;
			}else{
				++females;
			}
		}
		return (females < males);
	}
	
	public Point terrainAdjustment(Point p){
		// If this ain't air, go up until it is
		while(!this.worldObj.isAirBlock((int)p.x, (int)p.y, (int)p.z)){
			++p.y;
			if(p.y >= 126) return p; // failsafe
			if(!this.worldObj.isAirBlock((int)p.x, (int)p.y+1, (int)p.z)) ++p.y;
		}
		// else is air, if air beneath, go down until it ain't
		while(this.worldObj.isAirBlock((int)p.x, (int)p.y-1, (int)p.z)){
			--p.y;
			if(p.y <= 5) return p; // failsafe
		}
		return p;
	}

	public boolean getNextJob(EntityCitizen _citizen) {
		if(employersList == null || employersList.isEmpty()) return false;
		
		EntityCitizen newJob = null;
		for(TileEntityColoniesChest jobSite : employersList){
			if(jobSite.applyForJob(_citizen)){
				// citizen hired and converted
				return true;
			} // else didn't get hired
		}
		return false;
	}
	
	/*
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound){
	  super.writeToNBT(par1NBTTagCompound);
	  
	  par1NBTTagCompound.setString("Townsname",townName);
	  
	  int i0=0;
	  TileEntityColoniesChest i1;
	  Iterator<TileEntityColoniesChest> ite  = homesList.iterator();
	  while(ite.hasNext()){
		++i0;
		i1 = ite.next();
		par1NBTTagCompound.setInteger("Homeslist" + i0 + "x", i1.xCoord);
		par1NBTTagCompound.setInteger("Homeslist" + i0 + "y", i1.yCoord);
		par1NBTTagCompound.setInteger("Homeslist" + i0 + "z", i1.zCoord);
		// System.out.println(i0);
	  }
	  par1NBTTagCompound.setInteger("HomeslistSize", i0);
	  
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound){
	  super.readFromNBT(par1NBTTagCompound);

	  townName = par1NBTTagCompound.getString("Townsname");
	  
	  int size = par1NBTTagCompound.getInteger("HomeslistSize");
	  homelistvalues= new Integer[size][3];
	  for(int i=1;i<=size;i++){
		int x = par1NBTTagCompound.getInteger("Homeslist"+i+"x");
		int y = par1NBTTagCompound.getInteger("Homeslist"+i+"y");
		int z = par1NBTTagCompound.getInteger("Homeslist"+i+"z");
		
		homelistvalues[i-1][0]=x;
		homelistvalues[i-1][1]=y;
		homelistvalues[i-1][2]=z;
		notreturnedlist = true;
		
	  }
	  maxPopulation += 4;
	  playerTown = this;
	}
	*/
	
	@Override
	public String getTextureFile(){
		return ClientProxy.TOWNHALLCHEST_PNG;
	}

}

package colonies.src.util;

import colonies.src.TileEntityColoniesChest;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Vec3;

public class Point 
{
	public double x = 0;
	public double y = 0;
	public double z = 0;
	
	public Point(){
		x = y = z = 0;
	}
	public Point(double _x, double _y, double _z) {
		set(_x, _y, _z);
	}
	public Point(TileEntityColoniesChest _chest){
		set(_chest.xCoord, _chest.yCoord, _chest.zCoord);
	}

	public void set(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	
	public double getDistance(Point other){
		return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y) + (z-other.z)*(z-other.z));  
	}
	public double getDistance(EntityLiving _entityLiving){
		return Math.sqrt((x-_entityLiving.posX)*(x-_entityLiving.posX) + (y-_entityLiving.posY)*(y-_entityLiving.posY) + (z-_entityLiving.posZ)*(z-_entityLiving.posZ));
	}
	public double getDistance(TileEntityColoniesChest _chest){
		return Math.sqrt((x-_chest.xCoord)*(x-_chest.xCoord) + (y-_chest.yCoord)*(y-_chest.yCoord) + (z-_chest.zCoord)*(z-_chest.zCoord));
	}
	public double getDistance(double _x, double _y, double _z){
		return Math.sqrt((x-_x)*(x-_x) + (y-_y)*(y-_y) + (z-_z)*(z-_z));
	}
	
	public Point plus(Point other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	public Point plus(double _x, double _y, double _z){
		x += _x;
		y += _y;
		z += _z;
		return this;
	}
	
	public Point polarTranslation(double equatorialAngle, double polarAngle, double distance){
		x = distance * Math.sin(polarAngle) * Math.cos(equatorialAngle);
		y = distance * Math.cos(polarAngle);
		z = distance * Math.sin(equatorialAngle) * Math.sin(polarAngle);
		return this; 
	}
	
	public String toString(){
		return "x="+x + " y="+y + " z="+z;
	}
	
	public String toRoundedString(){
		return "x:"+(int)x + " y:"+(int)y + " z:"+(int)z;
	}
}

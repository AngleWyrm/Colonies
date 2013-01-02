package colonies.anglewyrm.src;

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

	public void set(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	
	public double getDistance(Point other){
		return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y) + (z-other.z)*(z-other.z));  
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
	
	public Point polarTranslation(double d, double e, double distance){
		x = distance * Math.sin(e) * Math.cos(d);
		y = distance * Math.cos(e);
		z = distance * Math.sin(d) * Math.sin(e);
		return this; 
	}
	
	public String toString(){
		return "x="+x + " y="+y + " z="+z;
	}
}

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
		x = _x;
		y = _y;
		z = _z;
	}
	
	public double getDistance(Point other){
		return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y) + (z-other.z)*(z-other.z));  
	}
	
	public Point plus(Point other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Point polarTranslation(float equatorial, float polar, double distance){
		x = distance * Math.sin(polar) * Math.cos(equatorial);
		y = distance * Math.cos(polar);
		z = distance * Math.sin(equatorial) * Math.sin(polar);
		return this; 
	}
	
	public String toString(){
		return "x="+x + " y="+y + " z="+z;
	}
}

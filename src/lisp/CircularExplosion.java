package lisp;
import java.awt.Color;

import lisp.drawers.CircularExplosionGeoDrawer;


public class CircularExplosion extends GameObjectInRoom{
	
	public double x;
	public double y;
	public double radius;
	public int life = 0;
	GameRoom room;
	public Color color;
	
	CircularExplosion(double x, double y, GameRoom room, double radius, Color color){
		super(room);
		this.x = x;
		this.y = y;
		this.room = room;
		this.color = color;
		this.radius = radius;
		
		drawer = new CircularExplosionGeoDrawer();
	}
	
	CircularExplosion(double x, double y, GameRoom room, Color color){
		this(x, y,  room, 30, color);
	}

	@Override
	public void step() {
		life+=5;
		if( life>2*radius ){
			this.destroy();
			
		}
	}

}

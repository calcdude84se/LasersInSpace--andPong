package lisp;
import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;

import drawers.ExplosionGeoDrawer;
import drawers.ExplosionImageDrawer;


public class Explosion extends GameObjectInRoom implements WithPosition, WithId{
	
	public double x;
	public double y;
	public double radius;
	public int life = 0;
	GameRoom room;
	public Color color;
	
	Explosion(double x, double y, GameRoom room, double radius, Color color, boolean destroyed){
		super(room);
		this.x = x;
		this.y = y;
		this.room = room;
		this.color = color;
		this.radius = radius;
		if(destroyed) drawer = new ExplosionImageDrawer();
		else drawer = new ExplosionGeoDrawer();
	}

	@Override
	public void step() {
		life+=5;
		x--;
		if( life>2*radius ){
			this.destroy();
			
		}
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public Double getPosition() {
		return new Double(x, y, 2*radius, 2*radius);
	}

}

package lisp;
import java.awt.Color;
import java.awt.Graphics2D;


public class Explosion extends GameObjectInRoom{
	
	double x;
	double y;
	double radius;
	int life = 0;
	GameRoom room;
	Color color;
	
	Explosion(double x, double y, GameRoom room, double radius, Color color){
		super(room);
		this.x = x;
		this.y = y;
		this.room = room;
		this.color = color;
		this.radius = radius;
	}
	
	Explosion(double x, double y, GameRoom room, Color color){
		this(x, y,  room, 30, color);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for(double angle = 0; angle < 2*Math.PI; angle+=Math.PI/4){
			g.drawLine((int) (x+life*Math.sin(angle)), (int) (y+life*Math.cos(angle)), (int) (x+(radius+life)*Math.sin(angle)),(int) (y+(radius+life)*Math.cos(angle)));
		}

	}

	@Override
	public void step() {
		life+=5;
		if( life>2*radius ){
			this.destroy();
			
		}
	}

}

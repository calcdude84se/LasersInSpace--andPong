package lisp;
import java.awt.Color;
import java.awt.Graphics2D;


public class Explosion extends GameObjectInRoom{
	
	double x;
	double y;
	int life = 0;
	GameRoom room;
	Color color;
	
	Explosion(double x, double y, GameRoom room, Color color){
		super(room);
		this.x = x;
		this.y = y;
		this.room = room;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for(double angle = 0; angle < 2*Math.PI; angle+=Math.PI/4){
			g.drawLine((int) (x+life*Math.sin(angle)), (int) (y+life*Math.cos(angle)), (int) (x+(30+life)*Math.sin(angle)),(int) (y+(30+life)*Math.cos(angle)));
		}

	}

	@Override
	public void step() {
		life+=5;
		if( life>80 ){
			room.removeObject(this);
			
		}
	}

}

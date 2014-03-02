package lisp;
import java.awt.Color;
import java.awt.Graphics2D;


public class Explosion implements GameObject {
	
	double x;
	double y;
	int life = 0;
	GameRoom room;
	
	Explosion(double x, double y, GameRoom room){
		this.x = x;
		this.y = y;
		this.room = room;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
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

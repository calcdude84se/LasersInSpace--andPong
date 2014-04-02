package lisp;

import java.awt.geom.Rectangle2D.Double;
import java.util.Random;

public class Astronaut extends GameObjectABC<Astronaut> implements WithPosition, WithId{

	private double x;
	private double y;
	boolean dead;
	private GameRoom room;
	private Random gen = new Random();
	private int width = 32;
	private int height = 32;
	private AstronautField field;

	public Astronaut(GameRoom room, AstronautField afield) {
		dead = false;
		this.x = room.getPanel().getWidth();
		this.y = gen.nextInt(room.getPanel().getHeight());
		this.room = room;
		this.drawer = room.getAstronautDrawer();
		room.getAstronautDrawer().setPaths(0,"images/astronaut.png");
		field = afield;
	}
	
	public double getX(){ return x;}
	
	public double getY(){ return y;}
	
	public int getWidth(){ return width;}
	
	public int getHeight(){ return height;}

	@Override
	public void step() {
		x -= 1;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public Double getPosition() {
		return new Double(x,y,width, height);
	}
	
	public boolean testLazerFree(double xshot, double yshot){
		if(xshot < x || xshot > x+width || yshot < y || yshot> y+height){
			return true;
		}
		killAstronaut();
		return false;
	}
	
	public boolean isFree(double x1, double y1){
		return !((x1>x||x1<(x+width))&&(y1>y||y1<(y+height)));
	}

	public void killAstronaut(){
		dead = true;
		room.getAstronautDrawer().setPaths(0,"images/astronautDead.png");
	}
	
	@Override
	public void destroy() {
		field.removeObject();
	}
	
}

package lisp;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Double;

public class AsteroidImp extends GameObjectABC<AsteroidImp> implements Asteroid, WithId, WithPosition {
	
	public double x;
	public double y;
	public double r;
	private GameRoom room;
	private double xcenter;
	private double ycenter;
	private double xvelocity;
	private double yvelocity;
	private int id;
	private int health;

	
	public AsteroidImp(GameRoom room, double x, double y, double r, double vx, double vy, int img){
		this.room = room;
		this.x = x;
		this.y = y;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
		this.xvelocity = vx;
		this.yvelocity = vy;
		this.r = r;
		health = (int) r;
		id = img;
		this.drawer = room.getAsteroidDrawer();
	}

	public void step() {
		this.x += xvelocity;
		this.y += yvelocity;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
	}
	@Override
	public double getMass(){
		return r*r*r;
	}
	
	public double getR(){
		return r;
	}

	@Override
	public double getXCenter() {
		return xcenter;
	}

	@Override
	public double getYCenter() {
		return ycenter;
	}
	
	public boolean isFree(double x1, double y1){
		return !((Math.pow((x1 - xcenter), 2) + Math.pow((y1 - ycenter), 2)) <= Math.pow(r, 2));
	}
	public void destroy(){
		//nothing to do here.
	}

	@Override
	public Double getPosition() {
		return new Double(x, y, 2*r, 2*r);
	}

	@Override
	public int getId() {
		return id;
	}
	
	public void removeLife(){
		health--;
		if(health<0){
			room.addObject(new Explosion(getXCenter(),getYCenter(),room, Color.RED));
			room.removeObject(this);
	}
	}
		
	public boolean testLazerFree(double xshot, double yshot, boolean isMissile){
		if(xshot < x || xshot > x+2*xcenter || yshot < y || yshot> y + 2*ycenter){
			return true;
		}
		removeLife();
		return false;
			
	}
}

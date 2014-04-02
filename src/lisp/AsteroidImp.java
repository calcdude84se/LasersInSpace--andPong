package lisp;

import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;

public class AsteroidImp extends GameObjectABC<AsteroidImp> implements Asteroid, WithId, WithPosition {
	
	public double x;
	public double y;
	public double r;
	private GameRoom room;
	private AsteroidField aField;
	private double xcenter;
	private double ycenter;
	private double xvelocity;
	private int id;
	private int health;
	private int lightCount = 0;
	private boolean litUp=false;

	
	public AsteroidImp(GameRoom room, AsteroidField aField, double x, double y, double r, double vx, double vy, int img){
		this.room = room;
		this.aField = aField;
		this.x = x;
		this.y = y;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
		this.xvelocity = vx;
		this.r = r;
		health = (int) r*2;
		id = img;
		this.drawer = room.getAsteroidDrawer();
	}

	public void step() {
		this.x -= xvelocity;
		//this.y += yvelocity;
		this.xcenter = (x+ r);
		//this.ycenter = (y+ r);
		if(litUp) dim();
	}
	
	private void dim(){
		if(lightCount==0) litUp = false;
		lightCount--;
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
		
		aField.replaceRoid(this);
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
			room.addObject(new Explosion(x,y,room, getR(),Color.blue, true));
			room.addObject(new Explosion(x,y,room, getR(), Color.green, true));
			aField.replaceRoid(this);
	}
	}
		
	public boolean testLazerFree(double xshot, double yshot){
		if(xshot < x || xshot > xcenter+r || yshot < y || yshot> r+ycenter){
			return true;
		}
		lightUp();
		removeLife();
		return false;
	}
	
	public boolean isLitUp(){
		return litUp;
	}
	
	public void lightUp(){
		lightCount = 10;
		litUp=true;
	}
}

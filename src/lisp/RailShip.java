package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Random;

import lisp.drawers.RailShipBodyGeoDrawer;

public class RailShip extends GameObjectInRoom implements WithPosition, WithId{
	
	public double y;
	private double y_speed = 0;
	private boolean facesRight;
	private int lazerCoolDown=0;
	private int missileCoolDown=0;
	public int thrustingUp = 0;
	public int thrustingDown = 0;
	
	public Random r = new Random();
	
	private GameRoom room;
	public static final Stroke STROKE = new BasicStroke(2);
	private static double PER_STEP_ACC = .06;
	public int SHIP_WIDTH = 30;
	public int SHIP_HEIGHT = 55;
	private static final int COOL_DOWN_TIME = 50;
	
	
	private double health = 10;
	private int lives = 3;
	
	public RailShip(double y, boolean facesRight, GameRoom room){
		super(room);
		this.y = y;
		this.facesRight = facesRight;
		this.room = room;
		
		this.drawer = room.getShipDrawer();
		
	}
	
	public void step(){
		
		if(y<0){
			y_speed = .5*Math.abs(y_speed);
			y+=y_speed;
		}
		if(y>room.getPanel().getHeight()-SHIP_HEIGHT){
			y_speed = -.5*Math.abs(y_speed);
		}
		
		y += y_speed;
		lazerCoolDown--;
		thrustingUp--;
		thrustingDown--;
		missileCoolDown--;
		if(health<10){
			health+=.01;
		}
		if(health<0){
			removeLife();
			
		}
		if(lives <0){
			room.gameOver();
		}
	}
	
	public void accUp(){
		y_speed-= PER_STEP_ACC;
		thrustingUp = 3;
	}
	
	public void accDown(){
		y_speed+= PER_STEP_ACC;
		thrustingDown = 3;
	}
	
	public void fireLaser(){
		if(lazerCoolDown<0){
			
			room.addObject(new Laser(getX()+(facesRight?SHIP_WIDTH+1:-1) , y+SHIP_HEIGHT/2, facesRight, this, room));
			lazerCoolDown = COOL_DOWN_TIME;
		}
		
	}
	
	public void fireMissile(){
		if(missileCoolDown<0){
			
			room.addObject(new Missile(getX()+(facesRight?SHIP_WIDTH+1:-1) , y+SHIP_HEIGHT/2, facesRight, room));
			missileCoolDown = COOL_DOWN_TIME;
		}
		
	}
	
	public boolean testLazerFree(double xshot, double yshot, boolean isMissile){
		if(xshot < getX() || xshot > getX() +SHIP_WIDTH || yshot < y || yshot> y + SHIP_HEIGHT){
			return true;
		}
		room.addObject(new Explosion(xshot, yshot, room, Color.RED));
		if(isMissile){
			removeLife();
		} else {
			health--;
		}
		return false;
		
	}
	
	public void collideAsteroids(){
		for(AsteroidImp ast : Utilities.cloneArrayList(room.getAsteroidField().getAsteroids())){
			Ellipse2D asteroid = new Ellipse2D.Double(ast.getXCenter() - ast.getR(), ast.getYCenter()-ast.getR(), 2*ast.getR(), 2*ast.getR());
			if(asteroid.intersects(getX(), y, SHIP_WIDTH, SHIP_HEIGHT)){			
				room.addObject(new Explosion(ast.getXCenter(), ast.getYCenter(), room, Color.BLUE));
				removeLife();
				ast.destroy();
			}
			
		}
	}
	public double getX(){
		return facesRight ? 0 : room.getPanel().getWidth() - SHIP_WIDTH;
	}
	public double getY(){
		return y;
	}
	public void removeLife(){
		lives--;
		health = 10;
		room.addObject(new CircularExplosion(getX(), y, room, 180, Color.CYAN));
	}
	public double getHealth(){
		return health;
	}
	public int getLives(){
		return lives;
	}

	@Override
	public int getId() {
		return facesRight ? 1 : 0;
	}

	@Override
	public Double getPosition() {
		return new Double(getX(), y, SHIP_WIDTH, SHIP_HEIGHT);
	}
}

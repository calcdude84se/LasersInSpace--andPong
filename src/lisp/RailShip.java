package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import lisp.drawers.RailShipGeoDrawer;

public class RailShip extends GameObjectInRoom {
	

	public double x;
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
	public static final int SHIP_WIDTH = 20;
	public static final int SHIP_HEIGHT = 40;
	private static final int COOL_DOWN_TIME = 50;
	
	private double health = 20;
	private int lives = 10;
	
	public RailShip(double x, double y, boolean facesRight, GameRoom room){
		super(room);
		this.x = x;
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
		if(health<20){
			health+=.01;
		}
		if(health<0){
			lives--;
			health = 20;
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
			
			room.addObject(new Laser(x+(facesRight?SHIP_WIDTH+1:-1) , y+SHIP_HEIGHT/2, facesRight, this, room));
			lazerCoolDown = COOL_DOWN_TIME;
		}
		
	}
	
	public void fireMissile(){
		if(missileCoolDown<0){
			
			room.addObject(new Missile(x+(facesRight?SHIP_WIDTH+1:-1) , y+SHIP_HEIGHT/2, facesRight, room));
			missileCoolDown = COOL_DOWN_TIME;
		}
		
	}
	
	public boolean testLazerFree(double xshot, double yshot){
		if(xshot < x || xshot > x+SHIP_WIDTH || yshot < y || yshot> y + SHIP_HEIGHT){
			return true;
		}
		room.addObject(new Explosion(xshot, yshot, room, Color.RED));
		health--;
		return false;
		
	}
	
	public void collideAsteroids(){
		for(AsteroidImp ast : CollectionUtilities.cloneArrayList(room.getAsteroidField().getAsteroids())){
			Ellipse2D asteroid = new Ellipse2D.Double(ast.getXCenter() - ast.getR(), ast.getYCenter()-ast.getR(), 2*ast.getR(), 2*ast.getR());
			if(asteroid.intersects(x, y, SHIP_WIDTH, SHIP_HEIGHT)){			
				room.addObject(new Explosion(ast.getXCenter(), ast.getYCenter(), room, Color.BLUE));
				room.getScoreBoard().setScore(facesRight?1:0);
				ast.destroy();
			}
			
		}
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

}

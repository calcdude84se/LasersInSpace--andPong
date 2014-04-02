package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Random;

public class RailShip extends GameObjectInRoom implements WithPosition, WithId{
	
	public double y;
	public double x;
	private double y_speed = 0;
	private boolean facesRight;
	private int lazerCoolDown=0;
	public int thrustingUp = 0;
	public int thrustingDown = 0;
	public int tilted=-1;
	public boolean tiltLeft=false;
	
	public Random r = new Random();
	
	private GameRoom room;
	public static final Stroke STROKE = new BasicStroke(2);
	private static double PER_STEP_ACC = .06;
	private int width = 55;
	private int height = 55;
	private static final int COOL_DOWN_TIME = 50;
	
	
	private double health = 5;
	private int lives = 1;
	
	public RailShip(double y, double x, boolean facesRight, GameRoom room){
		super(room);
		this.y = y;
		this.x = x;
		this.facesRight = facesRight;
		this.room = room;
		this.drawer = room.getShipDrawer();
		
	}
	
	public void step(){
		
		if(y<0){
			y_speed = .5*Math.abs(y_speed);
			y+=y_speed;
		}
		if(y>room.getPanel().getHeight()-height){
			y_speed = -.5*Math.abs(y_speed);
		}
		
		y += y_speed;
		lazerCoolDown--;
		thrustingUp--;
		thrustingDown--;
		if(tilted>-1) tilt(tilted--);
		collideAsteroids();
		rescueAstronaut();
		if(health<0){
			removeLife();
			
		}
		if(lives !=1){
			room.gameOver();
		}
	}
	
	public void accUp(){
		y_speed-= PER_STEP_ACC;
		thrustingUp = 3;
		lightShip();
	}
	
	public void accDown(){
		y_speed+= PER_STEP_ACC;
		thrustingDown = 3;
		lightShip();
	}
	
	public void fireLaser(){
		if(lazerCoolDown<0){
			
			room.addObject(new Laser(getX()+(facesRight?width+1:-1) , y+height/2, facesRight, this, room));
			lazerCoolDown = COOL_DOWN_TIME;
		}
		lightShip();
		
	}
	
	public boolean testLazerFree(double xshot, double yshot, boolean isMissile){
		if(xshot < getX() || xshot > getX() +width || yshot < y || yshot> y + height){
			return true;
		}
		room.addObject(new Explosion(xshot, yshot, room, 30, Color.blue, false));
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
			if(tilted>-1){
				if(asteroid.intersects(getTiltedPosition(tiltLeft))){			
					room.addObject(new Explosion(ast.x, ast.y, room, ast.getR(), Color.BLUE, true));
					health--;
					ast.destroy();
				}
			}
			else{
				if(asteroid.intersects(getPosition())){			
					room.addObject(new Explosion(ast.x, ast.y, room, ast.getR(), Color.blue, true));
					health--;
					ast.destroy();
				}
			}
		}
	}
	
	public void lightShip(){
		for(AsteroidImp ast : Utilities.cloneArrayList(room.getAsteroidField().getAsteroids())){
			Ellipse2D asteroid = new Ellipse2D.Double(ast.getXCenter() - ast.getR()-80, ast.getYCenter()-ast.getR()-80, 2*ast.getR()+80, 2*ast.getR()+80);
			if(asteroid.intersects(getPosition())){			
				ast.lightUp();
			}
		}
	}
	
	public void rescueAstronaut(){
		Astronaut astronaut = room.getAstronautField().getAstronaut();
		if(astronaut!=null){
			Rectangle2D astro = new Rectangle2D.Double(astronaut.getX(), astronaut.getY(), astronaut.getWidth(), astronaut.getHeight());
			if(astro.intersects(getPosition())&&!astronaut.dead){
				room.getAstronautField().saveAstronaut();
				health++;
			}
		}
	}
	
	public void tilt(int tiltCt){
		if((tiltCt>0&&tiltCt<5) || (tiltCt>24&&tiltCt<=30)) room.getShipBodyDrawer().setPaths(1,"images/myshiptilt1.png");
		else if((tiltCt>4&&tiltCt<10) || (tiltCt>19&&tiltCt<25)) room.getShipBodyDrawer().setPaths(1,"images/myshiptilt2.png");
		else if(tiltCt>9 && tiltCt<20) room.getShipBodyDrawer().setPaths(1,"images/myshiptilt3.png");
		else room.getShipBodyDrawer().setPaths(1,"images/spaceship2.png");
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void removeLife(){
		lives--;
		health = 10;
		room.addObject(new Explosion(getX(), y, room, getWidth(), Color.CYAN, true));
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
		return new Double(getX(), y, width, height);
	}
	
	public Double getTiltedPosition(boolean tiltLeft) {
		if(tiltLeft) return new Double(x, y+height*3/4,width,height);
		else return new Double(x, y+height/2,width,height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int sHIP_WIDTH) {
		width = sHIP_WIDTH;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int sHIP_HEIGHT) {
		height = sHIP_HEIGHT;
	}
	
	public void setTilt(){
		tilted = 30;
	}
}

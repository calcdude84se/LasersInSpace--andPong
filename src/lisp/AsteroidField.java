package lisp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class AsteroidField extends GameObjectInRoom {
	
	Random gen = new Random();
	private int asteroidNum = gen.nextInt(4) + 5;
	private Collection<AsteroidImp> asteroids = new ArrayList<>();
	private JPanel panel;
	
	public AsteroidField(GameRoom room){
		super(room);
		/*
		 * Creates a random number of asteroids between 5 and 9
		 * Based on a 480 X 620 sized room
		 */
		panel = room.getPanel();
		int x1 = panel.getWidth() - 120;
		int y1 = panel.getHeight() - 120;
		for (int i=0; i<=asteroidNum; i++){
		asteroids.add(getRoid(x1,y1));
		}

	}
	/*
	 * This gets the coordinates for the asteroids based on the frame size and not arbitrary magic numbers.
	 */
	private AsteroidImp getRoid(int x1, int y1){
		int x = gen.nextInt(x1)+60;
		int y = gen.nextInt(y1)-30;
		int r = gen.nextInt(20) + 15;
		double vx = gen.nextDouble() - (.5);
		double vy = gen.nextDouble() + .2;
		int img = (int)Math.round(gen.nextDouble());
		AsteroidImp jimmy = new AsteroidImp(room, this, x,y,r,vx,vy,img);
		return jimmy; 
	}
	
	public void replaceRoid(AsteroidImp asteroid){
		asteroids.remove(asteroid);
		asteroids.add(getRoid(panel.getWidth()/2,1));
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		for (AsteroidImp asteroid : getAsteroidsClone()){
			//Draw each asteroid in Asteroid Field
			asteroid.draw(g);
		}
	}

	@Override
	public void step() {
		
		for (AsteroidImp asteroid: getAsteroidsClone()){
			asteroid.step();
			if (room.isOutOfBounds(asteroid)){
				asteroids.remove(asteroid);
				asteroids.add(getRoid(panel.getWidth()/2, 1));
			}
		}
	}
	
	private Collection<AsteroidImp> getAsteroidsClone() {
		return Utilities.cloneArrayList(asteroids);
	}
	
	public Collection<AsteroidImp> getAsteroids(){
		return asteroids;
	}
	/*
	 * Checks that the passed coordinates are not occupied.
	 */
	public boolean testLazerFree(double x, double y){
		for (AsteroidImp asteroid : getAsteroidsClone()){
			if (!asteroid.testLazerFree(x, y)) return false;
		}
		return true;
	}
	
	public boolean isFree(double x, double y){
		for (AsteroidImp asteroid : getAsteroidsClone()){
			if (!asteroid.isFree(x, y)) return false;
		}
		return true;
	}
	
	public double[] getAcceleration(double x, double y){
		double[] f = {0.0, 0.0};
		
		List<double[]> AsteroidList = new ArrayList<>();
		
		for(Asteroid ast : getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000}; 
			AsteroidList.add(n);
		}
	
	
		for(double[] ast : AsteroidList){
		
			double dist = -Math.sqrt((x-ast[0])*(x-ast[0])+(y-ast[1])*(y-ast[1]));
		
			f[0] += ast[2]*(x-ast[0])/(dist*dist*dist);
			f[1] += ast[2]*(y-ast[1])/(dist*dist*dist);
			
		}
		return f;
	}
	public void removeObject(AsteroidImp asteroidImp) {
		asteroids.remove(asteroidImp);
	}
}

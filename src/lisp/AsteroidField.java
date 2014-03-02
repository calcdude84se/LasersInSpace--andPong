package lisp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class AsteroidField implements GameObject{
	
	Random gen = new Random();
	private int asteroidNum = gen.nextInt(4) + 5;
	private Collection<AsteroidImp> asteroids = new ArrayList<>();
	private JPanel panel;
	private GameRoom room;
	
	public AsteroidField(GameRoom room){
		/*
		 * Creates a random number of asteroids between 5 and 9
		 * Based on a 480 X 620 sized room
		 */
		panel = room.getPanel();
		this.room = room;
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
		AsteroidImp jimmy = new AsteroidImp(room, x,y,r,vx,vy,img);
		return jimmy; 
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		for (AsteroidImp asteroid : asteroids){
			//Draw each asteroid in Asteroid Field
			asteroid.draw(g);
		}
	}

	@Override
	public void step() {
		
		for (AsteroidImp asteroid: CollectionUtilities.cloneArrayList(asteroids)){
			asteroid.step();
			if (asteroid.getXCenter() > panel.getWidth()
					|| asteroid.getYCenter() > panel.getHeight()){
				asteroids.remove(asteroid);
				asteroids.add(getRoid(panel.getWidth()/2, 1));
			}
		}
	}
	
	public Collection<AsteroidImp> getAsteroids(){
		return asteroids;
	}
	/*
	 * Checks that the passed coordinates are not occupied.
	 */
	public boolean isFree(double x, double y){
		for (AsteroidImp asteroid : asteroids){
			if (!asteroid.isFree(x, y)) return false;
		}
		return true;
	}
	@Override
	public void destroy() {
		//Not sure what to do here yet....
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
}

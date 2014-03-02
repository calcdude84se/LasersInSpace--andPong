package lisp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.JPanel;

public class AsteroidField implements GameObject{
	
	Random gen = new Random();
	private int asteroidNum = gen.nextInt(4) + 5;
	private Collection<AsteroidImp> asteroids = new ArrayList<>();
	
	public AsteroidField(GameRoom room){
		/*
		 * Creates a random number of asteroids between 5 and 9
		 * Based on a 480 X 620 sized room
		 */
		for (int i=0; i<=asteroidNum; i++){
		int[] a = getCoords(room);
		asteroids.add(new AsteroidImp(a[0],a[1],a[2]));
		}
	}
	/*
	 * This gets the coordinates for the asteroids based on the frame size and not arbitrary magic numbers.
	 */
	private int[] getCoords(GameRoom room){
		JPanel panel = room.getPanel();
		int x1 = panel.getWidth();
		int y1 = panel.getHeight();
		
		int x = gen.nextInt(x1-120)+ 60;
		int y = gen.nextInt(y1-120) + 60;
		int r = gen.nextInt(20) + 15;
		int[] a = new int[] {x,y,r};
		return a; 
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		for (AsteroidImp asteroid : asteroids){
			//Draw each asteroid in Asteroid Field
			asteroid.draw(g);
		}
	}

	@Override
	public void step() {
		//Dont Step the feild yet.  THX.
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

}

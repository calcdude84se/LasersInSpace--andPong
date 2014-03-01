package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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
		int x = gen.nextInt(430)+ 50;
		int y = gen.nextInt(540) + 50;
		int r = gen.nextInt(30) + 30;
		asteroids.add(new AsteroidImp(x,y,r));
		}
	}
	

	@Override
	public void draw(Graphics2D g) {
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

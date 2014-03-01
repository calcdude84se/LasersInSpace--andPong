package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AsteroidField implements GameObject{
	
	Random gen = new Random();
	private int asteroidNum = gen.nextInt(4) + 5;
	private Collection<Asteroid> asteroids = new ArrayList<>();
	
	public AsteroidField(){
		for (int i=0; i<=asteroidNum; i++){
		int x = gen.nextInt(430)+ 50;
		int y = gen.nextInt(540) + 50;
		int r = gen.nextInt(30) + 30;
		asteroids.add(new Asteroid(x,y,r));
		}
	}
	

	@Override
	public void draw(Graphics2D g) {
		for (Asteroid asteroid : asteroids){
			g.drawOval(asteroid.getX(), asteroid.getY(), asteroid.getR(), asteroid.getR());
		}
	}

	@Override
	public void step() {
		throw new RuntimeException("Stahp.  Dont step the AsteroidField yet!");
	}

}

package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.JPanel;

public class StarField  extends GameObjectInRoom {
	
	Random gen = new Random();
	private int asteroidNum = gen.nextInt(4) + 10;
	private Collection<Star> stars = new ArrayList<>();
	private JPanel panel;
	public Astronaut astronaut;
	private static final int STARNUM = 10;
	
	public StarField(GameRoom room){
		super(room);
		/*
		 * Creates a random number of asteroids between 5 and 9
		 * Based on a 480 X 620 sized room
		 */
		panel = room.getPanel();
		for (int i=0; i<=STARNUM; i++){
			stars.add(getStar());
		}

	}
	/*
	 * This gets the coordinates for the asteroids based on the frame size and not arbitrary magic numbers.
	 */
	private Star getStar(){
		int x = 2*gen.nextInt(panel.getWidth()/2);
		int y = gen.nextInt(panel.getHeight());
		int img = (int) Math.floor((gen.nextInt(3)));
		Star newStar = new Star(room, this, x, y, img);
		return newStar; 
	}
	
	public void replaceStar(Star star){
		stars.remove(star);
		stars.add(getStar());
	}

	@Override
	public void draw(Graphics2D g) {
		for (Star object : getStarsClone()){
			//Draw each asteroid in Asteroid Field
			object.draw(g);
		}
	}

	@Override
	public void step() {
		for (Star object: getStarsClone()){
			object.step();
			if (room.isOutOfBounds((WithPosition) object)){
				stars.remove(object);
				stars.add(getStar());
			}
		}
	}
	
	private ArrayList<Star> getStarsClone() {
		return Utilities.cloneArrayList(stars);
	}
	
	public void removeObject(GameObject object) {
		stars.remove(object);
	}
}


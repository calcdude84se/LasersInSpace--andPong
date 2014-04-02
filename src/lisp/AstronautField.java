package lisp;

import java.awt.Graphics2D;
import java.util.Random;

public class AstronautField extends GameObjectInRoom {
	
	private Astronaut astronaut = null;
	private Random r = new Random();
	private int count;
	public int deadAstronauts = 0;
	public int rescuedAstronauts = 0;
	public int lostAstronauts = 0;
	
	public AstronautField(GameRoom room) {
		super(room);
	}

	@Override
	public void draw(Graphics2D g) {
		if(astronaut != null){
			if(astronaut.dead){
				if(count==40) deadAstronauts++;
				if(count%2==0){
					astronaut.draw(g);
				}
				count--;
				if(count<0) astronaut.destroy();
			}
			else astronaut.draw(g);
		}
	}

	@Override
	public void step() {
		if(astronaut != null) {
			astronaut.step();
			if (room.isOutOfBounds(astronaut)){
				astronaut.destroy();
				lostAstronauts++;
			}
		}
		else {
			if(r.nextInt(200) == 0){
				astronaut = new Astronaut(room, this);
				count = 40;
			}
		}
	}

	public boolean testLazerFree(double xshot, double yshot){
		if(astronaut!=null) return astronaut.testLazerFree(xshot, yshot);
		return true;
	}
	
	public void saveAstronaut(){
		rescuedAstronauts++;
		removeObject();
	}
	
	public Astronaut getAstronaut(){
		return astronaut;
	}
	
	public void removeObject() {
		astronaut = null;
	}

}

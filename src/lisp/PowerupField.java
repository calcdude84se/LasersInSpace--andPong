package lisp;

import java.awt.Graphics2D;
import java.util.Random;

import lisp.powerups.Powerup;
import lisp.powerups.Unicorn;

public class PowerupField extends GameObjectInRoom {
	
	private Powerup<?> powerup = null;
	private Random r = new Random();
	
	public PowerupField(GameRoom room) {
		super(room);
	}

	@Override
	public void draw(Graphics2D g) {
		Powerup<?> powerup = this.powerup;
		if(powerup != null)
			powerup.draw(g);
	}

	@Override
	public void step() {
		Powerup<?> powerup = this.powerup;
		if(powerup != null) {
			powerup.step();
			if (room.isOutOfBounds(powerup))
				powerup.destroy();
		}
		else
			this.powerup = new Unicorn(room, this, r.nextDouble()*room.getPanel().getWidth(), 0,
					r.nextDouble()*4-2, r.nextDouble()*3+1, r.nextDouble()*30+30, r.nextInt(2));
	}

	public void doPowerupAt(double x, double y) {
		Powerup<?> powerup = this.powerup;
		if(powerup != null)
			powerup.doPowerupAt(x, y);
	}

	public void removeObject(Powerup powerup) {
		if(powerup == this.powerup)
			this.powerup = null;
	}

}

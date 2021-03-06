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
		else {
			double x = r.nextDouble()*120 + (room.getPanel().getWidth()/2-100);
			double y = 0;
			double vx = r.nextDouble()*4-2;
			double vy = r.nextDouble()*3+1;
			double rad = r.nextDouble()*20 + 15;
			if(r.nextInt(1000) == 0)
				this.powerup = new Unicorn(room, this, x, y,
						vx, vy, rad, r.nextInt(2));
		}
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

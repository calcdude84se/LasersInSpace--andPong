package lisp;

import java.awt.Graphics2D;

import lisp.powerups.Powerup;

public class PowerupField extends GameObjectInRoom {
	
	private Powerup<?> powerup = null;
	
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
		//TODO step magic
		Powerup<?> powerup = this.powerup;
		if(powerup != null)
			powerup.step();
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

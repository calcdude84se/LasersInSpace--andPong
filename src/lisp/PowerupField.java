package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

public class PowerupField extends GameObjectInRoom {
	
	private Collection<Powerup> powerups = new ArrayList<>();
	
	public PowerupField(GameRoom room) {
		super(room);
	}

	@Override
	public void draw(Graphics2D g) {
		for(Powerup powerup : getPowerupsClone())
			powerup.draw(g);
	}

	@Override
	public void step() {
		//TODO step magic
		for(Powerup powerup : getPowerupsClone())
			powerup.step();
	}

	public void doPowerupAt(double x, double y) {
		for(Powerup powerup : getPowerupsClone())
			powerup.doPowerupAt(x, y);
	}
	
	private Collection<Powerup> getPowerupsClone() {
		return Utilities.cloneArrayList(powerups);
	}

}

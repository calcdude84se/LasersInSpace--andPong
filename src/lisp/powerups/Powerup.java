package lisp.powerups;

import java.awt.Graphics2D;

import lisp.GameObjectABC;
import lisp.PowerupField;
import lisp.WithPosition;

public abstract class Powerup<T extends GameObjectABC<T>> extends GameObjectABC<T> implements WithPosition {
	
	private PowerupField puField;
	public double x, y, r;
	private double xv = 0, yv = 0;
	protected boolean activated = false;
	
	public Powerup(PowerupField puField, double x, double y, double xv, double yv, double r) {
		this.puField = puField;
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
		this.r = r;
	}

	public void doPowerupAt(double x, double y) {
		if(!activated && Math.pow(this.x + r - x, 2) + Math.pow(this.y + r - y, 2) <= r * r) {
			activated = true;
			activate();
		}
	}
	
	public void step() {
		if(activated) {
			activatedStep();
		} else {
			x += xv;
			y += yv;
		}
	}
	
	public void destroy() {
		puField.removeObject(this);
	}
	
	public void draw(Graphics2D g) {
		if(!activated)
			drawer.draw((T)this, g);
	}
	
	protected abstract void activate();
	protected abstract void activatedStep();

}

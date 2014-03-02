package lisp.drawers;

import java.awt.Graphics2D;

import lisp.CircularExplosion;
import lisp.Explosion;

public class CircularExplosionGeoDrawer implements GameObjectDrawer<CircularExplosion> {

	@Override
	public void draw(CircularExplosion explosion, Graphics2D g) {
		g.setColor(explosion.color);
		g.drawOval((int) (explosion.x-explosion.life), (int) (explosion.y-explosion.life), (int) 2*explosion.life,(int) 2*explosion.life);

	}
}
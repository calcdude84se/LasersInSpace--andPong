package lisp.drawers;

import java.awt.Graphics2D;

import lisp.Explosion;

public class ExplosionGeoDrawer implements GameObjectDrawer<Explosion> {

	public void draw(Explosion explosion, Graphics2D g) {
		g.setColor(explosion.color);
		for(double angle = 0; angle < 2*Math.PI; angle+=Math.PI/4){
			g.drawLine((int) (explosion.x+explosion.life*Math.sin(angle)), (int) (explosion.y+explosion.life*Math.cos(angle)), (int) (explosion.x+(explosion.radius+explosion.life)*Math.sin(angle)),(int) (explosion.y+(explosion.radius+explosion.life)*Math.cos(angle)));
		}

	}
}
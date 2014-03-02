package lisp;

import java.awt.Graphics2D;

public class AsteroidGeoDrawer implements GameObjectDrawer<AsteroidImp> {

	public void draw(AsteroidImp asteroid, Graphics2D g) {
	//	g.drawOval(x, y, r, r); Commented out for science.  Swap these for outline vs. solid.
		g.fillOval((int)asteroid.x, (int)asteroid.y, (int)(2*asteroid.r), (int)(2*asteroid.r));
	}
}
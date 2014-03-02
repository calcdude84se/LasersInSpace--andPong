package lisp.drawers;

import java.awt.Graphics2D;

import lisp.AsteroidImp;

public class AsteroidImageDrawer implements GameObjectDrawer<AsteroidImp> {
	

	@Override
	public void draw(AsteroidImp asteroid, Graphics2D g) {
        g.drawImage(asteroid.getImage(),
        		(int)asteroid.x,(int)asteroid.y,(int)(2*asteroid.r),(int)(2*asteroid.r),
        		null);
	}
}
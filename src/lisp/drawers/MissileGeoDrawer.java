package lisp.drawers;

import java.awt.Color;
import java.awt.Graphics2D;

import lisp.Missile;

public class MissileGeoDrawer implements GameObjectDrawer<Missile> {

	public void draw(Missile missile, Graphics2D g){
		g.setColor(Color.CYAN);
		g.drawOval((int) missile.r[0]-5,(int) missile.r[1]-5, 10, 10);
		
		
	}
}
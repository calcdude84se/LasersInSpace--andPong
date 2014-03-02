package lisp.drawers;

import java.awt.Color;
import java.awt.Graphics2D;

import lisp.RailShip;

public class RailShipBodyGeoDrawer implements GameObjectDrawer<RailShip> {

	@Override
	public void draw(RailShip railShip, Graphics2D g) {
		
		g.setColor(Color.GREEN);
		g.drawRect((int) Math.round(railShip.getX()), (int) Math.round(railShip.y), railShip.SHIP_WIDTH, railShip.SHIP_HEIGHT);
		
	}

}
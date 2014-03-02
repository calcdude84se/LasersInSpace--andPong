package lisp;

import java.awt.Color;
import java.awt.Graphics2D;

public class RailShipGeoDrawer implements GameObjectDrawer<RailShip> {

	@Override
	public void draw(RailShip railShip, Graphics2D g) {

		g.setStroke(RailShip.STROKE);
		
		
		//Engines
		
		g.setColor(Color.RED);
		if(railShip.thrustingDown>0){
			for(int i = 0; i<12; i++){
				g.drawLine((int) Math.round(railShip.x+RailShip.SHIP_WIDTH/2), 
						   (int) Math.round(railShip.y), 
						   (int) Math.round(railShip.x+RailShip.SHIP_WIDTH*railShip.r.nextDouble()), 
						   (int) Math.round(railShip.y - RailShip.SHIP_HEIGHT*.5*railShip.r.nextDouble()));
		
			}
		}
		
		else if(railShip.thrustingUp>0){
			for(int i = 0; i<12; i++){
				g.drawLine((int) Math.round(railShip.x+RailShip.SHIP_WIDTH/2), 
						   (int) Math.round(railShip.y+RailShip.SHIP_HEIGHT), 
						   (int) Math.round(railShip.x+RailShip.SHIP_WIDTH*railShip.r.nextDouble()), 
						   (int) Math.round(railShip.y + RailShip.SHIP_HEIGHT*(1+.5*railShip.r.nextDouble())));
		
			}
		}
		
		g.setColor(Color.GREEN);
		g.drawRect((int) Math.round(railShip.x), (int) Math.round(railShip.y), RailShip.SHIP_WIDTH, RailShip.SHIP_HEIGHT);
		
	}

}
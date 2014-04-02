package lisp.drawers;

import java.awt.Color;
import java.awt.Graphics2D;

import lisp.RailShip;

public class RailShipFlareGeoDrawer implements GameObjectDrawer<RailShip> {

	@Override
	public void draw(RailShip railShip, Graphics2D g) {

		g.setStroke(RailShip.STROKE);
		
		
		//Engines
		
		double x = railShip.getX();
		double y = railShip.getY();
		
		g.setColor(Color.RED);
		if(railShip.thrustingDown>0){
			for(int i = 0; i<12; i++){
				g.drawLine((int) Math.round(x+railShip.getWidth()/2), 
						   (int) Math.round(railShip.y), 
						   (int) Math.round(x+railShip.getWidth()*railShip.r.nextDouble()), 
						   (int) Math.round(railShip.y - railShip.getHeight()*.5*railShip.r.nextDouble()));
		
			}
		}
		
		else if(railShip.thrustingUp>0){
			for(int i = 0; i<12; i++){
				g.drawLine((int) Math.round(x+railShip.getWidth()/2), 
						   (int) Math.round(railShip.y+railShip.getHeight()), 
						   (int) Math.round(x+railShip.getWidth()*railShip.r.nextDouble()), 
						   (int) Math.round(railShip.y + railShip.getHeight()*(1+.5*railShip.r.nextDouble())));
		
			}
		}
	}

}
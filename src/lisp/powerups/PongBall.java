package lisp.powerups;

import lisp.GameObjectInRoom;
import lisp.GameRoom;
import lisp.RailShip;
import lisp.drawers.PongBallGeoDrawer;

public class PongBall extends GameObjectInRoom {
	public double x, y, h, v;
	
	public static final int DIM = 16;
	public PongBall(double x, double y, double h, double v, GameRoom room){
		super(room);
		this.x = x; 
		this.y = y;
		this.h = h;
		this.v = v;
		drawer = new PongBallGeoDrawer();
	}
	
	public void step(){
		if(y<0){
			v = Math.abs(v);
			y+=v;
		}
		if(y>room.getPanel().getHeight()-DIM){
			v = -Math.abs(v);
		}
		
		if(x<RailShip.SHIP_WIDTH){
			h = Math.abs(h);
			x+=h;
			if(room.getShipLeft().getY()>y||room.getShipLeft().getY()+RailShip.SHIP_HEIGHT<y){
				room.getShipLeft().removeLife();
				y = 100000;
				x = 30;
				h = 0;
			}
		}
		if(x>room.getPanel().getWidth()-DIM - RailShip.SHIP_WIDTH){
			h = -Math.abs(h);
			if(room.getShipRight().getY()>y||room.getShipRight().getY()+RailShip.SHIP_HEIGHT<y){
				room.getShipRight().removeLife();
			}
			y = 100000;
			x = 30;
			h = 0;
		}
		
		
		x+=h;
		y+=v;
	}

}

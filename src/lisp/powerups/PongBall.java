package lisp.powerups;

import lisp.GameObjectInRoom;
import lisp.GameRoom;

public class PongBall extends GameObjectInRoom {
	public double x, y, h, v;
	
	public static final int DIM = 16;
	public PongBall(double x, double y, double h, double v, GameRoom room){
		super(room);
		this.x = x; 
		this.y = y;
		this.h = h;
		this.v = v;
	}
	
	public void step(){
		if(y<0){
			v = Math.abs(v);
			y+=v;
		}
		if(y>room.getPanel().getHeight()-DIM){
			v = -.5*Math.abs(v);
		}
		
		if(x<room.getShipLeft().getWidth()){
			h = Math.abs(h);
			x+=h;
			if(room.getShipLeft().getY()>y||room.getShipLeft().getY()+room.getShipLeft().getHeight()<y){
				room.getShipLeft().removeLife();
			}
		}
		if(x>room.getPanel().getWidth()-DIM - room.getShipRight().getWidth()){
			x = -.5*Math.abs(h);
			if(room.getShipRight().getY()>y||room.getShipRight().getY()+room.getShipRight().getHeight()<y){
				room.getShipRight().removeLife();
			}
		}
		
		
		x+=h;
		y+=v;
	}

}

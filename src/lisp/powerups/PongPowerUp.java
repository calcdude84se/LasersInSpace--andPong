package lisp.powerups;

import java.awt.geom.Rectangle2D;

import lisp.GameRoom;
import lisp.PowerupField;
import lisp.drawers.PongBallPowerUpDrawer;

public class PongPowerUp extends Powerup{
	GameRoom room;
	int timer;
	private PongBall ball;
	public PongPowerUp(PowerupField puField, double x, double y, double xv, double yv, double r, GameRoom room){
		super(puField, x, y, xv, yv, r);
		this.room = room;
		this.drawer = new PongBallPowerUpDrawer();
	}

	@Override
	protected void activate() {
		ball = new PongBall(x, y, 15, 15, room);
		room.addObject(ball);
		int timer = 270;
		
	}

	@Override
	protected void activatedStep() {
		timer --;
		if(timer == 0){
			ball.destroy();
			this.destroy();
		}
	}

	
	public Rectangle2D.Double getPosition(){
		return new Rectangle2D.Double(x, y, PongBall.DIM, PongBall.DIM);
	}
}

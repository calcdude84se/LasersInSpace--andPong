package lisp.powerups;

import lisp.GameRoom;
import lisp.PowerupField;

public class PongPowerUp extends Powerup{
	GameRoom room;
	int timer;
	private PongBall ball;
	public PongPowerUp(PowerupField puField, double x, double y, double xv, double yv, double r, GameRoom room){
		super(puField, x, y, xv, yv, r);
		this.room = room;
		
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

}

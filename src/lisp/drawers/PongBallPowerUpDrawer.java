package lisp.drawers;

import java.awt.Graphics2D;

import lisp.powerups.PongBall;
import lisp.powerups.PongPowerUp;

public class PongBallPowerUpDrawer implements GameObjectDrawer<PongPowerUp> {

	public void draw(PongPowerUp ball, Graphics2D g) {
	//	g.drawOval(x, y, r, r); Commented out for science.  Swap these for outline vs. solid.
		g.fillOval((int)ball.x, (int)ball.y, (int)(PongBall.DIM), (int)(PongBall.DIM));
	}

	
		
	
}
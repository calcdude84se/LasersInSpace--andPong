package lisp.drawers;

import java.awt.Graphics2D;

import lisp.powerups.PongBall;

public class PongBallGeoDrawer implements GameObjectDrawer<PongBall> {

	public void draw(PongBall ball, Graphics2D g) {
	//	g.drawOval(x, y, r, r); Commented out for science.  Swap these for outline vs. solid.
		g.fillOval((int)ball.x, (int)ball.y, (int)(ball.DIM), (int)(ball.DIM));
	}
}
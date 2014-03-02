package lisp;

import java.awt.Graphics2D;

public class LaserDrawer implements GameObjectDrawer<Laser> {

	@Override
	public void draw(Laser laser, Graphics2D g) {
		if(laser.on<5){
			
			g.setColor(Laser.RAINBOW[laser.on]);
			g.setStroke(Laser.STROKE);
			
			for(int i = 0; i<laser.path.length-1; i++){  			
				g.drawLine( laser.path[i][0], laser.path[i][1], laser.path[i+1][0], laser.path[i+1][1]);
			}
		}
	}
}
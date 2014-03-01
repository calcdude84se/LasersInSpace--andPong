package lisp;

import java.awt.Graphics2D;

public class Laser implements GameObject {
	int [][] path;
	boolean on = true;
	int life;
	GameRoom room;
	
	Laser(double x, double y, boolean facesRight, GameRoom room){
		path = integrate();
		life = 30;
		on = true;
		room = room;
	}
	@Override
	public void draw(Graphics2D g) {
		if(on){
			for(int i = 0; i<path.length-1; i++){  			
				g.drawLine(path[i][0], path[i][1], path[i+1][0], path[i+1][1]);
			}
		}
	}

	@Override
	public void step() {
		on = !on;
		life = life-1;
		if(life == 0){
			
		}
	}

}

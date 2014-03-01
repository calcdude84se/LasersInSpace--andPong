package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Laser implements GameObject {
	int [][] path;
	boolean on = true;
	int life;
	GameRoom room;
	RailShip ship1;
	RailShip ship2;
	
	Laser(double init_x, double init_y, boolean facesRight, GameRoom room){
		path = integrate(init_x, init_y, facesRight);
		life = 30;
		on = true;
		this.room = room;
		ship1 = room.getShipLeft();
		ship2 = room.getShipRight();
	}
	@Override
	public void draw(Graphics2D g) {
		if(on){
			for(int i = 0; i<path.length-1; i++){  			
				g.drawLine( path[i][0], path[i][1], path[i+1][0], path[i+1][1]);
			}
		}
	}

	@Override
	public void step() {
		on = !on;
		life--;
		if(life == 0){
			room.removeObject(this);
		}
	}
	
	public int[][] integrate(double init_x, double init_y, boolean facesRight){
		List<int[]> rList = new ArrayList<>();
		double[] r = {init_x, init_y, facesRight?1:-1, 0};
		double timeStep = 5;
		
		AsteroidField field = room.getAsteroidField();
		
		List<double[]> AsteroidList = new ArrayList<>();
		
		for(Asteroid ast : field.getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000}; 
			AsteroidList.add(n);
		}
		
		
		double dist;
		
		while(field.isFree(r[0], r[1]) && r[0]>0 && r[0]<room.getPanel().getWidth() && r[1]>0 && r[1]< room.getPanel().getHeight()){
			int[] e = {(int) r[0], (int) r[1], (int) r[2], (int) r[3]};
			rList.add(e);
			for(double[] ast : AsteroidList){
				
				dist = Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
				
				r[2] += timeStep*ast[2]*(r[0]-ast[0])/(dist*dist*dist);
				r[3] += timeStep*ast[2]*(r[1]-ast[1])/(dist*dist*dist);
				
				
			}
			r[0] += timeStep*r[2];
			r[1] += timeStep*r[3];
			
			timeStep = 5.0/Math.sqrt(r[2]*r[2]+r[3]*r[3]);
		}
		
		
		return (int[][]) rList.toArray();
		
		
	}
	
	
	

}

package lisp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Missile extends GameObjectInRoom {
	
	double[] r;
	int thrusting = 10;
	GameRoom room;
	
	Missile(double x, double y, boolean facesRight, GameRoom room){
		super(room);
		this.room = room;
		double [] r = {x, y, facesRight?1:-1, 0};
		this.r = r;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.CYAN);
		g.drawOval((int) r[0]-5,(int) r[1]-5, 10, 10);
		
		
	}
	
	public void step(){
		
		
		List<double[]> AsteroidList = new ArrayList<>();
		
		for(Asteroid ast : room.getAsteroidField().getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000}; 
			AsteroidList.add(n);
		}
		
		
		for(double[] ast : AsteroidList){
			
			double dist = -Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
			
			r[2] += ast[2]*(r[0]-ast[0])/(dist*dist*dist);
			r[3] += ast[2]*(r[1]-ast[1])/(dist*dist*dist);
			
			
		}
		
		r[0] += r[2];
		r[1] += r[3];
		
	}
}

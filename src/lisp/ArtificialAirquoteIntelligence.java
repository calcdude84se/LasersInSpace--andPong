package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ArtificialAirquoteIntelligence extends GameObjectInRoom {
	RailShip me;
	RailShip asshole;
	private static double STEPSIZE_GOAL = 5;
	private boolean facesRight;
	
	ArtificialAirquoteIntelligence(RailShip me, RailShip asshole, boolean facesRight, GameRoom room){
		super(room);
		
		this.me = me;
		this.asshole = asshole;
		this.facesRight = facesRight;
	}
	
	@Override
	public void draw(Graphics2D g) {
		//this space intentionaly left blank
	}

	@Override
	public void step() {
		
	}
	
	public boolean checkShot(double init_x, double init_y, boolean facesRight){
		
		
		double[] r = {init_x, init_y, facesRight?1:-1, 0};
		double timeStep = STEPSIZE_GOAL;
		
		AsteroidField field = room.getAsteroidField();
		
		List<double[]> AsteroidList = new ArrayList<>();
		
		for(Asteroid ast : field.getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000}; 
			AsteroidList.add(n);
		}
		
		
		double dist;
		
		while(field.isFree(r[0], r[1]) && 
			  me.testLazerFree(r[0], r[1]) &&
			  asshole.testLazerFree(r[0], r[1]) &&
			  r[0]>0 && r[0]<room.getPanel().getWidth() && 
			  r[1]>0 && r[1]< room.getPanel().getHeight()){
			
			for(double[] ast : AsteroidList){
				
				dist = -Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
				
				r[2] += timeStep*ast[2]*(r[0]-ast[0])/(dist*dist*dist);
				r[3] += timeStep*ast[2]*(r[1]-ast[1])/(dist*dist*dist);
				
				
			}
			timeStep = STEPSIZE_GOAL/Math.sqrt(r[2]*r[2]+r[3]*r[3]);
			r[0] += timeStep*r[2];
			r[1] += timeStep*r[3];
			
			
		}
		
	return Math.abs(r[1]-asshole.getY())<40;
	}
}

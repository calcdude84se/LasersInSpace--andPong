package lisp;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtificialAirquoteIntelligence extends GameObjectInRoom {
	RailShip me;
	RailShip asshole;
	private static double STEPSIZE_GOAL = 5;
	private boolean facesRight;
	private int movingUp = 0;
	private int movingDown = 0;
	Random r = new Random();
	private int difficulty;
	private static final int[] FIRE_PROBABILITIES = {400, 300, 200, 100, 50, 50, 100, 100, 200, 600}; 
	
	ArtificialAirquoteIntelligence(RailShip me, RailShip asshole, boolean facesRight, int difficulty, GameRoom room){
		super(room);
		
		this.me = me;
		this.asshole = asshole;
		this.facesRight = facesRight;
		
		difficulty = r.nextInt(10);
		
		if(difficulty < 0 || difficulty >9){
			throw new RuntimeException("dificulty Out of bounds");
		}
		
		System.out.println(difficulty);
		
		this.difficulty = difficulty;
	}
	
	@Override
	public void draw(Graphics2D g) {
		//this space intentionally left blank
	}

	@Override
	public void step() {
		movingUp--;
		movingDown--;
		if(checkShot(me.getX()+RailShip.SHIP_WIDTH/2, me.getY()+RailShip.SHIP_HEIGHT/2, facesRight )){
			if(r.nextInt(10)<difficulty)
				me.fireLaser();
		}
		if(movingUp>0){
			me.accUp();
		} else if(movingDown > 0){
			me.accDown();
		}
		
		if(r.nextInt(200 - 12*difficulty)==0){
			movingUp = 25;
		}
		if(r.nextInt(200 - 12*difficulty)==0){
			movingDown = 25;
		}
		if(r.nextInt(300 - 25*difficulty)==0){
			me.fireMissile();
		}
		if(r.nextInt(FIRE_PROBABILITIES[difficulty])==0){
			me.fireLaser();
		}
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

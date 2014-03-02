package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class Laser extends GameObjectInRoom {
	int [][] path;
	int on = 5;
	int life = 30;
	GameRoom room;
	RailShip ship1;
	RailShip ship2;
	
	private double init_x;
	private double init_y;
	private boolean facesRight;
	
	private static final double STEPSIZE_GOAL = .5; 
	
	private static final Stroke STROKE = new BasicStroke(2);
	
	private static final Color[] RAINBOW = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,new Color(200, 0, 200)};
	
	Laser(double init_x, double init_y, boolean facesRight, GameRoom room){
		super(room);
		ship1 = room.getShipLeft();
		ship2 = room.getShipRight();
		this.room = room;
		path = integrate(init_x, init_y, facesRight);
		
		this.init_x = init_x;
		this.init_y = init_y;
		this.facesRight = facesRight;
		
		
		
		
	}
	@Override
	public void draw(Graphics2D g) {
		if(on<5){
			
			g.setColor(RAINBOW[on]);
			g.setStroke(STROKE);
			
			for(int i = 0; i<path.length-1; i++){  			
				g.drawLine( path[i][0], path[i][1], path[i+1][0], path[i+1][1]);
			}
		}
	}

	@Override
	public void step() {
		path = integrate(init_x, init_y, facesRight);
		on = (on<5)?on+1:0;
		life--;
		if(life == 0){
			destroy();
		}
	}
	
	public int[][] integrate(double init_x, double init_y, boolean facesRight){
		
		List<int[]> rList = new ArrayList<>();
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
			  ship1.testLazerFree(r[0], r[1]) &&
			  ship2.testLazerFree(r[0], r[1]) &&
			  r[0]>0 && r[0]<room.getPanel().getWidth() && 
			  r[1]>0 && r[1]< room.getPanel().getHeight()){
			
			
			
			int[] e = {(int) r[0], (int) r[1], (int) r[2], (int) r[3]};
			rList.add(e);
			for(double[] ast : AsteroidList){
				
				dist = -Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
				
				r[2] += timeStep*ast[2]*(r[0]-ast[0])/(dist*dist*dist);
				r[3] += timeStep*ast[2]*(r[1]-ast[1])/(dist*dist*dist);
				
				
			}
			timeStep = STEPSIZE_GOAL/Math.sqrt(r[2]*r[2]+r[3]*r[3]);
			r[0] += timeStep*r[2];
			r[1] += timeStep*r[3];
			
			
		}
		
		if(!field.isFree(r[0], r[1])){
			room.addObject(new Explosion(r[0], r[1], room, 15,Color.BLUE ));
		}
		
		
		return  rList.toArray(new int[0][]);
		
		
	}
	
	
	

}

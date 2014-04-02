package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import lisp.drawers.LaserDrawer;

public class Laser extends GameObjectInRoom {
	public int [][] path;
	public int on = 5;
	public int life = 10;
	public GameRoom room;
	public RailShip ship1;
	public RailShip ship2;
	public RailShip owner;
	
	private double init_x;
	private double init_y;
	private boolean facesRight;
	
	private static final double STEPSIZE_GOAL = 5; 
	
	public static final Stroke STROKE = new BasicStroke(2);
	
	public static final Color[] RAINBOW = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,new Color(200, 0, 200)};
	
	Laser(double init_x, double init_y, boolean facesRight, RailShip owner, GameRoom room){
		super(room);
		ship1 = room.getShipLeft();
		ship2 = room.getShipRight();
		this.room = room;
		path = integrate(init_x, init_y, facesRight);
		
		this.init_x = init_x;
		this.init_y = init_y;
		this.facesRight = facesRight;
		this.owner = owner;
        SoundJLayer soundToPlay = new SoundJLayer("/sounds/laser.mp3");
        soundToPlay.play();
		drawer = new LaserDrawer();
		
	}

	@Override
	public void step() {
		init_y = owner.getY() + owner.getHeight()/2;
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
		AstronautField astronaut = room.getAstronautField();
		
		List<double[]> AsteroidList = new ArrayList<>();
		int location = 0;
		
		for(Asteroid ast : field.getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000, location}; 
			location++;
			AsteroidList.add(n);
		}
		
		
		double dist;
		
		while(field.testLazerFree(r[0], r[1]) && astronaut.testLazerFree(r[0], r[1]) && 
			  ship1.testLazerFree(r[0], r[1], false) &&
			  r[0]>0 && r[0]<room.getPanel().getWidth() && 
			  r[1]>0 && r[1]< room.getPanel().getHeight()){
			
			int[] e = {(int) r[0], (int) r[1], (int) r[2], (int) r[3]};
			rList.add(e);
			int count = 0;
			ship1.lightShip();
			for(double[] ast : AsteroidList){
				
				dist = -Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
				
				r[2] += timeStep*ast[2]*(r[0]-ast[0])/(dist*dist*dist);
				r[3] += timeStep*ast[2]*(r[1]-ast[1])/(dist*dist*dist);
				if (Math.abs(dist)<50) field.lightAsteroid(count);
				count++;
				
			}
			timeStep = STEPSIZE_GOAL/Math.sqrt(r[2]*r[2]+r[3]*r[3]);
			r[0] += timeStep*r[2];
			r[1] += timeStep*r[3];
			
			
		}
		
		if(!field.isFree(r[0], r[1])){
			room.addObject(new Explosion(r[0], r[1], room, 15,Color.BLUE, false ));
		}
		
		
		return  rList.toArray(new int[0][]);
		
		
	}
	

}

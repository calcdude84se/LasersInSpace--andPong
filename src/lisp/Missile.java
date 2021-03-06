package lisp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lisp.drawers.MissileGeoDrawer;

public class Missile extends GameObjectInRoom {
	
	public double[] r;
	private int thrusting = 10;
	private GameRoom room;
	private RailShip ship1, ship2; 
	
	Missile(double x, double y, boolean facesRight, GameRoom room){
		super(room);
		this.room = room;
		double [] r = {x, y, facesRight?5:-5, 0};
		this.r = r;
		ship1 = room.getShipLeft();
		ship2 = room.getShipRight();
        SoundJLayer soundToPlay = new SoundJLayer("/sounds/missile.mp3");
        soundToPlay.play();
		drawer = new MissileGeoDrawer();
	}
	
	public void step(){
		
		if(!(     room.getAsteroidField().isFree(r[0], r[1]) && 
				  ship1.testLazerFree(r[0], r[1], true) &&
				  ship2.testLazerFree(r[0], r[1], true) &&
				  r[0]>0 && r[0]<room.getPanel().getWidth() && 
				  r[1]>0 && r[1]< room.getPanel().getHeight())){
			this.destroy();
			room.addObject(new Explosion(r[0], r[1], room, 15,Color.BLUE ));
			
			
		}
		
		room.doPowerupAt(r[0], r[1]);
		
		
		
		List<double[]> AsteroidList = new ArrayList<>();
		/*
		for(Asteroid ast : room.getAsteroidField().getAsteroids()){
			double[] n = {ast.getXCenter(), ast.getYCenter(), ast.getMass()/2000}; 
			AsteroidList.add(n);
		}
		
		
		for(double[] ast : AsteroidList){
			
			double dist = -Math.sqrt((r[0]-ast[0])*(r[0]-ast[0])+(r[1]-ast[1])*(r[1]-ast[1]));
			
			r[2] += ast[2]*(r[0]-ast[0])/(dist*dist*dist);
			r[3] += ast[2]*(r[1]-ast[1])/(dist*dist*dist);
			
			
		}
		*/
		
		double f[] = room.getAsteroidField().getAcceleration(r[0], r[1]);
		r[2] += 15*f[0];
		r[3] += 15*f[1];
		
		
		r[0] += r[2];
		r[1] += r[3];
		
	}
}

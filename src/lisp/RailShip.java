package lisp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class RailShip implements GameObject {
	private double x;
	private double y;
	private double y_speed = 0;
	private boolean facesRight;
	private int lazerCoolDown=0;
	
	
	private GameRoom room;
	private static final Stroke STROKE = new BasicStroke(2);
	private static double PER_STEP_ACC = .1;
	public static final int SHIP_WIDTH = 20;
	private static final int SHIP_HEIGHT = 40;
	
	public RailShip(double x, double y, boolean facesRight, GameRoom room){
		
		this.x = x;
		this.y = y;
		this.facesRight = facesRight;
		//this.y_speed = 1;
		this.room = room;
		
	}
	
	public void step(){
		y += y_speed;
		lazerCoolDown--;
		//fireLaser();
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.GREEN);
		g.setStroke(STROKE);
		g.drawRect((int) Math.round(x), (int) Math.round(y), SHIP_WIDTH, SHIP_HEIGHT);
	}
	
	public void accUp(){
		y_speed+= PER_STEP_ACC;
	}
	
	public void accDown(){
		y_speed-= PER_STEP_ACC;
	}
	
	public void fireLaser(){
		if(lazerCoolDown<0){
			
			room.addObject(new Laser(x+SHIP_WIDTH/2, y+SHIP_HEIGHT/2, facesRight, room));
			lazerCoolDown = 30;
		}
		
	}
}

package lisp;
import java.awt.Graphics2D;

public class Asteroid{
	
	private int x;
	private int y;
	private int r;
	
	public Asteroid(int x, int y, int r){
		this.x = x;
		this.y = y;
		this.r = r; //radius of asteroid
	}

	public void draw(Graphics2D g) {
		g.drawOval(x, y, r, r);
	}

	public void step() {
		throw new RuntimeException("What the fuck are you doing stepping an Asteroid??");
	}
	
	public double getMass(){
		return r*r*r;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getR(){
		return r;
	}
}

package lisp;
import java.awt.Graphics2D;

public class AsteroidImp implements Asteroid{
	
	private int x;
	private int y;
	private int r;
	
	public AsteroidImp(int x, int y, int r){
		this.x = x;
		this.y = y;
		this.r = r; //radius of asteroid
	}

	public void draw(Graphics2D g) {
		g.drawOval(x, y, r, r);
	}

	public void step() {
		//Dont step this yet.  These are static.
	}
	@Override
	public double getMass(){
		return r*r*r;
	}
	
	public int getR(){
		return r;
	}

	@Override
	public double getXCenter() {
		return x;
	}

	@Override
	public double getYCenter() {
		return y;
	}
	
	public boolean isFree(double x1, double y1){
		return !((Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2)) <= Math.pow(r, 2));
	}

}

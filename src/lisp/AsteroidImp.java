package lisp;
import java.awt.Graphics2D;

public class AsteroidImp implements Asteroid{
	
	private int x;
	private int y;
	private int r;
	private double xcenter;
	private double ycenter;
	
	public AsteroidImp(int x, int y, int r){
		this.x = x;
		this.y = y;
		this.xcenter = (x+ (1/2)*r);
		this.ycenter = (y+ (1/2)*r);
		this.r = r; //radius of asteroid
	}

	public void draw(Graphics2D g) {
	//	g.drawOval(x, y, r, r); Commented out for science.  Swap these for outline vs. solid.
		g.fillOval(x, y, r, r);
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
		return !((Math.pow((x1 - xcenter), 2) + Math.pow((y1 - ycenter), 2)) <= Math.pow(r, 2));
	}

}

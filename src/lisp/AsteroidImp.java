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
		throw new RuntimeException("What the fuck are you doing stepping an Asteroid??");
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
}

package lisp;

public class AsteroidImp extends GameObjectABC<AsteroidImp> implements Asteroid{
	
	double x;
	double y;
	double r;
	private double xcenter;
	private double ycenter;
	private double xvelocity;
	private double yvelocity;

	
	public AsteroidImp(double x, double y, double r, double vx, double vy){
		this.x = x;
		this.y = y;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
		this.xvelocity = vx;
		this.yvelocity = vy;
		this.r = r;
		
		this.drawer = new AsteroidGeoDrawer();
	}

	public void step() {
		this.x += xvelocity;
		this.y += yvelocity;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
	}
	@Override
	public double getMass(){
		return r*r*r;
	}
	
	public double getR(){
		return r;
	}

	@Override
	public double getXCenter() {
		return xcenter;
	}

	@Override
	public double getYCenter() {
		return ycenter;
	}
	
	public boolean isFree(double x1, double y1){
		return !((Math.pow((x1 - xcenter), 2) + Math.pow((y1 - ycenter), 2)) <= Math.pow(r, 2));
	}
	public void destroy(){
		//nothing to do here.
	}
}

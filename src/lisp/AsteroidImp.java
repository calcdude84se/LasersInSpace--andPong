package lisp;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AsteroidImp extends GameObjectABC implements Asteroid{
	
	private double x;
	private double y;
	private double r;
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

	public class AsteroidGeoDrawer implements Drawable {

		public void draw(Graphics2D g) {
		//	g.drawOval(x, y, r, r); Commented out for science.  Swap these for outline vs. solid.
			g.fillOval((int)x, (int)y, (int)(2*r), (int)(2*r));
		}
	}
	public class AsteroidImageDrawer implements Drawable {
		@Override
		public void draw(Graphics2D g) {
			String path = "images/asteroid.png";
			BufferedImage image = null;
	        try {image = ImageIO.read(new File(path));
	        } catch (IOException e){
	        }
	        g.drawImage(image,
	        		(int)x,(int)y,(int)(2*r),(int)(2*r),
	        		null);
		}
	}
}

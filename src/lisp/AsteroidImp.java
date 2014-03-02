package lisp;

import lisp.drawers.AsteroidGeoDrawer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AsteroidImp extends GameObjectABC<AsteroidImp> implements Asteroid, WithImage{
	
	public double x;
	public double y;
	public double r;
	private double xcenter;
	private double ycenter;
	private double xvelocity;
	private double yvelocity;
	private BufferedImage image;

	
	public AsteroidImp(GameRoom room, double x, double y, double r, double vx, double vy, int img){
		this.x = x;
		this.y = y;
		this.xcenter = (x+ r);
		this.ycenter = (y+ r);
		this.xvelocity = vx;
		this.yvelocity = vy;
		this.r = r;
		String path = "images/asteroid" + img + ".png";
		image = null;
		{
	        try {image = ImageIO.read(new File(path));
	        } catch (IOException e){
	        }
		}
		this.drawer = room.getAsteroidDrawer();
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
	public BufferedImage getImage(){
		return image;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 2*r;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 2*r;
	}
}

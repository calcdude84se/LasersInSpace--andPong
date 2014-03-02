package lisp.drawers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lisp.AsteroidImp;

public class AsteroidImageDrawer implements GameObjectDrawer<AsteroidImp> {
	
	String path = "images/asteroid.png";
	BufferedImage image = null;
	{
        try {image = ImageIO.read(new File(path));
        } catch (IOException e){
        }
	}

	@Override
	public void draw(AsteroidImp asteroid, Graphics2D g) {
        g.drawImage(image,
        		(int)asteroid.x,(int)asteroid.y,(int)(2*asteroid.r),(int)(2*asteroid.r),
        		null);
	}
}
package lisp.drawers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lisp.GameObject;
import lisp.WithId;
import lisp.WithPosition;

public abstract class GenericImageDrawer<T extends GameObject & WithId & WithPosition> implements GameObjectDrawer<T> {
	
	private BufferedImage[] images;
	
	protected abstract String[] getPaths();
	
	protected void init() {
		String[] paths = getPaths();
		images = new BufferedImage[paths.length];
		for(int i = 0; i < images.length; i++)
			images[i] = lisp.Utilities.loadImage(this.getClass().getResource(paths[i]));
	        
	}

	@Override
	public void draw(T go, Graphics2D g) {
		Rectangle2D.Double position = go.getPosition();
		g.drawImage(images[go.getId()], (int)(position.x+.5), (int)(position.y+.5), (int)(position.width+.5), (int)(position.height+.5), null);
	}
}

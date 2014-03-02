package lisp.drawers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lisp.GameObject;

public abstract class GenericImageDrawer<T extends WithImage & GameObject> implements GameObjectDrawer<T> {

	@Override
	public void draw(T go, Graphics2D g) {
		g.drawImage(go.getImage(), go.getX(), go.getY(), go.getWidth(), go.getHeight(), null);
	}
}

package lisp;

import java.awt.Graphics2D;

public interface GameObjectDrawer<T extends GameObject> {

	public void draw(T go, Graphics2D g);
}

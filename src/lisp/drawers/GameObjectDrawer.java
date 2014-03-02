package lisp.drawers;

import java.awt.Graphics2D;

import lisp.GameObject;

public interface GameObjectDrawer<T extends GameObject> {

	public void draw(T go, Graphics2D g);
}

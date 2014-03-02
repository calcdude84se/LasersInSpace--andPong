package lisp;

import java.awt.Graphics2D;

import lisp.drawers.GameObjectDrawer;

public abstract class GameObjectABC<T extends GameObjectABC> implements GameObject {

	protected GameObjectDrawer<T> drawer;
	
	@Override
	public void draw(Graphics2D g) {
		drawer.draw((T)this, g);
	}
}

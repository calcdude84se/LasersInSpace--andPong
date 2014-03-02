package lisp;

import java.awt.Graphics2D;

public abstract class GameObjectABC implements GameObject {

	protected Drawable drawer;
	
	@Override
	public void draw(Graphics2D g) {
		drawer.draw(g);
	}
}

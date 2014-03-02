package lisp.drawers;

import java.awt.Graphics2D;

import lisp.GameObject;

public class ComboGameObjectDrawer<T extends GameObject> implements GameObjectDrawer<T> {
	
	private GameObjectDrawer<T>[] drawers;
	
	public ComboGameObjectDrawer(GameObjectDrawer<T> ... drawers) {
		this.drawers = drawers;
	}

	@Override
	public void draw(T go, Graphics2D g) {
		for(GameObjectDrawer<T> drawer : drawers)
			drawer.draw(go, g);
	}

}

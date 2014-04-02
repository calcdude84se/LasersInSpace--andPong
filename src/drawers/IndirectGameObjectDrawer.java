package drawers;

import java.awt.Graphics2D;

import lisp.GameObject;

public class IndirectGameObjectDrawer<T extends GameObject> implements GameObjectDrawer<T> {

	private GameObjectDrawer<T> drawer;
	
	public IndirectGameObjectDrawer(GameObjectDrawer<T> drawer) {
		this.drawer = drawer;
	}

	public GameObjectDrawer<T> getDrawer() {
		return drawer;
	}

	public void setDrawer(GameObjectDrawer<T> drawer) {
		this.drawer = drawer;
	}

	@Override
	public void draw(T go, Graphics2D g) {
		drawer.draw(go, g);
	}
}

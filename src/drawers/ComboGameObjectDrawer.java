package drawers;

import java.awt.Graphics2D;

import lisp.GameObject;
import lisp.RailShip;

public class ComboGameObjectDrawer<T extends GameObject> implements GameObjectDrawer<T> {
	
	public GameObjectDrawer<T>[] drawers;
	
	public ComboGameObjectDrawer(GameObjectDrawer<T> ... drawers) {
		this.drawers = drawers;
	}

	@Override
	public void draw(T go, Graphics2D g) {
		for(GameObjectDrawer<T> drawer : drawers)
			drawer.draw(go, g);
	}
	
	public GameObjectDrawer<T> getDrawer(int drawerIndex) {
		return drawers[drawerIndex];
	}

}

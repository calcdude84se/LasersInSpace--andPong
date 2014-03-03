package lisp.drawers;

import lisp.AsteroidImp;

public class AsteroidImageDrawer extends GenericImageDrawer<AsteroidImp> {
	
	private static final String[] paths = new String[] {"/images/asteroid0.png", "/images/asteroid1.png"};
	
	public AsteroidImageDrawer() {
		init();
	}
	
	@Override
	protected String[] getPaths() {
		return paths;
	}
}
package lisp.drawers;

import lisp.AsteroidImp;

public class AsteroidUnicornDrawer extends GenericImageDrawer<AsteroidImp> {
	
	private static final String[] paths = new String[] {"/images/puffball0.png", "/images/puffball1.png"};

	@Override
	protected String[] getPaths() {
		return paths;
	}
	
	public AsteroidUnicornDrawer() {
		init();
	}

}

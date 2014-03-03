package lisp.drawers;

import lisp.powerups.Unicorn;

public class UnicornImageDrawer extends GenericImageDrawer<Unicorn> {
	
	private static final String[] paths = new String[] {"/images/puffball0.png", "/images/puffball1.png"};

	@Override
	protected String[] getPaths() {
		return paths;
	}
	
	public UnicornImageDrawer() {
		init();
	}

}

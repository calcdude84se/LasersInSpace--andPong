package lisp.drawers;

import lisp.AsteroidImp;
import lisp.Star;

public class StarImageDrawer extends GenericImageDrawer<Star> {

	private static final String[] paths = new String[] {"images/red obj.png", "images/yellow obj.png","images/blue obj.png"};
	
	public StarImageDrawer() {
		init();
	}
	
	@Override
	protected String[] getPaths() {
		return paths;
	}
	
}

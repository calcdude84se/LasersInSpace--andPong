package lisp.drawers;

import lisp.RailShip;

public class RailShipBodyUnicornDrawer extends GenericImageDrawer<RailShip> {
	
	private static final String[] paths = new String[] {"images/unicorn0.png", "images/unicorn1.png"};

	@Override
	protected String[] getPaths() {
		return paths;
	}
	
	public RailShipBodyUnicornDrawer() {
		init();
	}

}

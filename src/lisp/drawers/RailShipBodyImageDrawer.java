package lisp.drawers;

import lisp.RailShip;


public class RailShipBodyImageDrawer extends GenericImageDrawer<RailShip>{
	private static final String[] paths = {"/images/ship0.png", "/images/ship1.png"};
	
	public RailShipBodyImageDrawer(){
		init();
	}
	
	@Override
	protected String[] getPaths() {
		// TODO Auto-generated method stub
		return paths;
	}

}

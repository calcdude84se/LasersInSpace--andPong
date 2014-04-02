package drawers;

import lisp.RailShip;


public class RailShipBodyImageDrawer extends GenericImageDrawer<RailShip>{
	public String[] paths = {"images/spaceship1.png","images/spaceship2.png"};
	
	public RailShipBodyImageDrawer(){
		init();
	}
	
	@Override
	protected String[] getPaths() {
		// TODO Auto-generated method stub
		return paths;
	}
	
	public void setPaths(int index, String location){
		paths[index]=location;
		images[index]=lisp.Utilities.loadImage(paths[index]);
	}

	
	

}

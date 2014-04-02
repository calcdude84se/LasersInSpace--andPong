package drawers;

import lisp.Astronaut;

public class AstronautImageDrawer extends GenericImageDrawer<Astronaut>{

	//private static final String[] paths = new String[] {"images/asteroid0.png", "images/asteroid1.png"};
	
	public String[] paths;
	
	public AstronautImageDrawer(){
		paths = new String[] {"images/astronaut.png"};
		init();
	}
	
	@Override
	protected String[] getPaths() {
		return paths;
	}
	
	public void setPaths(int index, String location){
		paths[index]=location;
		images[0]=lisp.Utilities.loadImage(paths[0]);
	}

}

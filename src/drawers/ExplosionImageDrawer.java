package lisp.drawers;

import lisp.Explosion;

public class ExplosionImageDrawer extends GenericImageDrawer<Explosion>{

	public String[] paths = {"images/explosion.png"};

	public ExplosionImageDrawer(){
		init();
	}
	
	@Override
	protected String[] getPaths() {
		return paths;
	}
	
}

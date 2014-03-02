package lisp.powerups;

import lisp.AsteroidImp;
import lisp.GameRoom;
import lisp.PowerupField;
import lisp.RailShip;
import lisp.drawers.GameObjectDrawer;
import lisp.drawers.IndirectGameObjectDrawer;

public class Unicorn extends Powerup<Unicorn> {
	
	private int life = 200;
	private GameObjectDrawer<AsteroidImp> oldAsteroidDrawer;
	private GameObjectDrawer<RailShip> oldShipBodyDrawer;
	private IndirectGameObjectDrawer<AsteroidImp> asteroidDrawer;
	private IndirectGameObjectDrawer<RailShip> shipBodyDrawer;

	public Unicorn(GameRoom room, PowerupField puField, double x, double y, double xv,
			double yv, double r) {
		super(puField, x, y, xv, yv, r);
		asteroidDrawer = (IndirectGameObjectDrawer<AsteroidImp>)room.getAsteroidDrawer();
		shipBodyDrawer = (IndirectGameObjectDrawer<RailShip>)room.getShipDrawer();
		drawer = new UnicornImageDrawer();
	}

	@Override
	protected void activate() {
		oldAsteroidDrawer = asteroidDrawer.getDrawer();
		asteroidDrawer.setDrawer(new AsteroidUnicornDrawer());

		oldShipBodyDrawer = shipBodyDrawer.getDrawer();
		shipBodyDrawer.setDrawer(new ShipUnicornDrawer());
	}

	@Override
	protected void activatedStep() {
		if(--life < 0) {
			asteroidDrawer.setDrawer(oldAsteroidDrawer);
			shipBodyDrawer.setDrawer(oldShipBodyDrawer);
			destroy();
		}
	}

}

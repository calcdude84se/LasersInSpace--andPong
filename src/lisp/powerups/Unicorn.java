package lisp.powerups;

import java.awt.geom.Rectangle2D.Double;

import lisp.AsteroidImp;
import lisp.GameRoom;
import lisp.PowerupField;
import lisp.RailShip;
import lisp.WithId;
import lisp.drawers.AsteroidUnicornDrawer;
import lisp.drawers.GameObjectDrawer;
import lisp.drawers.IndirectGameObjectDrawer;
import lisp.drawers.RailShipBodyUnicornDrawer;
import lisp.drawers.UnicornImageDrawer;

public class Unicorn extends Powerup<Unicorn> implements WithId {
	
	private int life = 200;
	private GameObjectDrawer<AsteroidImp> oldAsteroidDrawer;
	private GameObjectDrawer<RailShip> oldShipBodyDrawer;
	private IndirectGameObjectDrawer<AsteroidImp> asteroidDrawer;
	private IndirectGameObjectDrawer<RailShip> shipBodyDrawer;
	public int id;

	public Unicorn(GameRoom room, PowerupField puField, double x, double y, double xv,
			double yv, double r, int id) {
		super(puField, x, y, xv, yv, r);
		asteroidDrawer = (IndirectGameObjectDrawer<AsteroidImp>)room.getAsteroidDrawer();
		shipBodyDrawer = (IndirectGameObjectDrawer<RailShip>)room.getShipBodyDrawer();
		drawer = new UnicornImageDrawer();
		this.id = id;
	}

	@Override
	protected void activate() {
		oldAsteroidDrawer = asteroidDrawer.getDrawer();
		asteroidDrawer.setDrawer(new AsteroidUnicornDrawer());

		oldShipBodyDrawer = shipBodyDrawer.getDrawer();
		shipBodyDrawer.setDrawer(new RailShipBodyUnicornDrawer());
	}

	@Override
	protected void activatedStep() {
		if(--life < 0) {
			asteroidDrawer.setDrawer(oldAsteroidDrawer);
			shipBodyDrawer.setDrawer(oldShipBodyDrawer);
			destroy();
		}
	}

	@Override
	public Double getPosition() {
		return new Double(x, y, 2*r, 2*r);
	}

	@Override
	public int getId() {
		return id;
	}

}

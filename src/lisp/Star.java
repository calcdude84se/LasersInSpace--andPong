package lisp;

import java.awt.geom.Rectangle2D.Double;

public class Star extends GameObjectABC<Star> implements WithId, WithPosition{

	private GameRoom room;
	private StarField field;
	private int x;
	private int y;
	private final static int WIDTH = 16;
	private final static int HEIGHT = 16;
	private int id;
	
	public Star(GameRoom room, StarField field, int initX, int initY, int img){
		this.room = room;
		this.field = field;
		x = initX;
		y = initY;
		id = img;
		drawer = room.getStarDrawer();
	}
	
	@Override
	public void step() {
		x--;
	}

	@Override
	public void destroy() {
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Double getPosition() {
		return new Double(x,y,WIDTH,HEIGHT);
	}

}

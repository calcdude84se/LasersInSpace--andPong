package lisp;

import java.awt.Font;

import javax.swing.JPanel;

import drawers.ScoreBoardDrawer;

public class ScoreBoard extends GameObjectInRoom {
	
	public static int INDENT = 200;
	public int height;
	public int width;
	public RailShip shipLeft;
	public RailShip shipRight;

	public ScoreBoard(GameRoom room){
		super(room);
		JPanel panel = room.getPanel();
		this.width = panel.getWidth();
		this.height = 60;
		
		this.shipLeft = room.getShipLeft();
		this.shipRight = room.getShipRight();
		
		drawer = new ScoreBoardDrawer();
	}

	@Override
	public void step() {
		//Don't step this.
	}
	
		
}

package lisp.drawers;

import java.awt.Color;
import java.awt.Graphics2D;

import lisp.RailShip;
import lisp.ScoreBoard;

public class ScoreBoardDrawer implements GameObjectDrawer<ScoreBoard> {

	@Override
	public void draw(ScoreBoard scoreBoard, Graphics2D g) {
		g.setColor(Color.white);
		g.drawRect(0, 0, scoreBoard.width, scoreBoard.height);
		drawLives(scoreBoard, g);
		drawHealth(scoreBoard, scoreBoard.shipLeft, 0, g);
		drawHealth(scoreBoard, scoreBoard.shipRight, scoreBoard.width/2, g);
	}
	
	private void drawLives(ScoreBoard scoreBoard, Graphics2D g) {
		String left = Integer.toString(scoreBoard.shipLeft.getLives());
		String right = Integer.toString(scoreBoard.shipRight.getLives());
		scoreBoard.room.drawString(g, left, scoreBoard.width/2-ScoreBoard.INDENT, scoreBoard.height/2 - 20);
		scoreBoard.room.drawString(g, right, scoreBoard.width/2+ScoreBoard.INDENT, scoreBoard.height/2 - 20);
		
	
	}
	private void drawHealth(ScoreBoard s, RailShip ship, double x, Graphics2D g){
		g.setColor(Color.GREEN);
		g.drawRect((int)x, s.height-14, (int)ship.getHealth()*s.width/40, 10);
	}
}
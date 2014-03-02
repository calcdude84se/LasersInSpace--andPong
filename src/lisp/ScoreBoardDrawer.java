package lisp;

import java.awt.Color;
import java.awt.Graphics2D;

public class ScoreBoardDrawer implements GameObjectDrawer<ScoreBoard> {

	@Override
	public void draw(ScoreBoard scoreBoard, Graphics2D g) {
		g.setColor(Color.white);
		g.drawRect(0, 0, scoreBoard.width, scoreBoard.height);
		drawScore(scoreBoard, g);
	}
	
	private void drawScore(ScoreBoard scoreBoard, Graphics2D g) {
		String left = scoreBoard.shipLeft + "";
		String right = scoreBoard.shipRight + "";
		scoreBoard.room.drawString(g, left, scoreBoard.width/2-ScoreBoard.INDENT, scoreBoard.height/2 - 20);
		scoreBoard.room.drawString(g, right, scoreBoard.width/2+ScoreBoard.INDENT, scoreBoard.height/2 - 20);
	}
}
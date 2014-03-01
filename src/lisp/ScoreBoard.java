package lisp;

import java.awt.Graphics2D;

public class ScoreBoard implements GameObject {
	
	private static int INDENT;
	
	public ScoreBoard(GameRoom room){
		//TODO constructor
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(0, 0, width, height);
		drawScore(g, p1Score);
		drawScore(g, p2Score);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawScore(Graphics2D g, Score score) {
		g.fillRect(INDENT, INDENT, width/2-INDENT, height/2-INDENT);
	}
	
}

package lisp;

import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ScoreBoard implements GameObject {
	
	private static int INDENT = 50;
	private int height;
	private int width;
	private int shipLeft;
	private int shipRight;

	public ScoreBoard(GameRoom room){
		JPanel panel = room.getPanel();
		this.width = panel.getWidth();
		this.height = 60;
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(0, 0, width, height);
		drawScore(g);
	}

	@Override
	public void step() {
		//Don't step this.
	}
	
	private void drawScore(Graphics2D g) {
		String left = shipLeft + "";
		String right = shipRight + "";
		g.drawString(left, width/2-INDENT, width/2+INDENT);
		g.drawString(right, width/2-INDENT, width/2+INDENT);
	}
	public void setScore(int bit){
		if (bit == 0) shipLeft += 1;
		else shipRight += 1;
	}
	
}

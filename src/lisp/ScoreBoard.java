package lisp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ScoreBoard extends GameObjectInRoom {
	
	private static int INDENT = 200;
	private int height;
	private int width;
	private int shipLeft;
	private int shipRight;

	public ScoreBoard(GameRoom room){
		super(room);
		JPanel panel = room.getPanel();
		this.width = panel.getWidth();
		this.height = 60;
		
		drawer = new ScoreBoardDrawer();
	}

	@Override
	public void step() {
		//Don't step this.
	}
	
	public void setScore(int bit){
		if (bit == 0) shipLeft += 1;
		else shipRight += 1;
	}
	
	public class ScoreBoardDrawer implements Drawable {


		@Override
		public void draw(Graphics2D g) {
			g.setColor(Color.white);
			g.drawRect(0, 0, width, height);
			drawScore(g);
		}
		
		private void drawScore(Graphics2D g) {
			String left = shipLeft + "";
			String right = shipRight + "";
			room.drawString(g, left, width/2-INDENT, height/2 - 20);
			room.drawString(g, right, width/2+INDENT, height/2 - 20);
		}
	}
	
}

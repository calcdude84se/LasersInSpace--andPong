package lisp;

import java.awt.Font;

import javax.swing.JPanel;

public class ScoreBoard extends GameObjectInRoom {
	
	static int INDENT = 200;
	int height;
	int width;
	int shipLeft;
	int shipRight;

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
	
}

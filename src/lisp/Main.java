package lisp;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public abstract class Main {

	public static void main(String[] args) {
		JFrame gameFrame = new JFrame("Lasers In Space! -- and Pong");
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GameRoom gameRoom = new GameRoom();
		gameFrame.add(gameRoom.getPanel());
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameRoom.getPanel().requestFocusInWindow();
		gameRoom.startGame();
	}

}

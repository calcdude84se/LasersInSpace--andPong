package lisp;

import javax.swing.JApplet;
import javax.swing.JPanel;

public class Main extends JApplet {
	
	GameRoom gameRoom;

	@Override
	public void init() {
		gameRoom = new GameRoom();
		JPanel gamePanel = gameRoom.getPanel();
		gamePanel.requestFocusInWindow();
		setContentPane(gamePanel);
	}
	
	@Override
	public void start() {
		(new Thread(new Runnable() {
			
			@Override
			public void run() {
				gameRoom.startGame();
			}
		})).start();
	}

}

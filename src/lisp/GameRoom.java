package lisp;

import javax.swing.JPanel;

public class GameRoom {
	
	private JPanel panel;
	private int stepSize;
	
	public GameRoom()
	{
		//TODO initialize this GameRoom
		stepSize = 10;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void startGame() {
		init();
		//Run the event loop
		while(!isGameOver())
		{
			//Process all game events, wait one step, and continue
			processEvents();
			Thread.sleep(stepSize);
		}
		deinit();
	}

}

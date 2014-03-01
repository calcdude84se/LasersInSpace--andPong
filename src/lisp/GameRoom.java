package lisp;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

public class GameRoom {
	
	private JPanel panel;
	/**
	 * Step size in milliseconds
	 */
	private int stepSize;
	private RailShip shipLeft, shipRight;
	private AScoreBoard scoreBoard;
	private Collection<Asteroid> asteroids = new ArrayList<>();
	
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

	/**
	 * Initializes for one game.
	 */
	private void init() {
		//TODO write method
		final int shipY = panel.getHeight() / 2;
		final int shipLeftX = 0, shipLeftY = shipY,
				shipRightX = panel.getWidth() - RailShip.SHIP_WIDTH,
				shipRightY = shipY;
		shipLeft = new RailShip(shipLeftX, shipLeftY, true, this);
		shipRight = new RailShip(shipRightX, shipRightY, false, this);
		
	}

}

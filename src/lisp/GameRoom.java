package lisp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JPanel;

public class GameRoom {
	
	private JPanel panel;
	/**
	 * Step size in milliseconds
	 */
	private int stepSize;
	private RailShip shipLeft, shipRight;
	private ScoreBoard scoreBoard;
	private AsteroidField asteroidField;
	private Collection<GameObject> gameObjects = new ArrayList<>();
	
	public GameRoom()
	{
		stepSize = 10;
		final int panelWidth = 640, panelHeight = 480;
		panel = new GamePanel();
		panel.setSize(panelWidth, panelHeight);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void startGame() {
		init();
		//Run the event loop
		while(!isGameOver())
		{
			//Process all game events, step all objects, wait one step, and continue
			processEvents();
			for(GameObject go : gameObjects)
				go.step();
			Thread.sleep(stepSize);
		}
		deinit();
	}

	/**
	 * Initializes for one game.
	 */
	private void init() {
		//TODO write method
		gameObjects.clear();
		final int shipY = panel.getHeight() / 2;
		final int shipLeftX = 0, shipLeftY = shipY,
				shipRightX = panel.getWidth() - RailShip.SHIP_WIDTH,
				shipRightY = shipY;
		shipLeft = new RailShip(shipLeftX, shipLeftY, true, this);
		shipRight = new RailShip(shipRightX, shipRightY, false, this);
		scoreBoard = new ScoreBoard(this);
		asteroidField = new AsteroidField(this);
		gameObjects.add(shipLeft);
		gameObjects.add(shipRight);
		gameObjects.add(scoreBoard);
		gameObjects.add(asteroidField);
	}
	
	public void addObject(GameObject go)
	{
		gameObjects.add(go);
	}
	
	public void removeObject(GameObject go)
	{
		gameObjects.remove(go);
	}
	
	private class GamePanel extends JPanel {
		@Override
		public void paintComponents(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponents(g);
			Graphics2D g2d = (Graphics2D)g;
			for(GameObject go : gameObjects)
				go.draw(g2d);
		}
	}

}

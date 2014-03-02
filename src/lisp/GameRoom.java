package lisp;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

public class GameRoom implements Drawable {
	
	private KeyPanel panel;
	/**
	 * Step size in milliseconds
	 */
	private int stepSize;
	private RailShip shipLeft, shipRight;
	private ScoreBoard scoreBoard;
	private AsteroidField asteroidField;
	private Collection<GameObject> gameObjects = new ArrayList<>();
	private boolean isGameOver;
	
	public GameRoom()
	{
		stepSize = 10;
		final int panelWidth = 640, panelHeight = 480;
		panel = new KeyPanel(this);
		panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
	}

	public JPanel getPanel() {
		return panel;
	}

	public void startGame() {
		init();
		//Run the event loop
		while(!isGameOver)
		{
			//Process all game events, step all objects, wait one step, and continue
			processEvents();
			panel.repaint();
			for(GameObject go : getGameObjectsClone())
				go.step();
			try {
				Thread.sleep(stepSize);
			} catch (InterruptedException e) {
				System.out.println("Sleep was interrupted! Oh noes!");
				e.printStackTrace();
			}
		}
		deinit();
	}
	
	/**
	 * Initializes for one game.
	 */
	private void init() {
		panel.resetPressedKeys();
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
	
	private void processEvents() {
		for (int keyCode : panel.receivePressedKeyCodes()) {
			if(keyCode == shipLeftUp)
				shipLeft.accUp();
			else if(keyCode == shipLeftLaser)
				shipLeft.fireLaser();
			else if(keyCode == shipLeftDown)
				shipLeft.accDown();
			else if(keyCode == shipRightUp)
				shipRight.accUp();
			else if(keyCode == shipRightLaser)
				shipRight.fireLaser();
			else if(keyCode == shipRightDown)
				shipRight.accDown();
		}
	}
	
	/**
	 * Gets run after the game has ended
	 */
	private void deinit() {
		//TODO do something
	}
	
	public void addObject(GameObject go)
	{
		gameObjects.add(go);
	}
	
	public void removeObject(GameObject go)
	{
		gameObjects.remove(go);
	}
	
	public AsteroidField getAsteroidField() {
		return asteroidField;
	}
	
	public RailShip getShipLeft() {
		return shipLeft;
	}
	
	public RailShip getShipRight() {
		return shipRight;
	}
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
	
	Collection<GameObject> getGameObjectsClone() {
		return (Collection<GameObject>)((ArrayList)gameObjects).clone();
	}
	
	public void draw(Graphics2D g) {
		for(GameObject go : getGameObjectsClone())
			go.draw(g);
	}
	
	private final int shipLeftUp = KeyEvent.VK_Q, shipLeftLaser = KeyEvent.VK_A,
			shipLeftDown = KeyEvent.VK_Z, shipRightUp = KeyEvent.VK_CLOSE_BRACKET,
			shipRightLaser = KeyEvent.VK_QUOTE, shipRightDown = KeyEvent.VK_SLASH;

}

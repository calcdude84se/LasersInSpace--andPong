package lisp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	private Set<Integer> keysPressed = new HashSet<>();
	private Set<Integer> keysReleased = new HashSet<>();
	private boolean isGameOver;
	
	public GameRoom()
	{
		stepSize = 10;
		final int panelWidth = 640, panelHeight = 480;
		panel = new GamePanel();
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
			for(GameObject go : (Collection<GameObject>)((ArrayList)gameObjects).clone())
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
		keysPressed.clear();
		keysReleased.clear();
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
		synchronized (keysPressed) {
			synchronized (keysReleased) {
				for (int keyCode : keysPressed) {
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
					keysReleased.remove(keyCode);
				}
			}
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
	
	private final int shipLeftUp = KeyEvent.VK_Q, shipLeftLaser = KeyEvent.VK_A,
			shipLeftDown = KeyEvent.VK_Z, shipRightUp = KeyEvent.VK_CLOSE_BRACKET,
			shipRightLaser = KeyEvent.VK_QUOTE, shipRightDown = KeyEvent.VK_SLASH;
	
	private class GamePanel extends JPanel {
		
		public GamePanel() {
			addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					//Does nothing
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					synchronized (keysPressed) {
						synchronized (keysReleased) {
							keysReleased.add(arg0.getKeyCode());
						}
					}
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					int keyCode = arg0.getKeyCode();
					synchronized (keysPressed) {
						synchronized (keysReleased) {
							keysPressed.add(keyCode);
							keysReleased.remove(keyCode);
						}
					}
				}
			});
		}
		
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;
			for(GameObject go : gameObjects)
				go.draw(g2d);
		}
	}

}

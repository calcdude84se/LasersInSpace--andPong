package lisp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import lisp.drawers.AsteroidImageDrawer;
import lisp.drawers.ComboGameObjectDrawer;
import lisp.drawers.GameObjectDrawer;
import lisp.drawers.IndirectGameObjectDrawer;
import lisp.drawers.RailShipBodyGeoDrawer;
import lisp.drawers.RailShipBodyImageDrawer;
import lisp.drawers.RailShipFlareGeoDrawer;

public class GameRoom implements Drawable {
	
	//Gametype flags changeable in menu
	private boolean singlePlayer = false;
	
	
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
	private boolean done;
	
	private int fontSize = 30;
	private Font text = new Font("Helvetica", Font.PLAIN, fontSize);
	private Color textColor = Color.white;
	
	private GameObjectDrawer<AsteroidImp> asteroidDrawer;
	private GameObjectDrawer<RailShip> shipBodyDrawer, shipDrawer;
	
	private MenuState menuState;
	
	public GameRoom()
	{
		stepSize = 10;
		final int panelWidth = 640, panelHeight = 480;
		panel = new KeyPanel(this);
		panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		panel.setBackground(Color.black);
		menuState = new MenuState();
	}

	public JPanel getPanel() {
		return panel;
	}

	public void startGame() {
		isGameOver = true;
		done = false;
		//Run the event loop
		while(!done)
		{
			if(!isGameOver) {
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
			} else {
				menuState.processKey(panel.getKey());
				panel.repaint();
			}
		}
		deinit();
	}
	
	private static enum Menu {
		main, options
	}
	
	private class MenuState {
		
		private Menu menu = Menu.main;
		
		/**
		 * Process a key in menu mode
		 * @param key the key to be processed.
		 */
		private void processKey(char key) {
			switch(menu) {
			case main:
				main(key);
			}
		}
		
		private void main(char key) {
			if(key == startGame) {
				isGameOver = false;
				init();
			} else if(key == exit) {
				done = true;
			} else if(key == toggleSinglePlayer){
				singlePlayer = !singlePlayer;
			}
		}
	}

	/**
	 * Initializes for one game.
	 */
	private void init() {
		asteroidDrawer = new IndirectGameObjectDrawer<>(new AsteroidImageDrawer());
		shipBodyDrawer = new IndirectGameObjectDrawer<>(new RailShipBodyImageDrawer());
		shipDrawer = new ComboGameObjectDrawer<>(shipBodyDrawer, new RailShipFlareGeoDrawer());
		panel.resetKeys();
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
		if(singlePlayer){
			gameObjects.add(new ArtificialAirquoteIntelligence(shipRight, shipLeft, false, this));
		}
	}
	
	private void processEvents() {
		for (int keyCode : panel.receivePressedKeyCodes()) {
			if(keyCode == shipLeftUp)
				shipLeft.accUp();
			else if(keyCode == shipLeftLaser)
				shipLeft.fireLaser();
			else if(keyCode == shipLeftMissile)
				shipLeft.fireMissile();
			else if(keyCode == shipLeftDown)
				shipLeft.accDown();
			else if(keyCode == shipRightUp)
				shipRight.accUp();
			else if(keyCode == shipRightLaser)
				shipRight.fireLaser();
			else if(keyCode == shipRightMissile)
				shipRight.fireMissile();
			else if(keyCode == shipRightDown)
				shipRight.accDown();
			else if(keyCode == exitGame)
				gameOver();
		}
	}
	
	public void gameOver() {
		isGameOver = true;
		panel.resetKeys();
		panel.repaint();
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
	
	public Font getText() {
		return text;
	}
	
	public GameObjectDrawer<AsteroidImp> getAsteroidDrawer() {
		return asteroidDrawer;
	}
	
	public GameObjectDrawer<RailShip> getShipDrawer() {
		return shipDrawer;
	}

	private Collection<GameObject> getGameObjectsClone() {
		return CollectionUtilities.cloneArrayList(gameObjects);
	}
	
	public void draw(Graphics2D g) {
		if(!isGameOver)
			for(GameObject go : getGameObjectsClone())
				go.draw(g);
		else {
			drawString(g, "Lasers In Space! -- and Pong\nPress 's' to start,  'e' to exit\n'p' to toggle single-player\n"+(singlePlayer?"Single-player on!":"Single-player off!"), 0, 0);
		}
			
	}
	
	public void drawString(Graphics2D g, String string, int x, int y) {
		g.setColor(textColor);
		g.setFont(text);
		String[] lines = string.split("\n");
		for(int i = 0; i < lines.length; i++)
			g.drawString(lines[i], x, y + (i + 1) * fontSize);
	}
	
	private final int shipLeftUp = KeyEvent.VK_Q, shipLeftLaser = KeyEvent.VK_A,
			shipLeftMissile = KeyEvent.VK_S, shipLeftDown = KeyEvent.VK_Z,
			shipRightUp = KeyEvent.VK_CLOSE_BRACKET, shipRightLaser = KeyEvent.VK_QUOTE,
			shipRightMissile = KeyEvent.VK_SEMICOLON, shipRightDown = KeyEvent.VK_SLASH,
			exitGame = KeyEvent.VK_E;
	private final char startGame = 's', exit = 'e', toggleSinglePlayer = 'p';

}

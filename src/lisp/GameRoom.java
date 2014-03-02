package lisp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import lisp.drawers.AsteroidGeoDrawer;
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
	private boolean retro = false;
	
	
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
	
	private BufferedImage background;
	{
		try {
			background = ImageIO.read(new File("images/space.png"));
		} catch (IOException e) {
			
		}
	}
	
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
		main, options, controls, gameOver
	}
	
	private class MenuState implements Drawable {
		
		private Menu menu = Menu.main;
		
		/**
		 * Process a key in menu mode
		 * @param key the key to be processed.
		 */
		private void processKey(char key) {
			switch(menu) {
			case main:
				main(key);
				break;
			case options:
				options(key);
				break;
			case controls:
				controls(key);
				break;
			case gameOver:
				gameOver(key);
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
			} else if(key == options) {
				menu = Menu.options;
			} else if(key == controls) {
				menu = Menu.controls;
			}
		}
		
		private void options(char key) {
			if(key == exit)
				menu = Menu.main;
			else if(key == toggleRetro)
				retro ^= true;
		}
		
		private void controls(char key) {
			if(key == exit)
				menu = Menu.main;
		}
		
		private void gameOver(char key) {
			menu = Menu.main;
		}

		private final char startGame = 's', exit = 'e', toggleSinglePlayer = 'p', options = 'o', controls = 'c',
				toggleRetro = 'r';

		@Override
		public void draw(Graphics2D g) {
			switch(menu) {
			case main:
				drawString(g, "Lasers In Space! -- and Pong\nPress '"+startGame+"' to start,  '"+exit+"' to exit\n'"+toggleSinglePlayer+"' to toggle single-player\n'"+options+"' for options\n'"+controls+"' for controls\n"+"Single-player "+booleanToString(singlePlayer)+"!", 0, 0);
				break;
			case options:
				drawString(g, "Options\nPress '"+toggleRetro+"' to toggle retro mode: "+booleanToString(retro)+"\nPress '"+exit+"' to return to the main menu", 0, 0);
				break;
			case controls:
				drawString(g,"Controls\nPlayer 1:\n's': shoot missile\n'a': shoot laser\n'q': go up\n'z': go down\nPlayer 2:\n':':shoot missile\n'\"': shoot laser\n'}':go up\n'/': go down\n'"+exit+"' to return to the main menu",0,0);
				break;
			case gameOver:
				drawString(g, "Game over!\nPress any key to return to main menu.", 0, 0);
			}
		}
	}
	
	private static String booleanToString(boolean arg) {
		return arg ? "on" : "off";
	}

	/**
	 * Initializes for one game.
	 */
	private void init() {
		asteroidDrawer = new IndirectGameObjectDrawer<>(retro ? new AsteroidGeoDrawer() : new AsteroidImageDrawer());
		shipBodyDrawer = new IndirectGameObjectDrawer<>(retro ? new RailShipBodyGeoDrawer() : new RailShipBodyImageDrawer());
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
		menuState.menu = Menu.gameOver;
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
		if(!isGameOver){
			g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
			for(GameObject go : getGameObjectsClone())
				go.draw(g);
		}
		else 
			menuState.draw(g);
			
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

}

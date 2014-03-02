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
	private int difficulty = 0;
	
	public static final String NAME = "Laser Space Pong -- In Spaaaace";
	
	private KeyPanel panel;
	/**
	 * Step size in milliseconds
	 */
	private int stepSize;
	private RailShip shipLeft, shipRight;
	private ScoreBoard scoreBoard;
	private AsteroidField asteroidField;
	private PowerupField powerupField;
	private Collection<GameObject> gameObjects = new ArrayList<>();
	private boolean isGameOver;
	private boolean done;
	
	private int fontSize = 30;
	private Font text = new Font("Helvetica", Font.PLAIN, fontSize);
	private Color textColor = Color.white;
	
	private GameObjectDrawer<AsteroidImp> asteroidDrawer;
	private GameObjectDrawer<RailShip> shipBodyDrawer, shipDrawer;
	private static int BUFFER = 10;
	private MenuState menuState;
	
	private BufferedImage background = Utilities.loadImage("images/space.png");
	private BufferedImage logo = Utilities.loadImage("images/title.png");
	private BufferedImage s = Utilities.loadImage("images/s.png");
	private BufferedImage o = Utilities.loadImage("images/o.png");
	private BufferedImage p = Utilities.loadImage("images/p.png");
	private BufferedImage e = Utilities.loadImage("images/e.png");
	private BufferedImage c = Utilities.loadImage("images/c.png");
	private BufferedImage keys = Utilities.loadImage("images/keys.png");
	public BufferedImage on = Utilities.loadImage("images/on.png");
	public BufferedImage off = Utilities.loadImage("images/off.png");	
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
			} else if (key == increaseDiff){
				difficulty++;
			} else if (key == decreaseDiff){
				difficulty--;
			}
		}
		
		private void options(char key) {
			if(key == exit)
				menu = Menu.main;
			else if(key == toggleRetro)
				retro ^= true;
			else if (key == increaseDiff){
				if (difficulty < 9)difficulty++;
			} else if (key == decreaseDiff){
				if (difficulty > 0)difficulty--;
			}
		}
		
		private void controls(char key) {
			if(key == exit)
				menu = Menu.main;
		}
		
		private void gameOver(char key) {
			menu = Menu.main;
		}

		private final char startGame = 's', exit = 'e', toggleSinglePlayer = 'p', options = 'o', controls = 'c',
				toggleRetro = 'r', increaseDiff = '+', decreaseDiff = '-';

		@Override
		public void draw(Graphics2D g) {
			switch(menu) {
			case main:
				double scale = 1/1.8;
				//drawString(g, "Press '"+startGame+"' to start,  '"+exit+"' to exit\n'"+toggleSinglePlayer+"' to toggle single-player\n'"+options+"' for options\n'"+controls+"' for controls\n"+"Single-player "+booleanToString(singlePlayer)+"!", 0, logo.getHeight());
				g.drawImage(logo, panel.getWidth()/2-logo.getWidth()/2, 0, logo.getWidth(), logo.getHeight(), null);
				g.drawImage(s, BUFFER, logo.getHeight(), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(e, BUFFER, (int)(logo.getHeight()+s.getHeight()*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(o, BUFFER, (int)(logo.getHeight()+s.getHeight()*2*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(p, BUFFER, (int)(logo.getHeight()+s.getHeight()*3*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(c, BUFFER, (int)(logo.getHeight()+s.getHeight()*4*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(keys, BUFFER + s.getWidth(), logo.getHeight(), keys.getWidth(), keys.getHeight(), null);
				if (!singlePlayer) {
					g.drawImage(off, BUFFER + s.getWidth()+ keys.getWidth(), (int)(logo.getHeight()+s.getHeight()*3.25*scale), off.getWidth(), off.getHeight(), null);
				}
				else g.drawImage(on, BUFFER + s.getWidth()+ keys.getWidth(), (int)(logo.getHeight()+s.getHeight()*3.25*scale), on.getWidth(), on.getHeight(), null);
				break;
			case options:
				drawString(g, "Options\nPress '"+toggleRetro+"' to toggle retro mode: "+booleanToString(retro)+"\n'"+increaseDiff+"' to increase difficulty\n'"+decreaseDiff+"' to decrease difficulty\nDifficulty currently "+difficulty+"\nPress '"+exit+"' to return to the main menu", 0, 0);
				break;
			case controls:
				drawString(g,"Controls\nPlayer 1:\n's': shoot missile\n'a': shoot laser\n'q': go up\n'z': go down\nPlayer 2:\n':':shoot missile\n'\"': shoot laser\n'}':go up\n'/': go down\n'"+exit+"' to return to the main menu",0,0);
				break;
			case gameOver:
				if(shipLeft.getLives()>shipRight.getLives())
				drawString(g, "Game over!\nPlayer 1 has won!\nPress any key to return to main menu.", 0, 0);
				else if(shipLeft.getLives()<shipRight.getLives())
					drawString(g, "Game over!\nPlayer 2 has won!\nPress any key to return to main menu.", 0, 0);
				else if(shipLeft.getHealth()>shipRight.getHealth())
					drawString(g, "Game over!\nPlayer 1 has won!\nPress any key to return to main menu.", 0, 0);
				else if(shipLeft.getHealth()<shipRight.getHealth())
					drawString(g, "Game over!\nPlayer 2 has won!\nPress any key to return to main menu.", 0, 0);
				else
					drawString(g, "Game over!\nPlayer 2 and Player 1 have tied!\nPress any key to return to main menu.", 0, 0);
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
		powerupField = new PowerupField(this);
		gameObjects.add(shipLeft);
		gameObjects.add(shipRight);
		gameObjects.add(scoreBoard);
		gameObjects.add(asteroidField);
		gameObjects.add(powerupField);
		if(singlePlayer){
			gameObjects.add(new ArtificialAirquoteIntelligence(shipRight, shipLeft, false, difficulty, this));
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

	public void doPowerupAt(double x, double y) {
		powerupField.doPowerupAt(x, y);
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
		return Utilities.cloneArrayList(gameObjects);
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
	
	private final int shipLeftUp = KeyEvent.VK_W, shipLeftLaser = KeyEvent.VK_D,
			shipLeftMissile = KeyEvent.VK_A, shipLeftDown = KeyEvent.VK_S,
			shipRightUp = KeyEvent.VK_P, shipRightLaser = KeyEvent.VK_L,
			shipRightMissile = KeyEvent.VK_QUOTE, shipRightDown = KeyEvent.VK_SEMICOLON,
			exitGame = KeyEvent.VK_E;

}

package lisp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import drawers.AsteroidImageDrawer;
import drawers.AstronautImageDrawer;
import drawers.ComboGameObjectDrawer;
import drawers.ExplosionGeoDrawer;
import drawers.GameObjectDrawer;
import drawers.IndirectGameObjectDrawer;
import drawers.RailShipBodyImageDrawer;
import drawers.RailShipFlareGeoDrawer;
import drawers.StarImageDrawer;

public class GameRoom implements Drawable {
	
	public static final String NAME = "Laser Space Pong -- In Spaaaace";
	
	private KeyPanel panel;
	/**
	 * Step size in milliseconds
	 */
	private int stepSize;
	private RailShip shipLeft, shipRight;
	private ScoreBoard scoreBoard;
	private AsteroidField asteroidField;
	private AstronautField astronautField;
	private StarField starField;
	private Collection<GameObject> gameObjects = new ArrayList<>();
	private boolean isGameOver;
	private boolean done;
	
	private int fontSize = 30;
	private Font text = new Font("Helvetica", Font.PLAIN, fontSize);
	private Color textColor = Color.white;
	
	private GameObjectDrawer<AsteroidImp> asteroidDrawer;
	private RailShipBodyImageDrawer shipBodyDrawer;
	private AstronautImageDrawer astronautDrawer;
	private GameObjectDrawer<RailShip> shipDrawer;
	private ExplosionGeoDrawer explosionDrawer;
	private StarImageDrawer starDrawer;
	private static int BUFFER = 10;
	private MenuState menuState;
	
	private BufferedImage logo = Utilities.loadImage("images/title.png");
	private BufferedImage s = Utilities.loadImage("images/s.png");
	private BufferedImage e = Utilities.loadImage("images/e.png");
	private BufferedImage c = Utilities.loadImage("images/c.png");
	private BufferedImage keys = Utilities.loadImage("images/keys.png");
	public BufferedImage on = Utilities.loadImage("images/on.png");
	public BufferedImage off = Utilities.loadImage("images/off.png");
	public BufferedImage gameover = Utilities.loadImage("images/gameover.png");

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
			} else if(key == controls) {
				menu = Menu.controls;
			}
		}
		
		private void controls(char key) {
			if(key == exit)
				menu = Menu.main;
		}
		
		private void gameOver(char key) {
			if( key == startGame){
				isGameOver = false;
				init();
			}
		}

		private final char startGame = 's', exit = 'e', controls = 'c';

		@Override
		public void draw(Graphics2D g) {
			switch(menu) {
			case main:
				double scale = 1/1.8;
				g.drawImage(logo, panel.getWidth()/2-logo.getWidth()/2, 0, logo.getWidth(), logo.getHeight(), null);
				g.drawImage(s, BUFFER, logo.getHeight(), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(e, BUFFER, (int)(logo.getHeight()+s.getHeight()*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(c, BUFFER, (int)(logo.getHeight()+s.getHeight()*2*scale), (int)(s.getWidth()/(1.5)), (int)(s.getHeight()/1.5), null);
				g.drawImage(keys, BUFFER + s.getWidth(), logo.getHeight(), keys.getWidth(), keys.getHeight(), null);
				break;
			case controls:
				drawString(g,"Controls\n\nPlayer 1:\n'up/down arrow': move ship\n'right arrow': tilt ship\n'd': shoot laser\n\n'"+exit+"' to return to the main menu",0,0);
				break;
			case gameOver:
				g.drawImage(gameover, panel.getWidth()/2 - gameover.getWidth()/2, 0, gameover.getWidth(), gameover.getHeight(), null);
				drawString(g, astronautField.deadAstronauts+"", panel.getWidth()/2, gameover.getHeight()/2);
				drawString(g, astronautField.rescuedAstronauts+"", panel.getWidth()/2, gameover.getHeight()/2-35);
				drawString(g, astronautField.lostAstronauts+"", panel.getWidth()/2, gameover.getHeight()/2+35);

			}
		}
	}

	/**
	 * Initializes for one game.
	 */
	private void init() {
		asteroidDrawer = new IndirectGameObjectDrawer<>( new AsteroidImageDrawer());
		shipBodyDrawer = new RailShipBodyImageDrawer();
		shipDrawer = new ComboGameObjectDrawer<>(shipBodyDrawer, new RailShipFlareGeoDrawer());
		explosionDrawer = new ExplosionGeoDrawer();
		astronautDrawer = new AstronautImageDrawer();
		starDrawer = new StarImageDrawer();
		panel.resetKeys();
		gameObjects.clear();
		final int shipY = panel.getHeight() / 2;
		final int shipX = panel.getWidth()/10;
		shipLeft = new RailShip(shipY, shipX, true, this);
		scoreBoard = new ScoreBoard(this);
		asteroidField = new AsteroidField(this);
		astronautField = new AstronautField(this);
		starField = new StarField(this);
		gameObjects.add(starField);
		gameObjects.add(scoreBoard);
		gameObjects.add(asteroidField);
		gameObjects.add(astronautField);
		gameObjects.add(shipLeft);
	}
	
	private void processEvents() {
		for (int keyCode : panel.receivePressedKeyCodes()) {
			if(keyCode == shipLeftUp)
				shipLeft.accUp();
			else if(keyCode == shipLeftTilt){
				if(shipLeft.tilted==-1)
				shipLeft.setTilt();
			}
			else if(keyCode == shipLeftLaser)
				shipLeft.fireLaser();
			else if(keyCode == shipLeftDown)
				shipLeft.accDown();
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
	
	public AstronautField getAstronautField() {
		return astronautField;
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
	
	public GameObjectDrawer<Explosion> getExplosionDrawer() {
		return explosionDrawer;
	}
	
	public GameObjectDrawer<RailShip> getShipDrawer() {
		return shipDrawer;
	}
	
	public RailShipBodyImageDrawer getShipBodyDrawer() {
		return shipBodyDrawer;
	}
	
	public AstronautImageDrawer getAstronautDrawer() {
		return astronautDrawer;
	}
	
	public StarImageDrawer getStarDrawer() {
		return starDrawer;
	}

	private Collection<GameObject> getGameObjectsClone() {
		return Utilities.cloneArrayList(gameObjects);
	}
	
	public void draw(Graphics2D g) {
		if(!isGameOver){
			//g.drawImage(background, 0, 0, panel.getWidth(), panel.getHeight(), null);
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
	
	/**
	 * DOES NOT TEST THE TOP
	 * @param go position
	 * @return whether go is in bounds
	 */
	public boolean isOutOfBounds(WithPosition go) {
		Rectangle2D.Double position = go.getPosition();
		return position.x+position.getWidth() < 0 || position.x > panel.getWidth() || position.y > panel.getHeight();
	}
	
	private final int shipLeftTilt = KeyEvent.VK_RIGHT, shipLeftUp = KeyEvent.VK_UP, shipLeftLaser = KeyEvent.VK_D,
			shipLeftDown = KeyEvent.VK_DOWN, exitGame = KeyEvent.VK_E;

}

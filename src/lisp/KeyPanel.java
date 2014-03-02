package lisp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;

public class KeyPanel extends JPanel {
	
	private Drawable drawable;
	private Set<Integer> keysPressed = new HashSet<>();
	private Set<Integer> keysReleased = new HashSet<>();
	private Queue<Character> keysTyped = new ConcurrentLinkedQueue<>();

	public KeyPanel(Drawable drawable) {
		this.drawable = drawable;
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				keysTyped.offer(arg0.getKeyChar());
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
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				requestFocusInWindow();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				//Do nothing
			}
		});
	}
	
	public Set<Integer> receivePressedKeyCodes() {
		synchronized (keysPressed) {
			synchronized (keysReleased) {
				keysPressed.removeAll(keysReleased);
				keysReleased.clear();
				return keysPressed;
			}
		}
	}
	
	public char getKey() {
		return keysTyped.remove();
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		drawable.draw((Graphics2D)g);
	}

	public void resetKeys() {
		keysTyped.clear();
		keysPressed.clear();
		keysReleased.clear();
	}
}
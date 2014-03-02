package lisp;

import java.awt.Graphics2D;

public interface GameObject {
	void draw(Graphics2D g);
	void step();
}

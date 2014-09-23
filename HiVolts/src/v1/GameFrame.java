package v1;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GameFrame extends JComponent {

	private static final long serialVersionUID = 1L;
	public static final int ROWS = 12;
	public static final int COLS = 12;
	// public static Cell[][] cell = new Cell[ROWS][COLS];

	private final int CELL_WIDTH = 72;
	private final int CELL_HEIGHT = 72;
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;

	public GameFrame(int width, int height) {
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();
	}

	public void init() {
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		repaint();
	}

	public void paintComponent(Graphics g) {
		Grid(g);
	}

	void Grid(Graphics g) {
		g.setColor(Color.BLACK);
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(X_GRID_OFFSET,
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
							+ COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
							+ (row * (CELL_HEIGHT + 1)));
		}
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
							+ ROWS * (CELL_HEIGHT + 1));
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}

package v1;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JComponent {

	private static final long serialVersionUID = 1L;
	public static final int ROWS = 12;
	public static final int COLS = 12;
	public static Cell[][] cell = new Cell[ROWS][COLS];

	private final int CELL_WIDTH = 72;
	private final int CELL_HEIGHT = 72;
	private final int X_GRID_OFFSET = 50; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int COL_COUNT = 13;
	private final int ROW_COUNT = 13;
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
		initCells();
	}

	public void initCells() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col);
				
			}
		}
	}

	void drawCells(Graphics g) {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
			cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
		}
			cell[3][2].setSmiley(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(X_GRID_OFFSET, Y_GRID_OFFSET, (CELL_WIDTH * 12)
				+ (1 * COL_COUNT), (CELL_HEIGHT * 12) + (1 * ROW_COUNT));
		g.setColor(Color.WHITE);
		drawGrid(g);
		drawCells(g);
	}

	void drawGrid(Graphics g) {
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
}

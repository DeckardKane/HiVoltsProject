package v1;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameFrame extends JComponent {

	private static final long serialVersionUID = 1L;


	public static final int ROWS = 14;
	public static final int COLS = 14;

	public static Cell[][] cell = new Cell[ROWS][COLS];;
	ArrayList<Integer> GeneratedX = new ArrayList<Integer>();
	ArrayList<Double> GeneratedY = new ArrayList<Double>();
	private final int CELL_WIDTH = 57;
	private final int CELL_HEIGHT = 57;
	private final int X_GRID_OFFSET = 80; // 25 pixels from left
	private final int Y_GRID_OFFSET = 70; // 40 pixels from top
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
		repaint();
	}

	public void initCells() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col);

			}
		}
		cell[RandomNumberInRangeX(1, 12)][RandomNumberInRangeY(1, 12)]
				.setSmiley(true);
		for (int i = 0; i < 20; i++) {
			cell[RandomNumberInRangeX(1, 12)][RandomNumberInRangeY(1, 12)]
					.setFence(true);
		}
		for (int i = 0; i < 12; i++) {
			cell[RandomNumberInRangeX(1, 12)][RandomNumberInRangeY(1, 12)]
					.setMoo(true);
		}
		for (int x = 0; x < 14; x++) {
			for (int y = 0; y < 14; y++) {
				cell[x][0].setFence(true);
				cell[0][y].setFence(true);
				cell[x][13].setFence(true);
				cell[13][y].setFence(true);
			}
		}
		cell[RandomNumberInRangeX(0, 11)][RandomNumberInRangeY(0, 11)]
				.setSmiley(true);
	}
	void drawCells(Graphics g) {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(X_GRID_OFFSET, Y_GRID_OFFSET, (CELL_WIDTH * 12)
				+ (1 * COL_COUNT), (CELL_HEIGHT * 12) + (1 * ROW_COUNT));
		g.setColor(Color.WHITE);
		drawGrid(g);
		drawCells(g);
	}
	private int TestX(int randd) {
		if (GeneratedX.contains(randd)) {
			return RandomNumberInRangeX(1, 12);
		} else {
			return randd;
		}
	}
	
	private int RandomNumberInRangeX(int start, int end) {
		double randd = Math.random();
		randd *= (end - start + 1);
		randd += start;
		int intRandd = (int) randd;
		return TestX(intRandd);
	}
	private int TestY(int randd) {
		if (GeneratedY.contains(randd)) {
			return RandomNumberInRangeX(1, 12);
		} else {
			return randd;
		}
	}
	private int RandomNumberInRangeY(int start, int end) {
		double randd = Math.random();
		randd *= (end - start + 1);
		randd += start;
		int intRandd = (int) randd;
		return TestY(intRandd);
		// return (int) randd;
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

package v1;


import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics;

import javax.swing.JComponent;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.Timer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import javax.swing.ActionMap;

import javax.swing.InputMap;

import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.KeyStroke;


public class GameFrame extends JComponent implements ActionListener {


	// Variables


	private static final long serialVersionUID = 1L;


	// Rows & Cols


	public static final int ROWS = 14;

	public static final int COLS = 14;

	private Timer timer;

	public static Cell[][] cell = new Cell[ROWS][COLS];


	// Cell Parameters


	private final int CELL_WIDTH = 57;

	private final int CELL_HEIGHT = 57;


	private final int X_GRID_OFFSET = 80; // 80 pixels from left

	private final int Y_GRID_OFFSET = 70; // 70 pixels from top


	private final int COL_COUNT = 14;

	private final int ROW_COUNT = 14;


	private final int DISPLAY_WIDTH;

	private final int DISPLAY_HEIGHT;


	private static final Font MYFONT = new Font("Lucida Console", Font.PLAIN,

			50);

	private JLabel Title;

	private JButton Reload;


	// Cell Arrays


	private Cell[] SmileyCell = null;

	private Cell[] FenceCell = null;

	private Cell[] bottomOutsideCell = null;

	private Cell[] rightOutsideCell = null;

	private Cell[] leftOutsideCell = null;

	private Cell[] topOutsideCell = null;

	private Cell[] MhoCell = null;


	// Colors

	private final Color GRID_COLOR = Color.BLACK;

	private final Color SMILEY = Color.ORANGE;

	private final Color FENCE = Color.GRAY;

	private final Color MHO = Color.GREEN;

	private final Color OUTSIDEFENCE = Color.GRAY;


	private Direction SmileyDirection = Direction.NONE;


	private static enum Direction {

		UP, DOWN, LEFT, RIGHT, NONE

	};


	// Methods


	public GameFrame(int width, int height) {

		DISPLAY_WIDTH = width;

		DISPLAY_HEIGHT = height;

		init();

	}


	public void init() {


		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		repaint();

		initCells();

		initPositions();


		Title = new JLabel("Hivolts - APCS");

		Title.setBounds(300, -5, 500, 100);

		Title.setFont(MYFONT);

		add(Title);

		Title.setVisible(true);


		Title = new JLabel("Options");

		Title.setBounds(950, -5, 500, 100);

		Title.setFont(MYFONT);

		add(Title);

		Title.setVisible(true);


		ActionMap actionMap = this.getActionMap();

		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);


		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Direction.UP);

		actionMap.put(Direction.UP, new AbstractAction() {


			@Override

			public void actionPerformed(ActionEvent arg0) {

				onUp();

			}

		});


		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),

				Direction.DOWN);

		actionMap.put(Direction.DOWN, new AbstractAction() {


			@Override

			public void actionPerformed(ActionEvent arg0) {

				onDown();

			}

		});


		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),

				Direction.LEFT);

		actionMap.put(Direction.LEFT, new AbstractAction() {


			@Override

			public void actionPerformed(ActionEvent arg0) {

				onLeft();

			}

		});


		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),

				Direction.RIGHT);

		actionMap.put(Direction.RIGHT, new AbstractAction() {


			@Override

			public void actionPerformed(ActionEvent arg0) {

				onRight();

			}

		});


		Reload = new JButton();

		Reload.setBounds(980, 100, 150, 36);

		Reload.setLabel("Reload");


		Reload.addActionListener(new ActionListener() {


			@Override

			public void actionPerformed(ActionEvent arg0) {

				Reload();

			}

		});

		add(Reload);

		Reload.setVisible(true);


		timer = new Timer(1000, this);

		timer.setInitialDelay(0);

		timer.start();

		repaint();

	}


	public void initCells() {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				cell[row][col] = new Cell(row, col, Color.BLACK);

			}

		}

	}


	public void initPositions() {

		bottomOutsideCell = new Cell[13];

		for (int i = 0; i < bottomOutsideCell.length; i++) {

			bottomOutsideCell[i] = new Cell(i, 13, OUTSIDEFENCE);

		}

		rightOutsideCell = new Cell[14];

		for (int i = 0; i < rightOutsideCell.length; i++) {

			rightOutsideCell[i] = new Cell(13, i, OUTSIDEFENCE);

		}

		leftOutsideCell = new Cell[14];

		for (int i = 0; i < leftOutsideCell.length; i++) {

			leftOutsideCell[i] = new Cell(0, i, OUTSIDEFENCE);

		}

		topOutsideCell = new Cell[14];

		for (int i = 0; i < topOutsideCell.length; i++) {

			topOutsideCell[i] = new Cell(i, 0, OUTSIDEFENCE);

		}

		FenceCell = new Cell[20];

		for (int i = 0; i < FenceCell.length; i++) {

			int x = RandomNumberInRange(1, 12);

			int y = RandomNumberInRange(1, 12);

			FenceCell[i] = new Cell(x, y, FENCE);

		}


		MhoCell = new Cell[12];


		for (int i = 0; i < MhoCell.length; i++) {

			int x = -5;

			int y = -5;

			if (!OverlapMhoAndFence(x, y)) {

				x = RandomNumberInRange(1, 12);

				y = RandomNumberInRange(1, 12);

			}


			MhoCell[i] = new Cell(x, y, MHO);

		}


		SmileyCell = new Cell[1];

		SmileyCell[0] = new Cell(RandomNumberInRange(1, 12),

				RandomNumberInRange(1, 12), SMILEY);

	}


	private int RandomNumberInRange(int start, int end) {

		double randd = Math.random();

		randd *= (end - start + 1);

		randd += start;


		return (int) randd;

	}


	public void Reload() {

		initPositions();

		repaint();

	}


	void drawCellTypes(Graphics g) {

		SmileyCell[0].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,

				CELL_HEIGHT, g);


		if (FenceCell != null && FenceCell.length > 0) {

			for (int i = 0; i < FenceCell.length; i++) {

				FenceCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,

						CELL_HEIGHT, g);

			}

		}

		if (MhoCell != null && MhoCell.length > 0) {

			for (int i = 0; i < MhoCell.length; i++) {

				MhoCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,

						CELL_HEIGHT, g);

			}

		}


		if (bottomOutsideCell != null && bottomOutsideCell.length > 0) {

			for (int i = 0; i < bottomOutsideCell.length; i++) {

				bottomOutsideCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET,

						CELL_WIDTH, CELL_HEIGHT, g);

			}

		}

		if (rightOutsideCell != null && rightOutsideCell.length > 0) {

			for (int i = 0; i < rightOutsideCell.length; i++) {

				rightOutsideCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET,

						CELL_WIDTH, CELL_HEIGHT, g);

			}

		}

		if (leftOutsideCell != null && leftOutsideCell.length > 0) {

			for (int i = 0; i < leftOutsideCell.length; i++) {

				leftOutsideCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET,

						CELL_WIDTH, CELL_HEIGHT, g);

			}

		}

		if (topOutsideCell != null && topOutsideCell.length > 0) {

			for (int i = 0; i < topOutsideCell.length; i++) {

				topOutsideCell[i].draw(X_GRID_OFFSET, Y_GRID_OFFSET,

						CELL_WIDTH, CELL_HEIGHT, g);

			}

		}


	}


	private void onUp() {

		SmileyDirection = Direction.UP;

	}


	private void onDown() {

		SmileyDirection = Direction.DOWN;

	}


	private void onLeft() {

		SmileyDirection = Direction.LEFT;

	}


	private void onRight() {

		SmileyDirection = Direction.RIGHT;

	}


	public void actionPerformed(ActionEvent e) {

		switch (SmileyDirection) {

		case DOWN:

			SmileyCell[0].setY(SmileyCell[0].getY() + 1);

			System.out.println("Down");

			SmileyDirection = Direction.NONE;

			break;

		case LEFT:

			SmileyCell[0].setX(SmileyCell[0].getX() - 1);

			System.out.println("Left");

			SmileyDirection = Direction.NONE;

			break;

		case RIGHT:

			SmileyCell[0].setX(SmileyCell[0].getX() + 1);

			System.out.println("Right");

			SmileyDirection = Direction.NONE;

			break;

		case UP:

			SmileyCell[0].setY(SmileyCell[0].getY() - 1);

			System.out.println("Up");

			SmileyDirection = Direction.NONE;

			break;

		case NONE:

			SmileyCell[0].setY(SmileyCell[0].getY());

		}

		repaint();


	}


	public void paintComponent(Graphics g) {

		drawGrid(g);

		drawCellTypes(g);


	}


	void drawGrid(Graphics g) {


		g.setColor(GRID_COLOR);


		for (int row = 0; row <= ROWS; row++) {

			g.drawLine(X_GRID_OFFSET,


					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET + COLS

					* (CELL_WIDTH + 1), Y_GRID_OFFSET

					+ (row * (CELL_HEIGHT + 1)));

		}


		for (int col = 0; col <= COLS; col++) {

			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,


					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET + ROWS

					* (CELL_HEIGHT + 1));

		}


		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				Cell.draw(col, row, X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,

						CELL_HEIGHT, GRID_COLOR, g);

			}

		}

	}


	private boolean OverlapMhoAndFence(int x, int y) {

		for (int i = 0; i < FenceCell.length; i++) {

			if (FenceCell[i].getX() == x && FenceCell[i].getY() == y) {

				return true;

			}

		}

		return false;

	}

}
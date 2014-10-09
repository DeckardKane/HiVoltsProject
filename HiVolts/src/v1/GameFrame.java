package v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GameFrame extends JComponent implements ActionListener {

	private static final int MAX_GRID_SIZE = 12;

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
	private JLabel gameOverLabel;
	private JButton Reload;

	// Cell Arrays

	private Cell SmileyCell = null;

	private Cell[] FenceCell = null;

	private Cell[] bottomOutsideCell = null;

	private Cell[] rightOutsideCell = null;

	private Cell[] leftOutsideCell = null;

	private Cell[] topOutsideCell = null;

	private Cell[] MhoCell = null;

	// Colors

	private final Color GRID_COLOR = Color.BLACK;

	private final Color SMILEY = Color.ORANGE;

	private final Color FENCE = Color.CYAN;

	private final Color MHO = Color.GREEN;

	private final Color OUTSIDEFENCE = Color.GRAY;

	private Direction SmileyDirection = Direction.NONE;

	private static enum Direction {

		UP, DOWN, LEFT, RIGHT, NONE, UPANDLEFT, DOWNANDLEFT, UPANDRIGHT, DOWNANDRIGHT, JUMP

	};

	private boolean ingame = false;
	private boolean mhoactive = false;


	// Methods

	public GameFrame(int width, int height) {

		DISPLAY_WIDTH = width;

		DISPLAY_HEIGHT = height;

		init();

	}

	public void init() {
		ingame = true;

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

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), Direction.UP);

		actionMap.put(Direction.UP, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onUp();

			}

		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0),

				Direction.DOWN);

		actionMap.put(Direction.DOWN, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onDown();

			}

		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0),

				Direction.LEFT);

		actionMap.put(Direction.LEFT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onLeft();

			}

		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0),

				Direction.RIGHT);

		actionMap.put(Direction.RIGHT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onRight();

			}

		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0),

				Direction.JUMP);

		actionMap.put(Direction.JUMP, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onJump();

			}

		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0),

				Direction.UPANDLEFT);

		actionMap.put(Direction.UPANDLEFT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UpAndLeft();

			}

		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0),

				Direction.UPANDRIGHT);

		actionMap.put(Direction.UPANDRIGHT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UpAndRight();

			}

		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0),

				Direction.DOWNANDRIGHT);

		actionMap.put(Direction.DOWNANDRIGHT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				DownAndRight();

			}

		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0),

				Direction.DOWNANDLEFT);

		actionMap.put(Direction.DOWNANDLEFT, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				DownAndLeft();

			}

		});

		Reload = new JButton();

		Reload.setBounds(980, 100, 150, 36);

		Reload.setLabel("Restart");

		Reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Reload();

			}

		});
		gameOverLabel = new JLabel("State: Game Over");
		gameOverLabel.setBounds(350, 900, 500, 50);
		gameOverLabel.setFont(MYFONT);
		gameOverLabel.setForeground(Color.red);
		add(gameOverLabel);
		gameOverLabel.setVisible(false);

		add(Reload);

		Reload.setVisible(true);

		timer = new Timer(1, this);

		timer.setInitialDelay(0);

		timer.start();

		repaint();

	}

	public void initCells() {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				cell[row][col] = new Cell(row, col, Color.BLACK, null);

			}

		}

	}

	public void initPositions() {

		bottomOutsideCell = new Cell[13];

		for (int i = 0; i < bottomOutsideCell.length; i++) {

			bottomOutsideCell[i] = new Cell(i, 13, null, readImage("Fence.png"));

		}

		rightOutsideCell = new Cell[14];

		for (int i = 0; i < rightOutsideCell.length; i++) {

			rightOutsideCell[i] = new Cell(13, i, null, readImage("Fence.png"));

		}

		leftOutsideCell = new Cell[14];

		for (int i = 0; i < leftOutsideCell.length; i++) {

			leftOutsideCell[i] = new Cell(0, i, null, readImage("Fence.png"));

		}

		topOutsideCell = new Cell[14];

		for (int i = 0; i < topOutsideCell.length; i++) {

			topOutsideCell[i] = new Cell(i, 0, null, readImage("Fence.png"));

		}

		FenceCell = new Cell[20];

		for (int i = 0; i < FenceCell.length; i++) {

			int x = RandomNumberInRange(1, MAX_GRID_SIZE);

			int y = RandomNumberInRange(1, MAX_GRID_SIZE);

			/*
			 * The System outs in this section were for validation purposes. I
			 * was checking whether or not what I did worked by checking if
			 * fences were overlapping each other or if Mhos were generated on
			 * the fences. At this point: fences do not overlap! Success!
			 * However, Mhos can be generated on top of the fences. So I need to
			 * implement this same collision detection but with the Mhos.
			 */
			System.out.println("Fence " + i + " has coordinates of: " + x
					+ " and " + y);

			/*
			 * What this while loop does is check if isFence is returning true
			 * with arguments of the x and y coordinates above (with i giving us
			 * the limit for values that have been assigned). If you try and
			 * check the whole array at once, you get a nullpointer error,
			 * because the coordinates have not been assigned yet. So we check
			 * it step by step, increasing as the elements in the array are
			 * assigned values. And if ifFence is true, the while loop generates
			 * new x and y coordinates until it returns false.
			 */
			while (isFence(x, y, i) == true) {

				x = RandomNumberInRange(1, MAX_GRID_SIZE);

				y = RandomNumberInRange(1, MAX_GRID_SIZE);

				System.out.println("isFence was true!");

				System.out.println("Fence " + i + " has coordinates of: " + x
						+ " and " + y);
			}

			FenceCell[i] = new Cell(x, y, null, readImage("Fence.png"));

		}

		MhoCell = new Cell[12];

		for (int i = 0; i < MhoCell.length; i++) {

			int x = RandomNumberInRange(1, MAX_GRID_SIZE);

			int y = RandomNumberInRange(1, MAX_GRID_SIZE);

			System.out.println("Mho " + i + " has coordinates of: " + x
					+ " and " + y);

			while (isFence(x, y) == true) {

				x = RandomNumberInRange(1, MAX_GRID_SIZE);

				y = RandomNumberInRange(1, MAX_GRID_SIZE);

				System.out.println("isFence was true!");

				System.out.println("Mho " + i + " has coordinates of: " + x
						+ " and " + y);
			}

			while (isMho(x, y, i) == true) {

				x = RandomNumberInRange(1, MAX_GRID_SIZE);

				y = RandomNumberInRange(1, MAX_GRID_SIZE);

				System.out.println("isMho was true!");

				System.out.println("Mho " + i + " has coordinates of: " + x
						+ " and " + y);
			}

			MhoCell[i] = new Cell(x, y, null, readImage("Mho.png"));

		}

		int x = RandomNumberInRange(1, MAX_GRID_SIZE);

		int y = RandomNumberInRange(1, MAX_GRID_SIZE);

		while (isFence(x, y) || isMho(x, y, MhoCell.length)) {

			x = RandomNumberInRange(1, MAX_GRID_SIZE);

			y = RandomNumberInRange(1, MAX_GRID_SIZE);

		}

		SmileyCell = new Cell(x, y, null, readImage("Smiley.png"));
		System.out.println("Smiley has coordinates of: " + x + " and " + y);

	}

	private BufferedImage readImage(String name) {
		BufferedImage img = null;
		try {
			InputStream stream = new BufferedInputStream(
					new FileInputStream(name));
			img = ImageIO.read(stream);
		} catch (IOException e) {
			System.out.println("Image " + name + " not found - exception");
		}
		return img;
	}

	/*
	private BufferedImage readImage(String name) {
		BufferedImage img = null;
		try {
			InputStream stream = getClass().getResourceAsStream(name);
			if (stream != null) {
				img = ImageIO.read(stream);
			} else {
				System.out.println("Image " + name + " not found");
			}
		} catch (IOException e) {
			System.out.println("Image " + name + " not found - exception");
		}
		System.out.println("Classpath is: " + System.getProperty("java.class.path"));
		return img;
	}
	 */
	private int RandomNumberInRange(int start, int end) {

		double randd = Math.random();

		randd *= (end - start + 1);

		randd += start;

		return (int) randd;

	}

	public void Reload() {
		gameOverLabel.setVisible(false);
		ingame = true;
		initPositions();
		repaint();

	}

	public void GameOver() {
		ingame = false;
		gameOverLabel.setVisible(true);
		System.out.println("Game Over");
	}

	void drawCellTypes(Graphics g) {

		SmileyCell.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,

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
		if (isFence(SmileyCell.getX(), SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UP;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}

	private void onDown() {
		if (isFence(SmileyCell.getX(), SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWN;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}

	private void onLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY()) == false) {
			if (SmileyCell.getX() > 1) {
				SmileyDirection = Direction.LEFT;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}

	private void onRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY()) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.RIGHT;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}

	private void onJump() {
		SmileyDirection = Direction.JUMP;
		
		int jumpX = RandomNumberInRange(1,12);
		int jumpY = RandomNumberInRange(1,12);
		while (isFence(jumpX, jumpY, FenceCell.length) == true) {
			jumpX = RandomNumberInRange(1,12);
			jumpY = RandomNumberInRange(1,12);
			
		}
		
		SmileyCell.setX(jumpX);
		SmileyCell.setY(jumpY);
		
	}

	private void UpAndRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE && SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UPANDRIGHT;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}

	private void UpAndLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getX() > 1 && SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UPANDLEFT;
				mhoMovement();
				checkIfMhoHitFence();
			} else {
				GameOver();

			}
		} else {
			GameOver();
		}
	}

	private void DownAndRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE && SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWNANDRIGHT;
				mhoMovement();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}

	}

	private void DownAndLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getX() > 1 && SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWNANDLEFT;
				mhoMovement();
			} else {
				GameOver();
			}
		} else {
			GameOver();
		}
	}
	private void checkIfMhoHitFence(){

	}
	private void mhoMovement() {
		/*
		 * After player moves, mho movement is done in accordance to smiley
		 * coordinates. Afterwards, mho coordinates must be detected for
		 * collision.
		 */
		
		mhoLoop:
		for (int i = 0; i < MhoCell.length; i++) {

			//If a Mho is directly horizontal or vertical to you, the Mho MUST move directly towards you one square. 
			
			//Horizontal Move
			if (MhoCell[i].getX() == SmileyCell.getX()) {
				if (MhoCell[i].getX() < SmileyCell.getX()) {
					MhoCell[i].setX(MhoCell[i].getX() + 1);
				} else if (MhoCell[i].getX() > SmileyCell.getX()) {
					MhoCell[i].setX(MhoCell[i].getX() - 1);
				}
				if (isFence(MhoCell[i].getX(), MhoCell[i].getY(), MhoCell.length) == true) {
					
					//This mho is now dead! Add a flag for this?
					System.out.println("Kill this mho");
				}
				break mhoLoop;
			}
			//Vertical Move
			if (MhoCell[i].getY() == SmileyCell.getY()) {
				if (MhoCell[i].getY() < SmileyCell.getY()) {
					MhoCell[i].setY(MhoCell[i].getY() + 1); 

				} else if (MhoCell[i].getY() > SmileyCell.getY()) {
					MhoCell[i].setY(MhoCell[i].getY() - 1);
				}
				if (isFence(MhoCell[i].getX(), MhoCell[i].getY(), MhoCell.length) == true) {

					//This mho is now dead! Add a flag for this?
					System.out.println("Kill this mho");
				}
				break mhoLoop;
			}
			
			//Diagonal Move
			int tempX = 0;
			
			int tempY = 0;
			
			if (MhoCell[i].getX() < SmileyCell.getX()) {
				tempX = MhoCell[i].getX() + 1;
			} else if (MhoCell[i].getX() > SmileyCell.getX()) {
				tempX = MhoCell[i].getX() - 1;
			}
			
			if (MhoCell[i].getY() < SmileyCell.getY()) {
				tempY = MhoCell[i].getY() + 1;
			} else if (MhoCell[i].getY() > SmileyCell.getY()) {
				tempY = MhoCell[i].getY() - 1;
			}
			
			if (!isFence(tempX, tempY, FenceCell.length) &&
					!isMho(tempX, tempY, MhoCell.length)) {
				MhoCell[i].setX(tempX);
				MhoCell[i].setY(tempY);
				break mhoLoop;
			}
			
			//Horizontal/vertical if delta...
			int deltaX = SmileyCell.getX() - MhoCell[i].getX();
			
			int deltaY = SmileyCell.getY() - MhoCell[i].getY();
			
			/*
			//If horizontal distance towards Smiley is greater than or 
			if (Math.abs(deltaX) >= Math.abs(deltaY)) {
				int tempX = ;
				
				if (!isFence) {
					tempX = MhoCell[i].getX() + 1;
				}
				break mhoLoop;
			}
			
			if (Math.abs(deltaY) >= Math.abs(deltaX)) {
				
				break mhoLoop;
			}
			*/
			
			
		}
	}

	/*
	 * isFence searches for the value pair x and y in the FenceCell array.
	 */
	private boolean isFence(int x, int y) {
		return isFence(x, y, FenceCell.length);
	}

	/*
	 * isFence searches for the value pair x and y in the FenceCell array up to
	 * the element designated by CellLength. CellLength is used so only assigned
	 * values are searched during initialization.
	 */
	private boolean isFence(int x, int y, int CellLength) {
		for (int i = 0; i < CellLength; i++) {
			if (FenceCell[i].getX() == x && FenceCell[i].getY() == y) {

				return true;

			}

		}

		return false;
	}

	private boolean isMho(int x, int y, int CellLength) {
		for (int i = 0; i < CellLength; i++) {

			if (MhoCell[i].getX() == x && MhoCell[i].getY() == y) {

				return true;

			}

		}

		return false;
	}

	public void actionPerformed(ActionEvent e) {
		if (!ingame) {
			return;
		}	

		switch (SmileyDirection) {

		case DOWN:

			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down");

			SmileyDirection = Direction.NONE;

			break;

		case LEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);

			System.out.println("Left");

			SmileyDirection = Direction.NONE;

			break;

		case RIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);

			System.out.println("Right");

			SmileyDirection = Direction.NONE;

			break;

		case UP:

			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up");

			SmileyDirection = Direction.NONE;

			break;

		case UPANDLEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);
			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up and Left");

			SmileyDirection = Direction.NONE;

			break;

		case UPANDRIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);
			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up and Right");

			SmileyDirection = Direction.NONE;

			break;

		case DOWNANDRIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);
			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down and Right");

			SmileyDirection = Direction.NONE;

			break;
		case DOWNANDLEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);
			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down and Left");

			SmileyDirection = Direction.NONE;

			break;
		case NONE:
			SmileyCell.setY(SmileyCell.getY());
			SmileyCell.setX(SmileyCell.getX());

			break;
		}
		repaint();
		if (isMho(SmileyCell.getX(), SmileyCell.getY(), MhoCell.length) == true) {
			GameOver();
		}
		// delete mho from fence
		for(int x = 0; x < MhoCell.length; x++){
			if(isFence(MhoCell[x].getX(), MhoCell[x].getY()) == true) {
				if (MhoCell.length == 0) {
					// No mho left
					GameOver();
					System.out.println("You won the game!!!!!!!");
				}
				Cell[] tempMhoCell = new Cell[MhoCell.length-1];
				int j=0;
				for (int i=0; i<MhoCell.length; i++) {
					if (i != x) {
						tempMhoCell[j] = MhoCell[i];
						j++;
					} // else do nothing
				}
				MhoCell = tempMhoCell;
			}
		}

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

						CELL_HEIGHT, GRID_COLOR, g, null);

			}

		}

	}

}
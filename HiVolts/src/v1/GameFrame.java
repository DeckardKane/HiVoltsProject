package v1;

// This is all of the libraries that are passed. 
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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GameFrame extends JComponent implements ActionListener {
	
	// VARIABLES

	// This section contains Variables that contain all of the settings for
	// the Grid graphics, text, etc.

	private static final long serialVersionUID = 1L;

	// These variables contain the constants Rows & Cols, which create
	// the Grid.

	public static final int ROWS = 14;
	public static final int COLS = 14;

	// Instead of constantly using 12, we created a constant for the Max Grid
	// Size.
	// The reason the value is 12 because the outside boundaries of the grid
	// contain fences.

	private static final int MIN_GRID_SIZE = 1;
	private static final int MAX_GRID_SIZE = 12;

	// This creates an object from the Timer Library. This allows to
	// create delay and constantly refresh the game.

	private Timer timer;

	// This created the Double Array passing the class Cell for the Grid,
	// containing the Rows and Columns, which is the whole grid.

	public static Cell[][] cell = new Cell[ROWS][COLS];

	// CELL PARAMETERS 

	// This defines number of pixels the width and height need.
	private final int CELL_WIDTH = 57;
	private final int CELL_HEIGHT = 57;

	// These are the offsets that create spacing of the grid from the
	// top of the JFrame, making a cleaner UI

	private final int X_GRID_OFFSET = 80; // 80 pixels from left
	private final int Y_GRID_OFFSET = 70; // 70 pixels from top

	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;

	// For the UI/Titles, we used the font Lucida Console, making a Retro
	// style for the game.

	private static final Font MYFONT = new Font("Lucida Console", Font.PLAIN,

	50);

	// These are the labels, buttons that are needed to be defined. It uses
	// the library JLabel and JButton.
	private JLabel Title;
	private JLabel gameOverLabel;
	private JButton Reload;
	private JButton Jump;

	// Cell Arrays. These are the arrays

	// This creates the SmileyCell, which is not an array, but rather
	// an object. The reason is that there is only one element, so
	// there is no point to have an array.

	private Cell SmileyCell = null;
	private Cell[] MhoCell = null;

	// This contains the Fences. We have the FenceCell Array, which spans
	// randomly
	// and also the Fences that surround the Grid.

	private Cell[] FenceCell = null;
	private Cell[] bottomOutsideCell = null;
	private Cell[] rightOutsideCell = null;
	private Cell[] leftOutsideCell = null;
	private Cell[] topOutsideCell = null;

	// Colors

	// This is the color of the Grid, which is black. We thought this
	// is the most appropriate color, and we didn't wan't to include a
	// background.
	
	private final Color GRID_COLOR = Color.BLACK;

	// This contains the Directions that can be passed to the Smiley, including
	// all the key commands, Jump, and Restart. 

	private static enum Direction {

		UP, DOWN, LEFT, RIGHT, NONE, UPANDLEFT, DOWNANDLEFT, UPANDRIGHT, DOWNANDRIGHT, JUMP, SIT

	};
	// This is the default Direction, making it not move. 
	
	private Direction SmileyDirection = Direction.NONE;

	// Boolean flags. This will not allow key inputs to be accepted. 

	private boolean ingame = false;

	// METHODS 

	// This is the constructor, which passes the height and width of the JFrame
	// to the game. Then, it calls init() method, which sets the initial state
	// of the game. 
	
	public GameFrame(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();

	}

	// The init() method creates the initial state of the game with all of 
	// the elements on the screen.
	public void init() {
		// Sets ingame to true to allow game to start. 
		ingame = true;
		
		// Sets size, and repaints it to refresh Graphics. 
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		repaint();
		
		// This sets the initial coordinates of the cell types (Smiley, Mho, Fence)
		// using the initCells() and initPositions() method. InitCells() also creates the
		// "empty" cells. 
		initCells();
		initPositions();

		
		// This sets the title "HiVolts - APCS" above the grid. 
		Title = new JLabel("HiVolts - APCS");
		Title.setBounds(300, -5, 500, 100);
		// Lucida Console font 
		Title.setFont(MYFONT);
		add(Title);
		Title.setVisible(true);
		
		// This sets the title "Option" above option buttons. 
		Title = new JLabel("Options");
		Title.setBounds(950, -5, 500, 100);
		// Lucida Console font 
		Title.setFont(MYFONT);
		add(Title);
		Title.setVisible(true);

		// KEYS 
		
		// Allows ActionMap / InputMap to allow Computer Keys to start methods  
		ActionMap actionMap = this.getActionMap();
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		// When W key is clicked, the method onUp() is invoked
		// and sets Direction to UP
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), Direction.UP);

		actionMap.put(Direction.UP, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4971154488127225952L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onUp();

			}

		});
		// When X key is clicked, the method onDown() is invoked
		// and sets Direction to DOWN
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0),

		Direction.DOWN);

		actionMap.put(Direction.DOWN, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6239815442467611636L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onDown();

			}

		});
		// When A key is clicked, the method onLeft() is invoked
		// and sets Direction to LEFT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0),

		Direction.LEFT);

		actionMap.put(Direction.LEFT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1014579560679734420L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onLeft();

			}

		});
		// When D key is clicked, the method onRight() is invoked
		// and sets Direction to RIGHT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0),

		Direction.RIGHT);

		actionMap.put(Direction.RIGHT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2178548698356335736L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onRight();

			}

		});

		// When Q key is clicked, the method UpAndLeft() is invoked
		// and sets Direction to UPANDLEFT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0),

		Direction.UPANDLEFT);

		actionMap.put(Direction.UPANDLEFT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -221844758514001376L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UpAndLeft();

			}

		});
		
		// When E key is clicked, the method UpAndRight() is invoked
		// and sets Direction to UPANDRIGHT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0),

		Direction.UPANDRIGHT);

		actionMap.put(Direction.UPANDRIGHT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5986345215633994605L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UpAndRight();

			}

		});
		// When C key is clicked, the method DownAndRight() is invoked
		// and sets Direction to DOWNANDRIGHT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0),

		Direction.DOWNANDRIGHT);

		actionMap.put(Direction.DOWNANDRIGHT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4942072327431687504L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				DownAndRight();

			}

		});
		// When Z key is clicked, the method DownAndLeft() is invoked
		// and sets Direction to DOWNANDLEFT
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0),

		Direction.DOWNANDLEFT);

		actionMap.put(Direction.DOWNANDLEFT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1091693991622853693L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				DownAndLeft();

			}

		});
		
		// When S key is clicked, the method onSit() is invoked
		// and sets Direction to SIT, meaning it doesn't move. 
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0),

		Direction.SIT);

		actionMap.put(Direction.SIT, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6587852390563056463L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onSit();

			}

		});
		// The Jump Method is invoked when J button is clicked. It
		// invokes onJump(), which randomly spawns the Smiley. 
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0),

				Direction.JUMP);

				actionMap.put(Direction.JUMP, new AbstractAction() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -621020681565499781L;

					@Override
					public void actionPerformed(ActionEvent arg0) {

						onJump();

					}

				});
				
		// BUTTONS ARE MADE 
				
		// This button restarts the game and randomly spawns the cell types
		// for new game. Invokes Reload() method. 
				
		Reload = new JButton();

		Reload.setBounds(980, 100, 150, 36);

		Reload.setLabel("Restart");

		Reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Reload();

			}

		});
		
		// This button restarts the game and randomly spawns Smiley in the original
		// game. Invokes onJump() method. 
		
		Jump = new JButton();

		Jump.setBounds(980, 150, 150, 36);

		Jump.setLabel("Jump");

		Jump.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				onJump();

			}

		});
		
		// This makes the buttons visible. 
				add(Reload);
				add(Jump);

				Reload.setVisible(true);
				Jump.setVisible(true);
		
		// LABELS 
		
		// This label which is red is created, but not shown until the GameOver()
		// is passed. 
		
		gameOverLabel = new JLabel("State: Game Over");
		gameOverLabel.setBounds(350, 900, 500, 50);
		gameOverLabel.setFont(MYFONT);
		gameOverLabel.setForeground(Color.red);
		add(gameOverLabel);
		gameOverLabel.setVisible(false);
		
		
		
		// This sets the timer. This will have minimal delay and quick speed. 
		timer = new Timer(1, this);
		timer.setInitialDelay(0);
		timer.start();
		
		// Repaint refreshes Graphics 
		repaint();

	}

	// InitCells method sets the Grid Cells to Black without any images. These will
	// be the "empty" cells. 
	
	public void initCells() {

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col, Color.BLACK, null);

			}
		}
	}
	
	// The initPositions method adds elements to all of the Fence arrays,
	// Mho arrays, and Fence arrays. 
	public void initPositions() {

		bottomOutsideCell = new Cell[13];

		for (int i = 0; i < bottomOutsideCell.length; i++) {
			// Accesses all of the elements, sets positions from (i(0-13), 13), and adds 
			// image "Fence.png". Creates row of fences in outside boundary at bottom. 
			bottomOutsideCell[i] = new Cell(i, 13, null, readImage("Fence.png"));

		}

		rightOutsideCell = new Cell[14];

		for (int i = 0; i < rightOutsideCell.length; i++) {
			// Accesses all of the elements, sets positions from (13, i(0-13)), and adds 
			// image "Fence.png". Creates row of fences in outside boundary at right.  
			rightOutsideCell[i] = new Cell(13, i, null, readImage("Fence.png"));

		}

		leftOutsideCell = new Cell[14];

		for (int i = 0; i < leftOutsideCell.length; i++) {
			// Accesses all of the elements, sets positions from (0, i(0-13)), and adds 
			// image "Fence.png". Creates row of fences in outside boundary at left.
			leftOutsideCell[i] = new Cell(0, i, null, readImage("Fence.png"));

		}

		topOutsideCell = new Cell[14];

		for (int i = 0; i < topOutsideCell.length; i++) {
			// Accesses all of the elements, sets positions from (i(0-13),0), and adds 
			// image "Fence.png". Creates row of fences in outside boundary at top. 
			topOutsideCell[i] = new Cell(i, 0, null, readImage("Fence.png"));

		}

		// This is for debugging code, Coordinates of Hivolts. 
		System.out.println("COORDINATES OF HIVOLTS");

		// Now let's place 20 fence cells randomly on the grid.
		FenceCell = new Cell[20];

		for (int i = 0; i < FenceCell.length; i++) {
			// Using the RandomNumberInRange to find random number from
			// Min Grid to Max Grid Size.
			
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

			// This checks that the Fence Cell 
			while (isFence(x, y, i)) {

				x = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);
				y = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

				System.out.println("Fence " + i + " may have coordinates of: ("
						+ x + "," + y + ")");
			}
			// Debugging. 
			FenceCell[i] = new Cell(x, y, null, readImage("Fence.png"));
			System.out.println("New Fence " + i + " has coordinates of: (" + x
					+ "," + y + ")");

		}

		// These for loops places 12 Mho cells. This first randomly spawns 
		// the Mhos, but also checks that there is no overlap with other 
		// cell types. 
		MhoCell = new Cell[12];

		for (int i = 0; i < MhoCell.length; i++) {

			int x = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

			int y = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

			// Debugging. 
			System.out.println("Mho " + i + " may have coordinates of: (" + x
					+ "," + y + ")");

			while (isFence(x, y) || isMho(x, y, i)) {

				x = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

				y = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

				System.out.println("Mho " + i + " may have coordinates of: ("
						+ x + "," + y + ")");
			}

			// Debugging. 
			MhoCell[i] = new Cell(x, y, null, readImage("Mho.png"));
			System.out.println("New Mho " + i + " has coordinates of: (" + x
					+ "," + y + ")");
		}

		// Now let's place the only Smiley :)
		
		// This randomly spawns the Smiley and uses the isFence and isMho methods
		// to check if there is overlap. 
		int x = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

		int y = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

		while (isFence(x, y) || isMho(x, y, MhoCell.length)) {

			x = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

			y = RandomNumberInRange(MIN_GRID_SIZE, MAX_GRID_SIZE);

		}
		
		// Debugging. 
		SmileyCell = new Cell(x, y, null, readImage("Smiley.png"));
		System.out.println("Smiley has coordinates of: (" + x + " and " + y
				+ ")");

	}
	
	// To get the images from the root folder, we used a readImage method 
	// that finds the image. It also catches any exceptions as to not 
	// crash the program. 
	
	private BufferedImage readImage(String name) {
		BufferedImage img = null;
		try {
			InputStream stream = new BufferedInputStream(new FileInputStream(
					name));
			img = ImageIO.read(stream);
		} catch (IOException e) {
			System.out.println("Image " + name + " not found - exception");
		}
		return img;
	}
	
	// Previous code for readImage that finds image in the bin folder 
	
	/*
	 * private BufferedImage readImage(String name) { BufferedImage img = null;
	 * try { InputStream stream = getClass().getResourceAsStream(name); if
	 * (stream != null) { img = ImageIO.read(stream); } else {
	 * System.out.println("Image " + name + " not found"); } } catch
	 * (IOException e) { System.out.println("Image " + name +
	 * " not found - exception"); } System.out.println("Classpath is: " +
	 * System.getProperty("java.class.path")); return img; }
	 */
	
	// This method calculates a random number from 1 to 12 to find
	// coordinates of cells when spawning. 
	private int RandomNumberInRange(int start, int end) {

		double randd = Math.random();

		randd *= (end - start + 1);

		randd += start;

		return (int) randd;

	}
	
	// When the Reload button is clicked, it makes ingame true to allow 
	// movement, removes gameOverLabel, restarts initPositions, and refreshes
	// Graphics. 
	public void Reload() {
		gameOverLabel.setVisible(false);
		ingame = true;
		initPositions();
		repaint();

	}

	// This method only exists because Reload is used in an ActionMap, and
	// pitches a fit when used by our endgame prompts.
	public void Restart() {
		gameOverLabel.setVisible(false);
		ingame = true;
		initPositions();
		repaint();
	}
	
	// GameOver disables movement and shows Game Over Label. It also 
	// prints game over. 
	public void GameOver() {
		ingame = false;
		gameOverLabel.setVisible(true);
		System.out.println("Game Over");
	}
	/*
	 * This method is used to draw cells through the use of the draw method
	 * located in the cell class. It takes the randomly generated coordinates
	 * for fences and Mhos and uses them in creating them on the grid. The last
	 * 4 methods within drawCellTypes draw the outside border that encompasses
	 * the grid. All of these coordinates are set as fences so that the program
	 * will recognize it if the player decides to move the smiley into one of
	 * these coordinates.
	 */
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

	/*
	 * The following ten methods are executed when a key binding is used. This
	 * includes the keys Q, W, E, A, S, D, Z, X, and C and are defined above
	 * this portion of the code. When each of these method is run, the method
	 * first checks the future coordinate of the smiley with the coordinates of
	 * the Fences. This allows the game to determine whether the next movement
	 * will end the game, if it an illegal movement, or if it is a legal one the
	 * movement will be made. The else statements following the if statement
	 * catch illegal moves which execute the methods GameOver and
	 * handleLoseFence.
	 */
	// This is the method executed when the player moves the smiley up.
	private void onUp() {
		if (isFence(SmileyCell.getX(), SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UP;
			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player moves the smiley down.
	private void onDown() {
		if (isFence(SmileyCell.getX(), SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWN;
			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player moves the smiley left.
	private void onLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY()) == false) {
			if (SmileyCell.getX() > 1) {
				SmileyDirection = Direction.LEFT;
			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player moves the smiley right.
	private void onRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY()) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.RIGHT;

			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player jumps.
	private void onJump() {
		SmileyDirection = Direction.JUMP;
	}

	// This is the method executed when the player sits.
	private void onSit() {
		SmileyDirection = Direction.SIT;
	}

	// This is the method executed when the player moves the smiley up and
	// right.
	private void UpAndRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE && SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UPANDRIGHT;

			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player moves the smiley up and left.
	private void UpAndLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY() - 1) == false) {
			if (SmileyCell.getX() > 1 && SmileyCell.getY() > 1) {
				SmileyDirection = Direction.UPANDLEFT;

			} else {
				GameOver();
				handleLoseFence();

			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	// This is the method executed when the player moves the smiley down and
	// right.
	private void DownAndRight() {
		if (isFence(SmileyCell.getX() + 1, SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getX() < MAX_GRID_SIZE
					&& SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWNANDRIGHT;
			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}

	}

	// This is the method executed when the player moves the smiley down and
	// left.
	private void DownAndLeft() {
		if (isFence(SmileyCell.getX() - 1, SmileyCell.getY() + 1) == false) {
			if (SmileyCell.getX() > 1 && SmileyCell.getY() < MAX_GRID_SIZE) {
				SmileyDirection = Direction.DOWNANDLEFT;
			} else {
				GameOver();
				handleLoseFence();
			}
		} else {
			GameOver();
			handleLoseFence();
		}
	}

	private void mhoMovement() {
		/*
		 * After the player moves the smiley, Mho movement is done by comparing
		 * the X and Y values of each Mho. This is done by running a for loop so
		 * that each Mho in the array which stores Mhos is accounted for and
		 * moved. After calculating the direction that the Mho must move, the X
		 * and Y values are changed accordingly and then checked for collision
		 * with fences.
		 */
		for (int i = 0; i < MhoCell.length; i++) {
			int tempX = 0;
			int tempY = 0;
			// If a Mho is directly horizontal or vertical to you, the Mho MUST
			// move directly towards you one square.

			// Horizontal Move
			System.out.println("Moving mho " + i + " has coordinates of: "
					+ MhoCell[i].getX() + ", " + MhoCell[i].getY());

			if (MhoCell[i].getY() == SmileyCell.getY()) {
				if (MhoCell[i].getX() < SmileyCell.getX()) {
					tempX = MhoCell[i].getX() + 1;
					MhoCell[i].setX(tempX);
				} else if (MhoCell[i].getX() > SmileyCell.getX()) {
					tempX = MhoCell[i].getX() - 1;
					MhoCell[i].setX(tempX);
				}
				System.out.println("Directly horizontal mho is moving to: "
						+ MhoCell[i].getX() + ", " + MhoCell[i].getY());
				killMhos();
				continue;
			}
			// Vertical Move
			if (MhoCell[i].getX() == SmileyCell.getX()) {
				if (MhoCell[i].getY() < SmileyCell.getY()) {
					MhoCell[i].setY(MhoCell[i].getY() + 1);

				} else if (MhoCell[i].getY() > SmileyCell.getY()) {
					MhoCell[i].setY(MhoCell[i].getY() - 1);
				}
				System.out.println("Directly vertical mho is moving to: "
						+ MhoCell[i].getX() + ", " + MhoCell[i].getY());
				killMhos();
				continue;
			}

			// Diagonal Move

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

			if (!isFence(tempX, tempY, FenceCell.length)
					&& !isMho(tempX, tempY, MhoCell.length)) {
				MhoCell[i].setX(tempX);
				MhoCell[i].setY(tempY);
				System.out.println("Diagonal mho is moving to: "
						+ MhoCell[i].getX() + ", " + MhoCell[i].getY());
				killMhos();
				continue;
			}

			// Horizontal/vertical if delta...
			int deltaX = SmileyCell.getX() - MhoCell[i].getX();

			int deltaY = SmileyCell.getY() - MhoCell[i].getY();

			// If horizontal distance towards Smiley is greater than or equal to
			// the vertical distance, move horizontally.
			if (Math.abs(deltaX) >= Math.abs(deltaY)) {
				tempX = (int) (MhoCell[i].getX() + Math.signum(deltaX));

				tempY = MhoCell[i].getY();
				if (!isFence(tempX, tempY, FenceCell.length)
						&& !isMho(tempX, tempY, MhoCell.length)) {
					MhoCell[i].setX(tempX);
					MhoCell[i].setY(tempY);
					System.out
							.println("Horizontal moving deltaX>=deltaY mho is moving to: "
									+ MhoCell[i].getX()
									+ ", "
									+ MhoCell[i].getY());
					killMhos();
					continue;
				}
			}

			// If vertical distance towards Smiley is greater than or equal to
			// the vertical distance, move vertically.
			if (Math.abs(deltaY) >= Math.abs(deltaX)) {
				tempX = MhoCell[i].getX();

				tempY = (int) (MhoCell[i].getY() + Math.signum(deltaY));
				if (!isFence(tempX, tempY, FenceCell.length)
						&& !isMho(tempX, tempY, MhoCell.length)) {
					MhoCell[i].setX(tempX);
					MhoCell[i].setY(tempY);
					System.out
							.println("Vertical moving deltaY>=deltaX mho is moving to: "
									+ MhoCell[i].getX()
									+ ", "
									+ MhoCell[i].getY());
					killMhos();
					continue;
				}
			}

			/*
			 * If none of these can result in landing on an empty square, repeat
			 * the order with the attempt to move onto an electric fence.
			 */

			// Diagonal kill on fence
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

			if (isFence(tempX, tempY, FenceCell.length)) {
				MhoCell[i].setX(tempX);
				MhoCell[i].setY(tempY);
				System.out.println("Diagonal kill on fence at position: "
						+ MhoCell[i].getX() + ", " + MhoCell[i].getY());
				killMhos();
				continue;
			}

			// Horizontal/vertical if delta...

			// If horizontal distance towards Smiley is greater than or equal to
			// the vertical distance, move horizontally.
			if (Math.abs(deltaX) >= Math.abs(deltaY)) {
				tempX = (int) (MhoCell[i].getX() + Math.signum(deltaX));

				tempY = MhoCell[i].getY();
				if (isFence(tempX, tempY, FenceCell.length)) {
					MhoCell[i].setX(tempX);
					MhoCell[i].setY(tempY);
					System.out
							.println("Horizontal deltaX>=deltaY kill on fence at position: "
									+ MhoCell[i].getX()
									+ ", "
									+ MhoCell[i].getY());
					killMhos();
					continue;
				}
			}

			// If vertical distance towards Smiley is greater than or equal to
			// the vertical distance, move vertically.
			if (Math.abs(deltaY) >= Math.abs(deltaX)) {
				tempX = MhoCell[i].getX();

				tempY = (int) (MhoCell[i].getY() + Math.signum(deltaY));
				if (isFence(tempX, tempY, FenceCell.length)) {
					MhoCell[i].setX(tempX);
					MhoCell[i].setY(tempY);
					System.out
							.println("Vertical deltaY>=deltaX kill on fence at position: "
									+ MhoCell[i].getX()
									+ ", "
									+ MhoCell[i].getY());
					killMhos();
					continue;
				}
			}

		}
		// Now we sweep for all the mhos that moved onto fences, and kill them.
		killMhos();
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

	/*
	 * isMho searches for the value pair x and y in the MhoCell array up to the
	 * element designated by CellLength. CellLength is used so only assigned
	 * values are searched during initialization.
	 */
	private boolean isMho(int x, int y, int CellLength) {
		for (int i = 0; i < CellLength; i++) {
			if (MhoCell[i].getX() == x && MhoCell[i].getY() == y) {
				return true;
			}
		}
		return false;
	}

	/*
	 * The method actionPerformed holds the cases that will be run when a key is
	 * pressed. The methods increases or decreases an X and/or Y value of the
	 * smiley, prints a line of text to the console, and sets the player turn to
	 * end while also initiating Mho movement.
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (!ingame) {
			return;
		}
		switch (SmileyDirection) {
		// This case is for when the smiley is moved down.
		case DOWN:

			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved left.
		case LEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);

			System.out.println("Left");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved right.
		case RIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);

			System.out.println("Right");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved up.
		case UP:

			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved up and left.
		case UPANDLEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);
			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up and Left");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved up and right.
		case UPANDRIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);
			SmileyCell.setY(SmileyCell.getY() - 1);

			System.out.println("Up and Right");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved down and right.
		case DOWNANDRIGHT:

			SmileyCell.setX(SmileyCell.getX() + 1);
			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down and Right");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the smiley is moved down and left.
		case DOWNANDLEFT:

			SmileyCell.setX(SmileyCell.getX() - 1);
			SmileyCell.setY(SmileyCell.getY() + 1);

			System.out.println("Down and Left");

			SmileyDirection = Direction.NONE;

			mhoMovement();

			break;
		// This case is for when the player jumps. It randomly creates
		// coordinates and sets the smiley to that poisiton. This case does not
		// end the player turn.
		case JUMP:
			int jumpX = RandomNumberInRange(1, 12);
			int jumpY = RandomNumberInRange(1, 12);
			while (isFence(jumpX, jumpY, FenceCell.length) == true) {
				jumpX = RandomNumberInRange(1, 12);
				jumpY = RandomNumberInRange(1, 12);

			}

			SmileyCell.setX(jumpX);
			SmileyCell.setY(jumpY);

			System.out.println("Jump!");

			SmileyDirection = Direction.NONE;

			break;
		// This case is for when the player sits, or ends the turn without any
		// movement.
		case SIT:
			SmileyCell.setY(SmileyCell.getY());
			SmileyCell.setX(SmileyCell.getX());

			SmileyDirection = Direction.NONE;

			System.out.println("Sit");

			mhoMovement();

			break;

		case NONE:

			break;
		}
		repaint();
		if (isMho(SmileyCell.getX(), SmileyCell.getY(), MhoCell.length) == true) {
			GameOver();
			handleLoseMho();
		}

	}
	// To remove the Mhos, this method removes specific elements from the array. Because 
	// we didn't use ArrayList, we had to find a shortcut that allows us to remove 
	// specific elements. So, when a Mho hits a fence, it creates a temporary cell, 
	// then makes the MhoCell equal the temporary array. 
	public void killMhos() {
		// Delete mho from fence
		System.out.println("The mho cell array is " + MhoCell.length + " long");
		for (int x = 0; x < MhoCell.length; x++) {
			if (isFence(MhoCell[x].getX(), MhoCell[x].getY(), FenceCell.length) == true) {

				System.out.println("Killing mho " + x + " at: "
						+ MhoCell[x].getX() + ", " + MhoCell[x].getY());

				Cell[] tempMhoCell = new Cell[MhoCell.length - 1];
				int j = 0;
				for (int i = 0; i < MhoCell.length; i++) {
					if (i != x) {
						tempMhoCell[j] = MhoCell[i];
						j++;
					} // else do nothing
				}
				MhoCell = tempMhoCell;
			}

		}
		System.out.println("After sweeping, the mho cell array is "
				+ MhoCell.length + " long");
		// If there are no Mhos left, then the handleWin is invoked,
		// showing a specific dialogue box to allow the user to restart
		// or end. 

		if (MhoCell.length == 0) {
			// No mho lefts 
			handleWin();
			System.out.println("You won the game!");
		}
	}

	public void handleWin() {
		// Display the showOptionDialog. This is the dialogue box when the user wins 
		int choice = JOptionPane.showOptionDialog(null,
				"You win! Do you want to play again?", "You Won!",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		// Interpret the choice of the user
		if (choice == JOptionPane.YES_OPTION) {
			Restart();
		} else {
			System.exit(0);
		}
	}

	public void handleLoseMho() {
		// Display the showOptionDialog. This dialogue pops up when the player loses to a mho. 
		int choice = JOptionPane.showOptionDialog(null,
				"You were killed by a mho. Do you want to play again?",
				"You Lose", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		// Interpret the choice of the user
		if (choice == JOptionPane.YES_OPTION) {
			Restart();
		} else {
			System.exit(0);
		}
	}

	public void handleLoseFence() {
		// Display the showOptionDialog. This dialogue pops up when you die. 
		int choice = JOptionPane
				.showOptionDialog(
						null,
						"You moved onto a fence and died. Do you want to play again?",
						"You Lose", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);

		// Interpret the choice of the user
		if (choice == JOptionPane.YES_OPTION) {
			Restart();
		} else {
			System.exit(0);
		}
	}

	// This method invokes the methods that draw the cells (Mhos, Fences, etc.) 
	// and the grid. 
	public void paintComponent(Graphics g) {

		drawGrid(g);
		drawCellTypes(g);

	}
	
	// The drawGrid method draws the Grid by drawing the lines of the Grid, then spawning
	// the empty cells in their specific location. 
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
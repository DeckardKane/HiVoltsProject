package v1;

// Imported Libraries 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Cell extends JPanel {

	private static final long serialVersionUID = 1L;
	private int myX, myY; // The X and Y coordinates on grid.
	private Color myColor; // This defines a cell's color based upon whether it
							// is set as "dead" or "alive.
	private Image myImage; // Defines an image that should be draw upon the
							// cell.

	/*
	 * Cell is a method which takes in int x, int y, a Color, and an image. The
	 * inputs are then used as the values for myColor, myX, myY, and myImage.
	 * These will then be used in the method draw to draw the image/color on the
	 * specific cell.
	 */
	public Cell(int x, int y, Color color, Image image) {
		myColor = color;
		myX = x;
		myY = y;
		myImage = image;
	}

	/*
	 * Getters and setters for myX, myY, and myColor. These are used in
	 * GameFrame to get specific values which relate to the cell.
	 */
	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	public Color getColor() {
		return myColor;
	}

	public void setX(int x) {
		myX = x;
	}

	public void setY(int y) {
		myY = y;
	}

	public void setColor(Color color) {
		myColor = color;
	}

	/*
	 * This is the main method which does the drawing of the contents of the
	 * cell. It intakes int x_offset, int y_offset, int width, and int height
	 * and then runs the other draw function using other values found such as
	 * myX, myY, myColor, and myImage.
	 */
	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {
		draw(myX, myY, x_offset, y_offset, width, height, myColor, g, myImage);
	}

	/*
	 * This method is executed through the initial draw method. It intakes int
	 * x, int y, int x_offset, int y_offset, int width, int height, a Color
	 * value which is set to c, a Graphics value which is set to g, and an Image
	 * value. The class then calculates two values, xleft and ytop by using 6 of
	 * the inputed values. Afterwards, it uses the defined Graphics and Color to
	 * set the color of the cell. Finally, there is an if statement to determine
	 * whether an image should be draw in the cell by checking if the status of
	 * the image value is null. If it is then the cell is simply draw without an
	 * image. Otherwise, which is if there is an image value, the image value is
	 * then used in the drawing of the cell as well as some of the other values
	 * which were inputed and calculated.
	 */
	public static void draw(int x, int y, int x_offset, int y_offset,
			int width, int height, Color c, Graphics g, Image image) {
		int xleft = x_offset + 1 + (x * (width + 1));
		int ytop = y_offset + 1 + (y * (height + 1));
		g.setColor(c);
		if (image == null) {
			g.fillRect(xleft, ytop, width, height);
		} else {
			g.drawImage(image, xleft, ytop, width, height, c, null);
		}
	}
}

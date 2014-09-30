package v1;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cell extends JPanel {
	private int myX, myY;

	private Color myColor;
	private boolean myAlive;

	private String myType;

	private final Color SMILEY = Color.ORANGE;
	private final Color DEFAULT_DEAD = Color.GRAY;

	ImageIcon Smiley = new ImageIcon("Smiley.png");
	ImageIcon Sad = new ImageIcon("SadFace.png");
	ImageIcon Fence = new ImageIcon("Fence.jpg");

	public Cell(int x, int y) {
		this(x, y, Color.BLACK, null);
	}

	public Color getColor() {
		return myColor;
	}

	public Cell(int row, int col, Color color, String type) {
		myType = type;
		myX = col;
		myColor = color;
		myY = row;
	}

	public void setSmiley(Boolean Smiley) {
		if (Smiley == true) {
			myColor = Color.ORANGE;
		}
	}

	public String getType() {
		return myType;
	}

	/*
	 * public void setType(String Type, Graphics g){ myType = Type; if(myType ==
	 * "Smiley"){ super.paintComponent(g); Smiley.paintIcon(Smiley, g, 10, 10);
	 * } if(myType == "Sad"){ setImage("SadFace.png"); } if(myType == "Fence"){
	 * setImage("Fence.jpg"); } }
	 */
	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	// Kuszmaul's Example. Not needed right now.
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		// I leave this understanding to the reader.
		int xLeft = xOffset + 1 + (myX * (width + 1));
		int yTop = yOffset + 1 + (myY * (height + 1));
		g.setColor(myColor);
		g.fillRect(xLeft, yTop, width, height);
	}
}

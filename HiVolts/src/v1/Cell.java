package v1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cell extends JPanel {
	
	private int myX, myY; // x,y position on grid
	private Color myColor; // Based on alive/dead rules
	private Image myImage; // optional image

	public Cell(int x, int y, Color color, Image image) {
		myColor = color;
		myX = x;
		myY = y;
		myImage = image;
		
	}

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

	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {
		draw(myX, myY, x_offset, y_offset, width, height, myColor, g, myImage);
	}

	public static void draw(int x, int y, int x_offset, int y_offset,int width, int height, Color c, Graphics g, Image image) {
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

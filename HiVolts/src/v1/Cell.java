package v1;
import java.awt.Color;
import java.awt.Graphics; 
import javax.swing.ImageIcon;

public class Cell {
	private int myX, myY;
	private Color myColor;
	private boolean myAlive; 
	private String myType;
	ImageIcon Smiley = new ImageIcon("Smiley.png"); 
	
	
	public Cell(int row, int col, boolean alive, String type) {
		myAlive = alive;
		myType = type;
		myX = col;
		myY = row;
	}
	
	
	public boolean getAlive() {
		return myAlive;
	}

	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}
	public String getType(){
		return myType;
	}
	
	//Kuszmaul's Example. Not needed right now. 
	private int RandomNumberInRange(int start, int end){
		double randd = Math.random();
		randd *= (end-start + 1);
		randd += start; 
		return (int) randd; 
	}
}

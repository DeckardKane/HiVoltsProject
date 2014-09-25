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
	ImageIcon Smiley = new ImageIcon("Smiley.png"); 
	ImageIcon Sad = new ImageIcon("SadFace.png");
	ImageIcon Fence = new ImageIcon("Fence.jpg");
	public Cell(int x, int y){
		this(x,y,null);
	}
	public Cell(int row, int col, String type) {
		myType = type;
		myX = col;
		myY = row;
	}
	public String getType(){
		return myType;
	}
	
/*	public void setType(String Type, Graphics g){
		myType = Type;
		if(myType == "Smiley"){
			super.paintComponent(g);
			Smiley.paintIcon(Smiley, g, 10, 10);
		}
		if(myType == "Sad"){
			setImage("SadFace.png");
		}
		if(myType == "Fence"){
			setImage("Fence.jpg");
		}
	}
*/
	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	//Kuszmaul's Example. Not needed right now. 
	private int RandomNumberInRange(int start, int end){
		double randd = Math.random();
		randd *= (end-start + 1);
		randd += start; 
		return (int) randd; 
	}
}

package v1;
import java.awt.Color;
import java.awt.Graphics; 
import javax.swing.ImageIcon;

public class Cell {
	private int myX, myY;
	private Color myColor;
	private final Color DEFAULT_ALIVE = Color.ORANGE;
	private final Color DEFAULT_DEAD = Color.GRAY;
	private String type;
	ImageIcon Smiley = new ImageIcon("Smiley.png") 
	

	//Kuszmaul's Example. Not needed right now. 
	private int RandomNumberInRange(int start, int end){
		double randd = Math.random();
		randd *= (end-start + 1);
		randd += start; 
		return (int) randd; 
	}
}

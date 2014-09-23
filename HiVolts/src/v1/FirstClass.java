/* Hello! The authors of this code are: Misha Lavva, Peter Turnbull, and Przemek Gardias
 * 
 * Other contributors/helpers:
 * 
 * Sources of code:
 * 
 * Problems We Encountered:
 * 
 * Decisions We Made:
 * 
 * Assumptions We Made in the Bleak Storm of Ambiguity
 * 
 * Justifications for Choices We Made That Resulted in Fewer Features Than the Requirements Call For:
 * 
 */

package v1;

import java.awt.Dimension;
import javax.swing.JFrame;

public class FirstClass {
	
	private static final int HEIGHT = 864;
	private static final int WIDTH = 864;

	public static void main(String[] args) {
		
		JFrame frame = new Jframe();
		GameFrame f = new GameFrame();
		frame.add(c);
		f.setSize(WIDTH, HEIGHT);
		Display display = new Display(WIDTH, HEIGHT);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("HiVolts");
		f.add(display);
		f.setVisible(true);
	}
}
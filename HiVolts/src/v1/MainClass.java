/* Hello! The authors of this code are: Misha Lavva, Peter Turnbull, and Przemek Gardias
 * 
 * Other contributors/helpers:
 * 
 * Sources of code:
 * http://examples.javacodegeeks.com/desktop-java/awt/event/a-complete-keylistener-example/
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

//Changing this to just include the entire swing library.
import javax.swing.*;

public class MainClass extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765284626059265563L;

	public static void main(String[] args) {
		final int HEIGHT = 1000;
		final int WIDTH = 1200;
		JFrame f = new JFrame();
		f.setSize(WIDTH, HEIGHT);
		GameFrame display = new GameFrame(WIDTH, HEIGHT);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("HiVolts");
		f.add(display);
		f.setVisible(true);
	}
}
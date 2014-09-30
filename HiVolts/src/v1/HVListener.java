/* NOT OURS. This is a modified version of an example keylistener, located here:
 * http://examples.javacodegeeks.com/desktop-java/awt/event/a-complete-keylistener-example/
 */

package v1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class HVListener {
	static int myXPosition = 0;
	static int myYPosition = 0;
	static int turnCount = 0;

	public static void moveUpLeft() {
		myXPosition--;
		myYPosition++;
		turnCount++;
	}

	public static void moveUp() {
		myYPosition++;
		turnCount++;
	}

	public static void moveUpRight() {
		myXPosition++;
		myYPosition++;
		turnCount++;
	}

	public static void moveLeft() {
		myXPosition--;
		turnCount++;
	}

	public static void sit() {
		turnCount++;
	}

	public static void moveRight() {
		myYPosition++;
		turnCount++;
	}

	public static void moveDownLeft() {
		myXPosition--;
		myYPosition--;
		turnCount++;
	}

	public static void moveDown() {
		myYPosition--;
		turnCount++;
	}

	public static void moveDownRight() {
		myXPosition++;
		myYPosition--;
		turnCount++;
	}

	public static void jump() {
		// Random position
		turnCount++;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Key Listener");
		Container contentPane = frame.getContentPane();
		KeyListener listener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				printEventInfo("Key Pressed ", e);
				if (e.getKeyChar() == KeyEvent.VK_Q) {
					moveUpLeft();
				} else if (e.getKeyChar() == KeyEvent.VK_W
						|| e.getKeyChar() == KeyEvent.VK_UP) {
					moveUp();
				} else if (e.getKeyChar() == KeyEvent.VK_E) {
					moveUpRight();
				} else if (e.getKeyChar() == KeyEvent.VK_A
						|| e.getKeyChar() == KeyEvent.VK_LEFT) {
					moveLeft();
				} else if (e.getKeyChar() == KeyEvent.VK_S) {
					sit();
				} else if (e.getKeyChar() == KeyEvent.VK_D
						|| e.getKeyChar() == KeyEvent.VK_RIGHT) {
					moveRight();
				} else if (e.getKeyChar() == KeyEvent.VK_Z) {
					moveDownLeft();
				} else if (e.getKeyChar() == KeyEvent.VK_X
						|| e.getKeyChar() == KeyEvent.VK_DOWN) {
					moveDown();
				} else if (e.getKeyChar() == KeyEvent.VK_C) {
					moveDownRight();
				} else if (e.getKeyChar() == KeyEvent.VK_J
						|| e.getKeyChar() == KeyEvent.VK_SPACE) {
					jump();
				} else {
					System.out.println("Key pressed was invalid.");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				printEventInfo("Key Released ", e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				printEventInfo("Key Typed ", e);
			}

			private void printEventInfo(String str, KeyEvent e) {
				System.out.println(str);
				int code = e.getKeyCode();
				System.out.println("Code: " + KeyEvent.getKeyText(code));
				System.out.println("Char: " + e.getKeyChar());
				int mods = e.getModifiersEx();
				System.out
						.println("Mods: " + KeyEvent.getModifiersExText(mods));
				System.out.println("Location: "
						+ keyboardLocation(e.getKeyLocation()));
				System.out.println("Action?: " + e.isActionKey());
			}

			private String keyboardLocation(int keybrd) {
				switch (keybrd) {
				case KeyEvent.KEY_LOCATION_RIGHT:
					return "Right";
				case KeyEvent.KEY_LOCATION_LEFT:
					return "Left";
				case KeyEvent.KEY_LOCATION_NUMPAD:
					return "NumPad";
				case KeyEvent.KEY_LOCATION_STANDARD:
					return "Standard";
				case KeyEvent.KEY_LOCATION_UNKNOWN:
				default:
					return "Unknown";
				}
			}
		};
		JTextField textField = new JTextField();
		textField.addKeyListener(listener);
		contentPane.add(textField, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
	}
}

package v1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Control {
	static int myXPosition = 0;
	static int myYPosition = 0;
	static boolean playerTurn = true;

	public static void turnEnd() {
		playerTurn = false;
	}

	//Movement Control methods!
	public static void moveUpLeft() {
		myXPosition--;
		myYPosition++;
		turnEnd();
	}

	public static void moveUp() {
		myYPosition++;
		turnEnd();
	}

	public static void moveUpRight() {
		myXPosition++;
		myYPosition++;
		turnEnd();
	}

	public static void moveLeft() {
		myXPosition--;
		turnEnd();
	}

	public static void sit() {
		turnEnd();
	}

	public static void moveRight() {
		myYPosition++;
		turnEnd();
	}

	public static void moveDownLeft() {
		myXPosition--;
		myYPosition--;
		turnEnd();
	}

	public static void moveDown() {
		myYPosition--;
		turnEnd();
	}

	public static void moveDownRight() {
		myXPosition++;
		myYPosition--;
		turnEnd();
	}

	public static void jump() {
		// Random position, need to call GameFrame.RandomNumberInRangeX and GameFrame.RandomNumberInRangeY
		
		turnEnd();
	}
	
	//Turncontrol 
	public void turnControl(){
		if (playerTurn == true) {
			System.out.println("It's the player's turn.");
		} else if (playerTurn == false) {
			System.out.println("Now it's time for the mho's to move!");
		}
			
	}
	
	//Keylistener. Not ours. 
	public static void main(String[] args) {
		JFrame frame = new JFrame("Key Listener");
		Container contentPane = frame.getContentPane();
		KeyListener listener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				printEventInfo("Key Pressed ", e);
				if (playerTurn == true) {
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
				} else {
					System.out.println("It is not your turn!");
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

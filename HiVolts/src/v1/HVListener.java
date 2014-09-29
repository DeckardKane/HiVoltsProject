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

	public static void main(String[] args) {
		JFrame frame = new JFrame("Key Listener");
		Container contentPane = frame.getContentPane();
		KeyListener listener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				printEventInfo("Key Pressed ", e);
				if (e.getKeyChar() == KeyEvent.VK_Q) {

				}
				if (e.getKeyChar() == KeyEvent.VK_W || e.getKeyChar() == KeyEvent.VK_UP) {

				}
				if (e.getKeyChar() == KeyEvent.VK_E) {

				}
				if (e.getKeyChar() == KeyEvent.VK_A || e.getKeyChar() == KeyEvent.VK_LEFT) {

				}
				if (e.getKeyChar() == KeyEvent.VK_S) {

				}
				if (e.getKeyChar() == KeyEvent.VK_D || e.getKeyChar() == KeyEvent.VK_RIGHT) {

				}
				if (e.getKeyChar() == KeyEvent.VK_Z) {

				}
				if (e.getKeyChar() == KeyEvent.VK_X || e.getKeyChar() == KeyEvent.VK_DOWN) {

				}
				if (e.getKeyChar() == KeyEvent.VK_C) {

				}
				if (e.getKeyChar() == KeyEvent.VK_J) {

				}
				else {
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

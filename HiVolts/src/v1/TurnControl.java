package v1;

public class TurnControl {
	//Game Values
	static boolean playerTurn = true;

	public boolean getPlayerTurn() {
		return playerTurn;
	}
	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}

	public void turnControl(){
		if (playerTurn == true) {
			System.out.println("It's the player's turn.");
			/* PSEUDOCODE
			playerTurn = false is handled by the movement methods, which is really clever.
			So whenever we press a valid key and invoke a movement command, the movement method
			will set playerTurn to false.
			preventplayerinput; (how do I implement that?)
			}
			*/
		} else if (playerTurn == false) {
			System.out.println("Now it's time for the mho's to move!");
		}
			
		}
}
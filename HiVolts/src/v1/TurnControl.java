package v1;

public class TurnControl {
	//Game Values
	boolean playerTurn = true;
	//Mho values
	int mhoX;
	int mhoY;
	
	
	public int getMhoX() {
		return mhoX;
	}
	public void setMhoX(int mhoX) {
		this.mhoX = mhoX;
	}
	public int getMhoY() {
		return mhoY;
	}
	public void setMhoY(int mhoY) {
		this.mhoY = mhoY;
	}
	
	public void turnControl(){
		if playerTurn == true {
			print "It's the player's turn.";
			/* PSEUDOCODE
			
			gamestate++;
			preventplayerinput; (how do I implement that?)
			}
			*/
		} else if playerTurn == false {
			print "Now it's time for the mho's to move!"
		}
			
		}
}
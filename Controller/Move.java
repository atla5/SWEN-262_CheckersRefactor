package Controller;

/**
 * Controller.Move.java
 *
 * Version:
 *    $Id: Controller.Move.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *    $Log: Controller.Move.java,v $
 *    Revision 1.1  2002/10/22 21:12:52  se362
 *    Initial creation of case study
 *
 */

/**
 * An object representation of a move.
 *
 */
 public class Move {
	 
	private int startLocation;	// the starting location
	private int endLocation;	// the ending location

	/**
	 *  The player that this move is intended for.
	 */
	public Player thePlayer;

     
	/**
	 * Create a move with the starting location and 
	 * ending location passed in as paremeters.
	 *	
	 * @param startLoc The starting point of the move
	 * @param endLoc   The ending point of the move
	 * 
	 * @pre startLoc and endLoc are valid locations
	 */
	public Move( Player player, int startLoc, int endLoc ) {
	
		thePlayer = player;
		startLocation = startLoc;
		endLocation = endLoc;
	}

     
	/**
	 * Return the player who made this move
	 * 
	 * @return the player who made this move
	 *
	 */
	public Player getPlayer() { return thePlayer; }

     
	/**
	 * Return the starting location of this move.
	 *
	 * @return The starting point of the move.
	 * 
	 * @post nothing has changed
 	 */
	public int getStartLocation() { return startLocation; }

     
	/**
	 * Return the ending location of this move.
	 *
	 * @return The ending point of this location.
	 * 
	 * @post Nothing has changed
	 */
	public int getEndLocation() { return endLocation; }

	/**
	 * Allows printing of /Move/'s in the form
	 * 		"Move: [Player.toString] moves from [startLoc] to [endLoc]."
	 */
	public String toString(){
		return "Move: " + this.getPlayer() + " moves from " + this.getStartLocation() +
				" to " + this.getEndLocation() + ".";
	}
     
} //Controller.Move.java

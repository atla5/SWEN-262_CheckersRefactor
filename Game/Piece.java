package Game;

/**
 * Game.Piece.java
 *
 * Version:
 *   $Id: Game.Piece.java,v 1.1 2002/10/22 21:12:53 se362 Exp $
 *
 * Revisions:
 *   $Log: Game.Piece.java,v $
 *   Revision 1.1  2002/10/22 21:12:53  se362
 *   Initial creation of case study
 *
 */

/**
 * This is an abstract class representing any piece that
 * know about it's color and possible moves and captures
 *
 */

import Controller.Player;

import java.awt.*;

public class Piece {

   private Player player;
	
   private Color color; // the color of the piece
   private int type;

   /**
    * constructor for an empty piece
    */
   public Piece(){
      this.color = null;
      this.type = -1;
   }
      
   /**
    * The constructor for this piece

    * @param c - the color for this piece
    */
   public Piece( Color c ) {
	   color = c;
   }

   public Piece( Player p) { player = p; }

   /**
    *
    * @return the type of the piece
    */
   public int getType(){ return type; }

   /**
    * This method returns the color of this piece
    * 
    * @return the color for this piece
    */
   public Color getColor() { return color; }

   /**
    * returns the player to whom the piece belongs
    */
   public Player getPlayer(){ return this.player; }

}// Game.Piece

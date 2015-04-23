package Game; /**
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
 * @author
 *
 */

import java.util.*;
import java.awt.*;

public abstract class Piece {
	
   private Color color; // the color of the piece
   private int type;

      
   /**
    * The constructor for this piece
    * 
    * @param c - the color for this piece
    */
   public Piece( Color c ) {
	   color = c;
   }

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

}// Game.Piece

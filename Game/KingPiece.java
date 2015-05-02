package Game; /**
 * Game.KingPiece.java
 *
 * Version:
 *   $Id: Game.KingPiece.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *   $Log: Game.KingPiece.java,v $
 *   Revision 1.1  2002/10/22 21:12:52  se362
 *   Initial creation of case study
 *
 */
import Game.Piece;

import java.awt.*;

/**
 * This is a class representing a king piece (a piece that has been kinged)
 *
 * @author
 *
 */
public class KingPiece extends Piece {

   private static int KING = 1; // the king type
   private int type; // the type of his object
  
   /**
    * This constructor creates a king piece object
    * 
    * @param c - the color of this king piece
    */
   public KingPiece( Color c ) {

      super(c);
      type = KING;
   }
   
}//Game.KingPiece

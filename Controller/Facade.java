package Controller; /**
 * Controller.Facade.java
 *
 * Version	
 *   $Id: Controller.Facade.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *   $Log: Controller.Facade.java,v $
 *   Revision 1.1  2002/10/22 21:12:52  se362
 *   Initial creation of case study
 *
 */

import Game.Board;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

/**
 * An interface between the GUI and the kernel classes in a checkers game.
 * 
 * @author
 */

public class Facade extends Component {

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    public static String update       = "update";
    public static String playerSwitch = "switch";
    public static String ID           = "facade";
    
    public Driver   theDriver;
    public Board    theBoard;
    public Player   passivePlayer;
    public Player   activePlayer;
    
    private int startSpace = 99; // Starting space for current move
    private int endSpace   = 99; // Ending space for current move
    
    // The numbers associated with the timer
    private int timer       = 999;
    private int warningTime = 999;
    
    private ActionListener actionListener;
      
    /**
     * Constructor for the facade.  Initializes the data members.
     * 
     * @param newBoard  Game.Board  object Controller.Facade will manipulate.
     * @param newDriver Controller.Driver object that will communicate
     *                  with the Controller.Facade.
     */
    public Facade( Board newBoard, Driver newDriver ){
	
        theBoard = newBoard;
        theDriver = newDriver;
	
    }
    
    /**
     * Set which players turn it is.
     * 
     * @param active  The active player
     * @param passive The passive player
     */
    public void setPlayerModes( Player active, Player passive ){
	
	activePlayer = active;
	passivePlayer = passive;
	
	// Tell GUI to update
	generateActionPerformed( update );
    }

    /**
     * Generates an action. This is inhereted from Componen
     */    
    private void generateActionPerformed( String command ){
	
	if ( actionListener != null ) {
	    actionListener.actionPerformed( 
              new ActionEvent( this, ActionEvent.ACTION_PERFORMED, command ) );
	    // Fires an event associated with timer, or move made on GUI
	}
    }


}// Controller.Facade.java

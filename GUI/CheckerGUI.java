package GUI;

/**
 * GUI.CheckerGUI.java
 * 
 * GUI representation of the board
 * 
 * Version
 * $Id: GUI.CheckerGUI.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 * 
 * Revisions
 * $Log: GUI.CheckerGUI.java,v $
 * Revision 1.1  2002/10/22 21:12:52  se362
 * Initial creation of case study
 *
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.net.*;
import Controller.Facade;
import Game.Board;

/**
 * Main screen for play.
 *  -includes board with tiles, side panel for turn notification and 'draw' and 'resign' buttons
 */
public class CheckerGUI extends JFrame implements ActionListener{
    
    //the facade for the game
    
    private static Facade theFacade; //the facade
    private Vector possibleSquares = new Vector();//a vector of the squares
    private int timeRemaining;//the time remaining
    //the names and time left
    private static String playerOnesName="", playerTwosName="", timeLeft="";

    private JLabel whosTurnLabel, playerOneLabel, playerTwoLabel;
	private String player1, player2;

	/**
     *
     * Constructor, creates the GUI and all its components
     *
     * @param facade the facade for the GUI to interact with
     * @param name1 the first players name
     * @param name2 the second players name
     *
     */
    public CheckerGUI( Facade facade, String name1, String name2 ) {

		//name window
        super("Checkers");

		//long names mess up the way the GUI displays
		//this code shortens the name if it is too long
        String nameOne="", nameTwo="";
        if(name1.length() > 7 ){
            nameOne = name1.substring(0,7);
        }else{
            nameOne = name1;
        }
        if(name2.length() > 7 ){
            nameTwo = name2.substring(0,7);
        }else{
            nameTwo = name2;
        }

		//set player names
        theFacade = facade;
		player1 = name1;
		player2 = name2;

        //register();
		getContentPane().add(mkSidePanel(), BorderLayout.EAST);
		getContentPane().add(mkCenterPanel());

		//frame things
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//        update();
        //updateTime();
    }

	public JPanel mkTopPanel(){

		JPanel topPanel = new JPanel();
		playerOneLabel  = new JLabel("Player:       " + player1 );
		playerOneLabel.setForeground( Color.black );
		topPanel.add(playerOneLabel);
		return topPanel;

	}

	public JPanel mkBottomPanel(){
		JPanel bottomPanel = new JPanel();
		playerTwoLabel = new JLabel("Player 2:     " + player2 );
		playerTwoLabel.setForeground( Color.black );
		bottomPanel.add(playerTwoLabel);
		return bottomPanel;
	}

	public JPanel mkCenterPanel(){
		JPanel centerPanel = new JPanel(new BorderLayout() );
		centerPanel.add(mkTopPanel(),	BorderLayout.NORTH);
		centerPanel.add(mkBottomPanel(),BorderLayout.SOUTH);
		centerPanel.add(mkBoardPanel(),	BorderLayout.CENTER);

		return centerPanel;
	}

	public JPanel mkSidePanel(){

		//create left panel with gridBagLayout
		JPanel sidePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//whosTurnLabel: notifies user of whose turn it is
		whosTurnLabel = new JLabel("Player1's Turn");
		whosTurnLabel.setForeground( new Color( 0, 100 , 0 ) );

		//drawButton
		//@toDo add 'Draw' functionality
		JButton drawButton = new JButton("Draw");


		//resignButton
		//@toDo add 'resign' functionality
		JButton resignButton = new JButton("Resign");



		// -- add components to panel -- //

		//whosTurnLabel
		c.gridy = 1;
		sidePanel.add(whosTurnLabel, c);

		//drawButton
		c.gridy = 2;
		sidePanel.add(drawButton, c);

		//resignButton
		c.gridy = 3;
		sidePanel.add(resignButton, c);


		//return panel
		return sidePanel;
	}

	public JPanel mkBoardPanel(){

		JPanel boardPanel = new JPanel(new GridLayout(8,8));

		//create and add all tiles.
		int id = 0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				JButton tile = new Tile(id);
				tile.setActionCommand(Integer.toString(id));
				tile.addActionListener(this);
				boardPanel.add(tile);
				this.possibleSquares.add(tile);
				id++;
			}
		}

		return boardPanel;
	}

    /**
     * Takes care of input from users, handles any actions performed
     *
     * @param e  the event that has occured
     */
    
    public void actionPerformed( ActionEvent e ) {
        
	try{
	    //if a square gets clicked
	    if( e.getActionCommand().equals(  "1" ) ||
		e.getActionCommand().equals(  "3" ) || 
		e.getActionCommand().equals(  "5" ) ||
		e.getActionCommand().equals(  "7" ) ||
		e.getActionCommand().equals(  "8" ) ||
		e.getActionCommand().equals( "10" ) ||
		e.getActionCommand().equals( "12" ) ||
		e.getActionCommand().equals( "14" ) ||
		e.getActionCommand().equals( "17" ) ||
		e.getActionCommand().equals( "19" ) ||
		e.getActionCommand().equals( "21" ) ||
		e.getActionCommand().equals( "23" ) ||
		e.getActionCommand().equals( "24" ) ||
		e.getActionCommand().equals( "26" ) ||
		e.getActionCommand().equals( "28" ) ||
		e.getActionCommand().equals( "30" ) ||
		e.getActionCommand().equals( "33" ) ||
		e.getActionCommand().equals( "35" ) ||
		e.getActionCommand().equals( "37" ) ||
		e.getActionCommand().equals( "39" ) ||
		e.getActionCommand().equals( "40" ) ||
		e.getActionCommand().equals( "42" ) ||
		e.getActionCommand().equals( "44" ) ||
		e.getActionCommand().equals( "46" ) ||
		e.getActionCommand().equals( "49" ) ||
		e.getActionCommand().equals( "51" ) ||
		e.getActionCommand().equals( "53" ) ||
		e.getActionCommand().equals( "55" ) ||
		e.getActionCommand().equals( "56" ) ||
		e.getActionCommand().equals( "58" ) ||
		e.getActionCommand().equals( "60" ) ||
		e.getActionCommand().equals( "62" ) ) {
		
		//call selectSpace with the button pressed
		theFacade.selectSpace(
				   Integer.parseInt( e.getActionCommand() ) );
		
		//if draw is pressed
	    }else if( e.getActionCommand().equals( "draw" ) ){
		//does sequence of events for a draw
		theFacade.pressDraw();
		
		//if resign is pressed
	    }else if( e.getActionCommand().equals( "resign" ) ) {
		//does sequence of events for a resign
		theFacade.pressQuit();
		
		//if the source came from the facade
	    }else if( e.getSource().equals( theFacade ) ) {
		
		//if its a player switch event
		if ( (e.getActionCommand()).equals(theFacade.playerSwitch) ) {
		    //set a new time
//		    timeRemaining = theFacade.getTimer();
		    //if it is an update event
		} else if ( (e.getActionCommand()).equals(theFacade.update) ) {
		    //update the GUI
		    update();
		} else {
		    throw new Exception( "unknown message from facade" );
		}
	    }
	    //catch various Exceptions
	}catch( NumberFormatException excep ){
	    System.err.println(
		     "GUI exception: Error converting a string to a number" );
	}catch( NullPointerException exception ){
	    System.err.println( "GUI exception: Null pointerException "
				+ exception.getMessage() );
	    exception.printStackTrace();
	}catch( Exception except ){
	    System.err.println( "GUI exception: other: "
				+ except.getMessage() );
	    except.printStackTrace();
	}
	
    }
    

    /**
     * Updates the GUI reading the pieces in the board
     * Puts pieces in correct spaces, updates whos turn it is
     */
    private void update(){

	if( checkEndConditions() ){
	    
	    theFacade.showEndGame(" ");
	}
	//the board to read information from
	Board board = theFacade.stateOfBoard();
	//a temp button to work with
	JButton temp =  new JButton();
	
	//go through the board
	for( int i = 1; i < board.sizeOf(); i++ ){
	    
	    // if there is a piece there
	    if( board.occupied( i ) ){
		
		//check to see if color is blue
		if( board.colorAt( i ) == Color.blue ){

		    //if there is a  single piece there
		    if((board.getPieceAt(i)).getType() == Board.SINGLE){

			//show a blue single piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			//try{
                System.out.println("We made it");
                ImageIcon iI = new ImageIcon("GUI/BlueSingle.gif");
                board.getPieceAt(i);

                temp.setIcon(iI);
			//}//catch( MalformedURLException e ){
			    //System.out.println(e.getMessage());
			//}

			//if there is a kinged piece there
		    }else if((board.getPieceAt(i)).getType() == Board.KING){

			//show a blue king piece in that spot board
			temp= (JButton)possibleSquares.get(i);

			//get the picture formt the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:BlueKing.gif") ) );
			}catch( Exception e ){}
			
		    }

		    //check to see if the color is white        
		}else if( board.colorAt(i) == Color.white ){

		    //if there is a single piece there
		    if((board.getPieceAt(i)).getType() == Board.SINGLE){

			//show a blue single piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:WhiteSingle.gif")));
			}catch( Exception e ){}
			
			//if there is a kinged piece there
		    }else if((board.getPieceAt(i)).getType() == Board.KING){

			//show a blue king piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:WhiteKing.gif") ) );
			}catch( Exception e ){}
		    }
                                //if there isnt a piece there        
		}
	    }else {
		//show no picture
		temp = (JButton)possibleSquares.get(i);
		temp.setIcon( null );
	    }
	}
	
	//this code updates whos turn it is
	if(theFacade.whosTurn() == 2 ){
	    playerTwoLabel.setForeground( Color.red );
	    playerOneLabel.setForeground(Color.black );
		whosTurnLabel.setText( player2 + "'s turn");
	}else if(theFacade.whosTurn() == 1 ){
	    playerOneLabel.setForeground( Color.red );
	    playerTwoLabel.setForeground(Color.black );
	    whosTurnLabel.setText( player1 + "'s turn" );
	}
    }


    /**
     * Checks the ending condotions for the game
     * see if there a no pieces left
     *
     * @return the return value for the method
     *           true if the game should end
     *           false if game needs to continue 
     */
	public boolean checkEndConditions(){
           
	    //the return value
            boolean retVal = false;
            try{
		//the number of each piece left
		int whitesGone = 0 , bluesGone = 0;
		
		//the board to work with
		Board temp = theFacade.stateOfBoard();
		
		//go through all the spots on the board
		for( int i=1; i<temp.sizeOf(); i++ ){
		    //if there is a piece there
		    if( temp.occupied( i  ) ){
			//if its a blue piece there
			if( (temp.getPieceAt( i )).getColor() == Color.blue ){
			    // increment number of blues
			    bluesGone++;
			    //if the piece is white
			}else if( (temp.getPieceAt( i )).getColor()
				  == Color.white ){
			    //increment number of whites
			    whitesGone++;
			}
		    }
		}//end of for loop
		
		//if either of the number are 0
		if( whitesGone == 0 || bluesGone == 0 ){
		    retVal = true;
		}

            }catch( Exception e ){
                
                System.err.println( e.getMessage() );            
                
            }
            return retVal;
            
        }//checkEndConditions

	public static void main(String[] args){
		CheckerGUI checkerGUI = new CheckerGUI(null,"player1 has a really long name","player2");
	}

}//checkerGUI.java

//bulb

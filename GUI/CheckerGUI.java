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
import Controller.Facade;
import Game.Board;

/**
 * Main screen for play.
 *  -includes board with tiles, side panel for turn notification and 'draw' and 'resign' buttons
 */
public class CheckerGUI extends JFrame implements ActionListener{
    
    private Tile[] tiles;
    private JLabel whoseTurnLabel, playerOneLabel, playerTwoLabel;
	private String player1, player2;
    private Mediator theMediator;
    public Board    theBoard;

    private int startSpace = 99; // Starting space for current move
    private int endSpace   = 99; // Ending space for current move

	/**
     *
     * Constructor, creates the GUI and all its components
     *
     * @param facade the facade for the GUI to interact with
     * @param name1 the first players name
     * @param name2 the second players name
     *
     */
    public CheckerGUI( GUIMediator med, String name1, String name2) {

		//name window
        super("Checkers");

		//initialize list of tiles
		tiles = new Tile[64];

		//set player names
        theMediator = med;
		player1 = name1;
		player2 = name2;
        theBoard = new Board();

		//add panel components
		getContentPane().add(mkSidePanel(), BorderLayout.EAST);
		getContentPane().add(mkCenterPanel());

        update();
        //updateTime();

		//frame things
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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

		//whoseTurnLabel: notifies user of whose turn it is
		whoseTurnLabel = new JLabel("Player1's Turn");
		whoseTurnLabel.setForeground(new Color(0, 100, 0));

		//drawButton
		//@toDo add 'Draw' functionality
		JButton drawButton = new JButton("Draw");
		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				theMediator.pressDraw();
			}
		});

		//resignButton
		//@toDo add 'resign' functionality
		JButton resignButton = new JButton("Resign");
		resignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
                theMediator.endInQuit();
			}
		});


		// -- add components to side panel -- //

		//whoseTurnLabel
		c.gridy = 1;
		sidePanel.add(whoseTurnLabel, c);

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
				final Tile tile = new Tile(id);
				tile.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {

						selectSpace(tile.getID());

					}
				});
				boardPanel.add(tile);
				tiles[id] = tile;
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
		
		//if the source came from the facade
	    if( e.getSource().equals( theMediator ) ) {
		
		//if its a player switch event
		if( (e.getActionCommand()).equals("update") ) {
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
	    
	    theMediator.showEndGame(" ");
	}

	//a temp button to work with
	Tile t;

	//go through the board
	for( int id = 0; id < theBoard.sizeOf(); id++ ){

		t = tiles[id];

		if(t != null && theBoard.getPieceAt(id) != null) {

			// if there is a piece there
			if (theBoard.occupied(id)) {


				//check to see if color is blue
				if (theBoard.colorAt(id) != null && theBoard.colorAt(id) == Color.blue) {

					//if there is a  single piece there
					if ((theBoard.getPieceAt(id)).getType() == Board.SINGLE) {

						//get the picture from the web
						try {
							t.setIcon(new ImageIcon("BlueSingle.gif"));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						//if there is a kinged piece there
					} else if ((theBoard.getPieceAt(id)).getType() == Board.KING) {

						//get the picture formt the web
						try {
							t.setIcon(new ImageIcon("BlueKing.gif"));
						} catch (Exception e) {
						}

					}

					//check to see if the color is white
				} else if (theBoard.colorAt(id) != null && theBoard.colorAt(id) == Color.white) {

					//if there is a single piece there
					if ((theBoard.getPieceAt(id)).getType() == Board.SINGLE) {

						//get the picture from the web
						try {
							t.setIcon(new ImageIcon("WhiteSingle.gif"));
						} catch (Exception e) {
						}

						//if there is a kinged piece there
					} else if ((theBoard.getPieceAt(id)).getType() == Board.KING) {

						//get the picture from the web
						try {
							t.setIcon(new ImageIcon("WhiteKing.gif"));
						} catch (Exception e) {
						}
					}
				}

				//if there isnt a piece there
			} else {
				//show no picture
				t.setIcon(null);
			}
		}
	}
	
	//this code updates whos turn it is
	if(theMediator.whosTurn() == 2 ){
	    playerTwoLabel.setForeground( Color.red );
	    playerOneLabel.setForeground(Color.black );
		whoseTurnLabel.setText(player2 + "'s turn");
	}else if(theMediator.whosTurn() == 1 ){
	    playerOneLabel.setForeground( Color.red );
	    playerTwoLabel.setForeground(Color.black );
	    whoseTurnLabel.setText(player1 + "'s turn");
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
		
		//go through all the spots on the board
		for( int i=1; i<theBoard.sizeOf(); i++ ){
		    //if there is a piece there
		    if( theBoard.occupied( i  ) ){
			//if its a blue piece there
			if( (theBoard.getPieceAt( i )).getColor() == Color.blue ){
			    // increment number of blues
			    bluesGone++;
			    //if the piece is white
			}else if( (theBoard.getPieceAt( i )).getColor()
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

    /**
     *
     * This method should be called to select a space on the board,
     * either as the starting point or the ending point for a move.
     * The Controller.Facade will interpret this selection and send a move on to
     * the kernel when two spaces have been selected.
     *
     * @param space an int indicating which space to move to,
     *              according to the standard checkers numbering
     *              scheme, left to right and top to bottom.
     */
    public void selectSpace( int space ){

        // When button is click, take info from the GUI
        if( startSpace == 99 ){

            // Set startSpace to space
            startSpace = space;

        }else if( startSpace != 99 && endSpace == 99 ){
            if( space == startSpace ){

                // Viewed as un-selecting the space selected
                // Set startSpace to predetermined unselected value
                startSpace = 99;

            }else{
                // The endSpace will be set to space
                endSpace = space;
                theMediator.makeLocalMove();
            }
        }
        this.update();

    }

	public static void main(String[] args){
		CheckerGUI checkerGUI = new CheckerGUI(null,"player1 has a really long name","player2");
	}

}//checkerGUI.java

//bulb

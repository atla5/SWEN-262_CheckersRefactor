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
    
    //the facade for the game
    private static Facade theFacade; //the facade
    private Tile[] tiles;
    private JLabel whoseTurnLabel, playerOneLabel, playerTwoLabel;
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

		//initialize list of tiles
		tiles = new Tile[64];

		//set player names
        theFacade = facade;
		player1 = name1;
		player2 = name2;

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
				theFacade.pressDraw();
			}
		});

		//resignButton
		//@toDo add 'resign' functionality
		JButton resignButton = new JButton("Resign");
		resignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				theFacade.pressQuit();
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
						JOptionPane.showMessageDialog(null,"tile " + tile.getID() + "pressed.");
						theFacade.selectSpace(tile.getID());

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
	    if( e.getSource().equals( theFacade ) ) {
		
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
	Board board = theFacade.getBoard();

	//a temp button to work with
	Tile t;

	//go through the board
	for( int id = 0; id < board.sizeOf(); id++ ){

		t = tiles[id];

		if(t != null) {

			// if there is a piece there
			if (board.occupied(id)) {


				//check to see if color is blue
				if (board.colorAt(id) != null && board.colorAt(id) == Color.blue) {

					//if there is a  single piece there
					if ((board.getPieceAt(id)).getType() == Board.SINGLE) {

						//get the picture from the web
						try {
							t.setIcon(
									new ImageIcon("BlueSingle.gif"));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						//if there is a kinged piece there
					} else if ((board.getPieceAt(id)).getType() == Board.KING) {

						//get the picture formt the web
						try {
							t.setIcon(new ImageIcon("BlueKing.gif"));
						} catch (Exception e) {
						}

					}

					//check to see if the color is white
				} else if (board.colorAt(id) != null && board.colorAt(id) == Color.white) {

					//if there is a single piece there
					if ((board.getPieceAt(id)).getType() == Board.SINGLE) {

						//get the picture from the web
						try {
							t.setIcon(new ImageIcon("WhiteSingle.gif"));
						} catch (Exception e) {
						}

						//if there is a kinged piece there
					} else if ((board.getPieceAt(id)).getType() == Board.KING) {

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
			}
		}
	}
	
	//this code updates whos turn it is
	if(theFacade.whosTurn() == 2 ){
	    playerTwoLabel.setForeground( Color.red );
	    playerOneLabel.setForeground(Color.black );
		whoseTurnLabel.setText(player2 + "'s turn");
	}else if(theFacade.whosTurn() == 1 ){
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
		
		//the board to work with
		Board temp = theFacade.getBoard();
		
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

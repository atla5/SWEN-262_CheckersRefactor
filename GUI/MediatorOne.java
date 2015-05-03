package GUI;

import Controller.Driver;
import Controller.Facade;
import Controller.Player;
import GUI.Secondscreen.BtnSSOk;
import GUI.Firstscreen.BtnFSOk;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Created by Aaron on 5/2/2015.
 */
public class MediatorOne implements Mediator{

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    public Firstscreen firstscreen;
    public static Secondscreen secondscreen;
    public Driver theDriver;
    public Facade theFacade;
    BtnSSOk btnSSOk;
    BtnFSOk btnFSOk;

    public MediatorOne(Facade f){
        theFacade = f;
    }
    /**
     * Create a player with the given type and player number.
     *
     * @param num  Int for player number (either 1 or 2)
     * @param type Int for type of player (Local, network, etc.)
     */
    public void createPlayer( int num, int type ) {

        if ( type == HOSTGAME || type == CLIENTGAME ) {
            theDriver.createPlayer( num, Player.NETWORKPLAYER, "UnNamedPlayer" );
        } else {
            theDriver.createPlayer( num, Player.LOCALPLAYER, "UnNamedPlayer" );
        }
    }
    public void registerSSOk(BtnSSOk ssOk){
        btnSSOk = ssOk;
    }
    public void registerFSOk(BtnFSOk fsOk){btnFSOk = fsOk;}

    public void FSOk(Firstscreen first, Secondscreen second){

        //a temporary button to use for determining the game type
        ButtonModel tempButton = first.gameModes.getSelection();
        try {
            //if check to see of the local radio button is selected
               if (tempButton.getActionCommand().equals("local")) {

                //set up a local game
                first.theFacade.setGameMode(theFacade.LOCALGAME);

                theFacade.createPlayer(1, theFacade.LOCALGAME);
                theFacade.createPlayer(2, theFacade.LOCALGAME);

                //hide the GUI.Firstscreen, make a GUI.Secondscreen and show it
                first.setVisible(false);
                second.setVisible(true);
                }
            }catch( Exception x ) {
                System.err.println( x.getMessage() );
            }
    }
    public void SSOk() {
        if (secondscreen.playerOneField.isEnabled()) {
            if ((secondscreen.playerOneField.getText()).equalsIgnoreCase("")) {
                secondscreen.playerOneField.setText("player1");
            }
        }

        if (secondscreen.playerTwoField.isEnabled()) {
            if ((secondscreen.playerTwoField.getText()).equalsIgnoreCase("")) {
                secondscreen.playerTwoField.setText("player2");
            }
        }

        theFacade.setPlayerName(1, secondscreen.playerOneField.getText());
        theFacade.setPlayerName(2, secondscreen.playerTwoField.getText());

        //if a timer is desired
//		if ( timedGameBox.isEnabled() ) {
//		    if( timedGameBox.getState() ){
//
//			//set the 2 timer values
//			try {
//
//			    theFacade.setTimer( turnLengthField.getValue(),
//					warningLengthField.getValue() );
//
//			} catch ( Exception x ) {
//
//			    JOptionPane.showMessageDialog( null,
//							   "Invalid Game.Timer value(s)",
//							   "Error",
//							   JOptionPane.INFORMATION_MESSAGE );
//			}
//			//else set timer values to a no timer constant
//		    } else {
//			theFacade.setTimer( -1, -1 );
//
//		    }
//		} else {
//		    theFacade.setTimer( -1, -1 );
//
//		}

        //start the game
        theFacade.startGame();
        //hide this screen, make and show the GUI
        secondscreen.hide();
        CheckerGUI GUI = new CheckerGUI(theFacade, theFacade.getPlayerName(1),
                theFacade.getPlayerName(2));
        GUI.show();
    }
}

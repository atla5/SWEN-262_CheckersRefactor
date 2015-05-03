package GUI;

import Controller.Driver;
import Controller.Facade;
import Controller.Player;
import GUI.Secondscreen.BtnSSOk;
import GUI.Firstscreen.BtnFSOk;
import GUI.Firstscreen.BtnFCancel;
import GUI.Secondscreen.BtnSCancel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aaron on 5/2/2015.
 */
public class MediatorOne implements Mediator{

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    public Driver theDriver;
    public Facade theFacade;
    BtnSSOk btnSSOk;
    BtnFSOk btnFSOk;
    BtnFCancel btnFCancel;
    BtnSCancel btnSCancel;

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
    public void registerSSOk(BtnSSOk ssOk){btnSSOk = ssOk;}
    public void registerFSOk(BtnFSOk fsOk){btnFSOk = fsOk;}
    public void registerFCancel(BtnFCancel cancel){btnFCancel = cancel;}
    public void registerSCancel(BtnSCancel cancel){btnSCancel = cancel;}

    public void FSOk(Firstscreen first, Secondscreen second){

        //a temporary button to use for determining the game type
        ButtonModel tempButton = first.gameModes.getSelection();
        try {
            //if check to see of the local radio button is selected
               if (tempButton.getActionCommand().equals("local")) {

                   //set up a local game
                   theFacade.setGameMode(theFacade.LOCALGAME);

                   theFacade.createPlayer(1, theFacade.LOCALGAME);
                   theFacade.createPlayer(2, theFacade.LOCALGAME);
               }
                else if(tempButton.getActionCommand().equals("host")){
                   first.theFacade.setGameMode(theFacade.HOSTGAME);

                   theFacade.createPlayer(1, theFacade.HOSTGAME);
                   theFacade.createPlayer(2, theFacade.HOSTGAME);
               }
               else if( tempButton.getActionCommand().equals( "join" ) ){

                   //set up to join a game
                   theFacade.setGameMode( theFacade.CLIENTGAME );

                   theFacade.createPlayer( 1, theFacade.CLIENTGAME );
                   theFacade.createPlayer( 2, theFacade.CLIENTGAME );

                   //try to connect
                   try {

                       //create a URL from the IP address in the IPfield
                       URL address = new URL( "http://" + first.IPField.getText() );
                       //set the host
                       theFacade.setHost( address );

                       //hide the GUI.Firstscreen, make and show the Second screen
                       second.changeGameType(theFacade.CLIENTGAME );
                       //catch any exceptions
                   } catch ( MalformedURLException x ) {
                       JOptionPane.showMessageDialog( null,
                               "Invalid host address",
                               "Error",
                               JOptionPane.INFORMATION_MESSAGE );
                   }//end of networking catch statement

                   //set up to connect to another person

            //if they hit cancel exit the game
               }
                //hide the GUI.Firstscreen, make second screen visible
                first.setVisible(false);
                second.setVisible(true);
            }catch( Exception x ) {
                System.err.println( x.getMessage() );
            }
    }

    public void FCancel(){
        System.exit( 0 );
    }

    public void SSOk(Secondscreen second) {
        if (second.playerOneField.isEnabled()) {
            if ((second.playerOneField.getText()).equalsIgnoreCase("")) {
                second.playerOneField.setText("player1");
            }
        }

        if (second.playerTwoField.isEnabled()) {
            if ((second.playerTwoField.getText()).equalsIgnoreCase("")) {
                second.playerTwoField.setText("player2");
            }
        }

        theFacade.setPlayerName(1, second.playerOneField.getText());
        theFacade.setPlayerName(2, second.playerTwoField.getText());

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
        second.setVisible(false);
        CheckerGUI GUI = new CheckerGUI(theFacade, theFacade.getPlayerName(1),
                theFacade.getPlayerName(2));
        GUI.setVisible(true);
    }

    @Override
    public void SCancel(Firstscreen first, Secondscreen second) {
        second.setVisible(false);
        first.setVisible(true);
    }
}

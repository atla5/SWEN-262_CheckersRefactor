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
public class GUIMediator implements Mediator{

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    private Driver theDriver;
    private Firstscreen first;
    private  Secondscreen second;
    BtnSSOk btnSSOk;
    BtnFSOk btnFSOk;
    BtnFCancel btnFCancel;
    BtnSCancel btnSCancel;

    private int startSpace = 99; // Starting space for current move
    private int endSpace   = 99; // Ending space for current move

    public GUIMediator(Driver d){
        this.theDriver = d;

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

    public void FSOk(){

        //a temporary button to use for determining the game type
        ButtonModel tempButton = first.gameModes.getSelection();
        try {
            //if check to see of the local radio button is selected
               if (tempButton.getActionCommand().equals("local")) {

                   //set up a local game
                   theDriver.setGameMode(LOCALGAME);
                   second.changeGameType(LOCALGAME);
                   theDriver.createPlayer(1, Player.LOCALPLAYER, "UnNamedPlayer" );
                   theDriver.createPlayer(2, Player.LOCALPLAYER, "UnNamedPlayer" );


               }
                else if(tempButton.getActionCommand().equals("host")){
                   theDriver.setGameMode(HOSTGAME);
                   second.changeGameType(HOSTGAME);
                   theDriver.createPlayer(1, Player.NETWORKPLAYER, "UnNamedPlayer" );
                   theDriver.createPlayer(2, Player.NETWORKPLAYER, "UnNamedPlayer" );

               }
               else if( tempButton.getActionCommand().equals( "join" ) ){

                   //set up to join a game
                   theDriver.setGameMode(CLIENTGAME);

                   theDriver.createPlayer(1, Player.NETWORKPLAYER, "UnNamedPlayer" );
                   theDriver.createPlayer(2, Player.NETWORKPLAYER, "UnNamedPlayer" );

                   //try to connect
                   try {

                       //create a URL from the IP address in the IPfield
                       URL address = new URL( "http://" + first.IPField.getText() );
                       //set the host
                       theDriver.setHost( address );

                       //hide the GUI.Firstscreen, make and show the Second screen
                       second.changeGameType(CLIENTGAME );
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
                this.first.setVisible(false);
                this.second.setVisible(true);
            }catch( Exception x ) {
                System.err.println( x.getMessage() );
            }
    }

    public void FCancel(){
        System.exit( 0 );
    }

    public void SSOk() {
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

        theDriver.setPlayerName(1, second.playerOneField.getText());
        theDriver.setPlayerName(2, second.playerTwoField.getText());

        //start the game
        theDriver.startGame();
        //hide this screen, make and show the GUI
        second.setVisible(false);
        CheckerGUI GUI = new CheckerGUI(this, theDriver.getPlayerOne().getName(),
                theDriver.getPlayerTwo().getName());
        GUI.setVisible(true);
    }

    @Override
    public void SCancel() {
        this.second.setVisible(false);
        this.first.setVisible(true);
    }

    public void setFirstScreen(Firstscreen f){
        this.first = f;
    }

    public void setSecondScreen(Secondscreen s){
        this.second = s;
    }

    /**
     * Send a move on to the kernel, i.e. call makeMove() in
     * the Controller.LocalPlayer and inform it whose turn it is.
     *
     * @pre startSpace is defined
     * @pre endSpace is defined
     */
    public void makeLocalMove(){

        //make sure startSpace and endSpace are defined
        if( startSpace != 99 && endSpace!= 99 ){
            // Takes the information of a move and calls makeMove()
            // in a localPlayer
            boolean result = theDriver.activePlayer.makeMove( startSpace, endSpace );
        }
        // Reset startSpace and endSpace to 99
        startSpace = 99;
        endSpace   = 99;
    }

    public void pressDraw(){

        // Alerts both players and the kernel that one person
        // has offered a draw calls offerDraw() on both players
        theDriver.drawOffered(theDriver.activePlayer);

    }

    public void endInQuit(){
        theDriver.endInQuit( theDriver.activePlayer );
    }

    public void showEndGame(String message){
        theDriver.endGame( message );
    }

    public int whosTurn(){
        return theDriver.activePlayer.getNumber();
    }
}

package GUI;

import Controller.Driver;
import Controller.Facade;
import Controller.Player;

import java.io.FileNotFoundException;

/**
 * Created by Aaron on 5/2/2015.
 */
public class MediatorOne {

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    public Firstscreen firstscreen;
    public Secondscreen secondscreen;
    public Driver theDriver;
    public Facade theFacade;

    public MediatorOne(Facade f){
        theFacade = f;
        firstscreen = new Firstscreen(theFacade);
        secondscreen = new Secondscreen(f, firstscreen, 0);
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
}

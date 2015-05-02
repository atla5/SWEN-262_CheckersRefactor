package GUI;
import GUI.Secondscreen.*;

import java.awt.*;

/**
 * Created by Kevin on 5/2/2015.
 */
class SecondScreenMediator implements Mediator2{

    BtnOk btnOk;

    public void registerOk(BtnOk k){
        btnOk = k;
    }

    public void Ok(){

        if ( playerOneField.isEnabled() ) {
            if ( ( playerOneField.getText() ).equalsIgnoreCase( "" ) ) {
                playerOneField.setText( "player1" );
            }
        }

        if ( playerTwoField.isEnabled() ) {
            if ( ( playerTwoField.getText() ).equalsIgnoreCase( "" ) ) {
                playerTwoField.setText( "player2" );
            }
        }

        theFacade.setPlayerName( 1, playerOneField.getText() );
        theFacade.setPlayerName( 2, playerTwoField.getText() );

        //if a timer is desired
//		if ( timedGameBox.isEnabled() ) {
//		    if( timedGameBox.getState() ){
//
//			//set the 2 timer values
//			try {
//
//			    theFacade.setTimer( turnLengthField.getValue(),
//						warningLengthField.getValue() );
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
        this.hide();
        CheckerGUI GUI = new CheckerGUI( theFacade, theFacade.getPlayerName( 1 ),
                theFacade.getPlayerName( 2 ) );
        GUI.show();

        //if they hit cancel go to the previous screen
    } else if( e.getActionCommand().equals( "cancel" ) ) {
        this.hide();
        theFirst.show();

        //handle whether or not a timer is desired
    } else if ( e.getSource() instanceof Checkbox) {

        if( timedGameBox.getState() ){
            turnLengthField.setEnabled( true );
            warningLengthField.setEnabled( true );
        } else {
            turnLengthField.setEnabled( false );
            warningLengthField.setEnabled( false );
        }
    }

} catch( Exception x ){
        x.printStackTrace();
        }

        }

    }

}

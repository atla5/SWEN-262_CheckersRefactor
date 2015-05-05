package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Representation for a checkerboard tile.
 */
public class Tile extends JButton{

    // location on board
    private int id;

    //@todo consider moving to other class
    private static Color color1 = Color.white;
    private static Color color2 = new Color(204, 204, 153);
    private static Dimension size = new Dimension(80,80);

    /**
     * construct tile with ID
     * @param newId
     */
    public Tile(int newId){
        this.id = newId;
        this.setPreferredSize(size);
        this.setTileColor(id);
    }
    /**
     * set Tile's background color given the int
     * @param id
     */
    public void setTileColor(int id){

        //alternate starting color by row
        if( (id/8) % 2 != 0 ){ id++; }

        //alternate color by column
        if(id%2 == 0){
            this.setBackground(color1);
        }else{
            this.setBackground(color2);
        }
    }

    public int getID(){ return this.id; }
}

package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Representation for a checkerboard tile.
 */
public class Tile extends JButton implements ActionListener{

    // location on board
    private int id;

    //@todo consider moving to other class
    private static Color color1 = Color.white;
    private static Color color2 = new Color(204, 204, 153);
    private boolean isOccupied;

    /**
     * construct tile with ID
     * @param newId
     */
    public Tile(int newId){
        this.id = newId;
        this.addActionListener(this);
        this.setPreferredSize(new Dimension(80,80));
        this.setColor(id);
        this.isOccupied = false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //getID();
    }

    /**
     * set Tile's background color given the int
     *
     * @todo switch to another algorithm for correct checkerboard pattern
     * @param id
     */
    public void setColor(int id){
        if(id%2 == 0){
            this.setBackground(color1);
        }else{
            this.setBackground(color2);
        }
    }

    public int getID(){ return this.id; }
}

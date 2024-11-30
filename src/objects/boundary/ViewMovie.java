//boundary, where when the user clicks on a movie, takes them to a page with the movie +full description

//purchase ticket, display seatmpa/browse available seats, view showtimes

package objects.boundary;

import objects.control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewMovie extends JPanel {
    //components
    private JLabel movietitle;
    private JLabel genreLabel;
    private JLabel ratingLabel;
    private JLabel runtimeLabel;
    private JLabel movieCover;

    private JButton showTimes;
    private JButton seatMap;
    private JButton purchaseTicket;
    
    
    private JFrame frame; //reference to parent frame

    private appGUI parent;

    //ctor
    public ViewMovie(appGUI parent){
        this.parent = parent;

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);


       
       

    }

    public void displayViewMovie(){
        
    }

    //temporary main method for testing

    /*
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new ViewMovie(frame));
        frame.setVisible(true);
    
    }
    */

}
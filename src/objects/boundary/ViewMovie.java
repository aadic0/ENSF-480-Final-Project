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

    private JButton showTimes;
    private JButton seatMap;
    private JButton purchaseTicket;
    
    
    private JFrame frame; //reference to parent frame

    //ctor
    public ViewMovie(JFrame mainFrame){
        this.frame = mainFrame;
        

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("Movie Ticket-Booking App");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel


    }

    public void viewMovie(String movieName){

    }
    

    //temporary main method for testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new ViewMovie(frame));
        frame.setVisible(true);
    
    }

}
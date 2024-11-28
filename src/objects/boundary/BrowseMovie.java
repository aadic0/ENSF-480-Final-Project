//boundary, browse movies 

package objects.boundary;

import objects.control.*;
//import objects.entity.RegisteredUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BrowseMovie extends JPanel {
    //components
    private JLabel title;
    private JTextField searchMovie;
    private JButton searchButton;

    //controller
    private ShowtimeController showControl;

    private JFrame frame; //reference to parent frame

    //ctor
    public BrowseMovie(JFrame frame){
        this.frame = frame;
        this.showControl = new ShowtimeController();

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

        /*
        //retreive movies?
        HashMap<Integer, ArrayList<Object>> movieMap = showControl.retrieveAllMovies(Connection con); //how to get connection to pass?
        */

    }
    



    //temporary main method for testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new BrowseMovie(frame));
        frame.setVisible(true);
    
    }

}
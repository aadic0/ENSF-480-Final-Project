//boundary, where when the user clicks on a movie, takes them to a page with the movie +full description

//purchase ticket, display seatmpa/browse available seats, view showtimes


package objects.boundary;

import objects.control.*;
import objects.entity.Movie;

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
    
    
    //private JFrame frame; //reference to parent frame

    private appGUI parent;

    //ctor
    public ViewMovie(appGUI parent){
        this.parent = parent;

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);

        movieCover = new JLabel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 8;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        add(movieCover, constraints);

        //reset constraints for labels

        constraints.gridheight = 1; 
        constraints.fill = GridBagConstraints.HORIZONTAL; 


        movietitle = new JLabel("Title:");
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(movietitle, constraints);

        genreLabel = new JLabel("Genre:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(genreLabel, constraints);

        ratingLabel = new JLabel("Rating:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(ratingLabel, constraints);

        runtimeLabel = new JLabel("Runtime:");
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(runtimeLabel, constraints);


        /*buttons */

        showTimes = new JButton("ShowTimes");
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(showTimes, constraints);

        seatMap = new JButton("Available Seats");
        constraints.gridx = 2;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(seatMap, constraints);
        
        purchaseTicket = new JButton("Purchase Ticket");
        constraints.gridx = 3;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(purchaseTicket, constraints);

        /*action listeners */



        


       
       

    }

    public void setMovieDetails(Movie movie) {
        movietitle.setText("Title: " + movie.getName());
        genreLabel.setText("Genre: " + movie.getGenre());
        ratingLabel.setText("Rating: " + movie.getRating());
        runtimeLabel.setText("Runtime: " + movie.getRuntime().toString());
        //load and resize cover
        ImageIcon originalIcon = new ImageIcon("Images/cover.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        movieCover.setIcon(scaledIcon);
    }

    public void showTimesPopUp(){
        
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
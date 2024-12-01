//boundary, browse movie announcments

package objects.boundary;

import objects.control.*;
//import objects.entity.RegisteredUser;
import objects.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BrowseAnnouncment extends JPanel {
    //components
    private JLabel title;
    private appGUI parent;

    //controller
    private AnnouncementController aControl;

    //private JFrame frame; //reference to parent frame

    //ctor
    public BrowseAnnouncment(appGUI parent){
        this.parent = parent;
        this.aControl = new AnnouncementController();

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("View Announcements");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel

        //connect to database
        Connection connection = DatabaseController.createConnection();

        //retrieve movies
        //HashMap<Integer, ArrayList<Object>> announceMap = aControl.retrieveAllAnnouncement(parent.getLoggedInUser());
        HashMap<Integer, ArrayList<Object>> announceMap = aControl.retrieveAllAnnouncement(parent.getLoggedInUser());

        //debug prints:
        System.out.println("Announcements retrieved from database:");
        for (Map.Entry<Integer, ArrayList<Object>> entry : announceMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        /* 
        AnnouncementID      INT AUTO_INCREMENT,
        IsPublic            BOOLEAN NOT NULL,
        AnnouncementMessage VARCHAR(255) NOT NULL,
        DateAnnounced       DATETIME NOT NULL,
        MovieID             INT,

        */

        // Convert to 2D array for JTable
        String[][] announceData = new String[announceMap.size()][2];
        String[] columnNames = {"Date", "Message"};
        int row = 0;

        for (ArrayList<Object> movieDetails : announceMap.values()) {
            announceData[row][0] = (String) movieDetails.get(4); // Date published
            announceData[row][1] = (String) movieDetails.get(3); // Message
            row++;
        }

        //Debug prints:
        System.out.println("Movies array:");
        for (String[] item : announceData) {
            System.out.println("Row: " + Arrays.toString(item));
        }


        JTable announceTable = new JTable(new DefaultTableModel(announceData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        });
        announceTable.setFillsViewportHeight(true); // Ensure table fills the scroll pane

        JScrollPane scrollPane = new JScrollPane(announceTable);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns if needed
        constraints.fill = GridBagConstraints.BOTH; // Stretch both horizontally and vertically
        constraints.weightx = 1.0; // Allow horizontal resizing
        constraints.weighty = 1.0; // Allow vertical resizing
        add(scrollPane, constraints);

        //button stuff  --> needs to be fixed for announement

        /*
        // Add a selection button
        JButton selectButton = new JButton("Select Announcement");

        // Configure constraints
        constraints.gridx = 0;
        constraints.gridy = 2; // Assuming the table is at gridy = 1
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        constraints.anchor = GridBagConstraints.CENTER; // Center the button
        constraints.fill = GridBagConstraints.HORIZONTAL; // Stretch horizontally
        constraints.weightx = 0; // No horizontal resizing
        constraints.weighty = 0; // No vertical resizing
        constraints.insets = new Insets(10, 10, 10, 10); // Add some spacing

        // Add button to the panel
        add(selectButton, constraints);

        // Add action listener for functionality
        selectButton.addActionListener(e -> {
            int selectedRow = announceTable.getSelectedRow();
            if (selectedRow >= 0) {

                HashMap<Integer, ArrayList<Object>> movieInfo = showControl.searchForMovie(connection, movieData[selectedRow][0]); 

                if (movieInfo!=null && !movieInfo.isEmpty()){
                    for (Map.Entry<Integer, ArrayList<Object>> entry : movieInfo.entrySet()) {
                    //in future add code so that movies match with respected covers
                    ArrayList<Object> movieDetails = entry.getValue();

                    //movie details
                    int movieID = entry.getKey();
                    String title = (String) movieDetails.get(0);
                    String genre = (String) movieDetails.get(1);
                    String rating = (String) movieDetails.get(2);
                    Time runtime = (Time) movieDetails.get(3);
                    String coverPath = "Images/cover.png";

                    Movie movie = new Movie(movieID, title, genre, rating, runtime);

                    ViewMovie viewMovie = parent.getViewMovie();
                    viewMovie.setMovieDetails(movie);
                    
                    parent.showCard("ViewMovie");
                    }}
                

                /*
                Movie movie = new Movie(movieData[selectedRow][0], movieData[selectedRow][1], movieData[selectedRow][2], movieData[selectedRow][3]);
                ViewMovie viewMovie = parent.getViewMovie();
                viewMovie.setMovieDetails(movie);
                parent.showCard("ViewMovie");
                */
                
            /*
            } else {
                JOptionPane.showMessageDialog(this, "Please select a movie from the list.");
            }
        });
        */


        setVisible(true);
    }

    public void displayMovies(){
        parent.showCard("BrowseMovie");
    }


    //temporary main method for testing

    /*
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new BrowseMovie(frame));
        frame.setVisible(true);
    
    }
    */

}

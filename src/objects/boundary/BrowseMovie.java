//boundary, browse movies 

package objects.boundary;

import objects.control.*;
import objects.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BrowseMovie extends JPanel {
    //components
    private JLabel title;
    private appGUI parent;

    //controller
    private ShowtimeController showControl;

    //ctor
    public BrowseMovie(appGUI parent){
        this.parent = parent;
        this.showControl = new ShowtimeController();

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("Browse Available Movies");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel

        //connect to database
        Connection connection = DatabaseController.createConnection();

        //retrieve movies
        HashMap<Integer, ArrayList<Object>> movieMap = showControl.retrieveAllMovies(connection);

        //debug prints:
        System.out.println("Movies retrieved from database:");
        for (Map.Entry<Integer, ArrayList<Object>> entry : movieMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        // Convert to 2D array for JTable
        String[][] movieData = new String[movieMap.size()][4];
        String[] columnNames = {"Title", "Genre", "Rating", "Runtime"};
        int row = 0;

        for (ArrayList<Object> movieDetails : movieMap.values()) {
            movieData[row][0] = (String) movieDetails.get(0); // Title
            movieData[row][1] = (String) movieDetails.get(1); // Genre
            movieData[row][2] = (String) movieDetails.get(2); // Rating
            movieData[row][3] = movieDetails.get(3).toString(); // Runtime
            row++;
        }

        //Debug prints:
        System.out.println("Movies array:");
        for (String[] item : movieData) {
            System.out.println("Row: " + Arrays.toString(item));
        }


        // Create table and add to frame
        
        JTable movieTable = new JTable(new DefaultTableModel(movieData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        });
        movieTable.setFillsViewportHeight(true); // Ensure table fills the scroll pane

        JScrollPane scrollPane = new JScrollPane(movieTable);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns if needed
        constraints.fill = GridBagConstraints.BOTH; // Stretch both horizontally and vertically
        constraints.weightx = 1.0; // Allow horizontal resizing
        constraints.weighty = 1.0; // Allow vertical resizing
        add(scrollPane, constraints);

        //button stuff  

        // Add a selection button
        JButton selectButton = new JButton("Select Movie");

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
            int selectedRow = movieTable.getSelectedRow();
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

                    Movie movie = new Movie(movieID, title, genre, rating, runtime);

                    ViewMovie viewMovie = parent.getViewMovie();
                    viewMovie.setMovieDetails(movie);
                    
                    parent.showCard("ViewMovie");
                    }}
                

            } else {
                JOptionPane.showMessageDialog(this, "Please select a movie from the list.");
            }
        });


        setVisible(true);
    }

    public void displayMovies(){
        parent.showCard("BrowseMovie");
    }


}

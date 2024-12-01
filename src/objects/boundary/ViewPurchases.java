//boundary, view purchases

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
import java.sql.*;


public class ViewPurchases extends JPanel {
    //components
    private JLabel title;
    private appGUI parent;

    //controller
    private AnnouncementController aControl;
    private TicketController tControl;

    //private JFrame frame; //reference to parent frame
    JTable purchaseTable;


    //ctor
    public ViewPurchases(appGUI parent){
        this.parent = parent;
        this.tControl = new TicketController();
        this.aControl = new AnnouncementController();

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("View Purchases");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel

        //connect to database
        Connection connection = DatabaseController.createConnection();
        //retrieve purchases
        HashMap<Integer, ArrayList<Object>> purchaseMap = aControl.retrieveEmailAnnouncement(connection, parent.getLoggedInUser());

        //debug prints:
        System.out.println("Announcements retrieved from database:");
        for (Map.Entry<Integer, ArrayList<Object>> entry : purchaseMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

   
        // Convert to 2D array for JTable
        String[][] purchaseData = new String[purchaseMap.size()][2];
        String[] columnNames = {"Date", "Message"};
        int row = 0;

        for (ArrayList<Object> purchaseDetails : purchaseMap.values()) {
            purchaseData[row][0] = purchaseDetails.get(1).toString(); // Date published 
            purchaseData[row][1] = (String) purchaseDetails.get(0); // Message
            row++;
        }

        //Debug prints:
        System.out.println("Announcement array:");
        for (String[] item : purchaseData) {
            System.out.println("Row: " + Arrays.toString(item));
        }

        //Table for announements:

        purchaseTable = new JTable(new DefaultTableModel(purchaseData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        });
        
        // Wrap table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(purchaseTable);
        
        // Adjust column widths
        purchaseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        purchaseTable.getColumnModel().getColumn(0).setPreferredWidth(200); //width for date 
        purchaseTable.getColumnModel().getColumn(1).setPreferredWidth(600); //width for message
        
        //set table size and fill
        purchaseTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
        purchaseTable.setFillsViewportHeight(true);
                
        //scroll pane constraints
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span entire panel width
        constraints.fill = GridBagConstraints.BOTH; // Allow both horizontal and vertical stretching
        constraints.weightx = 1.0; // Expand horizontally
        constraints.weighty = 1.0; // Expand vertically
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

    public void addPurchase(int showtimeID, int seatID, String movieTitle, String userEmail) {
        DefaultTableModel model = (DefaultTableModel) purchaseTable.getModel();
        String purchaseDate = new Timestamp(System.currentTimeMillis()).toString();
        String message = "Purchased ticket for " + movieTitle;
    
        // Add new row to the table
        model.addRow(new Object[] { purchaseDate, message });
    
        // Add click listener for the new row to display the receipt
        purchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = purchaseTable.getSelectedRow();
                if (selectedRow >= 0 && selectedRow == model.getRowCount() - 1) { // Ensure it's the new row clicked
                    Connection con = DatabaseController.createConnection();
                    SeatMapPageTest seatMapPage = parent.getSeatMapPage();
                    if (seatMapPage != null) {
                        seatMapPage.displayReceipt(con, null, seatID, showtimeID, userEmail);
                    } else {
                        JOptionPane.showMessageDialog(ViewPurchases.this, "Unable to display receipt.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
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

//boundary, where when the user clicks on a movie, takes them to a page with the movie + movie details

//purchase ticket, display seatmpa/browse available seats, view showtimes


package objects.boundary;

import objects.control.*;
import objects.entity.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.sql.*;



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

    private Movie chosenMovie;

    private int showtimeID;
    
    
    
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

        /*action listeners */

        showTimes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showTimesPopUp();
                
                
            }
        });



        


       
       

    }

    public void setMovieDetails(Movie movie) {
        this.chosenMovie = movie;
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

        if (chosenMovie == null) {
            JOptionPane.showMessageDialog(this, "No movie selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) null, "Showtimes for " + chosenMovie.getName(), true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        //retrieve showtimes
        ShowtimeController showtimeController= new ShowtimeController();
        Connection connection = DatabaseController.createConnection();
        HashMap<Integer, ArrayList<Object>> showtimeMap = showtimeController.searchForShowtime(connection, chosenMovie.getName());

        if (showtimeMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No showtimes available for this movie.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //convert showtimeMap into a list to sort it in ascending order
        ArrayList<Map.Entry<Integer, ArrayList<Object>>> showtimeEntries = new ArrayList<>(showtimeMap.entrySet());
        showtimeEntries.sort((entry1, entry2) -> {
        int roomID1 = (int) entry1.getValue().get(0);
        int roomID2 = (int) entry2.getValue().get(0);
        return Integer.compare(roomID1, roomID2);
    });
       

        //panel to hold all showtimes
        JPanel showtimesPanel = new JPanel();
        showtimesPanel.setLayout(new BoxLayout(showtimesPanel, BoxLayout.Y_AXIS));


        
        for (Map.Entry<Integer, ArrayList<Object>> entry : showtimeEntries) {
            
            ArrayList<Object> details = entry.getValue();
            Timestamp showDateTime = (Timestamp) details.get(2);
            int theatreRoomID = (int) details.get(0);
            int showtimeID = entry.getKey();


            //panel for each showtime
            JPanel showtimePanel = new JPanel(new BorderLayout());
            showtimePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
            //showtime details
            JLabel detailsLabel = new JLabel("<html><b>Theatre Room:</b> " + theatreRoomID +"<br><b>Time:</b> " + showDateTime.toString() + "</html>");
            showtimePanel.add(detailsLabel, BorderLayout.CENTER);

            //navigate to seat map after choosing a showtime
            showtimePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //JOptionPane.showMessageDialog(dialog, "You selected showtime: " + showDateTime, "Showtime Selected", JOptionPane.INFORMATION_MESSAGE);
                    //save showtime ID:
                    parent.setSelectedShowtimeID(showtimeID);
                    
                    TreeMap<Integer, Boolean> seatMap = fetchSeatMap(showtimeID);
                    parent.showSeatMap(seatMap);
                    dialog.dispose();
                }
            });

            
            showtimesPanel.add(showtimePanel);
        }

       
        JScrollPane scrollPane = new JScrollPane(showtimesPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);


       
        //close pop-up
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setVisible(true);



        
        
    }

    public Movie getChosenMovie() {
        return chosenMovie;
    }

    public String getMovieTitle() {
        return chosenMovie != null ? chosenMovie.getName() : null;
    }

    //fetch seatmap to pass to SeatMapPage 
    private TreeMap<Integer, Boolean> fetchSeatMap(int showtimeID) {
        TicketController ticketController = new TicketController();
        try (Connection connection = DatabaseController.createConnection()) {
            HashMap<Integer, Boolean> seatMap = ticketController.retrieveAvailableSeats(connection, showtimeID);
            return new TreeMap<>(seatMap); // Sort the seat map
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        }


}
package objects.boundary;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.*;

import objects.control.DatabaseController;
import objects.control.TicketController;

public class SeatMapPageTest {

    private JFrame frame;
    private JPanel currentSelectedSeat = null;  

    // Will get these values from somewhere
    private static int    showtimeID = 44;
    private static String userEmail = "user5@example.com";


    public SeatMapPageTest(JFrame parent, TreeMap<Integer, Boolean> seatMap) {

        TicketController ticketController = new TicketController();

        // ***************
        // Will properly instantiate variables here when avaialable
        // ****************
        // showtimeID = 44;
        // userEmail = "user5@example.com";
        // ***************
        // Will properly instantiate variables here when avaialable
        // ****************

        frame = new JFrame("Seat Map");
        frame.setLayout(new BorderLayout()); 
        frame.setAlwaysOnTop(true); 
        frame.setResizable(false); 
        frame.setLocationRelativeTo(parent);

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a screen panel at the top of the window
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new BorderLayout()); 
        screenPanel.setBackground(Color.LIGHT_GRAY); 
        screenPanel.setPreferredSize(new Dimension(600, 50)); 

        // Add a label to the screen panel
        JLabel screenLabel = new JLabel("SCREEN", JLabel.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 30)); 
        screenPanel.add(screenLabel, BorderLayout.CENTER);

        // Add padding around the screen
        screenPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the screen panel at the top of the window
        frame.add(screenPanel, BorderLayout.NORTH);

        // Create a seat grid panel
        JPanel seatGridPanel = new JPanel(new GridLayout(Math.floorDiv(seatMap.size(), 10), 0, 10, 10));
        seatGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a new panel for each seat, and set its colour depending on availability
        for (var entry : seatMap.entrySet()) {
            int seatID = entry.getKey();
            boolean isAvailable = entry.getValue();

            // Create a panel for each seat
            JPanel seatPanel = new JPanel();
            seatPanel.setBackground(isAvailable ? Color.GREEN : Color.RED); // Green = available, Red = booked
            seatPanel.setLayout(new BorderLayout());

            // Make each seat a square
            seatPanel.setPreferredSize(new Dimension(60, 60));

            // Make SeatID the label for the seat
            JLabel label = new JLabel(String.valueOf(seatID), JLabel.CENTER);
            label.setForeground(Color.BLACK);
            seatPanel.add(label, BorderLayout.CENTER);

            // Events for hovering over and selecting a seat
            seatPanel.addMouseListener(new MouseAdapter() {
                
                @Override
                // When hovering over a seat, create a black border
                public void mouseEntered(MouseEvent e) {
                    seatPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }

                @Override
                // When stop hovering over a seat, remove border
                public void mouseExited(MouseEvent e) {
                    seatPanel.setBorder(null);
                }

                @Override
                // If an available seat is clicked on, turn it grey to show that it is selected
                public void mouseClicked(MouseEvent e) {
                    if (isAvailable) {
                        // When selecting another seat, reset the color of the previously selected seat
                        if (currentSelectedSeat != null) 
                            currentSelectedSeat.setBackground(Color.GREEN);

                        // Change the current seat's color to gray
                        seatPanel.setBackground(Color.GRAY);
                        System.out.println("Clicked seat: " + seatID + " - Changed to gray");

                        currentSelectedSeat = seatPanel;
                    }
                }
            });

            // Add the seat panel to the seat grid
            seatGridPanel.add(seatPanel);
        }

        // Add seat grid panel to the center of the frame
        frame.add(seatGridPanel, BorderLayout.CENTER);

        // Create the "BUY" button and add it to the lower corner
        JButton buyButton = new JButton("BUY");
        buyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int seatID = -1;

                if(!ticketController.isPurchasable(seatID, showtimeID, userEmail)) {
                    System.out.println("not purchasable");
                }

                // Buy the currently selected seat
                else if (currentSelectedSeat != null) {
                    Component[] components = currentSelectedSeat.getComponents();
                    for (Component component : components) 
                        if (component instanceof JLabel) {
                            seatID = Integer.parseInt(((JLabel) component).getText());
                            ticketController.purchaseTicket(null,seatID, showtimeID, userEmail);
                            System.out.println("BOUGHT seat: " + seatID);
                        }
                    
                    // Set the color to RED after buying
                    currentSelectedSeat.setBackground(Color.RED);
                    currentSelectedSeat = null;

                    // Close the window
                    frame.dispose();
                    
                } else 
                    System.out.println("No seat selected.");
            }
        });

        // Create a buy button in bottom right corner
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buyButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Frame/window settings
        frame.setSize(600, 600); 
        frame.setVisible(true);
    }

    // public static void main(String[] args) {
    //     TicketController ticketController = new TicketController();
    //     HashMap<Integer, Boolean> seatMap = new HashMap<>();

    //     // ***************
    //     // Will properly instantiate variables here when avaialable
    //     // ****************
    //     // showtimeID = 44;
    //     // userEmail = "user1@example.com";
    //     // ***************
    //     // Will properly instantiate variables here when avaialable
    //     // ****************

    //     // Create a connection to DB to get a map for the showtime
    //     try (Connection connection = DatabaseController.createConnection()) {
    //         seatMap = ticketController.retrieveAvailableSeats(connection, showtimeID);
    //     } catch (Exception e) { e.printStackTrace(); }

    //     // Sort the HashMap by turning it into a TreeMap
    //     TreeMap<Integer, Boolean> sortedSeatMap = new TreeMap<>(seatMap);

    //     new SeatMapPageTest(sortedSeatMap);
    // }
}
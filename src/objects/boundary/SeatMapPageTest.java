package objects.boundary;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.*;

import objects.entity.*;
import objects.control.*;

public class SeatMapPageTest extends JPanel {

    //private JFrame frame;
    private JPanel currentSelectedSeat = null;  

    // Will get these values from somewhere
    //private static int    showtimeID = 9;
    //private static int selectedSeatID = -1;
    private static int    showtimeID;
    private static String userEmail;
    private static PaymentInfo paymentInfo;

    TicketController ticketController;

    appGUI parent;


    public SeatMapPageTest(TreeMap<Integer, Boolean> seatMap, appGUI parent) {
        
        this.parent = parent;
        showtimeID = parent.getSelectedShowtimeID();
        userEmail = parent.getLoggedInUser();
        //paymentInfo = new PaymentInfo(1234567812345678L, "2026-11-30", 123);
        


        // if (seatMap == null) {
        //     seatMap = new TreeMap<>();
        //     for (int i = 1; i <= 30; i++) {
        //         seatMap.put(i, true); // Default all seats to available
        //     }
        // }

        setLayout(new BorderLayout());


        TicketController ticketController = new TicketController();

        // ***************
        // Will properly instantiate variables here when avaialable
        // ****************
        // showtimeID = 44;
        // userEmail = "user5@example.com";
        // ***************
        // Will properly instantiate variables here when avaialable
        // ****************

        // frame = new JFrame("Seat Map");
        // frame.setLayout(new BorderLayout()); 
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        add(screenPanel, BorderLayout.NORTH);

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
                        if (currentSelectedSeat != null && isAvailable) 
                            currentSelectedSeat.setBackground(Color.GREEN);

                        // Change the current seat's color to gray
                        seatPanel.setBackground(Color.GRAY);
                        System.out.println("Clicked seat: " + seatID + " - Changed to gray");
                        //save the seatID the user has pressed
                        parent.setSeatID(seatID);

                        currentSelectedSeat = seatPanel;
                    }
                }
            });

            // Add the seat panel to the seat grid
            seatGridPanel.add(seatPanel);
        }

        // Add seat grid panel to the center of the frame
        add(seatGridPanel, BorderLayout.CENTER);

        //back button to go back to movie view:
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        
        buttonPanel.add(backButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                parent.showCard("ViewMovie");
            }

        });
        add(buttonPanel, BorderLayout.SOUTH);

        // Create the "BUY" button and add it to the lower corner
        JButton buyButton = new JButton("BUY");
        buyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RegisteredUserController registeredUserController = new RegisteredUserController();
                boolean isRegistered = registeredUserController.isRegisteredUser(parent.getLoggedInUser());

                if(currentSelectedSeat == null){
                    JOptionPane.showMessageDialog(SeatMapPageTest.this, "No seat selected. Please select a seat before proceeding.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                if (isRegistered){
                paymentInfo = parent.setRegisteredUserPayment(userEmail);
                System.out.println(paymentInfo);
                int seatID = -1;

                if(!ticketController.isPurchasable(seatID, showtimeID, userEmail)) {
                    System.out.println("not purchasable");
                    JOptionPane.showMessageDialog(SeatMapPageTest.this, "This seat is unavailable, please pick another one.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Buy the currently selected seat
                else if (currentSelectedSeat != null) {
                    Component[] components = currentSelectedSeat.getComponents();
                    for (Component component : components) 
                        if (component instanceof JLabel) {
                            seatID = Integer.parseInt(((JLabel) component).getText());
                            ticketController.purchaseTicket(paymentInfo, seatID, showtimeID, parent.getLoggedInUser());

                            //Connection con = DatabaseController.createConnection();
                            String movieTitle = parent.getViewMovie().getMovieTitle();

                            //RegisterUser registerUser = new RegisterUser(parent);

                            //PaymentInfo paymentInfo = registerUser.getPaymentInfo();

                            System.out.print(paymentInfo.getCardNumber());

                            // Add purchase to ViewPurchases
                            // ViewPurchases viewPurchases = parent.getViewPurchases();
                            // if (viewPurchases != null) {
                            //     viewPurchases.addPurchase(showtimeID, seatID, movieTitle, userEmail);
                            // }
                            updatePurchase(showtimeID, seatID, movieTitle, userEmail, paymentInfo);

                            System.out.println("BOUGHT seat: " + seatID);
                            break;
                        }
                    
                    // Set the color to RED after buying
                    currentSelectedSeat.setBackground(Color.RED);
                    currentSelectedSeat = null;

                    // Close the window
                    //frame.dispose();
                    Connection con = DatabaseController.createConnection();
                    displayReceipt(con, paymentInfo, seatID, showtimeID, userEmail);
                }
                    
                } else if(!isRegistered){
                    parent.showCard("Payment");

                }
                
            }
        });

        // Create a buy button in bottom right corner
       // JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buyButton);

        //buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buttonPanel, BorderLayout.SOUTH);

       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Frame/window settings
        //frame.setSize(600, 600); 
        //frame.setVisible(true);
    }

    //update purchases function

    public void updatePurchase(int showTimeID, int seatID, String movieTitle, String userEmail, PaymentInfo paymentInfo){
        
        ViewPurchases viewPurchases = parent.getViewPurchases();
         if (viewPurchases != null) {
             viewPurchases.addPurchase(showtimeID, seatID, movieTitle, userEmail, paymentInfo);
        }

    }

    //update seat to green/available
    public void updateSeatToGreen(int seatID) {
        Component[] components = this.getComponents(); // Get all components of SeatMapPageTest
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel seatPanel = (JPanel) component;
                Component[] seatComponents = seatPanel.getComponents();
                for (Component seatComponent : seatComponents) {
                    if (seatComponent instanceof JLabel) {
                        JLabel label = (JLabel) seatComponent;
                        if (label.getText().equals(String.valueOf(seatID))) {
                            seatPanel.setBackground(Color.GREEN); // Mark seat as available (green)
                            seatPanel.revalidate();
                            seatPanel.repaint();
                            return;
                        }
                    }
                }
            }
        }
    }

    //update seat to red/unavailable function
    public void updateSeatToRed(int seatID) {
        Component[] components = this.getComponents(); // Get all components of SeatMapPageTest
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel seatPanel = (JPanel) component;
                Component[] seatComponents = seatPanel.getComponents();
                for (Component seatComponent : seatComponents) {
                    if (seatComponent instanceof JLabel) {
                        JLabel label = (JLabel) seatComponent;
                        if (label.getText().equals(String.valueOf(seatID))) {
                            seatPanel.setBackground(Color.RED); // Mark seat as taken

                            seatPanel.revalidate();
                            seatPanel.repaint();
                        
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public void displayReceipt(Connection con, PaymentInfo paymentInfo, int seatID, int showtimeID, String email) {
        //get receipt details
        ticketController = new TicketController();
        String receiptMessage = ticketController.sendReceiptAnnouncement(con, paymentInfo, seatID, showtimeID, email);
    
        //error checking (if receipt is empty)
        if (receiptMessage.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Error generating receipt. Please try again.", 
                "Receipt Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        JDialog receiptDialog = new JDialog((Frame) null, "Receipt Details", true);
        receiptDialog.setLayout(new BorderLayout());
        receiptDialog.setSize(400, 300);
        receiptDialog.setLocationRelativeTo(null);

        //create a text area for the receipt message
        JTextArea receiptTextArea = new JTextArea(receiptMessage);
        receiptTextArea.setEditable(false);
        receiptTextArea.setWrapStyleWord(true);
        receiptTextArea.setLineWrap(true);
        receiptTextArea.setMargin(new Insets(10, 10, 10, 10));
        receiptDialog.add(new JScrollPane(receiptTextArea), BorderLayout.CENTER);

        //create button for user to be able to cancel ticket
        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setOpaque(true);

        //add action listener for the cancel button
        cancelButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(
                receiptDialog,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
        
            if (confirmation == JOptionPane.YES_OPTION) {
                int ticketID = ticketController.getTicketID(showtimeID, seatID, email);
                String cancellationMessage = ticketController.refundTicket(ticketID, email, paymentInfo);
        
                // Display the cancellation message
                JOptionPane.showMessageDialog(
                    receiptDialog,
                    cancellationMessage,
                    cancellationMessage.contains("successful") ? "Success" : "Error",
                    cancellationMessage.contains("successful") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE

                );
        
                // If successful, update the seat to green and close dialog
                if (cancellationMessage.contains("successful")) {
                    updateSeatToGreen(seatID);
                    receiptDialog.dispose();
                }
            }
        });

        // Add the button to the dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        receiptDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show the dialog
        receiptDialog.setVisible(true);
    }
    

    public int getShowtimeID(){
        return showtimeID;
    }

}
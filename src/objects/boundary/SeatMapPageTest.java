package objects.boundary;

import objects.control.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeMap;

public class SeatMapPageTest extends JPanel {

    private JPanel currentSelectedSeat = null;

    appGUI parent;

    private int seatID;

    public SeatMapPageTest(TreeMap<Integer, Boolean> seatMap, appGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // Create a screen panel
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new BorderLayout());
        screenPanel.setBackground(Color.LIGHT_GRAY);
        screenPanel.setPreferredSize(new Dimension(600, 50));

        JLabel screenLabel = new JLabel("SCREEN", JLabel.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 30));
        screenPanel.add(screenLabel, BorderLayout.CENTER);
        screenPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(screenPanel, BorderLayout.NORTH);

        // Create a seat grid panel
        JPanel seatGridPanel = new JPanel(new GridLayout(5, 10, 10, 10)); // Example grid
        seatGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (seatMap != null) {
            for (var entry : seatMap.entrySet()) {
                int seatID = entry.getKey();
                boolean isAvailable = entry.getValue();

                JPanel seatPanel = new JPanel();
                seatPanel.setBackground(isAvailable ? Color.GREEN : Color.RED);
                seatPanel.setPreferredSize(new Dimension(60, 60));
                JLabel label = new JLabel(String.valueOf(seatID), JLabel.CENTER);
                seatPanel.add(label);

                seatPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isAvailable) {
                            if (currentSelectedSeat != null)
                                currentSelectedSeat.setBackground(Color.GREEN);
                            seatPanel.setBackground(Color.GRAY);
                            currentSelectedSeat = seatPanel;
                        }
                    }
                });

                seatGridPanel.add(seatPanel);
            }
        }
        add(seatGridPanel, BorderLayout.CENTER);

        // Add BUY button
        JButton buyButton = new JButton("BUY");
        buyButton.addActionListener(e -> {handleBuyAction();});

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buyButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }


private void handleBuyAction(){

    if(currentSelectedSeat!=null){
        JLabel label = (JLabel) currentSelectedSeat.getComponent(0);
        int seatID = Integer.parseInt(label.getText());
        String loggedInUser = parent.getLoggedInUser();

        //check if the user is registered or not
        System.out.println(loggedInUser);
        RegisteredUserController registeredUserController = new RegisteredUserController();
        boolean isRegistered = registeredUserController.isRegisteredUser(loggedInUser);

        if(isRegistered){
            //also need to send receipt
            TicketController ticketController = new TicketController();
            ticketController.purchaseTicket(null, seatID, 1, loggedInUser); // Assuming showtimeID = 1 for example
            JOptionPane.showMessageDialog(this,
                    "Seat " + seatID + " purchased successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            currentSelectedSeat.setBackground(Color.RED);
            currentSelectedSeat = null;
        }
        else{
            parent.showCard("Payment");
        }
    }
}
public void displayPaymentFields(){
        
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);

        //payment info
        JLabel paymentInfoLabel = new JLabel("Payment Details:");
        paymentInfoLabel.setFont(new Font("Arial",Font.BOLD,10));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(paymentInfoLabel,constraints);

        JLabel cardNumberLabel= new JLabel("Card Number:");
        cardNumberLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(cardNumberLabel,constraints);

        JTextField cardNumberTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(cardNumberTxt, constraints);


        JLabel expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 5;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(expirationDateLabel,constraints);

        JTextField expirationDateTxt = new JTextField(20);
        constraints.gridx = 6;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(expirationDateTxt, constraints);


        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(cvvLabel,constraints);

        JTextField cvvTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(cvvTxt, constraints);

        // JLabel firstNameLabel = new JLabel("First Name:");
        // firstNameLabel.setFont(new Font("Arial",Font.PLAIN,10));
        // add(firstNameLabel);

        // JTextField firstNameTxt = new JTextField();
        // constraints.gridx = 1;
        // constraints.gridy = 1;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(firstNameTxt,constraints); 


        // JLabel lastNamLabel= new JLabel("Last Name:");
        // lastNamLabel.setFont(new Font("Arial",Font.PLAIN,10));
        // constraints.gridx = 5;
        // constraints.gridy = 1;
        // constraints.gridwidth = 1;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(lastNamLabel,constraints);

        // JTextField lastNameTxt = new JTextField(20);
        // constraints.gridx = 6;
        // constraints.gridy = 1;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(lastNameTxt,constraints); 


        // JLabel streetAddress= new JLabel("Street Address:");
        // streetAddress.setFont(new Font("Arial",Font.PLAIN,10));
        // constraints.gridx = 0;
        // constraints.gridy = 2;
        // constraints.gridwidth = 1;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(streetAddress,constraints);

        // JTextField streetAddressTxt = new JTextField(20);
        // constraints.gridx = 1;
        // constraints.gridy = 2;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(streetAddressTxt,constraints); 



        // JLabel cityLabel= new JLabel("City:");
        // cityLabel.setFont(new Font("Arial",Font.PLAIN,10));
        // constraints.gridx = 5;
        // constraints.gridy = 2;
        // constraints.gridwidth = 1;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(cityLabel,constraints);

        // JTextField cityTxt = new JTextField(20);
        // constraints.gridx = 6;
        // constraints.gridy = 2;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(cityTxt,constraints); 


        // JLabel provLabel= new JLabel("Province:");
        // provLabel.setFont(new Font("Arial",Font.PLAIN,10));
        // constraints.gridx = 0;
        // constraints.gridy = 4;
        // constraints.gridwidth = 1;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(provLabel,constraints);

        // JTextField provTxt = new JTextField(20);
        // constraints.gridx = 1;
        // constraints.gridy = 4;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(provTxt,constraints); 


        // JLabel postalLabel= new JLabel("Postal Code:");
        // postalLabel.setFont(new Font("Arial",Font.PLAIN,10));
        // constraints.gridx = 5;
        // constraints.gridy = 4;
        // constraints.gridwidth = 1;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(postalLabel,constraints);

        // JTextField postalTxt = new JTextField(20);
        // constraints.gridx = 6;
        // constraints.gridy = 4;
        // //make the text field stretch horizontally
        // constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.anchor = GridBagConstraints.WEST;
        // add(postalTxt,constraints); 


        
}
}

package objects.boundary;

import objects.control.*;
import objects.entity.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class PaymentPage extends JPanel {

    PaymentInfo paymentInfo;

    //boolean paymentSuccessful;


    public PaymentPage(appGUI parent) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Card number
        JLabel cardLabel = new JLabel("Card Number:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(cardLabel, constraints);

        JTextField cardField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(cardField, constraints);

        // Expiration date
        JLabel expLabel = new JLabel("Expiration Date:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(expLabel, constraints);

        JTextField expField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(expField, constraints);

        // CVV
        JLabel cvvLabel = new JLabel("CVV:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(cvvLabel, constraints);

        JTextField cvvField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(cvvField, constraints);

        // Submit button
        JButton submitButton = new JButton("Confirm");
        submitButton.addActionListener(e -> {
            String cardNumberString = cardField.getText();
            String expiration = expField.getText();
            String cvvString = cvvField.getText();
            //parse cvv into int
            int cvv = Integer.parseInt(cvvString);
            long cardNumber = Long.parseLong(cardNumberString);

            paymentInfo = new PaymentInfo(cardNumber, expiration, cvv);
            

            if (cardNumberString.isEmpty() || expiration.isEmpty() || cvvString.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Invalid payment details. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                
                
            } else {

                TicketController ticketController = new TicketController();

                int seatID = parent.getSeatID();
                int showTimeID = parent.getSelectedShowtimeID();
                String currentUser = parent.getLoggedInUser();
                
                ticketController.purchaseTicket(paymentInfo, seatID, showTimeID, currentUser );
                JOptionPane.showMessageDialog(this,
                        "Payment successful! Ticket purchased.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                parent.setPaymentSuccessful(true);
                //notify seatmap
                parent.getSeatMapPage().updateSeatToRed(parent.getSeatID());
                //display receipt
                Connection con = DatabaseController.createConnection();

                //add purchase to purchases 
                parent.getSeatMapPage().updatePurchase(showTimeID, seatID, expiration, currentUser, paymentInfo);
                
                parent.getSeatMapPage().displayReceipt(con, paymentInfo, seatID, showTimeID, currentUser);
                //update seat color/availability
                //parent.updateSeatColor(parent.getSeatID());
                parent.showCard("SeatMap");
                
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(submitButton, constraints);
    }

    public PaymentInfo getPaymentInfo(){
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo){
        this.paymentInfo = paymentInfo;
    }



    

   
}

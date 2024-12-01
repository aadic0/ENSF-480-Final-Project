package objects.boundary;

import objects.control.TicketController;

import javax.swing.*;
import java.awt.*;

public class PaymentPage extends JPanel {
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
        JLabel expLabel = new JLabel("Expiration Date (MM/YY):");
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
            String cardNumber = cardField.getText();
            String expiration = expField.getText();
            String cvv = cvvField.getText();

            if (validatePayment(cardNumber, expiration, cvv)) {
                TicketController ticketController = new TicketController();
                // Assuming seatID and showtimeID are passed dynamically
                ticketController.purchaseTicket(null, 1, 1, parent.getLoggedInUser());
                JOptionPane.showMessageDialog(this,
                        "Payment successful! Ticket purchased.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                parent.showCard("SeatMap");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid payment details. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(submitButton, constraints);
    }

    private boolean validatePayment(String cardNumber, String expiration, String cvv) {
        // Basic validation for example purposes
        return cardNumber.matches("\\d{16}") && expiration.matches("\\d{2}/\\d{2}") && cvv.matches("\\d{3}");
    }
}

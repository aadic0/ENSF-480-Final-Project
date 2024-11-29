package objects.boundary;

import objects.control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class RegisterUser extends JPanel {

    private JLabel emailLabel;
    private JLabel passLabel;
    private JLabel firstNameLabel;
    private JLabel lastNamLabel;
    private JLabel streetAddress;
    private JLabel cityLabel;
    private JLabel provLabel;
    private JLabel postalLabel;
    private JLabel paymentInfo;

    private JTextField emailTxt;
    private JTextField passTxt;
    private JTextField firstNameTxt;
    private JTextField lastNameTxt;
    private JTextField streetAddressTxt;
    private JTextField cityTxt;
    private JTextField provTxt;
    private JTextField postalTxt;
    private JTextField paymentInfoTxt;


    private JButton enterbtn;

    private JFrame frame;

    public RegisterUser(){

        RegisteredUserController controller;

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);


       


    }






    
}

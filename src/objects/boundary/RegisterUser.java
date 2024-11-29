package objects.boundary;

import objects.control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class RegisterUser extends JPanel{

    private Login loginDetails;
    private PaymentController paymentController;

    // private JLabel emailLabel;
    // private JLabel passLabel;
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

    private appGUI parent;

    public RegisterUser(appGUI parent){

        this.parent = parent;

        RegisteredUserController controller;

        // getContentPane().removeAll(); //clear
        // repaint();
        // revalidate();
        
        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);

        // menuBar(this);

        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(firstNameLabel,constraints);

        firstNameTxt = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 1;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(firstNameTxt,constraints); 


        lastNamLabel= new JLabel("Last Name:");
        lastNamLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(lastNamLabel,constraints);

        lastNameTxt = new JTextField(20);
        constraints.gridx = 6;
        constraints.gridy = 1;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(lastNameTxt,constraints); 


        streetAddress= new JLabel("Street Address:");
        streetAddress.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(streetAddress,constraints);

        streetAddressTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(streetAddressTxt,constraints); 



        cityLabel= new JLabel("City:");
        cityLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(cityLabel,constraints);

        cityTxt = new JTextField(20);
        constraints.gridx = 6;
        constraints.gridy = 2;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(cityTxt,constraints); 


        provLabel= new JLabel("Province:");
        provLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(provLabel,constraints);

        provTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(provTxt,constraints); 


        postalLabel= new JLabel("Postal Code:");
        postalLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 5;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(postalLabel,constraints);

        postalTxt = new JTextField(20);
        constraints.gridx = 6;
        constraints.gridy = 4;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(postalTxt,constraints); 


        //enter, and then payment info?
        paymentInfo= new JLabel("Payment");


       


    }

    public void displayRegisterAcc(){
        // JFrame frame = new JFrame();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(800, 800);
        // frame.add(new RegisterUser(frame));
        // frame.setVisible(true);
        parent.showCard("RegisterUser");

    }
    
    
    

    






    
}
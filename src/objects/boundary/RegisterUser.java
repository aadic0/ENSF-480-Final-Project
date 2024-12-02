package objects.boundary;

import objects.control.*;
import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;

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
    private JLabel cardNumberLabel;
    private JLabel expirationDateLabel;
    private JLabel cvvLabel;
    private JLabel paymentInfoLabel;

    private JTextField emailTxt;
    private JPasswordField passTxt;
    private JTextField firstNameTxt;
    private JTextField lastNameTxt;
    private JTextField streetAddressTxt;
    private JTextField cityTxt;
    private JTextField provTxt;
    private JTextField postalTxt;
    
    private JTextField cardNumberTxt;
    private JTextField expirationDateTxt;
    private JTextField cvvTxt;
    RegisteredUserController controller;



    private JButton enterBtn;

    //private JFrame frame;

    private PaymentInfo paymentInfo;

    private appGUI parent;

    public RegisterUser(appGUI parent){

        this.parent = parent;

        
        this.controller = new RegisteredUserController();

        // getContentPane().removeAll(); //clear
        // repaint();
        // revalidate();
        
        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);

        // menuBar(this);

        JLabel registerInfo = new JLabel("Pay $20 Annually to get The Full Experience!");
        registerInfo.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        add(registerInfo,constraints);

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


        //payment info
        paymentInfoLabel = new JLabel("Payment Details:");
        paymentInfoLabel.setFont(new Font("Arial",Font.BOLD,10));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(paymentInfoLabel,constraints);

        cardNumberLabel= new JLabel("Card Number:");
        cardNumberLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(cardNumberLabel,constraints);

        cardNumberTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(cardNumberTxt, constraints);


        expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 5;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(expirationDateLabel,constraints);

        expirationDateTxt = new JTextField(20);
        constraints.gridx = 6;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(expirationDateTxt, constraints);


        cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(cvvLabel,constraints);

        cvvTxt = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(cvvTxt, constraints);
        //enter button

       enterBtn = new JButton("Enter");
       constraints.gridx = 4;
       constraints.gridy = 8;
       constraints.gridwidth = 1;
       add(enterBtn, constraints);

        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   
                    String email = parent.getLoggedInUser();
                    String password = parent.getLoggedInPassword();
                    String firstName = firstNameTxt.getText();
                    String lastName = lastNameTxt.getText();
                    String streetAddress = streetAddressTxt.getText();
                    String city = cityTxt.getText();
                    String province = provTxt.getText();
                    String postalCode = postalTxt.getText();
                    long cardNumber = Long.parseLong(cardNumberTxt.getText());
                    String expirationDate = expirationDateTxt.getText();
                    int cvv = Integer.parseInt(cvvTxt.getText());

                    //create payment info
                    paymentInfo = new PaymentInfo(cardNumber, expirationDate, cvv);
                    System.err.println("Trying to register" + email);
                    //registered user object
                    RegisteredUser registeredUser = new RegisteredUser(firstName, lastName, streetAddress, city, province, postalCode, email, paymentInfo);

                    //create registered user
                    controller.registerUser(registeredUser, password);

                    //success message
                    JOptionPane.showMessageDialog(RegisterUser.this, "User registered successfully! Payment Confirmed!");
                    parent.showCard("Login");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RegisterUser.this, "Error: Please Fill in All Fields", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    public PaymentInfo getPaymentInfo(){
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo){
        this.paymentInfo = paymentInfo;
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
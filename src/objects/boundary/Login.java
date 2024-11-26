package objects.boundary;

import objects.control.*;
//import objects.entity.RegisteredUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 



public class Login extends JPanel {
    private JLabel title;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;

    private JButton loginButton;
    private JButton registerButton;
    private JButton guestButton;
    private JButton adminButton;

    //controllers
    private RegisteredUserController registeredUser;


    //ctor
    public Login(){
        registeredUser = new RegisteredUserController();

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("Movie Ticket-Booking App");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel

        /*username */
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameLabel,constraints);


        
        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        //make the text field stretch horizontally
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameField,constraints); 

        /*password */
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial",Font.PLAIN,10));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordLabel,constraints);


        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordField,constraints);

        //login button
        loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(loginButton, constraints);

        //register button
        registerButton = new JButton("Register");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(registerButton, constraints);
        
        //admin btn
        adminButton = new JButton("Login as Admin");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(adminButton, constraints);

        //guest button
        guestButton = new JButton("Continue as Guest");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        //constraints.anchor = GridBagConstraints.CENTER;
        add(guestButton, constraints);

        /*action listeners */

        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //need to verify that login details are correct (control method)
                String user = usernameField.getText().toLowerCase();
                String pass = String.valueOf(passwordField.getPassword());
                if(registeredUser.authenticateUser(user, pass) != false){
                    //go to main page
                    MainPage mainPage = new MainPage();
                    mainPage.displayMainPage();
                }
                else{
                    //display error message
                    //GridBagConstraints constraints = new GridBagConstraints();

                    JTextField authError = new JTextField("Incorrect Username or Password");
                    authError.setForeground(Color.RED);
                    constraints.gridx = 1;
                    constraints.gridy = 4;
                    add(authError, constraints);
                    
                    
                }


            }
        });

        registerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //go to register page
                Register registerPage = new Register();

                registerPage.displayRegister();
                


                
            }
        });

        guestButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //go to mainpage
                MainPage mainPage = new MainPage();
                mainPage.displayMainPage();

            }
        });


        adminButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //go to admin login page

            }
        });









    }

    public void displayLoginGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new Login());
        frame.setVisible(true);

    }

    //temporary main method for testing
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new Login());
        frame.setVisible(true);

    }




    
}

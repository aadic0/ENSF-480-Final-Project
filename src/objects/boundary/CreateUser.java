package objects.boundary;

import objects.control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class CreateUser extends JPanel {

    private JLabel title;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JPasswordField verifyPass;
    private JLabel verifyLabel;
    //private JFrame frame;

    private appGUI parent;

    //already have an account?
    //private JLabel loginHere;
    private JButton loginButton;
    private JButton registerSubmit;

    public CreateUser(appGUI parent){

    /*panel setup */
    setLayout(new GridBagLayout()); //arrange components in grid-like structure
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(10,10,10,10);

    this.parent = parent;
   
    title = new JLabel("Movie Ticket-Booking App");
    title.setFont(new Font("Arial", Font.BOLD,18));
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.CENTER;
    add(title, constraints); //add to the panel

    /*username */
    usernameLabel = new JLabel("Email:");
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
    passwordLabel = new JLabel("Enter Password:");
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
    
    //verify
    verifyLabel = new JLabel("Verify Your Password:");
    verifyLabel.setFont(new Font("Arial",Font.PLAIN,10));
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.WEST;
    add(verifyLabel,constraints);


    verifyPass = new JPasswordField(20);
    constraints.gridx = 1;
    constraints.gridy = 3;
    constraints.gridwidth = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.anchor = GridBagConstraints.WEST;
    add(verifyPass,constraints);

    //login button
    loginButton = new JButton("Login");
    constraints.gridx = 1;
    constraints.gridy = 4;
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.EAST;
    add(loginButton, constraints);

    //register button
    registerSubmit = new JButton("Register");
    constraints.gridx = 0;
    constraints.gridy = 4;
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.CENTER;
    add(registerSubmit, constraints);

    /*action listeners */
    //create new user in database
    registerSubmit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
    
        String email = usernameField.getText().toLowerCase();
        String pass = String.valueOf(passwordField.getPassword()); 
        String passVerify = String.valueOf(verifyPass.getPassword());

        if (email.isEmpty() || pass.isEmpty() || passVerify.isEmpty()) {
            JLabel authError = new JLabel("Please fill in all fields.");
            authError.setForeground(Color.RED);
            constraints.gridx = 1;
            constraints.gridy = 8;
            add(authError, constraints);
            revalidate();
            repaint();
            return; //exit the listener to prevent further checks
        }

        if( pass.equals(passVerify)){
            //create user in database
            RegisteredUserController createUser = new RegisteredUserController();
            createUser.createUser(email,pass);
            JLabel authError = new JLabel("User successfully created!");
            authError.setForeground(Color.GREEN);
            constraints.gridx = 1;
            constraints.gridy = 8;
            add(authError, constraints);
            revalidate();
            repaint();

    
        }else{
        //display error message
        JLabel authError = new JLabel("Passwords do not match");
        authError.setForeground(Color.RED);
        constraints.gridx = 1;
        constraints.gridy = 8;
        add(authError, constraints);
        revalidate();
        repaint();
    }
   
}
});

    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            // frame.dispose();
            // Login loginPage = new Login(frame);
            // loginPage.displayLoginGUI();
            parent.showCard("Login");
        }
    });



}

public void displayRegister(){
    // JFrame frame = new JFrame();
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 300);
    //     frame.add(new CreateUser(frame));
    //     frame.setVisible(true);

    parent.showCard("Create Account");

}

    
}

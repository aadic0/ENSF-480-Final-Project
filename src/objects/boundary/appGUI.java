//putting the gui together
package objects.boundary;

import java.awt.CardLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class appGUI extends JFrame{

    private CardLayout cardLayout;
    private JPanel mainPanel;
    JFrame frame;


    public appGUI(JFrame frame){

        setTitle("Movie App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different GUI classes to the CardLayout
        mainPanel.add(new Login(this), "Login");
        mainPanel.add(new MainPage(this), "Home");
        mainPanel.add(new RegisterUser(this), "RegisterUser");
        mainPanel.add(new CreateUser(this), "Create Account");

        // Add the main panel to the frame
        add(mainPanel);

        // Show the Login page initially
        showCard("Login");

        setVisible(true); // Make the frame visible
    }

    // Method to switch cards
    public void showCard(String cardName) {

        if(cardName.equals("Login")){
            setJMenuBar(null);
        }else{

            setJMenuBar(new MainPage(this).menuBar());

        }
        cardLayout.show(mainPanel, cardName);

        revalidate();
        repaint();

        

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        new appGUI(frame);
    }
    // public static void main(String[] args){
    //     Login loginPage;
        
    //     loginPage = new Login();

    //     loginPage.displayLoginGUI();
        
    // }
}




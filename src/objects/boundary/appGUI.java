//putting the gui together
package objects.boundary;

import java.awt.*;
import javax.swing.*;

import objects.entity.User;

public class appGUI extends JFrame{

    private CardLayout cardLayout;
    private JPanel mainPanel;

    //persistent variables
    private User user;  //store user
    private Boolean isLoggedIn = null; //is logged in or not

    public static void main(String[] args){
        //cardLayout = new CardLayout();
        //mainPanel = new JPanel(cardLayout);
        //user = new User("Charlie", true);

        // Create the main frame
        JFrame frame = new JFrame("APP GUI --> CardLayout Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a panel with CardLayout
        JPanel cardPanel = new JPanel(new CardLayout());

        //pages
        Login loginPage = new Login(frame);
        cardPanel.add(loginPage, "Page 1");
        //MainPage mainPage = new MainPage(frame);
        //cardPanel.add(mainPage, "Page 2");

        // Add the card panel to the frame
        frame.add(cardPanel);

        //CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        //cardLayout.show(cardPanel, "Page 1");

        // Make the frame visible
        frame.setVisible(true);

        
    }
}




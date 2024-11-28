//main page where all the movies are, nav bar(user,purchases), search bar, logo
package objects.boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener; 




public class MainPage extends JFrame {

    JMenuBar menubar;
    

    MainPage(){

        //setup frame
        setTitle("Welcome!");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
       menubar = new JMenuBar();
       
       //create menus
       JMenu homeBtn = new JMenu("Home");
       JMenu registerUser = new JMenu("Register Account");
       JMenu browseMovies = new JMenu("Movies");
       JMenu viewCart = new JMenu("View Purchases");

       //add menus to menubar
       //menubar.add(menubar);
       menubar.add(homeBtn);
       menubar.add(registerUser);
       menubar.add(browseMovies);
       menubar.add(viewCart);

       //attach menubar to frame
       setJMenuBar(menubar);
       setVisible(true);

       /*search bar code */

       //search panel
       JPanel searchPanel = new JPanel(new BorderLayout());
       searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

       //text field
       JTextField searchField = new JTextField();
       searchField.setToolTipText("Search for a movie...");
       searchPanel.add(searchField, BorderLayout.CENTER);

       //search btn
        JButton searchButton = new JButton();
        searchButton.setToolTipText("Search");
        ImageIcon searchIcon = new ImageIcon("/Images/search.png"); // Replace with your icon path
        searchButton.setIcon(searchIcon);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);





    }

    // public void displayMainPage(){
    //     //MainPage mainPage = new MainPage();
        
    // }
    
}

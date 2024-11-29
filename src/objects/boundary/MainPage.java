//main page where all the movies are, nav bar(user,purchases), search bar, logo
package objects.boundary;

import javax.swing.*;

import objects.control.MovieController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener; 




public class MainPage extends JFrame implements ActionListener {

    MovieController searchMovie;
    JMenuBar menubar;

    JMenu navigateMenu;
    JMenu accMenu;

    JMenuItem registerAcc;
    JMenuItem browseMovies;
    JMenuItem viewCart;
    JMenuItem logoutOption;
    JMenuItem homeBtn;

    //logo
    ImageIcon logoIcon;
    JLabel logoLabel;
    

    MainPage(){

        //setup frame
        setTitle("Movie App");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
       menubar = new JMenuBar();
       
       //create menus
       navigateMenu = new JMenu("Navigate");
       accMenu = new JMenu("Account");

       //navigate menu
       browseMovies = new JMenuItem("Movies");
       homeBtn = new JMenuItem("Home");
       //acount menu
       viewCart = new JMenuItem("View Purchases");
       logoutOption = new JMenuItem("Logout");
       registerAcc = new JMenuItem("Register Account");

       browseMovies.addActionListener(this);
       homeBtn.addActionListener(this);
       logoutOption.addActionListener(this);



       navigateMenu.add(browseMovies);
       navigateMenu.add(homeBtn);

       accMenu.add(viewCart);
       accMenu.add(registerAcc);
       accMenu.add(logoutOption);
       
       //add menus to menubar
       //menubar.add(menubar);
       //menubar.add(homeBtn);
       //menubar.add(registerUser);
       menubar.add(navigateMenu);
       menubar.add(accMenu);
      // menubar.add(logoutOption);


       //attach menubar to frame
       setJMenuBar(menubar);
       setVisible(true);

      

       //wrapper panel to center everything
       JPanel wrapperPanel = new JPanel(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10,10,10,10); //padding
       gbc.gridx = 0;//center horizontally

       //add logo above searchbar

       logoLabel = new JLabel();
       logoIcon = new ImageIcon(getClass().getResource("Images/AcmePlex.png"));
       Image scaledLogo = logoIcon.getImage().getScaledInstance(300,300, Image.SCALE_SMOOTH);
       gbc.gridy = 0;
       logoLabel.setIcon(new ImageIcon(scaledLogo));
       wrapperPanel.add(logoLabel,gbc);

    /*search bar setup*/

       //search panel
       JPanel searchPanel = new JPanel(new BorderLayout());
       searchPanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));

       //search text field
       JTextField searchField = new JTextField();
       searchField.setToolTipText("Search for a movie...");
       searchField.setPreferredSize(new Dimension(300, 30));
       searchPanel.add(searchField, BorderLayout.CENTER);
       gbc.gridy =1;
       gbc.fill = GridBagConstraints.HORIZONTAL;
       //wrapper
       wrapperPanel.add(searchPanel, gbc);
       add(wrapperPanel, BorderLayout.CENTER);



       //search btn
        JButton searchButton = new JButton();
        searchButton.setToolTipText("Search");
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("Images/search.png")); // Replace with your icon path
        //resize icon
        Image scaledImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        searchButton.setIcon(new ImageIcon(scaledImage));
        searchPanel.add(searchButton, BorderLayout.EAST);

        /*actionlistener for searchbtn */

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String movieSearch = searchField.getText();
                MovieController searchMovie = new MovieController();
                searchMovie.searchForMovie(movieSearch);
                //dispose();
                displaySearchedMovies(movieSearch);
            }
        });

        //add(searchPanel, BorderLayout.NORTH);





    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == logoutOption){

            dispose();
            JFrame frame = new JFrame();
            Login loginPage = new Login(frame);
            loginPage.displayLoginGUI();

        }

    }

    public void displaySearchedMovies(String movieSearched){
        //clear the mainpage so that it displays the movies searched

        // searchMovie = new MovieController();
        // searchMovie.searchForMovie(movieSearched);
        


    }

    // public void displayMainPage(){
    //     //MainPage mainPage = new MainPage();
        
    // }
    
}
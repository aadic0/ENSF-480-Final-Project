//main page where all the movies are, nav bar(user,purchases), search bar, logo
package objects.boundary;

import javax.swing.*;

import objects.control.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener; 
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class MainPage extends JFrame implements ActionListener {

    ShowtimeController searchMovie;
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

    JPanel searchPanel;
    //JTextField searchField;

    JPanel resultsPanel;
    JPanel mainPanel;
    

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

       //mainPanel

       mainPanel = new JPanel();
       mainPanel.setLayout(new BorderLayout());
    

      

       //wrapper panel to center everything
       JPanel searchwrapperPanel = new JPanel(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10,10,10,10); //padding
       gbc.gridx = 0;//center horizontally

       //add logo above searchbar

       logoLabel = new JLabel();
       logoIcon = new ImageIcon(getClass().getResource("Images/AcmePlex.png"));
       Image scaledLogo = logoIcon.getImage().getScaledInstance(300,300, Image.SCALE_SMOOTH);
       gbc.gridy = 0;
       logoLabel.setIcon(new ImageIcon(scaledLogo));
       searchwrapperPanel.add(logoLabel,gbc);

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
       searchwrapperPanel.add(searchPanel, gbc);
       mainPanel.add(searchwrapperPanel, BorderLayout.NORTH);

       /*panel for results after search */
       resultsPanel = new JPanel();
       resultsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
       JScrollPane scrollPane = new JScrollPane(resultsPanel);
       mainPanel.add(scrollPane, BorderLayout.CENTER);

       add(mainPanel);
       



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
                performSearch(movieSearch);
               
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

    public void performSearch(String movieName){
        //clear the mainpage so that it displays the movies searched
        
        try(Connection connection = DatabaseController.createConnection()){
            ShowtimeController controller = new ShowtimeController();
            HashMap<Integer, ArrayList<Object>> movieMap = controller.searchForMovie(connection, movieName);

            //clear results panel
            resultsPanel.removeAll();
            if (movieMap!=null && !movieMap.isEmpty()){
                for (Map.Entry<Integer, ArrayList<Object>> entry : movieMap.entrySet()) {
                //in future add code so that movies match with respected covers
                ArrayList<Object> movieDetails = entry.getValue();
                String title = (String) movieDetails.get(0);
                String coverPath = "Images/cover.png";

                //create panel for each movie
                JPanel moviePanel = new JPanel();
                moviePanel.setLayout(new BorderLayout());
                moviePanel.setPreferredSize(new Dimension(150,200));

                //add movie cover
                JLabel coverLabel = new JLabel();
                ImageIcon coverIcon = new ImageIcon(coverPath);
                Image scaledCover = coverIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                coverLabel.setIcon(new ImageIcon(scaledCover));
                moviePanel.add(coverLabel, BorderLayout.CENTER);

                //show movie title associated with cover
                JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
                titleLabel.setPreferredSize(new Dimension(150, 30));
                moviePanel.add(titleLabel, BorderLayout.SOUTH);

                resultsPanel.add(moviePanel);



                }



            } else{
                JLabel notFound = new JLabel("No movies found for:"+movieName);
                resultsPanel.add(notFound);
            }
            resultsPanel.revalidate();
            resultsPanel.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for movies.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        


    }

    // public void displayMainPage(){
    //     //MainPage mainPage = new MainPage();
        
    // }
    
}
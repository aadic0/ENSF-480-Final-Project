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

    private ViewMovie viewMovie;

    private String loggedinEmail;
    private String loggedinPass;


    public appGUI(JFrame frame){

        setTitle("Movie App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


       //add the different pages to the CardLayout

        viewMovie = new ViewMovie(this);
        
        mainPanel.add(new Login(this), "Login");
        mainPanel.add(new MainPage(this), "Home");
        mainPanel.add(new RegisterUser(this), "RegisterUser");
        mainPanel.add(new CreateUser(this), "Create Account");
        mainPanel.add(new BrowseMovie(this), "BrowseMovie");
        mainPanel.add(viewMovie, "ViewMovie");
        mainPanel.add(new BrowseAnnouncment(this), "BrowseAnnouncement");
        

        //add mainPanel to frame
        add(mainPanel);

        //start program with showing login page 
        showCard("Login");

        setVisible(true); 
    }

    //method to switch cards
    public void showCard(String cardName) {

        if(cardName.equals("Login") || cardName.equals("Create Account")){
            setJMenuBar(null);
        }else{

            setJMenuBar(new MainPage(this).menuBar());

        }
        cardLayout.show(mainPanel, cardName);

        revalidate();
        repaint();  

    }
    public void saveLoginDetails(String username, String password) {
        this.loggedinEmail = username;
        this.loggedinEmail = password;
    }

    public String getLoggedInUser() {
        return loggedinEmail;
    }

    public String getLoggedInPassword() {
        return loggedinPass;
    }

    public ViewMovie getViewMovie() {
        return viewMovie;
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




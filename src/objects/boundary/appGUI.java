//putting the gui together
package objects.boundary;

import java.awt.CardLayout;

import objects.control.*;
import objects.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.TreeMap; 

public class appGUI extends JFrame{

    private CardLayout cardLayout;
    private SeatMapPageTest seatMapPage;
    private ViewPurchases viewPurchases;
    private JPanel mainPanel;
    JFrame frame;

    private ViewMovie viewMovie;

    private String loggedinEmail;
    private String loggedinPass;

    private int seatID = -1;
    //check for regular users if payment for ticket is successful
    private boolean paymentSuccessful = false;

    int selectedShowtimeID;

    PaymentInfo paymentInfo;

    AnnouncementController announcementController;



    public appGUI(JFrame frame){

        setTitle("Movie App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        //setup seatmap
       
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        seatMapPage = new SeatMapPageTest(retrieveSeatMap(),this);
        viewPurchases = new ViewPurchases(this);
        PaymentPage paymentPage = new PaymentPage(this);


       //add the different pages to the CardLayout

        viewMovie = new ViewMovie(this);
        
        mainPanel.add(new Login(this), "Login");
        mainPanel.add(new MainPage(this), "Home");
        mainPanel.add(new RegisterUser(this), "RegisterUser");
        mainPanel.add(new CreateUser(this), "Create Account");
        mainPanel.add(new BrowseMovie(this), "BrowseMovie");
        mainPanel.add(viewMovie, "ViewMovie");
        mainPanel.add(seatMapPage, "SeatMap");
        mainPanel.add(paymentPage, "Payment");



       
        mainPanel.add(new BrowseAnnouncment(this), "BrowseAnnouncement");
        mainPanel.add(viewPurchases, "ViewPurchases");
        

        //add mainPanel to frame
        add(mainPanel);

        //start program with showing login page 
        showCard("Login");

        // announcementController = new AnnouncementController();
        // announcementController.retrieveAllAnnouncement(loggedinEmail);

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
    //method to setup seatmap
    public void showSeatMap(TreeMap<Integer, Boolean> seatMap) {
        seatMapPage = new SeatMapPageTest(seatMap, this);
        mainPanel.add(seatMapPage, "SeatMap");
        showCard("SeatMap");
    }
    public SeatMapPageTest getSeatMapPage() {
        return seatMapPage; 
    }

   

    public void saveLoginDetails(String username, String password) {
        this.loggedinEmail = username;
        this.loggedinPass = password;
    }

    private TreeMap<Integer, Boolean> retrieveSeatMap() {
        TicketController ticketController = new TicketController();
        HashMap<Integer, Boolean> seatMap = new HashMap<>();
    
        try (Connection connection = DatabaseController.createConnection()) {
            seatMap = ticketController.retrieveAvailableSeats(connection, 9); // Replace `9` with appropriate showtimeID
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Convert to TreeMap for sorting
        return new TreeMap<>(seatMap);
    }

    public void setSeatID(int seatID){
        this.seatID = seatID;
    }

    public int getSeatID(){
        return seatID;
    }

    public void setSelectedShowtimeID(int showtimeID) {
        this.selectedShowtimeID = showtimeID;
    }

    public int getSelectedShowtimeID() {
        return selectedShowtimeID;
    }
    

    public String getLoggedInUser() {
        return loggedinEmail;
    }

    public void setLoggedOut() {
        this.loggedinEmail = null;
    }

    public String getLoggedInPassword() {
        return loggedinPass;
    }

    public ViewMovie getViewMovie() {
        return viewMovie;
    }

    public void setPaymentSuccessful(boolean paymentSuccess){
        this.paymentSuccessful = paymentSuccess;
    }
    public boolean getPaymentSuccessful(){
        return paymentSuccessful;
    }
    public ViewPurchases getViewPurchases() {
        return viewPurchases;
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




package objects.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;
import objects.entity.Seat;
import java.sql.*;
import java.text.SimpleDateFormat;


// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

// NOTE: I'm using this file to test out database communication, this will (probably) not be the actual main we use
public class Main {

    public static void registerUserTest(){
        RegisteredUserController regUserController = new RegisteredUserController();

        // 1. Create test data
        String testEmail = "testuser@gmail.com";
        String testPassword = "password123";
        // Need to add L or it gets upset
        PaymentInfo testPaymentInfo = new PaymentInfo(1234567812345678L, "2025-12-31", 123);
        RegisteredUser testUser = new RegisteredUser(
            "John", "Doe",                         // First and last name
            "123 Fake Street", "Calgary", "AB",    // Address
            "T1X 1E4",                             // Postal code
            testEmail,                             // Email
            testPaymentInfo                        // Payment info
        );

        // 2. Run the registerUser method
        try {
            regUserController.registerUser(testUser, testPassword);
            System.out.println("User registration completed successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred during user registration:");
            e.printStackTrace();
        }

        // 3. Validate the data in the database
        try (Connection connection = DatabaseController.createConnection()) {
            // Verify data in USER_PAYMENT_INFO
            String paymentQuery = "SELECT * FROM USER_PAYMENT_INFO WHERE Email = ?";
            try (PreparedStatement psPayment = connection.prepareStatement(paymentQuery)) {
                psPayment.setString(1, testEmail);
                ResultSet rs = psPayment.executeQuery();

                if (rs.next()) {
                    System.out.println("Payment info verified:");
                    System.out.println("PaymentID: " + rs.getInt("PaymentID"));
                    System.out.println("NumberCC: " + rs.getLong("NumberCC"));
                    System.out.println("ExpirationDate: " + rs.getDate("ExpirationDate"));
                    System.out.println("CVV: " + rs.getInt("CVV"));
                    System.out.println("Email: " + rs.getString("Email"));
                } else {
                    System.out.println("No payment info found for email: " + testEmail);
                }
            }

            // Verify data in REGISTERED_USER
            String userQuery = "SELECT * FROM REGISTERED_USER WHERE Email = ?";
            try (PreparedStatement psUser = connection.prepareStatement(userQuery)) {
                psUser.setString(1, testEmail);
                ResultSet rs = psUser.executeQuery();

                if (rs.next()) {
                    System.out.println("Registered user info verified:");
                    System.out.println("Email: " + rs.getString("Email"));
                    System.out.println("FirstName: " + rs.getString("FirstName"));
                    System.out.println("LastName: " + rs.getString("LastName"));
                    System.out.println("StreetAddress: " + rs.getString("StreetAddress"));
                    System.out.println("City: " + rs.getString("City"));
                    System.out.println("Province: " + rs.getString("Province"));
                    System.out.println("PostalCode: " + rs.getString("PostalCode"));
                    System.out.println("PaymentID: " + rs.getInt("PaymentID"));
                } else {
                    System.out.println("No registered user found for email: " + testEmail);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during validation:");
            e.printStackTrace();
        }
    }

    public static void seatBookingTest() {
        // Sample seat, movieID, theatreRoomID, and showtime details for the test
        Seat seat = new Seat(1, 1); // SeatRow 1, SeatNumber 1
        int theatreRoomID = 1;
        int movieID = 1; // Glimbo's Revenge
        String showDateTimeStr = "2024-11-22 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date showDateTime = new Date(sdf.parse(showDateTimeStr).getTime()); // Convert showDateTime string to Date
            
            // Create an instance of the SeatController class
            SeatController seatController = new SeatController();

            // Call the method to check if the seat is available
            boolean isAvailable = seatController.isSeatAvailable(seat, theatreRoomID, showDateTime, movieID);
            System.out.println("Is seat available? " + isAvailable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {

    //------------------------------//
    //   RegisteredUserController   //
    //------------------------------//
    // RegisteredUserController regUserController = new RegisteredUserController();
    
    // // // Test the method
    // String testEmail = "test@test.com";
    // String testPassword = "jdoe";
    // boolean isAuthenticated = regUserController.authenticateUser(testEmail, testPassword);
    
    // System.out.println("Authentication result: " + isAuthenticated);

    // regUserController.registerUser("abc@gmail.com", "deezNutz", "Jane", "Doe", "123 Real Address", "Edmonton", "Alberta", "T2P 0G5");
    // regUserController.updateAddressInfo("Fake address");
    // regUserController.updateName("Bob", "Doe");
    // regUserController.updateEmailInfo("deeznuts@gmail.com");

    // ------------------------------//
    //   RegisteredUserController    //
    // ------------------------------//
    // Main.registerUserTest();


    // --------------------//
    //   SeatController    //
    // --------------------//
    seatBookingTest();

    //------------------------//
    //   ShowtimeController   //
    //------------------------//
    // ShowtimeController showtimeController = new ShowtimeController();
    // showtimeController.getAllShowtimes();

    //------------------------//
    //     MovieController    //
    //------------------------//
    // MovieController movieController = new MovieController();
    // movieController.getAllMovies();


    }

    
}

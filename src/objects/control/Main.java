package objects.control;

import objects.entity.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.sql.*;


// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

// NOTE: I'm using this file to test out database communication, this will (probably) not be the actual main we use
public class Main {

    // public static void buyTicketTest() {
    //     // Mock data for testing
    //     String email = "testuser@example.com";
        
    //     // Create mock payment info
    //     PaymentInfo paymentInfo = new PaymentInfo(
    //             1234567812345678L,  // Credit Card Number
    //             "2025-12-31",       // Expiration Date
    //             123                        // CVV
    //     );

    //     // Create a mock Seat object (for example, Row 2, Seat 5)
    //     Seat seat = new Seat(2, 5);

    //     // Create a mock Showtime object (for example, "The Dark Knight" at Room 1, 2024-11-23 18:30:00)
    //     Movie movie = new Movie(1, "Glimbo's Revenge", "Sci-Fi", "PG-13", Time.valueOf("14:30:00"));
    //     TheatreRoom theatreRoom = new TheatreRoom(2, new Theatre(1, "ACMEplex Theatre", "123 Main Street, Calgary, AB"), "Room B");
    //     Showtime showtime = new Showtime(1, movie, theatreRoom, Timestamp.valueOf("2024-11-25 15:00:00"));

    //     // Simulate the ticket purchase
    //     TicketController ticketController = new TicketController();
    //     ticketController.purchaseTicket(email, seat, showtime, paymentInfo, 15.99f);

    //     // Verify if the ticket was added to the database
    //     try (Connection connection = DatabaseController.createConnection()) {
    //         String query = "SELECT * FROM TICKET WHERE ShowtimeID = ?";
    //         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //             preparedStatement.setInt(1, showtime.getShowtimeID());

    //             var resultSet = preparedStatement.executeQuery();
    //             if (resultSet.next()) {
    //                 System.out.println("Ticket purchase was successful!");
    //                 System.out.println("Ticket Details:");
    //                 System.out.println("TicketID: " + resultSet.getInt("TicketID"));
    //                 System.out.println("SeatID: " + resultSet.getInt("SeatID"));
    //                 System.out.println("PurchaseDateTime: " + resultSet.getTimestamp("PurchaseDateTime"));
    //             } else {
    //                 System.out.println("Ticket purchase failed.");
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public static void buyTicketTest() {
        // Mock data for testing
        String email = "testuser@example.com";
    
        // Create mock payment info
        PaymentInfo paymentInfo = new PaymentInfo(
                1234567812345678L,  // Credit Card Number
                "2025-12-31",       // Expiration Date
                123                 // CVV
        );
    
        // Create a mock Seat object (for example, Row 2, Seat 5)
        Seat seat = new Seat(2, 5); // Ensure this seat exists in the database
    
        // Create a mock Showtime object (for example, "The Dark Knight" at Room 1, 2024-11-23 18:30:00)
        Movie movie = new Movie(10, "Devonian Park", "Adventure", "PG-13", Time.valueOf("2:01:00"));
        TheatreRoom theatreRoom = new TheatreRoom(1, new Theatre(1, "ACMEplex Theatre", "123 Main Street, Calgary, AB"), "Room A");
        Showtime showtime = new Showtime(20, movie, theatreRoom, Timestamp.valueOf("2024-11-25 14:30:00"));
    
        // Simulate the ticket purchase
        TicketController ticketController = new TicketController();
        ticketController.purchaseTicket(email, seat, showtime, paymentInfo, 15.99f);
    
        // Verify if the ticket was added to the database
        try (Connection connection = DatabaseController.createConnection()) {
            String query = "SELECT * FROM TICKET WHERE ShowtimeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, 1);
    
                var resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Ticket purchase was successful!");
                    System.out.println("Ticket Details:");
                    System.out.println("TicketID: " + resultSet.getInt("TicketID"));
                    System.out.println("SeatID: " + resultSet.getInt("SeatID"));
                    System.out.println("PurchaseDateTime: " + resultSet.getTimestamp("PurchaseDateTime"));
                } else {
                    System.out.println("Ticket purchase failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refundTicketTest() {
        TicketController tc = new TicketController();
        tc.refundTicket(7, "testuser@gmail.com", null);
    }
    


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
            Timestamp showDateTime = new Timestamp(sdf.parse(showDateTimeStr).getTime()); // Convert showDateTime string to Date
            
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
    // seatBookingTest();

    //-------------------------//
    //     TicketController    //
    //-------------------------//

    // buyTicketTest();
    // refundTicketTest(); // Need to change ticketID manually everytime or this wont work

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

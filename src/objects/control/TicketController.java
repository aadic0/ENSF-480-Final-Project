package objects.control;

// entity imports
import objects.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;
import objects.entity.Seat;
import objects.entity.Showtime;
import objects.entity.Ticket;


public class TicketController {
    
    public TicketController(){}

    /**
     * Have a user buy a ticket for a seat in a showtime
     * @param email
     * @param seat
     * @param showtime
     * @param paymentInfo
     * @param price
     */
    public void purchaseTicket(String email, Seat seat, Showtime showtime, PaymentInfo paymentInfo, float price){
        // FUNCTIONALITY MISSING:
        //  - Doesn't check for announcement date, so RUs cannot book 10% of seats early
        
        // Create controllers
        SeatController seatController = new SeatController();
        PaymentController paymentController = new PaymentController();

        // Create flags
        boolean ticketAvailable;
        boolean paymentValid;
        
        // Check if ticket is available
        ticketAvailable = seatController.isSeatAvailable(seat, 
                                                         showtime.getTheatreRoom().getTheatreRoomID(), 
                                                         showtime.getShowTimestamp(), 
                                                         showtime.getMovie().getMovieID()
                                                        );
        
        if(ticketAvailable) {
            // Try to pay for ticket
            paymentValid = paymentController.pay(paymentInfo, price); // This will always return true, but stimulates paying

            if(paymentValid) {
                // Add ticket to database
                try (Connection connection = DatabaseController.createConnection()) {
                    String query = "INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email)" + 
                                   "VALUES (?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        // I cannot overstate how much I hate this solution
                        // I added private seatID and showtimeID to the controller which is nasty but its such as simple solution
                        // -Damon Nov 23 
                        preparedStatement.setInt(1, seatController.getSeatID());
                        preparedStatement.setInt(2, seatController.getShowtimeID());
                        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                        preparedStatement.setString(4, email);

                    int rowsAffected = preparedStatement.executeUpdate();

                    // Testing to make sure ticket was added
                    if (rowsAffected > 0) {
                        System.out.println("Ticket added successfully!");
                    } 
                    else {
                        System.out.println("Failed to add ticket.");
                    }

                    } catch (Exception e) { e.printStackTrace(); }
                } catch (Exception e) { e.printStackTrace(); }
            }
            
        }
        // Need to implement a reciept thing
        // System.out.println("Sent user email and receipt");
    }

    /**
     * Refund a ticket to an email (NOTE: THIS METHOD IS UNTESTED)
     * @param t
     * @param user
     * @param paymentInfo
     */
    public void refundTicket(Ticket ticket, String email, PaymentInfo paymentInfo) {
        String query1 = "SELECT Email FROM TICKET WHERE TicketID = ?"; // Make sure ticket exists
        String query2 = "SELECT COUNT(Email) FROM REGISTERED_USER WHERE Email = ?"; // Check if email is in Reg User table

        boolean regUserFlag = false;

        PaymentController paymentController = new PaymentController();

        try (Connection connection = DatabaseController.createConnection()) {
            
            // Query 1 - Check if ticket exists
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {
                preparedStatement1.setInt(1, ticket.getTicketID());

                int rowsAffected = preparedStatement1.executeUpdate();

                // Check if ticket is in DB
                if (rowsAffected > 0) {
                    System.out.println("Ticket in DB");
                } else {
                    System.out.println("Ticket not in DB");
                    return;
                }

            } catch (Exception e) { e.printStackTrace(); }

            // Query 2 - Check if email is associated with a RegisteredUser
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                preparedStatement2.setString(1, email);

                int rowsAffected = preparedStatement2.executeUpdate();

                if (rowsAffected > 0) 
                    regUserFlag = true;
                
            } catch (Exception e) { e.printStackTrace(); }

            // Refund user
            // Temp refund price of $0 until we decide on what functionality is
            paymentController.refund(paymentInfo, 0, regUserFlag);

        } catch (Exception e) { e.printStackTrace(); }
    }
    
     


}

package objects.control;

// entity imports
import objects.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
                        preparedStatement.setInt(1, seatController.getShowtimeID());
                        preparedStatement.setInt(2, seatController.getSeatID());
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
     * Refund a ticket to an email
     * @param t
     * @param user
     * @param paymentInfo
     */
    public void refundTicket(int ticketID, String email, PaymentInfo paymentInfo) {
        
        String query1 = "SELECT Email, ShowtimeID FROM TICKET WHERE TicketID = ?"; // Make sure ticket exists
        int showtimeID = -1;
        String query22 = "SELECT ShowDateTime FROM SHOWTIME WHERE ShowtimeID = ?";
        String query2 = "SELECT COUNT(Email) FROM REGISTERED_USER WHERE Email = ?"; // Check if email is in Reg User table
        String query3 = "DELETE FROM TICKET WHERE TicketID = ?"; // Remove ticket from DB

        boolean regUserFlag = false;

        PaymentController paymentController = new PaymentController();

        try (Connection connection = DatabaseController.createConnection()) {
            
            // Query 1 - Check if ticket exists
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {
                preparedStatement1.setInt(1, ticketID);

                ResultSet rs1 = preparedStatement1.executeQuery();

                // Check if ticket is in DB
                if (rs1.next()) {
                    showtimeID = rs1.getInt("ShowtimeID");
                    System.out.println("Ticket in DB");
                } else {
                    System.out.println("Ticket not in DB");
                    return;
                }
            } catch (Exception e) { e.printStackTrace(); }

            // Query 22 - Check if cancellation is at least 72 hours before show
            try (PreparedStatement preparedStatement22 = connection.prepareStatement(query22)) {
                preparedStatement22.setInt(1, showtimeID);

                ResultSet rs22 = preparedStatement22.executeQuery();

                if (rs22.next()) {
                    // Get showDateTime and process
                    Timestamp showDateTime = rs22.getTimestamp("ShowDateTime");

                    if (showDateTime == null) return;

                    LocalDateTime showTime = showDateTime.toLocalDateTime();
                    System.out.println("showTime: " + showTime);
                    LocalDateTime nowTime = LocalDateTime.now();
                    
                    // Showtime is more than 72 hours away
                    if (nowTime.isBefore(showTime.minusHours(72))) {
                        System.out.println("Cancellation valid: More than 72 hours before showtime.");
                    } 
                    // User is trying to cancel after showtime
                    else if (nowTime.isAfter(showTime)) {
                        System.out.println("Cancellation not valid: Showtime has already occured");
                        return;
                    }
                    // Showtimes is less than 72 hours away
                    else {
                        System.out.println("Cancellation not valid: Less than 72 hours before showtime.");
                        return;
                    }
                } else {
                    System.out.println("No ShowtimeID");
                    return;
                }
            } catch (Exception e) { e.printStackTrace(); }

            // Query 2 - Check if email is associated with a RegisteredUser
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                preparedStatement2.setString(1, email);

                ResultSet rs2 = preparedStatement2.executeQuery();

                if (rs2.next()) 
                    regUserFlag = true;
                    System.out.println("email associated with RegUser");
                
            } catch (Exception e) { e.printStackTrace(); }

            // Refund user
            // Temp refund price of $0 until we decide on what functionality is
            paymentController.refund(paymentInfo, 0, regUserFlag);

            // Query 3 - Remove ticket from DB
            try (PreparedStatement preparedStatement3 = connection.prepareStatement(query3)) {
                preparedStatement3.setInt(1, ticketID);

                int rowsAffected = preparedStatement3.executeUpdate();

                if (rowsAffected > 0) 
                    System.out.println("Removed Ticket with TicketID " + ticketID + " from DB");
                else
                    System.out.println("Failed to remove ticket");
                
            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
     


}

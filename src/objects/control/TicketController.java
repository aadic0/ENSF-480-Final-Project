package objects.control;

// entity imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import objects.entity.PaymentInfo;
import objects.entity.Seat;
import objects.entity.Showtime;


public class TicketController {

    final float TICKET_PRICE = 20.00f;
    
    public TicketController(){}



//-----------------------------------------------------------------//
//                      TICKET PURCHASING                          //
//-----------------------------------------------------------------//

    /**
     * buy a ticket for a seat for a showtime
     * Remade version of purchaseTicket so that you don't have to make a showtime object
     * @param seat
     * @param showtimeID
     * @param paymentInfo
     * @param price
     * @param email
     */
    public void purchaseTicket(PaymentInfo paymentInfo, int seatID, int showtimeID, String email){
        // FUNCTIONALITY MISSING:
        //  - Doesn't check for announcement date, so RUs cannot book 10% of seats early
        
        // Create controller
        PaymentController paymentController = new PaymentController();
        AnnouncementController announcementController = new AnnouncementController();

        // Create flags
        boolean ticketAvailable;
        boolean paymentValid = true;

        Timestamp ticketBuyTime = null;
        
        // Check if ticket is available
        ticketAvailable = isPurchasable(seatID, showtimeID, email);
    
        if(ticketAvailable) {
            // Try to pay for ticket
            paymentValid = paymentController.pay(paymentInfo, TICKET_PRICE, email); // This will always return true, but stimulates paying
            

            if(paymentValid) {
                // Add ticket to database
                try (Connection connection = DatabaseController.createConnection()) {
                    String query = "INSERT INTO TICKET (ShowtimeID, SeatID, PurchaseDateTime, Email, TicketPrice)" + 
                                   "VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                        ticketBuyTime = new Timestamp(System.currentTimeMillis());

                        preparedStatement.setInt(1, showtimeID);
                        preparedStatement.setInt(2, seatID);
                        preparedStatement.setTimestamp(3, ticketBuyTime);
                        preparedStatement.setString(4, email);
                        preparedStatement.setFloat(5, TICKET_PRICE);

                    int rowsAffected = preparedStatement.executeUpdate();

                    // Testing to make sure ticket was added
                    if (rowsAffected > 0) {
                        System.out.println("Ticket added successfully!");
                    } 
                    else {
                        System.out.println("Failed to add ticket.");
                    }

                    } catch (Exception e) { e.printStackTrace(); }


                    String message = sendReceiptAnnouncement(connection, paymentInfo, seatID, showtimeID, email);
                    announcementController.sendEmailAnnouncement(message, email);

                } catch (Exception e) { e.printStackTrace(); }
            }
            
        } else {
            System.out.println("Ticket unavailable for purchase");
            return;
        }
        


    }

    

    /**
     * Refund a ticket to an email
     * @param t
     * @param user
     * @param paymentInfo
     */
    public String refundTicket(int ticketID, String email, PaymentInfo paymentInfo) {
        
        String query1 = "SELECT Email, ShowtimeID FROM TICKET WHERE TicketID = ?"; // Make sure ticket exists
        int showtimeID = -1;
        String query2 = "SELECT ShowDateTime FROM SHOWTIME WHERE ShowtimeID = ?";
        String query3 = "DELETE FROM TICKET WHERE TicketID = ?"; // Remove ticket from DB

        PaymentController paymentController = new PaymentController();
        AnnouncementController announcementController = new AnnouncementController();

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
                    return "Ticket not in DB";
                }
            } catch (Exception e) { e.printStackTrace(); }

            // Query 22 - Check if cancellation is at least 72 hours before show
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                preparedStatement2.setInt(1, showtimeID);

                ResultSet rs2 = preparedStatement2.executeQuery();

                if (rs2.next()) {
                    // Get showDateTime and process
                    Timestamp showDateTime = rs2.getTimestamp("ShowDateTime");

                    if (showDateTime == null) return "ShowDateTime is Null";

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
                        return "Cancellation not valid: Showtime has already occured";
                    }
                    // Showtimes is less than 72 hours away
                    else {
                        System.out.println("Cancellation not valid: Less than 72 hours before showtime.");
                        return "Cancellation not valid: Less than 72 hours before showtime.";
                    }
                } else {
                    System.out.println("No ShowtimeID");
                    return "No ShowtimeID";
                }
            } catch (Exception e) { e.printStackTrace(); }

            // Refund user after all checks
            paymentController.refund(ticketID, email);

            // Query 3 - Remove ticket from DB
            try (PreparedStatement preparedStatement3 = connection.prepareStatement(query3)) {
                preparedStatement3.setInt(1, ticketID);

                int rowsAffected = preparedStatement3.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Removed Ticket with TicketID " + ticketID + " from DB");
                    String message = "Cancelled ticket " + ticketID;
                    announcementController.sendEmailAnnouncement(message, email);
                    return "Cancellation successful"; 
                }
                else
                    System.out.println("Failed to remove ticket");
                    return "Failed to cancel ticket.";
                
            } catch (Exception e) { e.printStackTrace(); }

            

            
            

        } catch (Exception e) { 
            e.printStackTrace(); 
            return "An error occurred while processing the cancellation.";
        
        }

        return "Cancellation processing failed unexpectedly.";
   
    }

    //getter to retrieve TicketID

    public int getTicketID(int showtimeID, int seatID, String email) {
        String query = "SELECT TicketID FROM TICKET WHERE ShowtimeID = ? AND SeatID = ? AND Email = ?";
        int ticketID = -1;

        System.out.println("Retrieving ticket with ShowtimeID=" + showtimeID + ", SeatID=" + seatID + ", Email=" + email);

    
        try (Connection connection = DatabaseController.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setInt(1, showtimeID);
            preparedStatement.setInt(2, seatID);
            preparedStatement.setString(3, email);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ticketID = resultSet.getInt("TicketID");
                    System.out.println("Retrieved TicketID: " + ticketID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return ticketID;
    }
    

//-------------------------------------------------------------------


//-----------------------------------------------------------------//
//                      CHECKER METHODS                            //
//-----------------------------------------------------------------//




    /**
     * Check if the seat the user is trying to book for a showtime is able to be booked
     * @param seat
     * @param showtimeID
     * @return if the seat is available (true) or not (false)
     */
    public boolean isPurchasable(Seat seat, int showtimeID, String email) {

        int seatID = -1;

        try (Connection connection = DatabaseController.createConnection()) {

            // If the announcement associated with a showtime is private and the user purchasing is not a RegUser, return false
            if(isPrivateAnnouncementTicket(connection, showtimeID))
                if(!isBelowMaxPrivateTickets(connection, showtimeID, email)) 
                    return false;

            // If the purchasable time has passed, return false
            if (!isPurchasableTimeCheck(connection, showtimeID)) 
                return false; 

            // Find the seat ID to use in the next check
            seatID = retrieveSeatID(connection, seat, showtimeID);

            // If the seat for the showtime is not available for purchase, return false
            if (!isTicketAvailable(connection, seatID, showtimeID))
                return false; 
            
        } catch (Exception e) { e.printStackTrace(); }

        return true;
    }

    /**
     * Check if the seat the user is trying to book for a showtime is able to be booked
     * @param seat
     * @param showtimeID
     * @return if the seat is available (true) or not (false)
     */
    public boolean isPurchasable(int seatID, int showtimeID, String email) {

        // int seatID = -1;

        try (Connection connection = DatabaseController.createConnection()) {

            // If the announcement associated with a showtime is private and the user purchasing is not a RegUser, return false
            if(isPrivateAnnouncementTicket(connection, showtimeID))
                if(!isBelowMaxPrivateTickets(connection, showtimeID, email)) 
                    return false;

            // If the purchasable time has passed, return false
            if (!isPurchasableTimeCheck(connection, showtimeID)) 
                return false; 

            // Find the seat ID to use in the next check
            // seatID = retrieveSeatID(connection, seat, showtimeID);

            // If the seat for the showtime is not available for purchase, return false
            if (!isTicketAvailable(connection, seatID, showtimeID))
                return false; 
            
        } catch (Exception e) { e.printStackTrace(); }

        return true;
    }

    /**
     * Check if the showtime has passed. Also doubles as a check to see if the showtime exists
     * @param con
     * @param showtimeID
     * @return If the ticket is purchasable (true) or not (false)
     */
    public boolean isPurchasableTimeCheck(Connection con, int showtimeID) {
        
        String query1 = "SELECT MovieID, ShowDateTime FROM SHOWTIME WHERE ShowtimeID = ?";
        String query2 = "SELECT Runtime FROM MOVIE WHERE MovieID = ?";

        int movieID = -1;
        Timestamp showDateTime = null;
        Time movieRuntime_time = null;

        // Query 1 - Get movie from showtime
        try (PreparedStatement psQuery1 = con.prepareStatement(query1)) {
            psQuery1.setInt(1, showtimeID);

            // Get movieID and showdateTime or return false
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next()) {
                    movieID = rs1.getInt("MovieID");
                    showDateTime = rs1.getTimestamp("ShowDateTime");
                } else {
                    System.out.println("No movieID found");
                    return false; // No movie found
                }
            } catch (Exception e) { e.printStackTrace();  }
        } catch (Exception e) { e.printStackTrace();  }

        // Query 2 - Find the runtime from the movie and compare it to the showtime 
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
            psQuery2.setInt(1, movieID);
                
            try (ResultSet rs2 = psQuery2.executeQuery()) {
                if (rs2.next()) {
                    // get runtime
                    movieRuntime_time = rs2.getTime("Runtime");

                    // Get the runtime and add it to the showtime, then check if the current time is after the showtime+runtime
                    LocalTime movieRuntime_localtime = movieRuntime_time.toLocalTime();
                    LocalDateTime showTime = showDateTime.toLocalDateTime();
                    LocalDateTime nowTime = LocalDateTime.now();

                    int runtimeHours = movieRuntime_localtime.getHour();
                    int runtimeMinutes = movieRuntime_localtime.getMinute();

                    LocalDateTime showtimePlusRuntime = showTime.plusHours(runtimeHours).plusMinutes(runtimeMinutes);

                    // Current time is after the showtime
                    if(nowTime.isAfter(showtimePlusRuntime)) {
                        System.out.println("Cannot purchase, the showtime is over");
                        return false; // showtime is not avaiable
                    }

                } else {
                    System.out.println("No movie found");
                    return false; // No movie found
                }

            } catch (Exception e) { e.printStackTrace();  } 
            
        } catch (Exception e) { e.printStackTrace();  }

        // If all checks pass, return true
        return true;
    }

    /**
     * Get the seatID for a seat and a showtime
     * @param con
     * @param seat
     * @param showtimeID
     * @return the seatID for a showtime and seat
     */
    public int retrieveSeatID(Connection con, Seat seat, int showtimeID) {
        String query1 = "SELECT TheatreRoomID FROM SHOWTIME WHERE ShowtimeID = ?";
        String query2 = "SELECT SeatID FROM SEAT WHERE SeatRow = ? AND SeatNumber = ? AND TheatreRoomID = ?";

        int theatreRoomID = -1;
        int seatID = -1;


        // Query 1 - Get the TheatreRoomID from SHOWTIME table
        try (PreparedStatement psQuery1 = con.prepareStatement(query1)) {
                
            psQuery1.setInt(1, showtimeID);

            // Find seat ID
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next()) {
                    theatreRoomID = rs1.getInt("TheatreRoomID");
                } else {
                    System.out.println("TheatreRoomID not found");
                    return -1; // TheatreRoomID not found
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }


        // Query 2 - Get the SeatID from the SEAT table
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
                
            psQuery2.setInt(1, seat.getSeatRow());
            psQuery2.setInt(2, seat.getSeatNumber()); 
            psQuery2.setInt(3, theatreRoomID);

            // Find seat ID
            try (ResultSet rs2 = psQuery2.executeQuery()) {
                if (rs2.next()) {
                    seatID = rs2.getInt("SeatID");
                } else {
                    System.out.println("No matching seat found.");
                    return -1; // No matching seat found
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }

        return seatID;
    }

    /**
     * Return if the ticket is available for purchase (true) or not (false)
     * @param con
     * @param seatID
     * @param showtimeID
     * @return ticket is available for purchase (true) or not (false)
     */
    public boolean isTicketAvailable(Connection con, int seatID, int showtimeID) {
        String query = "SELECT TicketID FROM TICKET WHERE SeatID = ? AND ShowtimeID = ?";

        // Query - Find out if ticket is aviable for purchase
        try (PreparedStatement psQuery = con.prepareStatement(query)) {
            psQuery.setInt(1, seatID);
            psQuery.setInt(2, showtimeID);

            // If ticketID exists then return false
            try (ResultSet rs = psQuery.executeQuery()) {
                if (rs.next()) 
                    return false; // Seat is booked
                
            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }

        return true; // Ticket is purchaseable
    }

    /**
     * Check if the showtime is still private (true) or has been announced publicly (false)
     * @param con
     * @param showtimeID
     * @return the showtime is still private (true) or has been announced publicly (false)
     */
    public boolean isPrivateAnnouncementTicket(Connection con, int showtimeID) {
        String query1 = "SELECT MovieID FROM SHOWTIME WHERE ShowtimeID = ?";
        String query2 = "SELECT COUNT(*) AS numPublic FROM ANNOUNCEMENT WHERE MovieID = ? AND IsPublic = TRUE";

        int movieID = -1;
        int numPublic = -1;

        // Query 1 - Find the MovieID from the Showtime associated with ShowtimeID
        try (PreparedStatement psQuery1 = con.prepareStatement(query1)) {
            psQuery1.setInt(1, showtimeID);

            // get number of public announcements associated with a ShowtimeID
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next()) 
                    movieID = rs1.getInt("MovieID"); 
                else {
                    return false; // Query couldn't get any results
                }
            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }

        // Query 2 - See if there are any public announcements associated with the MovieID
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
            psQuery2.setInt(1, movieID);

            // get number of public announcements associated with a ShowtimeID
            try (ResultSet rs2 = psQuery2.executeQuery()) {
                if (rs2.next()) 
                    numPublic = rs2.getInt("numPublic"); 
                else {
                    return false; // Query couldn't get any results
                }
            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }

        // If there are any public announcements, return false. Otherwise return true
        if(numPublic >= 1) 
            return false;

        return true;
    }

    /**
     * Checks if there are few enough tickets for a privately announced showtime such that a RegUser can purchase one
     * @param con
     * @param showtimeID
     * @return true if purchasable, false if not purchasable
     */
    public boolean isBelowMaxPrivateTickets(Connection con, int showtimeID, String email) {
        String query0 = "SELECT COUNT(*) AS numEmail FROM REGISTERED_USER WHERE Email = ?";
        String query1 = "SELECT TheatreRoomID FROM Showtime WHERE ShowtimeID = ?";
        String query2 = "SELECT COUNT(*) AS numTickets FROM TICKET WHERE ShowtimeID = ?";
        String query3 = "SELECT COUNT(*) AS numSeats FROM SEAT WHERE TheatreRoomID = ?";

        int numEmail = -1;
        int theatreRoomID = -1;
        int numTickets = -1;
        int numSeats = -1;

        // Query 0 - Check if the email is associated with a RegisteredUser
        try (PreparedStatement psQuery0 = con.prepareStatement(query0)) {
                
            psQuery0.setString(1, email);

            // Find seat ID
            try (ResultSet rs0 = psQuery0.executeQuery()) {
                if (rs0.next())
                    numEmail = rs0.getInt("numEmail");
                else {
                    System.out.println("Email column not found");
                    return false; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        // If there is no RegUser associated with that email, return false
        if(numEmail < 1) {
            return false;
        }

        // Query 1 - Find the TheatreRoomID associated with a ShowtimeID
        try (PreparedStatement psQuery1 = con.prepareStatement(query1)) {
                
            psQuery1.setInt(1, showtimeID);

            // Find TheareRoomID
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next())
                    theatreRoomID = rs1.getInt("TheatreRoomID");
                else {
                    System.out.println("TheatreRoomID not found");
                    return false; // TheatreRoomID not found
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        // Query 2 - Find the number of tickets associated with ShowtimeID
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
            psQuery2.setInt(1, showtimeID);

            try (ResultSet rs2 = psQuery2.executeQuery()) {
                if (rs2.next())
                    numTickets = rs2.getInt("numTickets");
                else {
                    System.out.println("No tickets counted or some other error");
                    return false; 
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace(); }

        // Query 3 - Find the number of Seats associated with TheatreRoomID
        try (PreparedStatement psQuery3 = con.prepareStatement(query3)) {
            psQuery3.setInt(1, theatreRoomID);

            // Check how many seats have already been bought for this private showtime
            try (ResultSet rs3 = psQuery3.executeQuery()) {
                if (rs3.next())
                    numSeats = rs3.getInt("numSeats");
                else {
                    System.out.println("No seats counted or some other error");
                    return false; 
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        // If number of tickets is more than 10% of total seats, return false. Otherwise return true
        if(numTickets >= Math.floorDiv(numSeats, 10)) {
            return false;
        }
        
        return true;
    }

    /**
     * Create a send reciept as announcement to user with email
     * @param con
     * @param paymentInfo
     * @param seatID
     * @param showtimeID
     * @param email
     * @return
     */
    public String sendReceiptAnnouncement(Connection con, PaymentInfo paymentInfo, int seatID, int showtimeID, String email){

        
        String query1 = "SELECT ShowDateTime, TheatreRoomID, MovieID FROM SHOWTIME WHERE ShowtimeID = ?";
        String query0 = "SELECT Title FROM MOVIE WHERE MovieID = ?";
        String query2 = "SELECT SeatRow, SeatNumber FROM SEAT WHERE SeatID = ?";
        String query3 = "SELECT RoomName, TheatreID FROM THEATREROOM WHERE TheatreRoomID = ?";
        String query4 = "SELECT TheatreName, StreetAddress FROM THEATRE WHERE TheatreID = ?";

        String message = "";

        int movieID = -1;
        Timestamp showtimeTimestamp = null;
        int theatreRoomID = -1;

        String movieTitle = "";

        int seatRow = -1;
        int seatNum = -1;

        String roomName = "";
        int theatreID = -1;

        String theatreName = "";
        String theatreAddress = "";

        // Query 3 - Get theatreRoomID and ShowDateTime
        try (PreparedStatement psQuery1 = con.prepareStatement(query1)) {
                
            psQuery1.setInt(1, showtimeID);

            // Find seat ID
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next()) {
                    showtimeTimestamp = rs1.getTimestamp("ShowDateTime");
                    theatreRoomID = rs1.getInt("TheatreRoomID");
                    movieID = rs1.getInt("MovieID");
                }
                else {
                    System.out.println("Timestamp for showtime not found");
                    return ""; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        // Query 0 - Get movie title
        try (PreparedStatement psQuery0 = con.prepareStatement(query0)) {
                
            psQuery0.setInt(1, movieID);

            // Find seat ID
            try (ResultSet rs0 = psQuery0.executeQuery()) {
                if (rs0.next())
                movieTitle = rs0.getString("Title");

                else {
                    System.out.println("movie title not found");
                    return ""; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }


        // Query 2 - Get seat information
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
                
            psQuery2.setInt(1, seatID);

            // Find seat ID
            try (ResultSet rs2 = psQuery2.executeQuery()) {
                if (rs2.next()) {
                    seatRow = rs2.getInt("SeatRow");
                    seatNum = rs2.getInt("SeatNumber");
                }
                else {
                    System.out.println("seat row and num for seatID not found");
                    return ""; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        // Query 3 - Roomname and theatre ID
        try (PreparedStatement psQuery3 = con.prepareStatement(query3)) {
                
            psQuery3.setInt(1, theatreRoomID);

            // Find seat ID
            try (ResultSet rs3 = psQuery3.executeQuery()) {
                if (rs3.next()) {
                    roomName = rs3.getString("RoomName");
                    theatreID = rs3.getInt("TheatreID");
                }
                else {
                    System.out.println("could not get RoomName or TheatreID");
                    return ""; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }


        // Query 4 - TheatreName and address
        try (PreparedStatement psQuery4 = con.prepareStatement(query4)) {
                
            psQuery4.setInt(1, theatreID);

            // Find seat ID
            try (ResultSet rs4 = psQuery4.executeQuery()) {
                if (rs4.next()) {
                    theatreName = rs4.getString("TheatreName");
                    theatreAddress = rs4.getString("StreetAddress");
                }
                else {
                    System.out.println("could not get RoomName or TheatreID");
                    return ""; // Couldn't get num of emails
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }

        message = "Purchased ticket for " + movieTitle 
                + " with start time " + showtimeTimestamp
                + " at theatre " + theatreName
                + " with address " + theatreAddress  
                + " at seat row " + seatRow + " number " + seatNum
                + " in room " + roomName
                + " for $" + TICKET_PRICE
                + ". Email has been sent to " + email;

        return message;
    }



//-----------------------------------------------------------------//
    
//-----------------------------------------------------------------//
//                            SEAT METHODS                         //
//-----------------------------------------------------------------//

    /**
     * Finds out what seats for a showtime have been booked
     * @param con
     * @param showtimeID
     * @return HashMap<Integer, Boolean> where Integer is SeatID, and Boolean is if it is avaiable (true) or not (false)
     */
    public HashMap<Integer, Boolean> retrieveAvailableSeats(Connection con, int showtimeID) {

        String query1 = "SELECT TheatreRoomID FROM SHOWTIME WHERE ShowtimeID = ?";
        String query2 = "SELECT SeatID FROM SEAT WHERE TheatreRoomID = ?";
        String query3 = "SELECT SeatID FROM TICKET WHERE ShowtimeID = ?";

        int theatreRoomID = -1;

        HashMap<Integer, Boolean> seatMap = new HashMap<>(); // seatID : True/False
                                                             //          seat is available(true) or not(false)
        
        // Query 1 - Find the TheatreRoomID associated with a ShowtimeID
        try (PreparedStatement psQuery1 = DatabaseController.createConnection().prepareStatement(query1)) {
                
            psQuery1.setInt(1, showtimeID);

            // Find TheareRoomID
            try (ResultSet rs1 = psQuery1.executeQuery()) {
                if (rs1.next())
                    theatreRoomID = rs1.getInt("TheatreRoomID");
                else {
                    System.out.println("TheatreRoomID not found");
                    return null; // TheatreRoomID not found
                }

            } catch (Exception e) { e.printStackTrace(); }
        } catch (Exception e) { e.printStackTrace();  }
        
        
        // Query 2 - Get SeatIDs associated with TheatreRoomID and put them into hashmap
        try (PreparedStatement psQuery2 = con.prepareStatement(query2)) {
            psQuery2.setInt(1, theatreRoomID);

            try (ResultSet rs2 = psQuery2.executeQuery()) {
                // Get all seats and put them in the hashmap
                while (rs2.next()) {
                    int seatID = rs2.getInt("SeatID");
                    seatMap.put(seatID, true); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Query 3 - Update the booked seats to be unavailable
        try (PreparedStatement psQuery3 = con.prepareStatement(query3)) {
            psQuery3.setInt(1, showtimeID);

            try (ResultSet rs3 = psQuery3.executeQuery()) {
                // Mark seats that are unavailable (false)
                while (rs3.next()) {
                    int bookedSeatID = rs3.getInt("SeatID");
                    seatMap.put(bookedSeatID, false); // Seat is unavailable (false)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return seatMap;
    }


}

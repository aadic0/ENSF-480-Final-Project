package objects.control;

import objects.entity.Seat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class SeatController {
    public SeatController(){} 

    /**
     * Check to see if a ticket exists for a seat and showtime
     * 
     * BUGS: Does not check if a showtime has already passed, will say it is still avialable
     * I have the query prepared but I REALLY REALLY dont feel like implementing it rn
     * - Damon Nov 23
     * @param seat
     * @param theatreRoomID
     * @param showDateTime
     * @param movieID
     * @return
     */
    public boolean isSeatAvailable(Seat seat, int theatreRoomID, Date showDateTime, int movieID) {

        String query0 = "SELECT Runtime FROM MOVIE WHERE MovieID = ?"; // Put the implementation for this between query 1 and 2

        String query1 = "SELECT ShowtimeID FROM SHOWTIME WHERE TheatreRoomID = ? AND ShowDateTime = ? AND MovieID = ?";
        String query2 = "SELECT SeatID FROM SEAT WHERE SeatRow = ? AND SeatNumber = ? AND TheatreRoomID = ?";
        String query3 = "SELECT TicketID FROM TICKET WHERE SeatID = ? AND ShowtimeID = ?";

        int showtimeID = -1;
        int seatID = -1;


        try (Connection connection = DatabaseController.createConnection()) {


            // Query 1: check if the showtimes exists
            try (PreparedStatement psQuery1 = connection.prepareStatement(query1)) {
                
                // Set query parameters
                psQuery1.setInt(1, theatreRoomID);
                psQuery1.setTimestamp(2, new Timestamp(showDateTime.getTime())); 
                psQuery1.setInt(3, movieID);

                // Find showtime ID
                try (ResultSet rs1 = psQuery1.executeQuery()) {
                    if (rs1.next()) {
                        showtimeID = rs1.getInt("ShowtimeID");
                    } 
                    // Return false if no showtime is found
                    else {
                        System.out.println("No showtime found");
                        return false; // No showtime found
                    }

                } catch (Exception e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }

            // Query 2: check if SeatID exists using seat information 
            try (PreparedStatement psQuery2 = connection.prepareStatement(query2)) {
                
                // Set query parameters
                psQuery2.setInt(1, seat.getSeatRow());
                psQuery2.setInt(2, seat.getSeatNumber()); 
                psQuery2.setInt(3, theatreRoomID);

                // Find seat ID
                try (ResultSet rs2 = psQuery2.executeQuery()) {
                    if (rs2.next()) {
                        seatID = rs2.getInt("SeatID");
                    } 
                    // Return false if no seat is found
                    else {
                        System.out.println("No matching seat found.");
                        return false; // No matching seat found
                    }

                } catch (Exception e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }

            // Query 3: Find out if TicketID exists
            try (PreparedStatement psQuery3 = connection.prepareStatement(query3)) {
                psQuery3.setInt(1, seatID);
                psQuery3.setInt(2, showtimeID);

                // If ticketID exists then return true
                try (ResultSet rs3 = psQuery3.executeQuery()) {
                    if (rs3.next()) {
                        return false; // Seat is booked
                    }

                } catch (Exception e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }

        } catch (Exception e) { e.printStackTrace(); }

        return true;
    }
} 

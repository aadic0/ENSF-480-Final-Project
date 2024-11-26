package objects.control;

import objects.entity.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class SeatController {
    private int showtimeID;
    private int seatID;
    
    public SeatController(){
        this.showtimeID = -1;
        this.seatID = -1;
    } 

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
    public boolean isSeatAvailable(Seat seat, int theatreRoomID, Timestamp showDateTime, int movieID) {

        // Reset values
        this.showtimeID = -1;
        this.seatID = -1;
        Time runtimeTime;

        // Queries
        String query0 = "SELECT Runtime FROM MOVIE WHERE MovieID = ?"; // Put the implementation for this between query 1 and 2
        String query1 = "SELECT ShowtimeID FROM SHOWTIME WHERE TheatreRoomID = ? AND ShowDateTime = ? AND MovieID = ?";
        String query2 = "SELECT SeatID FROM SEAT WHERE SeatRow = ? AND SeatNumber = ? AND TheatreRoomID = ?";
        String query3 = "SELECT TicketID FROM TICKET WHERE SeatID = ? AND ShowtimeID = ?";

        


        try (Connection connection = DatabaseController.createConnection()) {


            // Query 0: Get the runtime from the movie (will use to make sure showtime has not passed)
            try (PreparedStatement psQuery0 = connection.prepareStatement(query0)) {
                
                // Set query parameters
                psQuery0.setInt(1, movieID);
                
                try (ResultSet rs1 = psQuery0.executeQuery()) {
                    if (rs1.next()) {
                        // get runtime
                        runtimeTime = rs1.getTime("Runtime");

                        // Get the runtime and add it to the showtime, then check if the current time is after the showtime+runtime
                        LocalTime runtime = runtimeTime.toLocalTime();
                        LocalDateTime showTime = showDateTime.toLocalDateTime();
                        LocalDateTime nowTime = LocalDateTime.now();

                        int runtimeHours = runtime.getHour();
                        int runtimeMinutes = runtime.getMinute();

                        LocalDateTime showtimePlusRuntime = showTime.plusHours(runtimeHours).plusMinutes(runtimeMinutes);

                        if(nowTime.isAfter(showtimePlusRuntime)) {
                            System.out.println("Cannot purchase, the showtime is finished");
                            return false; // showtime is not avaiable
                        }

                    } 
                    // Return false if no movie is found
                    else {
                        System.out.println("No movie found");
                        return false; // No movie found
                    }

                } catch (Exception e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }

            
            // Query 1: check if the showtimes exists
            try (PreparedStatement psQuery1 = connection.prepareStatement(query1)) {
                
                // Set query parameters
                psQuery1.setInt(1, theatreRoomID);
                psQuery1.setTimestamp(2, showDateTime); 
                psQuery1.setInt(3, movieID);

                // Find showtime ID
                try (ResultSet rs1 = psQuery1.executeQuery()) {
                    if (rs1.next()) {
                        this.showtimeID = rs1.getInt("ShowtimeID");
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
                psQuery3.setInt(2, this.showtimeID);

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

    /**
     * get showtimeID
     * @return showtimeID
     */
    public int getShowtimeID() {
        return showtimeID;
    }

    /**
     * get seatID
     * @return seatID
     */
    public int getSeatID() {
        return seatID;
    }

} 

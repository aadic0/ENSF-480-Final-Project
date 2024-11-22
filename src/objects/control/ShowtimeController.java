package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ShowtimeController {
    public ShowtimeController(){}

    /**
     * Show all showtime IDs from SQL database
     */
    public void getAllShowtimes() {
        
        // Try to connect to database
        try (Connection connection = DatabaseController.createConnection()) {
            
            // Prepare query
            String query = "SELECT s.ShowDateTime, m.Title AS MovieTitle, t.TheatreName, tr.RoomName " +
                           "FROM SHOWTIME s " +
                           "JOIN MOVIE m ON s.MovieTitle = m.Title " +
                           "JOIN THEATREROOM tr ON s.TheatreRoomID = tr.TheatreRoomID " +
                           "JOIN THEATRE t ON tr.TheatreID = t.TheatreID;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                String movieTitle = resultSet.getString("MovieTitle");
                Timestamp showDateTime = resultSet.getTimestamp("ShowDateTime");
                String theatreName = resultSet.getString("TheatreName");
                String roomName = resultSet.getString("RoomName");
                
                System.out.println("Movie: " + movieTitle);
                System.out.println("Time: " + showDateTime);
                System.out.println("Theatre: " + theatreName);
                System.out.println("Room: " + roomName);
                System.out.println("-----------");
            }
        
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}

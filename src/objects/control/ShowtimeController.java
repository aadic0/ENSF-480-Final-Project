package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowtimeController {
    public ShowtimeController(){}

    /**
     * Show all showtime IDs from SQL database
     */
    public void showShowtimes() {
        
        // Try to connect to database
        try (Connection connection = DatabaseController.createConnection()) {
            
            // Prepare query
            String query = "SELECT * FROM showtime;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int showtimeID = resultSet.getInt("ShowtimeID"); 
                System.out.println("ShowtimeID: " + showtimeID);
            }

            
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}

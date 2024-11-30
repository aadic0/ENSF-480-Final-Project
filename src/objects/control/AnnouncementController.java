package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


public class AnnouncementController{
    
    AnnouncementController() {}

    /**
     * 
     * @param message
     * @param movieID
     */
    public void sendPrivateShowTimeAnnouncement(String message, int movieID) {

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, movieID) VALUES (?, ?, ?, ?)";

        final boolean IS_PUBLIC = false;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
            preparedStatement.setInt(4, (movieID));

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Announcement added successfully!");
            } else {
                System.out.println("Failed to add the announcement.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param message
     * @param movieID
     */
    public void sendPublicShowTimeAnnouncement(String message, int movieID) {

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, movieID) VALUES (?, ?, ?, ?)";

        final boolean IS_PUBLIC = true;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
            preparedStatement.setInt(4, (movieID));

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Announcement added successfully!");
            } else {
                System.out.println("Failed to add the announcement.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 
     * @param message
     */
    public void sendPublicAnnouncement(String message){

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced) "
                                       + "VALUES (?, ?, ?)";
        final boolean IS_PUBLIC = true;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                        // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Announcement added successfully!");
            } else {
                System.out.println("Failed to add the announcement.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param message
     */
    public void sendPrivateAnnouncement(String message){

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced) "
                                       + "VALUES (?, ?, ?)";
        final boolean IS_PUBLIC = false;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                        // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Announcement added successfully!");
            } else {
                System.out.println("Failed to add the announcement.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}

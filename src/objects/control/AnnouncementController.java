package objects.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import objects.entity.Announcement;
import objects.entity.RegisteredUser;
import objects.entity.User;

public class AnnouncementController{
    
    AnnouncementController() {}

    /**
     * email an announcement to a list of registered users about early access showtimes
     * @param announcement
     * @param registeredUsersList
     */
    public void sendPrivateShowTimeAnnouncement(String message, int showTimeID) {

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, ShowtimeID) VALUES (?, ?, ?, ?)";

        final boolean IS_PUBLIC = false;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Convert String to SQL Date
            preparedStatement.setInt(4, (showTimeID));

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
     * Sends an announcement to all users
     * @param message
     * 
     * 
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

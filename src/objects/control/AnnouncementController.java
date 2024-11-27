package objects.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void sendPrivateShowTimeAnnouncement(int announcementID, String message, String dateAnnounced, int showTimeID) {

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (AnnouncementID, IsPublic, AnnouncementMessage, DateAnnounced, ShowtimeID) VALUES (?, ?, ?, ?, ?)";
        int isPublic = 0;
        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            preparedStatement.setInt(1, announcementID);
            preparedStatement.setInt(2, isPublic);
            preparedStatement.setString(3, message);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dateAnnounced)); // Convert String to SQL Date
            preparedStatement.setInt(5, (showTimeID));

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
    public void sendPublicAnnouncement(int announcementID, String message){

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (AnnouncementID, IsPublic, AnnouncementMessage) "
                                       + "VALUES (?, ?, ?)";
        int isPublic = 1;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            preparedStatement.setInt(1, announcementID);
            preparedStatement.setInt(2, isPublic);
            preparedStatement.setString(3, message);

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
    
    public void sendPrivateAnnouncement(int announcementID, String message){

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (AnnouncementID, IsPublic, AnnouncementMessage) "
                                       + "VALUES (?, ?, ?)";
        int isPublic = 0;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            preparedStatement.setInt(1, announcementID);
            preparedStatement.setInt(2, isPublic);
            preparedStatement.setString(3, message);

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

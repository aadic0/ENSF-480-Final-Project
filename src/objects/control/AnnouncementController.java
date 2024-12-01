package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


public class AnnouncementController{
    
    public AnnouncementController() {}




    //*************************************//
    //              SENDERS                //
    //*************************************//


    /**
     * Create a private showtime announcement on the DB
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
                System.out.println("Private Showtime Announcement added successfully!");
            } else {
                System.out.println("Failed to add the Private Showtime Announcement.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a public showtime announcement on the DB
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
                System.out.println("Public Showtime Announcement added successfully!");
            } else {
                System.out.println("Failed to add the Public Showtime Announcement.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Create a public announcement on the DB
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
                System.out.println("Public Announcement added successfully!");
            } else {
                System.out.println("Failed to add the Public Announcement.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Create a private announcement on the DB
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
                System.out.println("Private Announcement added successfully!");
            } else {
                System.out.println("Failed to add the Private Announcement.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * To be used as the receipt
     * @param message
     * @param email
     */
    public void sendEmailAnnouncement(String message, String email) {

        String insertAnnouncementQuery = "INSERT INTO ANNOUNCEMENT (IsPublic, AnnouncementMessage, DateAnnounced, Email) VALUES (?, ?, ?, ?)";

        final boolean IS_PUBLIC = false;

        try (Connection connection = DatabaseController.createConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertAnnouncementQuery)){     
            // Set values for the announcement table
            // preparedStatement.setInt(1, announcementID);
            preparedStatement.setBoolean(1, IS_PUBLIC);
            preparedStatement.setString(2, message);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
            preparedStatement.setString(4, (email));

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Email Announcement added successfully!");
            } else {
                System.out.println("Failed to add the Email announcement.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*************************************//
    //              RETREIVERS             //
    //*************************************//

/**
     * Get all movies from SQL database
     * @param con connection to database
     */
    public HashMap<Integer, ArrayList<Object>> retrieveAllAnnouncement(String email) {

        RegisteredUserController registeredUserController = new RegisteredUserController();

        HashMap<Integer, ArrayList<Object>> announcementMap = new HashMap<>();

        try (Connection connection = DatabaseController.createConnection()) {

            if(registeredUserController.isRegisteredUser(email)) 
                announcementMap.putAll(this.retrievePrivateAnnouncement(connection));
            
            announcementMap.putAll(retrievePublicAnnouncement(connection));
            announcementMap.putAll(retrieveEmailAnnouncement(connection, email));

        } catch (SQLException e){ e.printStackTrace(); }

        return announcementMap;
    }


    /**
     * 
     * @param con
     * @return Hashmap of announcements
     */
    public HashMap<Integer, ArrayList<Object>> retrievePrivateAnnouncement(Connection con) {
        String query = "SELECT * FROM ANNOUNCEMENT WHERE IsPublic = FALSE";

        HashMap<Integer, ArrayList<Object>> announcementMap = new HashMap<>();

        try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ArrayList<Object> valueArrayList = new ArrayList<>();

                int announcementID = rs.getInt("AnnouncementID");
                String announcementMessage = rs.getString("AnnouncementMessage");
                Timestamp dateAnnounced = rs.getTimestamp("DateAnnounced");  

                valueArrayList.add(announcementMessage);   // [0]
                valueArrayList.add(dateAnnounced);         // [1]

                //FORMAT:              int           ArrayList
                announcementMap.put(announcementID, valueArrayList);
            }

        } catch (SQLException e) { System.out.println(e); }

        return announcementMap;
    }

    /**
     * 
     * @param con
     * @return Hashmap of announcements
     */
    public HashMap<Integer, ArrayList<Object>> retrievePublicAnnouncement(Connection con) {
        String query = "SELECT * FROM ANNOUNCEMENT WHERE IsPublic = TRUE";

        HashMap<Integer, ArrayList<Object>> announcementMap = new HashMap<>();

        try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ArrayList<Object> valueArrayList = new ArrayList<>();

                int announcementID = rs.getInt("AnnouncementID");
                String announcementMessage = rs.getString("AnnouncementMessage");
                Timestamp dateAnnounced = rs.getTimestamp("DateAnnounced");  

                valueArrayList.add(announcementMessage);   // [0]
                valueArrayList.add(dateAnnounced);         // [1]

                //FORMAT:              int           ArrayList
                announcementMap.put(announcementID, valueArrayList);
            }

        } catch (SQLException e) { System.out.println(e); }

        return announcementMap;
    }

    /**
     * 
     * @param con
     * @param email
     * @return
     */
    public HashMap<Integer, ArrayList<Object>> retrieveEmailAnnouncement(Connection con, String email) {
        String query = "SELECT * FROM ANNOUNCEMENT WHERE Email = ?";

        HashMap<Integer, ArrayList<Object>> announcementMap = new HashMap<>();

        try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ArrayList<Object> valueArrayList = new ArrayList<>();

                int announcementID = rs.getInt("AnnouncementID");
                String announcementMessage = rs.getString("AnnouncementMessage");
                Timestamp dateAnnounced = rs.getTimestamp("DateAnnounced");  

                valueArrayList.add(announcementMessage);   // [0]
                valueArrayList.add(dateAnnounced);         // [1]

                //FORMAT:              int           ArrayList
                announcementMap.put(announcementID, valueArrayList);
            }

        } catch (SQLException e) { System.out.println(e); }

        return announcementMap;
    }

}
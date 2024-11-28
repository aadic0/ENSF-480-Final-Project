package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The controller for Users
 * Create and/or register users on the database 
 * @author Damon Mazurek
 * @version v1.0
 */
public class UserController {
    
    public UserController(){}
 
/* 
 * Don't need this anymore because I removed DEFAULT_USER from DB 
 * Damon Nov 21
 */
    // /**
    //  * Register a regular user into a registered user in DB
    //  * @param user user to sign up
    //  * @param pwd 
    //  * @param firstName
    //  * @param lastName
    //  * @param streetAddress
    //  * @param city
    //  * @param province
    //  * @param postalCode
    //  */
    // public void createRegisteredUser(
    //     User user, String pwd, 
    //     String firstName, String lastName, 
    //     String streetAddress, String city, String province, String postalCode ) {
        
    //         /* TO DO */
    //         // Add an Observer for Announcement to add them to list of RegisteredUsers objects to send announcements to
    //         // Optional: Add a check for a DEFAULT_USER using the name email before creating a RegisteredUser 
    //             // Idk if we'll need to do this because we'll be able to control when users sign up in the presentation layer


    //         // Try to connect to database
    //         try (Connection connection = DatabaseController.createConnection()) { 

    //             // Prepare query
    //             String query = "INSERT INTO REGISTERED_USER(Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode) VALUES(?,?,?,?,?,?,?,?)";
    //             PreparedStatement preparedStatement = connection.prepareStatement(query);

    //             preparedStatement.setString(1, user.getEmail()); // Changes '?' to the text
    //             preparedStatement.setString(2, pwd);

    //             preparedStatement.setString(3, firstName);
    //             preparedStatement.setString(4, lastName);

    //             preparedStatement.setString(5, streetAddress);
    //             preparedStatement.setString(6, city);
    //             preparedStatement.setString(7, province);
    //             preparedStatement.setString(8, postalCode);

    //             // Execute Query
    //             preparedStatement.executeUpdate();

                

    //         }
    //         catch (SQLException e) {
    //             System.out.println(e);
    //         }
    // }

/* 
 * Don't need this anymore because I removed DEFAULT_USER from DB 
 * Damon Nov 21
 */

    // /**
    //  * Create a user object and add to database (MIGHT CHANGE, IDK IF WE EVEN WANT TO ADD NON-REGISTERED USERS TO DB)
    //  * @param email
    //  * @param pwd
    //  * @return user object (may not keep)
    //  */
    // public void createUser(String email, String pwd) {
    //     // Try to connect to database
    //     try (Connection connection = DatabaseController.createConnection();) { 
            
    //         // Prepare query
    //         String query = "INSERT INTO DEFAULT_USER(Email, Pwd) VALUES(?,?)";
    //         PreparedStatement preparedStatement = connection.prepareStatement(query);

    //         preparedStatement.setString(1, email); // Changes '?' to the text
    //         preparedStatement.setString(2, pwd);
            
    //         // Execute Query
    //         preparedStatement.executeUpdate();
            
    //     }
    //     catch (SQLException e) {
    //         System.out.println(e);
    //     }
    // }

    /**
     * Create a user object and add to database (MIGHT CHANGE, IDK IF WE EVEN WANT TO ADD NON-REGISTERED USERS TO DB)
     * @param email
     * @param pwd
     * @return user object (may not keep)
     */
    public void createUser(String email, String pwd) {
        // Try to connect to database
        try (Connection connection = DatabaseController.createConnection();) { 
            
            // Prepare query
            String query = "INSERT INTO REGULAR_USER(Email, Pwd) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email); // Changes '?' to the text
            preparedStatement.setString(2, pwd);
            
            // Execute Query
            preparedStatement.executeUpdate();
            
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}

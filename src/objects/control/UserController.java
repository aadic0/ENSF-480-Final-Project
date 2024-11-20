package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import objects.entity.RegisteredUser;
import objects.entity.User;

public class UserController {
    
    public UserController(){}
 
    /**
     * turn a regular user into a registered user
     * @param user
     * @return RegisteredUser object (may not keep)
     */
    public RegisteredUser createRegisteredUser(User user) {
        RegisteredUser regUser = new RegisteredUser(
            null, null, 
            null, null, null, null, 
            user.getEmail(), null
        );

        // Add to database
        // ...
        
        return regUser;
    }

    /**
     * Create a user object and add to database (MIGHT CHANGE, IDK IF WE EVEN WANT TO ADD NON-REGISTERED USERS TO DB)
     * @param email
     * @param pwd
     * @return user object (may not keep)
     */
    public User createUser(String email, String pwd) {
        // Try to connect to database
        try (Connection connection = DatabaseController.getConnection();) { 
            
            // Prepare query
            String query = "INSERT INTO DEFAULT_USER(Email, Pwd) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email); // Changes '?' to the text
            preparedStatement.setString(2, pwd);
            
            // Execute Query
            // preparedStatement.executeQuery();
            preparedStatement.executeUpdate();
            
        }
        catch (SQLException e) { return null; }

        User user = new User(email);

        return user;
    }
}

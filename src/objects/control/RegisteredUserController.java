package objects.control;

// import objects.entity.PaymentInfo;
// import objects.entity.RegisteredUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;


public class RegisteredUserController {


    // private static final String DB_URL = "jdbc:mysql://localhost:3307/THEATRE_DB"; // i already had some shit running on port 3306, so i used 3307, change urs to 3306 or whatever port u are using
    // private static final String DB_USER = "admin";
    // private static final String DB_PASSWORD = "admin"; // replace with ur guys' user and password, theres probably a better way to implement htis

    private String emailID;

    public RegisteredUserController() {}


    /**
     * Verifies if the given email and password match a record in the REGISTERED_USER table.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return true if a match is found, false otherwise.
     */
    public boolean authenticateUser(String email, String password) {
        String query = "SELECT COUNT(*) FROM REGISTERED_USER WHERE Email = ? AND Pwd = ?";
        

        // For now i have it setup so that it connects to the DB on function call, might change later if we decide to just have one constant 
        try (Connection connection = DatabaseController.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set the query parameters
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Return true if count > 0
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
        return false;
    }

    /**
     * Register the user
     * @param registeredUser RegisteredUser object we want to add to DB
     * @param pwd password
     */
    public void registerUser(RegisteredUser registeredUser, String pwd) {
        String query1 = "INSERT INTO USER_PAYMENT_INFO (NumberCC, ExpirationDate, CVV, Email) " +
                        "VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode, PaymentID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int paymentId = -1; // Payment ID we get after adding info to USER_PAYMENT_INFO

        // Gotta setup default user first due to foreign key constraints
        try (Connection connection = DatabaseController.createConnection()) {

            connection.setAutoCommit(false); // Set auto commit to false so inserts succeed or fail together
            
            // Insert into USER_PAYMENT_INFO
            try (PreparedStatement psPaymentInfo  = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS)) {
                PaymentInfo paymentInfo = registeredUser.getPaymentInfo();

                // SEt paramaters for the statement
                psPaymentInfo.setLong(1, (long) paymentInfo.getCardNumber());
                psPaymentInfo.setString(2, paymentInfo.getExpiration());
                psPaymentInfo.setInt(3, paymentInfo.getCV());
                psPaymentInfo.setString(4, registeredUser.getEmail());
                int rowsAffectedPI = psPaymentInfo.executeUpdate();

                // Check if rows have been added
                if (rowsAffectedPI > 0) { System.out.println("Successfully added PaymentInfo"); } 
                else { System.out.println("Failed to add PaymentInfo"); }

                // Get PaymentID key that was generated
                try (ResultSet generatedKeys = psPaymentInfo.getGeneratedKeys()) {
                    if (generatedKeys.next()) { paymentId = generatedKeys.getInt(1); } 
                    else { throw new SQLException("Failed to retrieve PaymentID."); }

                } catch (Exception e) { e.printStackTrace(); }

            } catch (Exception e) { e.printStackTrace(); }

            // Insert into REGISTERED_USER
            try (PreparedStatement psRegisteredUser = connection.prepareStatement(query2)) {
                
                // SEt paramaters for the statement
                psRegisteredUser.setString(1, registeredUser.getEmail());
                psRegisteredUser.setString(2, pwd);
                psRegisteredUser.setString(3, registeredUser.getFName());
                psRegisteredUser.setString(4, registeredUser.getLName());
                psRegisteredUser.setString(5, registeredUser.getStreetAddress());
                psRegisteredUser.setString(6, registeredUser.getCity());
                psRegisteredUser.setString(7, registeredUser.getProvince());
                psRegisteredUser.setString(8, registeredUser.getPostalCode());
                psRegisteredUser.setInt(9, paymentId);
                int rowsAffectedRU = psRegisteredUser.executeUpdate();

                // Just for testing, feel free to remove whenever
                if (rowsAffectedRU > 0) {
                    System.out.println("User successfully registered");
                    emailID = registeredUser.getEmail();
                } 
                else { System.out.println("Failed to register user"); }
                // End testing, delete up to here

            } catch (Exception e) { e.printStackTrace(); }

            // Make sure paymentID was actually set before committing 
            if (paymentId < 0) { throw new Exception("PaymentID was not set"); }
            connection.commit(); // Commit the changes to the DB

        } catch (Exception e) { e.printStackTrace(); }
    }

    
    


    /**
     * Update payment information in SQL database
     * @param paymentInfo 
     */
    // public void updatePaymentInfo(PaymentInfo paymentInfo) {

    // }

    /**
     * Update address information in SQL database
     * @param address 
     */
    public void updateAddressInfo(String address) {
        String query = "UPDATE REGISTERED_USER SET StreetAddress = ? WHERE Email = ?";
        try (Connection connection = DatabaseController.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, emailID);

            int rowsAffected = preparedStatement.executeUpdate();

            // Only for testing
            if (rowsAffected > 0) {
                System.out.println("Address updated successfully.");
            } else {
                System.out.println("No rows updated. Check the email address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the name in SQL database
     * @param firstName
     * @param lastName
     */
    public void updateName(String firstName, String lastName) {
        String query = "UPDATE REGISTERED_USER SET FirstName = ? WHERE email = ?";
        try (Connection connection = DatabaseController.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, emailID);
            int rowsAffected = preparedStatement.executeUpdate();

            // Only for testing
            if (rowsAffected > 0) {
                System.out.println("Name updated successfully.");
            } else {
                System.out.println("No rows updated. Check the email address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Update email information in SQL database
     * @param email 
     */
    public void updateEmailInfo(String email) {
        // String queryDefaultUser = "UPDATE DEFAULT_USER SET Email = ? WHERE Email = ?";
        String queryRegisteredUser = "UPDATE REGISTERED_USER SET Email = ? WHERE Email = ?";

        try (Connection connection = DatabaseController.createConnection()) {

            // Update email in REGISTERED_USER
            try (PreparedStatement psRegisteredUser = connection.prepareStatement(queryRegisteredUser)) {
                psRegisteredUser.setString(1, email);
                psRegisteredUser.setString(2, emailID);
                int rowsAffected = psRegisteredUser.executeUpdate();

                // For testing
                if (rowsAffected > 0) {
                    System.out.println("Email updated successfully.");
                    emailID = email; // Update local reference to the new email
                } else {
                    System.out.println("No rows updated. Check the email address.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel the user's registration (stop payment)
     * This will stop any recurring payments and deactivate the user account.
     * @param regUser The registered user whose account will be cancelled
     */
    // public void cancelRegistration(RegisteredUser regUser) {

    // }




}

package objects.control;

// import objects.entity.PaymentInfo;
// import objects.entity.RegisteredUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class RegisteredUserController {


    // private static final String DB_URL = "jdbc:mysql://localhost:3307/THEATRE_DB"; // i already had some shit running on port 3306, so i used 3307, change urs to 3306 or whatever port u are using
    // private static final String DB_USER = "admin";
    // private static final String DB_PASSWORD = "admin"; // replace with ur guys' user and password, theres probably a better way to implement htis

    private String emailID;

    public RegisteredUserController() {}


    /****************** 
     * 
     * NOTE FOR LAB MEMBERS:
     * 
     * SINCE REGISTERED USER USES DEFAULT USER AS A FOREIGN KEY, FOR ANY INSERTIONS YOU GOTTA UPDATE DEFAULT USER AS WELL
     * 
     * - AADI
     * 
     * ********************/


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
     * @param email
     * @param pwd
     * @param fname
     * @param lname
     * @param addr
     * @param city
     * @param province
     * @param postalCode
     */
    public void registerUser(String email, String pwd, String fname, String lname, String addr, String city, String province, String postalCode) {
        String query = "INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Gotta setup default user first due to foreign key constraints
        try (Connection connection = DatabaseController.createConnection()) {
            
            // Insert into REGISTERED_USER
            try (PreparedStatement psRegisteredUser = connection.prepareStatement(query)) {
                psRegisteredUser.setString(1, email);
                psRegisteredUser.setString(2, pwd);
                psRegisteredUser.setString(3, fname);
                psRegisteredUser.setString(4, lname);
                psRegisteredUser.setString(5, addr);
                psRegisteredUser.setString(6, city);
                psRegisteredUser.setString(7, province);
                psRegisteredUser.setString(8, postalCode);
                int rowsAffected = psRegisteredUser.executeUpdate();


                // Just for testing, feel free to remove whenever
                if (rowsAffected > 0) {
                    System.out.println("User successfully registered!");
                    emailID = email;
                } else {
                    System.out.println("Failed to register user.");
                }

            } catch (Exception e) { e.printStackTrace(); }
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

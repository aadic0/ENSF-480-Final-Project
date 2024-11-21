package objects.control;

// import objects.entity.PaymentInfo;
// import objects.entity.RegisteredUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class RegisteredUserController {


    private static final String DB_URL = "jdbc:mysql://localhost:3307/THEATRE_DB"; // i already had some shit running on port 3306, so i used 3307, change urs to 3306 or whatever port u are using
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin"; // replace with ur guys' user and password, theres probably a better way to implement htis

    // constructor should 



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
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    
    public void registerUser(String email, String pwd, String fname, String lname, String addr, String city, String province, String postalCode) {
        String queryInsertDefaultUser = "INSERT INTO DEFAULT_USER (Email, Pwd) VALUES (?, ?)";
        String queryInsertRegisteredUser = "INSERT INTO REGISTERED_USER (Email, Pwd, FirstName, LastName, StreetAddress, City, Province, PostalCode) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Gotta setup default user first due to foreign key constraints
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert into DEFAULT_USER
            try (PreparedStatement psDefaultUser = connection.prepareStatement(queryInsertDefaultUser)) {
                psDefaultUser.setString(1, email);
                psDefaultUser.setString(2, pwd);
                psDefaultUser.executeUpdate();
            }

            // Insert into REGISTERED_USER
            try (PreparedStatement psRegisteredUser = connection.prepareStatement(queryInsertRegisteredUser)) {
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
                } else {
                    System.out.println("Failed to register user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public RegisteredUserController() {}

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

    }

    /**
     * Update the name in SQL database
     * @param firstName
     * @param lastName
     */
    public void updateName(String firstName, String lastName) {

    }

    /**
     * Update email information in SQL database
     * @param email 
     */
    public void updateEmailInfo(String email) {

    }

    /**
     * Cancel the user's registration (stop payment)
     * This will stop any recurring payments and deactivate the user account.
     * @param regUser The registered user whose account will be cancelled
     */
    // public void cancelRegistration(RegisteredUser regUser) {

    // }




}

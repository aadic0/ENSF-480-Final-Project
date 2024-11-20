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

    public static void main(String[] args) {
        RegisteredUserController controller = new RegisteredUserController();
        
        // Test the method
        String testEmail = "test@test.com";
        String testPassword = "jdoe";
        boolean isAuthenticated = controller.authenticateUser(testEmail, testPassword);
        
        System.out.println("Authentication result: " + isAuthenticated);
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

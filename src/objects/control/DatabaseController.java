package objects.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class representing SQL database
 * Used to create and close connections to MySQL database
 * @author Damon Mazurek    
 * @version v1.0
 */


public class DatabaseController {
    private static String USERNAME = "theatre_connect";
    private static String PASSWORD = "theatre";
    private static String URL = "jdbc:mysql://localhost/THEATRE_DB";
    private static Connection connection = null;

    /**
     * Create a connection to SQL database
     * @return connection to database
     */
    public static Connection createConnection() {
        // Success
        try { 
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
        } 
        // Failure
        catch (SQLException e) { connection = null; } 

        return connection;
    }

    /**
     * Close connection to SQL database
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        // Try closing connection
        try {
            if (connection != null && !connection.isClosed()) { 
                connection.close(); 
            }
        } catch(SQLException e) {} // Ignore SQLException for now -Damon Nov 19 

        finally {
            connection = null;
        }
        
    }

    /**
     * return Connection status
     * @return
     */
    public static Connection getConnection() {
        return connection;
    }

}
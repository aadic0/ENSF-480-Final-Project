package objects.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class representing SQL database
 * Used to create and close connections to MySQL database
 */
public class DatabaseController {
    private static String username = "theatre_connect";
    private static String password = "theatre";
    private static String url = "jdbc:mysql://localhost/THEATRE_DB";
    private static Connection connection = null;

    /**
     * Connect to Theatre SQL database
     * @return Connection to database
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    /**
     * Disconnect from Theatre SQL database
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && connection.isClosed() == false) {
            connection.close();
        }
    }

}

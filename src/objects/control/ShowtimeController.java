package objects.control;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowtimeController {
    public ShowtimeController(){}


//-------------------------------------------------------------//
//                      Showtime Methods                       //
//-------------------------------------------------------------//



    /**
     * 
     * @param con
     * @return A hasmap with ShowtimeID as key, and ArrayList with TheatreRoomID in [0], MovieID in [1], and ShowDateTime in [2] as value
     */
    public HashMap<Integer, ArrayList<Object>> retrieveAllShowtimes(Connection con) {

        HashMap<Integer, ArrayList<Object>> showtimeMap = new HashMap<>();
        
        String query = "SELECT ShowtimeID, TheatreRoomID, ShowDateTime, MovieID FROM SHOWTIME;";

        // Query - Get all information from SHOWTIME table and add it to a Hashmap
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
    
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ArrayList<Object> valueArrayList = new ArrayList<>();

                int showtimeID = resultSet.getInt("ShowtimeID");
                int TheatreRoomID = resultSet.getInt("TheatreRoomID");
                Timestamp showDateTime = resultSet.getTimestamp("ShowDateTime");
                int movieID = resultSet.getInt("MovieID");

                // Values in ArrayList
                valueArrayList.add(TheatreRoomID); // [0]
                valueArrayList.add(movieID);       // [1]
                valueArrayList.add(showDateTime);  // [2]
                
                showtimeMap.put(showtimeID, valueArrayList);
            }
        } catch (SQLException e) { System.out.println(e); }

        return showtimeMap;
    }

//-------------------------------------------------------------//
//                      Movie Methods                          //
//-------------------------------------------------------------//


    /**
     * Get all movies from SQL database
     * @param con connection to database
     */
    public void retrieveAllMovies(Connection con) {
            // Prepare query
            String query = "SELECT Title, Genre, Rating, Runtime FROM MOVIE;";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                // Execute Query
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String genre = resultSet.getString("Genre");
                    String rating = resultSet.getString("Rating");
                    Time runtime = resultSet.getTime("Runtime");

                    System.out.println("Movie Title: " + title);
                    System.out.println("Genre: " + genre);
                    System.out.println("Rating: " + rating);
                    System.out.println("Runtime: " + runtime);
                    System.out.println("-----------");
                }
        } catch (SQLException e) { System.out.println(e); }
    }

    /**
     * Search for a movie 
     * @param movieName
     */
    public void searchForMovie(Connection con, String movieName) {
        // Try to connect to database
        try (Connection connection = DatabaseController.createConnection()) {

            // Prepare query
            String query = "SELECT Title, Genre, Rating, Runtime FROM MOVIE;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String genre = resultSet.getString("Genre");
                String rating = resultSet.getString("Rating");
                Time runtime = resultSet.getTime("Runtime");

                System.out.println("Movie Title: " + title);
                System.out.println("Genre: " + genre);
                System.out.println("Rating: " + rating);
                System.out.println("Runtime: " + runtime);
                System.out.println("-----------");
            }
        } catch (SQLException e) { System.out.println(e); }
    }
    
}

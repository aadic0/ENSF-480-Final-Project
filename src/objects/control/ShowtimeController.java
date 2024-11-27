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
        try (PreparedStatement psQuery = con.prepareStatement(query)) {
    
            ResultSet resultSet = psQuery.executeQuery();

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
    public HashMap<Integer, ArrayList<Object>> retrieveAllMovies(Connection con) {

        HashMap<Integer, ArrayList<Object>> movieMap = new HashMap<>();

        String query = "SELECT MovieID, Title, Genre, Rating, Runtime FROM MOVIE;";

        // Query - Get all information from MOVIE table and add it to a Hashmap
        try (PreparedStatement psQuery = con.prepareStatement(query)) {
            // Execute Query
            ResultSet resultSet = psQuery.executeQuery();

            // Get values, add them to ArrayList and then put them in HashMap
            while (resultSet.next()) {

                ArrayList<Object> valueArrayList = new ArrayList<>();

                int movieID = resultSet.getInt("MovieID");
                String title = resultSet.getString("Title");
                String genre = resultSet.getString("Genre");
                String rating = resultSet.getString("Rating");
                Time runtime = resultSet.getTime("Runtime");

                valueArrayList.add(title);   // [0]
                valueArrayList.add(genre);   // [1]
                valueArrayList.add(rating);  // [2]
                valueArrayList.add(runtime); // [3]

                movieMap.put(movieID, valueArrayList);

            }
        } catch (SQLException e) { System.out.println(e); }
        
        return movieMap;
    }

    /**
     * Search for a specific movie title
     * @param con
     * @param movieTitle
     * @return If movie title exists, return a HashMap containing its information. Otherwise return null
     */
    public HashMap<Integer, ArrayList<Object>> searchForMovie(Connection con, String movieTitle) {
        // Try to connect to database

        HashMap<Integer, ArrayList<Object>> movieMap = new HashMap<>();

        String query = "SELECT MovieID, Title, Genre, Rating, Runtime FROM MOVIE WHERE Title = ?;";

        try (PreparedStatement psQuery = con.prepareStatement(query);) {
            psQuery.setString(1, movieTitle);

            // Execute Query
            ResultSet resultSet = psQuery.executeQuery();

            // Get values, add them to ArrayList and then put them in HashMap
            while (resultSet.next()) {

                ArrayList<Object> valueArrayList = new ArrayList<>();

                int movieID = resultSet.getInt("MovieID");
                String title = resultSet.getString("Title");
                String genre = resultSet.getString("Genre");
                String rating = resultSet.getString("Rating");
                Time runtime = resultSet.getTime("Runtime");

                valueArrayList.add(title);   // [0]
                valueArrayList.add(genre);   // [1]
                valueArrayList.add(rating);  // [2]
                valueArrayList.add(runtime); // [3]

                movieMap.put(movieID, valueArrayList);
            }

        } catch (SQLException e) { System.out.println(e); }

        // If there were values added to movieMap return movieMap, otherwise return null
        if(!movieMap.isEmpty())
            return movieMap;

        return null;
    }
    
}

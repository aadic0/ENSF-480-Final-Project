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
import java.util.Map;

public class ShowtimeController {
    public ShowtimeController(){}


//-------------------------------------------------------------//
//                      Showtime Methods                       //
//-------------------------------------------------------------//



    /**
     * Get all showtimes from the DB
     * @param con
     * @return A hasmap with ShowtimeID as key, and ArrayList with TheatreRoomID in [0], MovieID in [1], and ShowDateTime in [2] as value
     */
    public HashMap<Integer, ArrayList<Object>> retrieveAllShowtimes(Connection con) {

        HashMap<Integer, ArrayList<Object>> showtimeMap = new HashMap<>();
        
        String query = "SELECT ShowtimeID, TheatreRoomID, ShowDateTime, MovieID FROM SHOWTIME;";

        // Query - Get all information from SHOWTIME table and add it to a Hashmap
        try (PreparedStatement psQuery = con.prepareStatement(query)) {
    
            ResultSet rs = psQuery.executeQuery();

            // Get info from DB and add it to the hashmap
            while (rs.next()) {
                ArrayList<Object> valueArrayList = new ArrayList<>();

                int showtimeID = rs.getInt("ShowtimeID");
                int TheatreRoomID = rs.getInt("TheatreRoomID");
                Timestamp showDateTime = rs.getTimestamp("ShowDateTime");
                int movieID = rs.getInt("MovieID");

                // Values in ArrayList
                valueArrayList.add(TheatreRoomID); // [0]
                valueArrayList.add(movieID);       // [1]
                valueArrayList.add(showDateTime);  // [2]

                // If showtime datetime has already passed, ignore it
                if(showDateTime.before(new Timestamp(System.currentTimeMillis())))
                    continue;
                
                showtimeMap.put(showtimeID, valueArrayList);
            }
        } catch (SQLException e) { System.out.println(e); }

        return showtimeMap;
    }

    /**
     * Search for a specific movie title
     * @param con
     * @param movieTitle
     * @return If movie title exists, return a HashMap containing its information. Otherwise return null
     */
    public HashMap<Integer, ArrayList<Object>> searchForShowtime(Connection con, String movieTitle) {
        // Try to connect to database

        String query1 = "SELECT MovieID, Title, Genre, Rating, Runtime FROM MOVIE WHERE Title = ?";
        String query2 = "SELECT ShowtimeID, TheatreRoomID, ShowDateTime FROM SHOWTIME WHERE MovieID = ?";

        int movieID = -1;

        // Query 1 - Find movieID given a movie title
        try (PreparedStatement psQuery1 = con.prepareStatement(query1);) {
            psQuery1.setString(1, movieTitle);

            ResultSet rs1 = psQuery1.executeQuery();

            // get movieID if it exists
            if (rs1.next()) 
                movieID = rs1.getInt("MovieID");
                
        } catch (SQLException e) { System.out.println(e); }

        HashMap<Integer, ArrayList<Object>> showtimeMap = new HashMap<>();

        // Query 2 - Get information from all showtimes with a matching MovieID and the showDateTime has not passed and put it in a HashMap
        try (PreparedStatement psQuery2 = con.prepareStatement(query2);) {
            psQuery2.setInt(1, movieID);

            ResultSet rs2 = psQuery2.executeQuery();

            // Get info from DB and add it to the hashmap
            while (rs2.next()) {
                ArrayList<Object> valueArrayList = new ArrayList<>();

                int showtimeID = rs2.getInt("ShowtimeID");
                int TheatreRoomID = rs2.getInt("TheatreRoomID");
                Timestamp showDateTime = rs2.getTimestamp("ShowDateTime");

                // If showtime datetime has already passed, ignore it
                if(showDateTime.before(new Timestamp(System.currentTimeMillis())))
                    continue;

                // Values in ArrayList
                valueArrayList.add(TheatreRoomID); // [0]
                valueArrayList.add(movieID);       // [1]
                valueArrayList.add(showDateTime);  // [2]
                
                showtimeMap.put(showtimeID, valueArrayList);
            }

        } catch (SQLException e) { System.out.println(e); }

        return showtimeMap;
                
    }

    public HashMap<Integer, ArrayList<Object>> searchForShowtimeTheatre(Connection con, String movieTitle, int theatreID) {
        
        ArrayList<Integer> allowedTheatreRoomID = new ArrayList<>();
        HashMap<Integer, ArrayList<Object>> showtimeMap = new HashMap<>();
        HashMap<Integer, ArrayList<Object>> allShowtimeMap = new HashMap<>();

        String query = "SELECT TheatreRoomID FROM THEATREROOM WHERE TheatreID = ?";

        // Query - Find all theatreRoomIDs with TheatreID and add to allowedTheatreRoomID
        try (PreparedStatement psQuery = con.prepareStatement(query)) {
            psQuery.setInt(1, theatreID);
            ResultSet rs = psQuery.executeQuery();

            // Put all theatreRoomIDs wit TheatreID into allowedTheatreRoomID
            while (rs.next()) {
                int TheatreRoomID = rs.getInt("TheatreRoomID");
                allowedTheatreRoomID.add(TheatreRoomID); // [0]
            }
        } catch (SQLException e) { System.out.println(e); }

        // Get every showtime with the movie title
        //                           int          int     Timestamp
        // format: ShowtimeID: [TheatreRoomID, MovieID, showDateTime]
        allShowtimeMap = searchForShowtime(con, movieTitle);

        // Put all showtimes that are in theatreRooms that have TheatreID into showtimeMap
        if (allShowtimeMap != null) 
            for (int theatreRoomID : allowedTheatreRoomID) // Loop through every allowed TheatreRoomID
                for (Map.Entry<Integer, ArrayList<Object>> entry : allShowtimeMap.entrySet()) // Loop through every array in allShowtimeMap values
                    if (entry.getValue().contains(theatreRoomID))  // If the entry arraylist contains theatreRoomID, then add it to showtimeMap
                        showtimeMap.put(entry.getKey(), entry.getValue());

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
            ResultSet rs = psQuery.executeQuery();

            // Get values, add them to ArrayList and then put them in HashMap
            while (rs.next()) {

                ArrayList<Object> valueArrayList = new ArrayList<>();

                int movieID = rs.getInt("MovieID");
                String title = rs.getString("Title");
                String genre = rs.getString("Genre");
                String rating = rs.getString("Rating");
                Time runtime = rs.getTime("Runtime");

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
            ResultSet rs = psQuery.executeQuery();

            // Get values, add them to ArrayList and then put them in HashMap
            while (rs.next()) {

                ArrayList<Object> valueArrayList = new ArrayList<>();

                int movieID = rs.getInt("MovieID");
                String title = rs.getString("Title");
                String genre = rs.getString("Genre");
                String rating = rs.getString("Rating");
                Time runtime = rs.getTime("Runtime");

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

package objects.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class ShowtimeController {
    public ShowtimeController(){}


//-------------------------------------------------------------//
//                      Showtime Methods                       //
//-------------------------------------------------------------//


    /**
     * Show all showtime IDs from SQL database
     */
    public void retrieveAllShowtimes(Connection con) {
        
        String query = "SELECT ShowtimeID, TheatreRoomID, ShowDateTime, MovieID FROM SHOWTIME;";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                // Execute Query
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int showtimeID = resultSet.getInt("ShowtimeID");
                    int TheatreRoomID = resultSet.getInt("TheatreRoomID");
                    Timestamp showDateTime = resultSet.getTimestamp("ShowDateTime");
                    int movieID = resultSet.getInt("MovieID");

                    System.out.println("showtimeID " + showtimeID);
                    System.out.println("TheatreRoomID " + TheatreRoomID);
                    System.out.println("showDateTime " + showDateTime);
                    System.out.println("movieID " + movieID);
                    System.out.println("-----------");
                }
        } catch (SQLException e) { System.out.println(e); }

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

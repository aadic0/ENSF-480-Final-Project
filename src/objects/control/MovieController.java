package objects.control;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Time;

// import objects.entity.Movie;

public class MovieController {
    public MovieController(){}

    /**
    * Get all movies from SQL database
    */
    // public void getAllMovies() {
    //     // Try to connect to database
    //     try (Connection connection = DatabaseController.createConnection()) {

    //         // Prepare query
    //         String query = "SELECT Title, Genre, Rating, Runtime FROM MOVIE;";
    //         PreparedStatement preparedStatement = connection.prepareStatement(query);

    //         // Execute Query
    //         ResultSet resultSet = preparedStatement.executeQuery();

    //         while (resultSet.next()) {
    //             String title = resultSet.getString("Title");
    //             String genre = resultSet.getString("Genre");
    //             String rating = resultSet.getString("Rating");
    //             Time runtime = resultSet.getTime("Runtime");

    //             System.out.println("Movie Title: " + title);
    //             System.out.println("Genre: " + genre);
    //             System.out.println("Rating: " + rating);
    //             System.out.println("Runtime: " + runtime);
    //             System.out.println("-----------");
    //         }
    //     } catch (SQLException e) { System.out.println(e); }
    // }

    // public void searchForMovie(String movieName) {
    //     // Try to connect to database
    //     try (Connection connection = DatabaseController.createConnection()) {

    //         // Prepare query
    //         String query = "SELECT Title, Genre, Rating, Runtime FROM MOVIE;";
    //         PreparedStatement preparedStatement = connection.prepareStatement(query);

    //         // Execute Query
    //         ResultSet resultSet = preparedStatement.executeQuery();

    //         while (resultSet.next()) {
    //             String title = resultSet.getString("Title");
    //             String genre = resultSet.getString("Genre");
    //             String rating = resultSet.getString("Rating");
    //             Time runtime = resultSet.getTime("Runtime");

    //             System.out.println("Movie Title: " + title);
    //             System.out.println("Genre: " + genre);
    //             System.out.println("Rating: " + rating);
    //             System.out.println("Runtime: " + runtime);
    //             System.out.println("-----------");
    //         }
    //     } catch (SQLException e) { System.out.println(e); }
    // }


    // /**
    //  * Remove movie from SQL database
    //  * @param movie
    //  */
    // public void removeMovie(Movie movie) {

    // } 
}

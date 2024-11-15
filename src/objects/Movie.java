package objects;
public class Movie {
    //---------------//
    //   Variables   //
    //---------------//
    private String name;
    private String genre;
    private String rating;


    //---------------//
    //  Constructor  //
    //---------------//
    public Movie(String name, String genre, String rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getter and Setter for rating
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    //-------------//
    //   Methods   //
    //-------------//
}

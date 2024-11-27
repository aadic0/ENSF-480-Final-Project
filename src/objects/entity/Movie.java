package objects.entity;

import java.sql.Time;

public class Movie {
    //---------------//
    //   Variables   //
    //---------------//
    private int movieID;
    private String name;
    private String genre;
    private String rating;
    private Time runtime;


    //---------------//
    //  Constructor  //
    //---------------//
    public Movie(int movieID, String name, String genre, String rating, Time runtime) {
        this.movieID = movieID;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.runtime = runtime;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    
    public int getMovieID() {
        return movieID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Time getRuntime() {
        return runtime;
    }

    public void setRuntime(Time runtime) {
        this.runtime = runtime;
    }




    //-------------//
    //   Methods   //
    //-------------//


}

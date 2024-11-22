package objects.entity;

import java.time.LocalTime;
import java.util.ArrayList;

public class Movie {
    //---------------//
    //   Variables   //
    //---------------//
    private int movieID;
    private String name;
    private String genre;
    private String rating;
    private LocalTime runtime;


    //---------------//
    //  Constructor  //
    //---------------//
    public Movie(int movieID, String name, String genre, String rating, LocalTime runtime) {
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

    public LocalTime getRuntime() {
        return runtime;
    }

    public void setRuntime(LocalTime runtime) {
        this.runtime = runtime;
    }




    //-------------//
    //   Methods   //
    //-------------//


}

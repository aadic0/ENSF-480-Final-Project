package objects.entity;

import java.util.ArrayList;

public class Movie {
    //---------------//
    //   Variables   //
    //---------------//
    private String name;
    private String genre;
    private String rating;
    private ArrayList<String> actors;


    //---------------//
    //  Constructor  //
    //---------------//
    public Movie(String name, String genre, String rating, ArrayList<String> actors) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.actors = actors;
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




    //-------------//
    //   Methods   //
    //-------------//

    public void addActor(String actor) {
        this.actors.add(actor);
    }

    public void removeActor(String actor) {
        this.actors.remove(actor);
    }
}

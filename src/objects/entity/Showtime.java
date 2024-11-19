package objects.entity;

import java.time.LocalDateTime;

public class Showtime {
    //---------------//
    //   Variables   //
    //---------------//
    private Movie movie;
    private LocalDateTime showingTime;
    

    //---------------//
    //  Constructor  //
    //---------------//
    public Showtime(Movie movie, LocalDateTime showingTime) {
        this.movie = movie;
        this.showingTime = showingTime;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public LocalDateTime getShowingTime() {
        return showingTime;
    }

    public void setShowingTime(LocalDateTime showingTime) {
        this.showingTime = showingTime;
    }
    //-------------//
    //   Methods   //
    //-------------//
}

package objects.entity;

import java.time.LocalDateTime;

public class Showtime {
    //---------------//
    //   Variables   //
    //---------------//
    
    private int showtimeID;
    private Movie movie;
    private TheatreRoom theatreRoom;
    private LocalDateTime showDateTime;

    //---------------//
    //  Constructor  //
    //---------------//
    public Showtime(int showtimeID, Movie movie, TheatreRoom theatreRoom, LocalDateTime showDateTime) {
        this.showtimeID = showtimeID;
        this.movie = movie;
        this.theatreRoom = theatreRoom;
        this.showDateTime = showDateTime;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//

    public int getShowtimeID() {
        return showtimeID;
    }

    public Movie getMovie() {
        return movie;
    }

    public TheatreRoom getTheatreRoom() {
        return theatreRoom;
    }

    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

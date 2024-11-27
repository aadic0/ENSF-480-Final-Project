package objects.entity;

import java.sql.Timestamp;


public class Showtime {
    //---------------//
    //   Variables   //
    //---------------//
    
    private int showtimeID;
    private Movie movie;
    private TheatreRoom theatreRoom;
    private Timestamp showDateTime;

    //---------------//
    //  Constructor  //
    //---------------//
    public Showtime(int showtimeID, Movie movie, TheatreRoom theatreRoom, Timestamp showDateTime) {
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

    public Timestamp getShowTimestamp() {
        return showDateTime;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

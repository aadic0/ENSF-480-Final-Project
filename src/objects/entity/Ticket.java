package objects.entity;
public class Ticket {
    //---------------//
    //   Variables   //
    //---------------//
    private int ticketID;
    private Showtime showtime;
    private Theatre theatre;
    private Seat seat;

    //---------------//
    //  Constructor  //
    //---------------//
    public Ticket(Showtime showtime, Theatre theatre, Seat seat, int ticketID) {
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.theatre = theatre;
        this.seat = seat;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public int getTicketID() { return ticketID; }
    public void setTicketID(int ticketID) { this.ticketID = ticketID; }

    public Showtime getShowtime() { return showtime; }
    public void setShowtime(Showtime showtime) { this.showtime = showtime; }

    public Theatre getTheatre() { return theatre; }
    public void setTheatre(Theatre theatre) { this.theatre = theatre; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }


    //-------------//
    //   Methods   //
    //-------------//
}

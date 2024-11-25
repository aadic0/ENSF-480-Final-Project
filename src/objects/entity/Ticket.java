package objects.entity;

import java.sql.Timestamp;

public class Ticket {
    //---------------//
    //   Variables   //
    //---------------//
    private int ticketID;
    private Showtime showtime;
    private Seat seat;
    private Timestamp purchaseDateTime;

    //---------------//
    //  Constructor  //
    //---------------//
    public Ticket(Showtime showtime, Timestamp purchaseDateTime, Seat seat, int ticketID) {
        this.ticketID = ticketID;
        this.showtime = showtime;
        this.purchaseDateTime = purchaseDateTime;
        this.seat = seat;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//

    public int getTicketID() {
        return ticketID;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public Timestamp getPurchaseDateTime() {
        return purchaseDateTime;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

package objects.entity;

import java.time.LocalDateTime;

public class Ticket {
    //---------------//
    //   Variables   //
    //---------------//
    private int ticketID;
    private Showtime showtime;
    private Seat seat;
    private LocalDateTime purchaseDateTime;

    //---------------//
    //  Constructor  //
    //---------------//
    public Ticket(Showtime showtime, LocalDateTime purchaseDateTime, Seat seat, int ticketID) {
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

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

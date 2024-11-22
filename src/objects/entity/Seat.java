package objects.entity;

public class Seat {
    //---------------//
    //   Variables   //
    //---------------//
    private int seatID;
    private TheatreRoom theatreRoom;
    private int seatRow;
    private int seatNumber;

    //---------------//
    //  Constructor  //
    //---------------//
    public Seat(int seatID, TheatreRoom theatreRoom, int seatRow, int seatNumber) {
        this.seatID = seatID;
        this.theatreRoom = theatreRoom;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public int getSeatID() {
        return seatID;
    }

    public TheatreRoom gettheatreRoom() {
        return theatreRoom;
    }

    public int getSeatRow() {
        return seatRow;
    }


    public int getSeatNumber() {
        return seatNumber;
    }


    //-------------//
    //   Methods   //
    //-------------//
}

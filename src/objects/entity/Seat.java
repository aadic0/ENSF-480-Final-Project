package objects.entity;

public class Seat {
    //---------------//
    //   Variables   //
    //---------------//

    private int seatRow;
    private int seatNumber;

    //---------------//
    //  Constructor  //
    //---------------//
    public Seat(int seatRow, int seatNumber) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//


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

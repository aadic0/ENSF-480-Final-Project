package objects;
public class Seat {
    //---------------//
    //   Variables   //
    //---------------//
    private int seatNumber;

    //---------------//
    //  Constructor  //
    //---------------//
    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    //-------------//
    //   Methods   //
    //-------------//
}

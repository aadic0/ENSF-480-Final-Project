package objects.entity;

public class TheatreRoom {
    //---------------//
    //   Variables   //
    //---------------//

    private int theatreRoomID;
    private Theatre theatre;
    private String roomName;

    //---------------//
    //  Constructor  //
    //---------------//
    public TheatreRoom(int theatreRoomID, Theatre theatre, String roomName) {
        this.theatreRoomID = theatreRoomID;
        this.theatre = theatre;
        this.roomName = roomName;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public int getTheatreRoomID() {
        return theatreRoomID;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    //-------------//
    //   Methods   //
    //-------------//
}

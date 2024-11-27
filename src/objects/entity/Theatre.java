package objects.entity;


public class Theatre {
    //---------------//
    //   Variables   //
    //---------------//

    private int theatreID;
    private String theatreName;
    private String streetAddress;

    //---------------//
    //  Constructor  //
    //---------------//
    public Theatre(int theatreID, String theatreName, String streetAddress) {
        this.theatreID = theatreID;
        this.theatreName = theatreName;
        this.streetAddress = streetAddress;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//

    public int getTheatreID() {
        return theatreID;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

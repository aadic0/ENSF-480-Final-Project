package objects.entity;

import java.util.HashMap;

public class Theatre {
    //---------------//
    //   Variables   //
    //---------------//
    private String name;
    private HashMap<Showtime, SeatMap> mapShowtimeSeating;
    //---------------//
    //  Constructor  //
    //---------------//
    public Theatre(String name) {
        this.name = name;
        this.mapShowtimeSeating = new HashMap<>();
    }

    public Theatre(String name, HashMap<Showtime, SeatMap> mapShowtimeSeating) {
        this.name = name;
        this.mapShowtimeSeating = mapShowtimeSeating;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Showtime, SeatMap> getMapShowtimeSeating() {
        return mapShowtimeSeating;
    }

    public void setMapShowtimeSeating(HashMap<Showtime, SeatMap> mapShowtimeSeating) {
        this.mapShowtimeSeating = mapShowtimeSeating;
    }

    //-------------//
    //   Methods   //
    //-------------//

}

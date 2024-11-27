// package objects.entity;

// import java.util.ArrayList;

// /* WE CAN PROBABLY GET RID OF THIS CLASS
//  * -Damon Nov 21
//  */


// public class SeatMap {
//     //---------------//
//     //   Variables   //
//     //---------------//
//     private ArrayList<ArrayList<Seat>> seatMap;

//     //---------------//
//     //  Constructor  //
//     //---------------//

//     // Creates a seatMap with given number of rows and columns
//     public SeatMap(int rows, int columns, Seat lowerSeat) {
//         this.seatMap = generateSeatmap(rows, columns, lowerSeat.getSeatNumber());
        
//     }
//     //---------------------//
//     //  Getters + Setters  //
//     //---------------------//
//     public void setSeatMap(ArrayList<ArrayList<Seat>> seatMap) {
//         this.seatMap = seatMap;
//     }

//     public ArrayList<ArrayList<Seat>> getSeatMap() {
//         return this.seatMap;
//     }

//     //-------------//
//     //   Methods   //
//     //-------------//
//     private ArrayList<ArrayList<Seat>> generateSeatmap(int rows, int columns, int lowerSeatNum) {
//         seatMap = new ArrayList<>();
//         for (int i = 0; i < rows; i++) {
//             ArrayList<Seat> row = new ArrayList<>();
//             for (int j = 0; j < columns; j++) {
//                 row.add(new Seat(lowerSeatNum + j + 1));  // Add a Seat with seatNumber (j + 1) to each row
//             }
//             seatMap.add(row);
//         }

//         return seatMap;
//     }


// }

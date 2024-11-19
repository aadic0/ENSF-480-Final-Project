### These are the **CONTROLLER** classes for the design project.

### These are the classes that are the link between the **BOUNDARY** and the **ENTITY** classes

# TO-DO:

### AnnouncementController.java  
- Implement sendAnnouncement() method
- Implement createAnnouncement() method
### MovieController.java  
- implement showMovies() method
    - will return list of all movies (including ones without showtimes)
### PaymentController.java  
- Implement a method to process payments
### ReceiptController.java  
- Implement sendReceipt() method
- Implement createReceipt() method
### RegisteredUserController.java  
- 
### SeatMapController.java  
- implement selectSeat() method
- selecting seat will be done in gui, which will be passed to boundary class, which is passed to this class
### ShowtimeController.java  
- add createShowtime() method
    - 
### TheatreController.java  
- 
### TicketController.java 
- implement cancelTicket() method
    - Create two seperate methods for User and RegisteredUser
- implement purchaseTicket() method
    - Have to select a showtime, theatre, seat, paymentInfo, and the user (or RU) buying the ticket
### UserController.java  
- Implement registerUser() method

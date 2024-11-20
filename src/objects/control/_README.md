### These are the **CONTROLLER** classes for the design project.

### These are the classes that are the link between the **BOUNDARY** and the **ENTITY** classes

# TO-DO:

### AnnouncementController.java  
- Implement sendAnnouncement() method

### MovieController.java  
- implement showMovies() method
    - will return list of all movies (including ones without showtimes)

### PaymentController.java  
- Implement a method to process payments
    - Ticket and RegisteredUser would have a PaymentController in their methods to facilitate payments? (unsure of how to implement this rn 11/19)

### ~~ReceiptController.java~~ __Can just put implementation into purachsing a ticket__ 
- ~~Implement sendReceipt() method~~


### RegisteredUserController.java  
- Add methods for managing account
    - Update payment information
    - update address information
    - update name
    - update email information
    - cancel registration (stop paying)


### SeatMapController.java  
- implement selectSeat() method
- selecting seat will be done in gui, which will be passed to boundary class, which is passed to this class

### ShowtimeController.java  
- Add showShowtimes() method
    - Shows showtimes for all theatres
    
### TheatreController.java  
- Add showTheatreShowtimes() method
    - Shows showtimes for a specific theatre

- Add showTheatreMovies() method
    - Shows all movies being shown at a theatre
    - Could do a method or just steal movie data from showTheatreShowtimes()

### TicketController.java 
- implement cancelTicket() method
    - Create two seperate methods for User and RegisteredUser
- implement purchaseTicket() method
    - Have to select a showtime, theatre, seat, paymentInfo, and the user (or RU) buying the ticket
    - Sends email containing ticket and reciept to user

### UserController.java  
- Implement registerUser() method


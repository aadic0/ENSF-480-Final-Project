package objects.control;

// entity imports
import objects.entity.User;
import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;
import objects.entity.Ticket;


public class TicketController {
    
    public TicketController(){}

    /**
     * A user pays for a ticket
     * @param user
     * @param paymentInfo
     */
    public void purchaseTicket(User user, PaymentInfo paymentInfo){
        // Check payment 

        // Add ticket to database

        // Email user the ticket(s) and a receipt
    }

    /**
     * Refund a ticket to a User
     * @param t
     * @param user
     * @param paymentInfo
     */
    public void refundTicket(Ticket t, User user, PaymentInfo paymentInfo) {

    }

    /**
     * Refund a ticket to a RegisteredUser
     * @param t
     * @param registeredUser
     */
    public void refundTicket(Ticket t, RegisteredUser registeredUser) {

    }
    


}

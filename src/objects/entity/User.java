package objects.entity;
import java.util.ArrayList;
public class User {
    //---------------//
    //   Variables   //
    //---------------//
    private String email;
    private ArrayList<Ticket> ticketsSelected;


    //---------------//
    //  Constructor  //
    //---------------//
    public User(String email) {
        this.email = email;
    }
    
    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    //-------------//
    //   Methods   //
    //-------------//

    public void selectTicket(Ticket ticket){
        ticketsSelected.add(ticket);
    }

    public void deselectTicket(Ticket ticket){
        ticketsSelected.remove(ticket);
    }

    public Receipt checkout(PaymentInfo paymentInfo){
        return new Receipt(0);
    }


}
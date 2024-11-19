package objects.entity;

import java.util.ArrayList;

public class Receipt {
    //---------------//
    //   Variables   //
    //---------------//
    private ArrayList<Ticket> tickets;
    private double price;

    //---------------//
    //  Constructor  //
    //---------------//
    public Receipt(double price) {
        this.tickets = new ArrayList<>();
        this.price = price;
    }

    public Receipt(ArrayList<Ticket> tickets, double price) {
        this.tickets = tickets;
        this.price = price;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public ArrayList<Ticket> getTickets() { return tickets; }
    public void setTickets(ArrayList<Ticket> tickets) { this.tickets = tickets; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    //-------------//
    //   Methods   //
    //-------------//
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public void printReceipt(){
        
    }

}

package objects.control;

import objects.entity.PaymentInfo;
import objects.entity.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {
    public PaymentController(){}

    /**
     * process a payment 
     * @param paymentInfo
     * @param price
     */
    public boolean pay(PaymentInfo paymentInfo, float price) {
        return true; // Payment is successful
    }

    /**
     * Refund the user associated with email
     * This function should be called jointly with TicketController's refundTicket to ensure proper ticket deletion
     * Be careful with your arguments, this function doesnt have checks to make sure the ticketID is correctly associated with the email.
     * @param paymentInfo
     * @param price
     * @param email
     * @param isRegUser
     * @return the refund amount
     */
    public void refund(PaymentInfo paymentInfo, float price, boolean isRegUser, int ticketID, String email) {

        // UPDATE THE REGULAR_USER TABLE
        String query = "UPDATE REGULAR_USER "
                     + "SET StoreCredit = ? "
                     + "WHERE Email = ?";

        String deleteTicketQuery = "DELETE FROM TICKET WHERE TicketID = ?";

        try (Connection connection = DatabaseController.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, price);
            preparedStatement.setString(2, email);


            int rowsAffected = preparedStatement.executeUpdate();


            // Only for testing
            if (rowsAffected > 0) {
                System.out.println("Store credit updated successfully. New store credit: " + price);
            } else {
                System.out.println("No rows updated. Check the email address.");
            }
        
            // Delete the ticket
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteTicketQuery)) {
                deleteStatement.setInt(1, ticketID);
                int rowsDeleted = deleteStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Ticket deleted successfully. Ticket ID: " + ticketID);
                } else {
                    System.out.println("No ticket found with the specified Ticket ID.");
                }
                connection.commit();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }        
    
    catch (Exception e){
        e.printStackTrace();
    }

    }

    public void pay(PaymentInfo paymentInfo, float price, boolean isRegUser, int ticketID, String email, TicketController ticket, int seatID, int showtimeID, String time){

        // check if they have store credit, use that first:
        String query = "SELECT StoreCredit FROM REGULAR_USER WHERE Email = ?";
        int storeCredit;

        try (Connection connection = DatabaseController.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    storeCredit = resultSet.getInt("StoreCredit");
                    System.out.println("Store credit: " + storeCredit); // just for debugging purposes
                }
                else{
                    return;
                }
                
                // CASE FOR WE HAVE STORE CREDIT
                if (storeCredit >= 0){
                    float temp = storeCredit;
                    storeCredit -= price;
                    if (storeCredit < 0){
                        storeCredit = 0;
                    }
                    price = price - temp;
                }

                // CASE FOR WE DONT HAVE STORE CREDIT
                else{
                    // do nothing
                    storeCredit+= price;
                    storeCredit-= price;
                }

                // add ticket to database
                String insertQuery = "INSERT INTO TICKET (TicketID, ShowtimeID, SeatID, PurchaseDateTime, TicketPrice, Email) "
                                   + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedInsertStatement = connection.prepareStatement(insertQuery);
                preparedInsertStatement.setInt(1, ticketID);
                preparedInsertStatement.setInt(2, showtimeID);
                preparedInsertStatement.setInt(3, seatID);
                preparedInsertStatement.setString(4, time);
                preparedInsertStatement.setFloat(5, price);
                preparedInsertStatement.setString(6, email);

                preparedInsertStatement.execute();

                // uupdate storecredit in regular user
                String updateQuery = "UPDATE REGULAR_USER "
                                   + "SET StoreCredit = ? "
                                   + "WHERE Email = ?";

                PreparedStatement preparedUpdateStatement = connection.prepareStatement(updateQuery);
                preparedUpdateStatement.setFloat(1, storeCredit);
                preparedUpdateStatement.setString(2, email);

                preparedUpdateStatement.execute();
                
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


}

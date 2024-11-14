package objects;
public class RegisteredUser extends User {
    
    private String firstName;
    private String lastName; 
    private String address; // Might change this to a class
    private PaymentInfo paymentInfo;

    public RegisteredUser(String firstName, String lastName, String address, String email, PaymentInfo paymentInfo) {
        super(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentInfo = paymentInfo;
    }
}

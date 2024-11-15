package objects;
public class RegisteredUser extends User {
    
    //---------------//
    //   Variables   //
    //---------------//
    private String firstName, lastName; 
    private String streetAddress, city, province, postalCode;
    private PaymentInfo paymentInfo;

    //---------------//
    //  Constructor  //
    //---------------//
    public RegisteredUser(
        String firstName, String lastName,                                      // Name
        String streetAddress, String city, String province, String postalCode,  // Address
        String email,                                                           // Email
        PaymentInfo paymentInfo                                                 // CC info
    ) {
        super(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.paymentInfo = paymentInfo;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    //-------------//
    //   Methods   //
    //-------------//
}

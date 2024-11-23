package objects.entity;
public class PaymentInfo {
    //---------------//
    //   Variables   //
    //---------------//
    private double cardNumber;
    private String expiration;
    private int cvv;
    // private String fullName;
    
    //---------------//
    //  Constructor  //
    //---------------//
    public PaymentInfo(double cardNumber, String expiration, int cvv, String fullName) {
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
        // this.fullName = fullName;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public double getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(double cardNumber) {
        assert(String.valueOf(cardNumber).length() == 16);
        this.cardNumber = cardNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        // Create assert statement for expiration (might have to change to a DateTime type of object)
        this.expiration = expiration;
    }

    public int getCV() {
        return cvv;
    }

    public void setCVV(int cvv) {
        assert(cvv >= 100 && cvv <= 999);
        this.cvv = cvv;
    }

    // public String getFullName() {
    //     return fullName;
    // }

    // public void setFullName(String fullName) {
    //     this.fullName = fullName;
    // }
    //-------------//
    //   Methods   //
    //-------------//
    
}

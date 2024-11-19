package objects.entity;
public class PaymentInfo {
    //---------------//
    //   Variables   //
    //---------------//
    private double cardNumber;
    private String expiration;
    private int cv;
    private String fullName;
    
    //---------------//
    //  Constructor  //
    //---------------//
    public PaymentInfo(double cardNumber, String expiration, int cv, String fullName) {
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cv = cv;
        this.fullName = fullName;
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
        return cv;
    }

    public void setCV(int cv) {
        assert(cv >= 100 && cv <= 999);
        this.cv = cv;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    //-------------//
    //   Methods   //
    //-------------//
    
}

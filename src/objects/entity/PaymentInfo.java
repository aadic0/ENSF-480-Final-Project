package objects.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentInfo {
    //---------------//
    //   Variables   //
    //---------------//
    private long cardNumber;
    private String expiration;
    private int cvv;
    private boolean validInfo;
    // private String fullName;
    
    //---------------//
    //  Constructor  //
    //---------------//
    public PaymentInfo(long cardNumber, String expiration, int cvv) {

        validInfo = true;
        
        validateCardNumber(cardNumber);
        validateExpirationDate(expiration);
        validateCVV(cvv);

        if(validInfo) {
            this.cardNumber = cardNumber;
            this.expiration = expiration;
            this.cvv = cvv;
        }
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public double getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        validateCardNumber(cardNumber);
        this.cardNumber = cardNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        validateExpirationDate(expiration);
        this.expiration = expiration;
    }

    public int getCV() {
        return cvv;
    }

    public void setCVV(int cvv) {
        validateCVV(cvv);
        this.cvv = cvv;
    }

    public boolean getValidInfo() {
        return validInfo;
    }


    //-------------//
    //   Methods   //
    //-------------//

    /**
     * Check if card number is valid (16 digits)
     * @param cardNumber
     */
    private void validateCardNumber(long cardNumber) {
        // Convert to a string and check length
        String cardNumberStr = String.valueOf(cardNumber); 

        if (cardNumberStr.length() != 16) {
            System.out.println("Card number must be 16 digits.");
            validInfo = false; 
        }
    }

    /**
     * Check if expiration date is valid (valid date and in the future)
     * @param expiration
     */
    private void validateExpirationDate(String expiration) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expirationDate = LocalDate.parse(expiration, formatter);
            
            // Check if date is previous to right now
            if (expirationDate.isBefore(LocalDate.now())) {
                System.out.println("Expiration date must be in the future.");
                validInfo = false; 
            }

            // Check if expiry date is set to the last day of the month it expires
            else if (!expirationDate.equals(expirationDate.withDayOfMonth(expirationDate.lengthOfMonth()))) {
                System.out.println("Expiration date must be the last day of the month.");
                validInfo = false; 
            }
        } 
        // Check if date is valid
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid expiration date format. Use yyyy-MM-dd.");
        }
    }

    /**
     * Check is CVV number is valid (3 digits)
     * @param cvv
     */
    private void validateCVV(int cvv) {
        if (cvv < 100 || cvv > 999) { 
            System.out.println("CVV must be a 3 digit number.");
            validInfo = false; 
        }
    }
    
}
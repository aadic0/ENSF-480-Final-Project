package objects.control;

import objects.entity.PaymentInfo;

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
     * @param paymentInfo
     * @param price
     * @param isRegUser
     * @return the refund amount
     */
    public float refund(PaymentInfo paymentInfo, float price, boolean isRegUser) {
        if(isRegUser) 
            return price;
        else 
            return price - (price * 0.15f);
    }

}

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
}

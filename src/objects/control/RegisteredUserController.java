package objects.control;

import objects.entity.PaymentInfo;
import objects.entity.RegisteredUser;

public class RegisteredUserController {

    public RegisteredUserController() {}

    /**
     * Update payment information in SQL database
     * @param paymentInfo 
     */
    public void updatePaymentInfo(PaymentInfo paymentInfo) {

    }

    /**
     * Update address information in SQL database
     * @param address 
     */
    public void updateAddressInfo(String address) {

    }

    /**
     * Update the name in SQL database
     * @param firstName
     * @param lastName
     */
    public void updateName(String firstName, String lastName) {

    }

    /**
     * Update email information in SQL database
     * @param email 
     */
    public void updateEmailInfo(String email) {

    }

    /**
     * Cancel the user's registration (stop payment)
     * This will stop any recurring payments and deactivate the user account.
     * @param regUser The registered user whose account will be cancelled
     */
    public void cancelRegistration(RegisteredUser regUser) {

    }
}

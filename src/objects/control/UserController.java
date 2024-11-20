package objects.control;

import objects.entity.RegisteredUser;
import objects.entity.User;

public class UserController {
    
    public UserController(){}
 
    /**
     * turn a regular user into a registered user
     */
    public RegisteredUser createRegisteredUser(User user) {
        RegisteredUser regUser = new RegisteredUser(
            null, null, 
            null, null, null, null, 
            user.getEmail(), null
        );

        // Add to database
        // ...
        
        return regUser;
    }

    /**
     * Create a user object and add to database
     * @param email
     * @param pwd
     */
    public User createUser(String email, String pwd){
        User user = new User(email);

        // Add to database
        // ...

        return user;
    }
}

package objects.control;

import objects.entity.User;

// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

// NOTE: I'm using this file to test out database communication, this will (probably) not be the actual main we use
public class Main {
    public static void main(String[] args) {
    /* UserController testing */
        // UserController userController = new UserController();

        // userController.createUser("test@gobo.com", "pwd");
        // userController.createRegisteredUser(new User("test@gobo.com"), "pwd", 
        // "Borgen", "Schmorgen", 
        // "123 abc", "Salt Lake City", "Manitoba", "T2P0G5");

    /* ShowtimeController Testing */
        // ShowtimeController showtimeController = new ShowtimeController();
        // showtimeController.showShowtimes();

    RegisteredUserController controller = new RegisteredUserController();
    
    // Test the method
    String testEmail = "test@test.com";
    String testPassword = "jdoe";
    boolean isAuthenticated = controller.authenticateUser(testEmail, testPassword);
    
    System.out.println("Authentication result: " + isAuthenticated);


    controller.registerUser("aadichauhan321@gmail.com", "deezNutz", "Aadi", "Chauhan", "93 Legacy Common SE", "Calgary", testEmail, testPassword);
    


    }
}

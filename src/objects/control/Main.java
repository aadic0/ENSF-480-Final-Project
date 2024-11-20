package objects.control;

import objects.entity.User;

// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

// NOTE: I'm using this file to test out database communication, this will (probably) not be the actual main we use
public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();

        userController.createUser("test@gobo.com", "pwd");
        userController.createRegisteredUser(new User("test@gobo.com"), "pwd", 
        "Borgen", "Schmorgen", 
        "123 abc", "Salt Lake City", "Manitoba", "T2P0G5");

    }
}

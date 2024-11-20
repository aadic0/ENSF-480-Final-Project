package objects.control;

// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();

        userController.createUser("test@gobo.com", "pwd");
    }
}

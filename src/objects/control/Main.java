package objects.control;

// NOTE: I'm putting this file in the src/objects/control folder for now because it is easier to compile/
//       It should not stay in here long term

// NOTE: I'm using this file to test out database communication, this will (probably) not be the actual main we use
public class Main {
    public static void main(String[] args) {

    //------------------------------//
    //   RegisteredUserController   //
    //------------------------------//
    // RegisteredUserController regUserController = new RegisteredUserController();
    
    // // Test the method
    // String testEmail = "test@test.com";
    // String testPassword = "jdoe";
    // boolean isAuthenticated = regUserController.authenticateUser(testEmail, testPassword);
    
    // System.out.println("Authentication result: " + isAuthenticated);

    // regUserController.registerUser("aadichauhan321@gmail.com", "deezNutz", "Aadi", "Chauhan", "93 Legacy Common SE", "Calgary", "Alberta", "T2X 2A9");
    // regUserController.updateAddressInfo("Fake address");
    // regUserController.updateName("Aaditya", "Chonuts");
    // regUserController.updateEmailInfo("deeznuts@gmail.com");

    //------------------------//
    //   ShowtimeController   //
    //------------------------//
    // ShowtimeController showtimeController = new ShowtimeController();
    // showtimeController.getAllShowtimes();

    //------------------------//
    //     MovieController    //
    //------------------------//
    MovieController movieController = new MovieController();
    movieController.getAllMovies();


    }

    
}

package objects;
public class User {
    //---------------//
    //   Variables   //
    //---------------//
    private String email;

    //---------------//
    //  Constructor  //
    //---------------//
    public User(String email) {
        this.email = email;
    }
    
    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //-------------//
    //   Methods   //
    //-------------//
}
//main page where all the movies are, nav bar(user,cart), search bar
package objects.boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 



public class MainPage extends JPanel {

    MainPage(){

       //JMenubar menubar;

    }

    public void displayMainPage(){
        JFrame frame = new JFrame();
        JLabel random = new JLabel("mainpage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(random);
        frame.setVisible(true);
        
    }
    
}

//boundary, browse movie announcments

package objects.boundary;

import objects.control.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class BrowseAnnouncment extends JPanel {
    //components
    private JLabel title;
    private appGUI parent;

    //controller
    private AnnouncementController aControl;


    //ctor
    public BrowseAnnouncment(appGUI parent){
        this.parent = parent;
        this.aControl = new AnnouncementController();

        this.refreshAnnouncements();


        setVisible(true);
    }

    public void refreshAnnouncements() {
        removeAll(); // Clear the panel
        revalidate();
        repaint();

        /*panel setup */
        setLayout(new GridBagLayout()); //arrange components in grid-like structure
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);
       
        title = new JLabel("View Announcements");
        title.setFont(new Font("Arial", Font.BOLD,18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(title, constraints); //add to the panel
    
        // Retrieve announcements again
        HashMap<Integer, ArrayList<Object>> announceMap = aControl.retrieveAllAnnouncement(parent.getLoggedInUser());
    
        // Convert data for JTable
        String[][] announceData = new String[announceMap.size()][2];
        String[] columnNames = {"Date", "Message"};
        int row = 0;
    
        for (ArrayList<Object> announceDetails : announceMap.values()) {
            announceData[row][0] = announceDetails.get(1).toString(); // Date
            announceData[row][1] = (String) announceDetails.get(0); // Message
            row++;
        }
    
        JTable announceTable = new JTable(new DefaultTableModel(announceData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    
        JScrollPane scrollPane = new JScrollPane(announceTable);
        // Adjust column widths
        announceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        announceTable.getColumnModel().getColumn(0).setPreferredWidth(200); //width for date 
        announceTable.getColumnModel().getColumn(1).setPreferredWidth(600); //width for message
        
        //set table size and fill
        announceTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
        announceTable.setFillsViewportHeight(true);
                
        //scroll pane constraints
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span entire panel width
        constraints.fill = GridBagConstraints.BOTH; // Allow both horizontal and vertical stretching
        constraints.weightx = 1.0; // Expand horizontally
        constraints.weighty = 1.0; // Expand vertically
        add(scrollPane, constraints);
    
        revalidate();
        repaint();

        //RE-ADD BUTTON
        // Add a selection button
        JButton updateButton = new JButton("Update");
        

        // Configure constraints
        constraints.gridx = 0;
        constraints.gridy = 2; // Assuming the table is at gridy = 1
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        constraints.anchor = GridBagConstraints.CENTER; // Center the button
        constraints.fill = GridBagConstraints.HORIZONTAL; // Stretch horizontally
        constraints.weightx = 0; // No horizontal resizing
        constraints.weighty = 0; // No vertical resizing
        constraints.insets = new Insets(10, 10, 10, 10); // Add some spacing

        // Add button to the panel
        add(updateButton, constraints);

        // Add action listener for functionality
        updateButton.addActionListener(e -> {refreshAnnouncements();});

    }
    

    //temporary main method for testing

    /*
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new BrowseMovie(frame));
        frame.setVisible(true);
    
    }
    */

}

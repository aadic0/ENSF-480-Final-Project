package objects.entity;

import java.util.ArrayList;

public class Announcement {
    //---------------//
    //   Variables   //
    //---------------//
    private String announcementMessage;
    private ArrayList<RegisteredUser> subscribedUsers;
    //---------------//
    //  Constructor  //
    //---------------//
    public Announcement(String announcementMessage) {
        this.announcementMessage = announcementMessage;
        this.subscribedUsers = new ArrayList<>();  
    }

    public Announcement(String announcementMessage, ArrayList<RegisteredUser> subscribedUsers) {
        this.announcementMessage = announcementMessage;
        this.subscribedUsers = subscribedUsers;
    }

    //---------------------//
    //  Getters + Setters  //
    //---------------------//
    public String getAnnouncementMessage() {
        return announcementMessage;
    }

    public void setAnnouncementMessage(String announcementMessage) {
        this.announcementMessage = announcementMessage;
    }

    public ArrayList<RegisteredUser> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(ArrayList<RegisteredUser> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    //-------------//
    //   Methods   //
    //-------------//
    public void addSubscribedUser(RegisteredUser user) {
        subscribedUsers.add(user);
    }

    public void removeSubscribedUser(RegisteredUser user) {
        subscribedUsers.remove(user);
    }

    public void sendAnnouncement() {
        // Send an email to each RegisteredUser object containing announcementMessage
    }
}

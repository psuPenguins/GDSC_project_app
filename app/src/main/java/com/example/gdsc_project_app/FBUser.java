package com.example.gdsc_project_app;

public class FBUser {
    public String username, email, password, interactedPosts, interactedComments, profileImage, roomID;
    public boolean swipe;

    public FBUser(){}

    public FBUser(String email, String username, String password, String roomID, String profileImage, Boolean swipe){
        this.email = email;
        this.username = username;
        this.password = password;
        this.roomID = roomID;
        this.profileImage = profileImage;
        this.swipe = swipe;
    }
}

package com.example.gdsc_project_app;

public class FBPost {
    public String userID, username, roomID, description;
    public Integer likeCount, dislikeCount;

    public FBPost(String userID, String username, String roomID, String description) {
        this.userID = userID;
        this.username = username;
        this.roomID = roomID;
        this.description = description;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }
}

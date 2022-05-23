package com.example.gdsc_project_app;

public class FBPost {
    public String userID, username, topicID, description;
    public Integer likeCount, dislikeCount;

    public FBPost(String userID, String username, String topicID, String description) {
        this.userID = userID;
        this.username = username;
        this.topicID = topicID;
        this.description = description;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }
}

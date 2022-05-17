package com.example.gdsc_project_app;

public class FBPost {
    public String userID, username, questionID, description;
    public Integer likeCount, dislikeCount;

    public FBPost(String userID, String username, String questionID, String description) {
        this.userID = userID;
        this.username = username;
        this.questionID = questionID;
        this.description = description;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }
}

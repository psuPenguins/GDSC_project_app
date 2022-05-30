package com.example.gdsc_project_app;

public class FBComment {
    public String userID, username, postID, description;
    public Integer likeCount, dislikeCount;

    public FBComment(){}
    public FBComment(String userID, String username, String postID, String description, Integer likeCount, Integer dislikeCount) {
        this.userID = userID;
        this.username = username;
        this.postID = postID;
        this.description = description;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}

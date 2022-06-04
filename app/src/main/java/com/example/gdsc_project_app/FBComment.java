package com.example.gdsc_project_app;

import com.google.firebase.Timestamp;

public class FBComment {
    public String userID, username, postID, description, commentID;
    public Integer likeCount, dislikeCount;
    public long timestamp;

    public FBComment(){}
    public FBComment(String userID, String username, String postID, String description, Integer likeCount, Integer dislikeCount, String commentID, long timestamp) {
        this.userID = userID;
        this.username = username;
        this.postID = postID;
        this.description = description;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentID = commentID;
        this.timestamp = timestamp;
    }
}

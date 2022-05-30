package com.example.gdsc_project_app;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FBPost {
    public String userID, username, roomID, description, postID;
    public Integer likeCount, dislikeCount;
    public Boolean liked = false;
    public Boolean disliked = false;

    public FBPost(){}

    public FBPost(String userID, String username, String roomID, String description, Integer likeCount, Integer dislikeCount, String postID) {
        this.userID = userID;
        this.username = username;
        this.roomID = roomID;
        this.description = description;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.postID = postID;
    }

    public String getUsername() {
        return username;
    }
    public String getDescription() {return description;}
    public Integer getLikeCount() {return likeCount;}
    public Integer getDislikeCount() {return dislikeCount;}
    public String getUserID() {return userID;}
    public String getRoomID() {return roomID;}
    public String getPostID() {return postID;}

    public void setUserID(String userID) {this.userID = userID;}
    public void setUsername(String username) {this.username = username;}
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}

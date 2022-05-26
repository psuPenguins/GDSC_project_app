package com.example.gdsc_project_app;

public class FBPost {
    public String userID, username, roomID, description;
    public Integer likeCount, dislikeCount;
    public Boolean liked = false;
    public Boolean disliked = false;

    public FBPost(){}

    public FBPost(String userID, String username, String roomID, String description) {
        this.userID = userID;
        this.username = username;
        this.roomID = roomID;
        this.description = description;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    public String getUsername() {
        return this.username;
    }
    public String getDescription() {return this.description;}
    public Integer getLikeCount() {return this.likeCount;}
    public Integer getDislikeCount() {return this.dislikeCount;}
    public String getUserID() {return this.userID;}
    public String getRoomID() {return this.roomID;}

    public void setUserID(String userID) {this.userID = userID;}
    public void setUsername(String username) {this.username = username;}
    public void setLikeCount(Integer likeCount) {this.likeCount = likeCount;}
    public void setDislikeCount(Integer dislikeCount) {this.dislikeCount = dislikeCount;}
}

package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject{
    public String description;
    public Integer likeCount;
    public Integer dislikeCount;
    public static final String KEY_USERID = "userID";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LIKE_COUNT = "likeCount";
    public static final String KEY_DISLIKE_COUNT = "dislikeCount";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public String getUsername() {
        return getString(KEY_USERNAME);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public Integer getLikeCount() {
        return getInt(KEY_LIKE_COUNT);
    }

    public void setLikeCount() {
        put(KEY_LIKE_COUNT, likeCount);
    }

    public String getDislike() {
        return getString(KEY_DISLIKE_COUNT);
    }

    public void setDislike() {
        put(KEY_DISLIKE_COUNT, dislikeCount);
    }

    public String getUserID() {
        return getString(KEY_USERID);
    }

    public void setUserID(String userID) {
        put(KEY_USERID, userID);
    }

    public void setUsername(String username) {
        put(KEY_USERNAME, username);
    }
}

package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject{
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USERID = "userID";
    public static final String KEY_LIKE_COUNT = "likeCount";
    public static final String KEY_DISLIKE_COUNT = "dislikeCount";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription() {
        put(KEY_DESCRIPTION, description);
    }

    public String getLikeCount() {
        return getString(KEY_LIKE_COUNT);
    }

    public void setLikeCount() {
        put(KEY_LIKE_COUNT, likeCount);
    }

    public String getDisLike() {
        return getString(KEY_DISLIKE_COUNT);
    }

    public void setDescription() {
        put(KEY_DISLIKE_COUNT, dislikeCount);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USERID);
    }

    public void setUser(ParseUser user) {
        put(KEY_USERID, user);
    }
}

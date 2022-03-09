package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_COMMENT_DESCRIPTION = "description";
    public static final String KEY_COMMENT_USER_ID = "userID";
    public static final String KEY_COMMENT_POST_ID = "postID";
    public static final String KEY_COMMENT_LIKE_COUNT = "likeCount";
    public static final String KEY_COMMENT_DISLIKE_COUNT = "dislikeCount";
    public static final String KEY_COMMENT_USER_NAME = "userName";
    public static final String KEY_COMMENT_TIME = "createdAt";

    public String getCommentDescription(){
        return getString(KEY_COMMENT_DESCRIPTION);
    }

    public String getCommentUserId(){
        return getString(KEY_COMMENT_USER_ID);
    }

    public String getCommentUserName(){
        return getString(KEY_COMMENT_USER_NAME);
    }

    public String getCommentPostId(){
        return getString(KEY_COMMENT_POST_ID);
    }

    public Integer getCommentLikeCount(){
        return getInt(KEY_COMMENT_LIKE_COUNT);
    }

    public Integer getCommentDislikeCount(){
        return getInt(KEY_COMMENT_DISLIKE_COUNT);
    }

    public String getCommentTime(){
        return getString(KEY_COMMENT_TIME);
    }

    public void setUser(ParseUser user){
        put(KEY_COMMENT_USER_ID, user);
    }
}

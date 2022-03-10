package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_USER_USER_ID = "userID";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_PROFILE_IMAGE = "profileImage";

    public String getUserUserId(){
        return getString(KEY_USER_USER_ID);
    }

    public String getUserUsername(){
        return getString(KEY_USER_USERNAME);
    }

    public ParseFile getUserProfileImage(){
        return getParseFile(KEY_USER_PROFILE_IMAGE);
    }


}

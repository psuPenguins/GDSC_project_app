package com.example.gdsc_project_app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register for post class
        ParseObject.registerSubclass(Post.class);

        // register for comment class
        ParseObject.registerSubclass(Comment.class);

        // register for swipe class
        ParseObject.registerSubclass(Question.class);

        // register for User class
        ParseObject.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
    }
}

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

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yvEO7yE9aWqxurhUv65BAvD6zCVqw4sPNgRySxLF")
                .clientKey("eY6e8cxClOEFf7s4vzvCNFJEoXSdZT3pYuHKzanj")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

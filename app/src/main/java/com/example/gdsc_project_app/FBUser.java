package com.example.gdsc_project_app;

public class FBUser {
    public String username, email, password;

    public FBUser(){ }

    public FBUser(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

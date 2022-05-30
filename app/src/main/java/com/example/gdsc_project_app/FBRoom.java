package com.example.gdsc_project_app;

public class FBRoom {
    public String topic, userIDs, numOfUsers, postIDs;

    public FBRoom(){}

    
    public FBRoom(String topic, String numOfUsers) {
        this.topic = topic;
        this.numOfUsers = numOfUsers;
        this.postIDs = postIDs;
    }

    public String getTopic() {
        return topic;
    }

    public String getUserIDs() {
        return userIDs;
    }

    public String getNumOfUsers() {
        return numOfUsers;
    }
}

package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Question")
public class Question extends ParseObject{
    public static final String KEY_QUESTION = "question";
    public static final String KEY_TOPIC = "topic";
    public static final String KEY_QUESTIONID = "objectId";


    public String getQuestion() { return getString(KEY_QUESTION); }
    public String getTopic() { return getString(KEY_TOPIC); }
    public String getQuestionID() { return getString(KEY_QUESTIONID); }
}

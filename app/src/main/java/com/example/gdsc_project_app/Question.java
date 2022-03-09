package com.example.gdsc_project_app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Question")
public class Question extends ParseObject{
    public static final String KEY_QUESTION = "question";

    public String getQuestion() { return getString(KEY_QUESTION); }

}

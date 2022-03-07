package com.example.gdsc_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // this is a comment from Brian this is from main second time
    // this is a comment from Nick
    // second comment from Nick
    // this is a comment from Selina 2
    // comment from  ved
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "I'm in MainActivity");
    }
}
package com.example.gdsc_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //TODO: create private variables (btn, tv..)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Log.i(TAG, "I'm in MainActivity");

        // TODO: link the private variables to the elements in the xml files
    }

    // TODO: create onclicklistener for the button to the change the activity
}
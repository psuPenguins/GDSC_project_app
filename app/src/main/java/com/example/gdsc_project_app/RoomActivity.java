package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RoomActivity extends AppCompatActivity {

    public static final String TAG = "RoomActivity";

    //TODO: create private variables (btn, tv..)
    private Button buttonViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_room));

        // TODO: link the private variables to the elements in the xml files
        buttonViewReply = findViewById((R.id.btnViewReply));
    }

    // TODO: create onclicklistener for the button to the change the activity
    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
    }
}

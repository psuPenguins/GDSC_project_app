package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoomActivity extends AppCompatActivity {

    public static final String TAG = "RoomActivity";

    //TODO: create private variables (btn, tv..)
    private Button btnViewReply;
    private Button btnReturnFromRoom;
    private FloatingActionButton btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_room));
        Log.i(TAG, "I'm in RoomActivity");

        // link the private variables to the elements in the xml files
        btnViewReply = findViewById(R.id.btnViewReply);
        btnReturnFromRoom = findViewById(R.id.btnReturnFromRoom);
        btnPost = findViewById(R.id.btnPost);

        // create onclicklistener for the button to the change the activity
        //btnViewReply.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick (View view){
                //Log.i(TAG, "onClick view reply button");
                //goReplyActivity();
            //}
        //});

        btnReturnFromRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick return from room button");
                goMainActivity();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick post button");
                goPostActivity();
            }
        });
    }

    // declaring transition functions
    //private void goReplyActivity(){
        //Intent i = new Intent(this, ReplyActivity.class);
        //Log.i(TAG, "Going into ReplyActivity");
        //startActivity(i);
    //}

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
    }

    private void goPostActivity(){
        Intent i = new Intent(this, PostActivity.class);
        Log.i(TAG, "Going into PostActivity");
        startActivity(i);
    }

}

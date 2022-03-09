package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private Button btnBack;
    private Button btnAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_comment));

        // linking the private variables to the elements in the xml files
        btnBack = findViewById(R.id.btnBack);
        btnAddComment = findViewById(R.id.btnAddComment);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick Back button");
                goMainActivity();
            }
        });

        // TODO: activate this button
//        btnAddComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                Log.i(TAG, "onClick Add Comment button");
//                goSwipeActivity();
//            }
//        });
    }

    // create onclicklistener for the button to the change the activity
    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        // Intent temp = new Intent(this, SwipeActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
    }

//    private void goMainActivity(){
//        Intent i = new Intent(this, MainActivity.class);
//        // Intent temp = new Intent(this, SwipeActivity.class);
//        Log.i(TAG, "Going into MainActivity");
//        startActivity(i);
//        // startActivity(temp);
//    }
}

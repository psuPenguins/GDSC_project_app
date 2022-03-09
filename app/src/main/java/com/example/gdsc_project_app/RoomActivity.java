package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RoomActivity extends AppCompatActivity {

    public static final String TAG = "RoomActivity";

    //TODO: create private variables (btn, tv..)
    private RecyclerView rvPosts;
    private Button btnViewReply;
    private Button btnReturnFromRoom;
    private FloatingActionButton btnAddPost;
    private TextView tvRoomTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_room));
        Log.i(TAG, "I'm in RoomActivity");

        // link the private variables to the elements in the xml files
        btnViewReply = findViewById(R.id.btnViewReply);
        btnReturnFromRoom = findViewById(R.id.btnReturnFromRoom);
        btnAddPost = findViewById(R.id.btnAddPost);
        tvRoomTopic = findViewById(R.id.tvRoomTopic);

        tvRoomTopic.setText("Topic: Food");





        //for recycler view
        rvPosts = findViewById(R.id.rvPosts);
        // 1 Create layout for one row in the list
        // 2 Create the adapter
        // 3 Create the data source
        // 4 Set the adapter on the recycler view
        // 5 Set the layout manager on the recycler view
        queryPosts();



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

        btnAddPost.setOnClickListener(new View.OnClickListener() {
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

    // Get the posts from the data
    private void queryPosts() {
        ParseQuery<Post> query = new ParseQuery<>("Post");
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                PostAdapter adapter = new PostAdapter(this, objects);
                rvPosts.setLayoutManager(new LinearLayoutManager(this));
                rvPosts.setAdapter(adapter);
                Log.d(TAG, "New adapter. Objects: " + objects);
                return;
            } else {
                Log.e(TAG, "Something is wrong with querying data!");
            }
        });
    }
}

package com.example.gdsc_project_app;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.CommentsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private Button btnBack;
    private Button btnAddComment;
    private RecyclerView rvComments;
    private CommentsAdapter adapter;
    private List<Comment> allComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_comment));

        // linking the private variables to the elements in the xml files
        btnBack = findViewById(R.id.btnBack);
        btnAddComment = findViewById(R.id.btnAddComment);
        rvComments = findViewById(R.id.rvComments);

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
//                goAddCommentActivity();
//            }
//        });

        allComments = new ArrayList<>();
        adapter = new CommentsAdapter(this, allComments);

        // Steps to use the recycler view
        // 0. create layout for one row in the list
        // 1. create the adapter
        // 2. create the data source
        // 3. set the adapter on the recycler view
        rvComments.setAdapter(adapter);
        // 4. set the layout manger on the recycler view
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        queryComment();


    }

    private void queryComment() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_COMMENT_USER_ID);
        // TODO:
        //query.whereEqualTo(Comment.KEY_COMMENT_POST_ID, );
        query.orderByDescending(Comment.KEY_COMMENT_TIME);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting comments", e);
                    return;
                }
                for (Comment comment : comments){
                    Log.i(TAG, "Comment: " + comment.getCommentDescription() + ", username: " + comment.getCommentUserName());
                }
                allComments.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });
    }

    // create onclicklistener for the button to the change the activity
    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        Log.i(TAG, "Going into MainActivity");
        startActivity(i);
    }

//    private void goAddCommentActivity(){
//        Intent i = new Intent(this, MainActivity.class);
//        Log.i(TAG, "Going into AddCommentActivity");
//        startActivity(i);
//        // startActivity(temp);
//    }
}

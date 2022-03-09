package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.CommentsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private Button btnBack;
    private Button btnAddComment;
    private RecyclerView rvComments;
    private CommentsAdapter adapter;

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
//                goAddCommentActivity();
//            }
//        });

        queryComment();


    }

    private void queryComment() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_COMMENT_USER_ID);

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

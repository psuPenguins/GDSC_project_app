package com.example.gdsc_project_app;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private EditText etNewComment;
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
        etNewComment = findViewById(R.id.etNewComment);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick Back button");
                goRoomActivity();
            }
        });

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick btnAddComment button");
                String newComment = etNewComment.getText().toString();
                //Don't enter if blank
                if (newComment.length() != 0){
                    AddCommentActivity();
                    Log.i(TAG, "New Comment Added: "+newComment);
                }
                else{
                    Toast.makeText(CommentActivity.this, Html.fromHtml("<font color='#5e5e5e'><b>" + "Comment cannot be empty!" + "</b></font>"), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Empty Comment Submitted");
                }
            }
        });

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
    private void goRoomActivity(){
        Intent i = new Intent(this, RoomActivity.class);
        Log.i(TAG, "Going into RoomActivity");
        startActivity(i);
    }

    // onclicking add comment
    private void AddCommentActivity(){
        Log.i(TAG, "Adding Comment in CommentActivity");
        //TODO: Work with database to add comment,
        //
        Intent i = new Intent(this, CommentActivity.class);
        Log.i(TAG, "Refreshing CommentActivity");
        startActivity(i);
    }

}

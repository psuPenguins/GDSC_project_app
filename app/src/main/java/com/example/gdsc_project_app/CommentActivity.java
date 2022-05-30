package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.MainActivity.TAG;
import static com.example.gdsc_project_app.Post.KEY_QUESTIONID;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.CommentsAdapter;
import com.example.gdsc_project_app.adapters.PostAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private Button btnBack;
    private Button btnAddComment;
    private EditText etNewComment;
    private RecyclerView rvCurrentPost;
    private RecyclerView rvComments;
//    private PostAdapter postAdapter;
    private CommentsAdapter commentsAdapter;
    private List<Comment> allComments;
//    private List<Post> selectedPost;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");
    private DatabaseReference roomsRef = database.getReference("Rooms");
    private DatabaseReference postsRef = database.getReference("Posts");
    private DatabaseReference commentsRef = database.getReference("Comments");
    private String commentID;
    private ArrayList<String> commentIDs;
    private ArrayList<FBComment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_comment));


        // linking the private variables to the elements in the xml files
        btnBack = findViewById(R.id.btnBack);
        btnAddComment = findViewById(R.id.btnAddComment);
        rvCurrentPost = findViewById(R.id.rvCurrentPost);
        rvComments = findViewById(R.id.rvComments);
        etNewComment = findViewById(R.id.etNewComment);

        comments = new ArrayList<FBComment>();
        commentIDs = new ArrayList<String>();

        //get current post into layout
        querySelectedPost();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick make post button");
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

        queryComment();


    }

    private void queryComment() {
        rvComments.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        FirebaseRecyclerOptions<FBComment> options = new FirebaseRecyclerOptions.Builder<FBComment>().setQuery(roomsRef.child(RoomFragment.currentRoomID).child("posts").child(PostAdapter.currentPostId).child("comments"), FBComment.class).build();
        CommentsAdapter adapter = new CommentsAdapter(options);
        rvComments.setAdapter(adapter);
        adapter.startListening();
    }

    // onclicking add comment
    private void AddCommentActivity(){
        Log.i(TAG, "Adding Comment in CommentActivity");
        //TODO: Work with database to add comment,
        saveComment(etNewComment.getText().toString(), ParseUser.getCurrentUser());
        Intent i = new Intent(this, CommentActivity.class);
        Log.i(TAG, "Refreshing CommentActivity");
        startActivity(i);
    }

    // get selected post
    private void querySelectedPost() {
//        ParseQuery<Post> query = new ParseQuery<>("Post");
//        query.whereEqualTo("objectId", PostAdapter.currentPostId);
//        Log.i("SelectedPost", "currentPost:"+PostAdapter.currentPostId+" postID:"+ "objectId");
//        query.findInBackground((objects, e) -> {
//            if (e == null) {
//                PostAdapter adapter = new PostAdapter(this, objects);
//                rvCurrentPost.setLayoutManager(new LinearLayoutManager(this));
//                rvCurrentPost.setAdapter(adapter);
//                Log.d(TAG, "SELECTED POST: " + objects);
//                return;
//            } else {
//                Log.e(TAG, "Something is wrong with querying current post data!");
//            }
//        });
    }

    private void saveComment(String description, ParseUser currentUser) {
        Comment comment = new Comment();
        comment.setDescription(description);
        comment.setUserID(currentUser.getObjectId());
        comment.setUsername(currentUser.getUsername());
//        comment.setPostID(PostAdapter.currentPostId);
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving!", e);
                    Toast.makeText(CommentActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                btnAddComment.setText("");
            }
        });
    }

    // declaring transition functions
    private void goRoomActivity(){
        Intent i = new Intent(this, MainActivity.class);
        Log.i(TAG, "Going into RoomActivity");
        startActivity(i);
    }

}

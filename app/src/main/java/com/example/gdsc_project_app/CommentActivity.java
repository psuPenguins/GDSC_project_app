package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.MainActivity.TAG;
import static com.example.gdsc_project_app.Post.KEY_QUESTIONID;
import static com.example.gdsc_project_app.RoomFragment.currentRoomID;
import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdsc_project_app.adapters.CommentsAdapter;
import com.example.gdsc_project_app.adapters.PostAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.UUID;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private ImageButton btnBack;
    private Button btnAddComment;
    private EditText etNewComment;
    private RecyclerView rvCurrentPost;
    private RecyclerView rvComments;
//    private PostAdapter postAdapter;
    private CommentsAdapter commentsAdapter;
    private List<Comment> allComments;
//    private List<Post> selectedPost;

    private Timestamp timestamp = Timestamp.now();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");
    private DatabaseReference roomsRef = database.getReference("Rooms");
    private DatabaseReference postsRef = database.getReference("Posts");
    private DatabaseReference commentsRef = database.getReference("Comments");
    private String commentID;
    private ArrayList<String> commentIDs;
    private ArrayList<FBComment> comments;
    private FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
    private String user;
    private String pfp;


    FirebaseRecyclerOptions<FBPost> newOption;

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
        FirebaseRecyclerOptions<FBComment> options = new FirebaseRecyclerOptions.Builder<FBComment>().setQuery(roomsRef.child(currentRoomID).child("posts").child(PostAdapter.currentPostId).child("comments").orderByChild("timestamp"), FBComment.class).build();
        CommentsAdapter adapter = new CommentsAdapter(options);
        rvComments.setAdapter(adapter);
        adapter.startListening();
    }

    // onclicking add comment
    private void AddCommentActivity(){
        Log.i(TAG, "Adding Comment in CommentActivity");
        //TODO: Work with database to add comment,
        saveComment(etNewComment.getText().toString(), usersRef);
        Intent i = new Intent(this, CommentActivity.class);
        Log.i(TAG, "Refreshing CommentActivity");
        startActivity(i);
    }

    // get selected post
    private void querySelectedPost() {
        rvCurrentPost.setLayoutManager(new LinearLayoutManager(this));
        Log.i(TAG, "roomID is: " + RoomFragment.currentRoomID);
        Log.i(TAG, "postID is: " + PostAdapter.currentPostId);

        FirebaseRecyclerOptions<FBPost> options = new FirebaseRecyclerOptions.Builder<FBPost>().setQuery(roomsRef.child(RoomFragment.currentRoomID.toString()).child("posts").orderByKey().equalTo(PostAdapter.currentPostId), FBPost.class).build();

        PostAdapter adapter = new PostAdapter(options);
        rvCurrentPost.setAdapter(adapter);
        adapter.startListening();
    }

    private void saveComment(String description, DatabaseReference usersRef) {
        // TODO: save in Room as well.
        usersRef.child(userID.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FBUser user = dataSnapshot.getValue(FBUser.class);
                FBComment comment = new FBComment(
                        userID.getUid(),
                        user.username,
                        PostAdapter.currentPostId,
                        description,
                        0,
                        0,
                        UUID.randomUUID().toString(),
                        timestamp.getSeconds()
                );
                roomsRef.child(currentRoomID).child("posts").child(PostAdapter.currentPostId).child("comments").child(comment.commentID).setValue(comment);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG,"The read failed for user: " + databaseError.getCode());
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

package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import java.util.List;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "PostActivity";

    // create private variables (btn, tv..)
    private Button btnReturnFromPost;
    private Button btnMakePost;
    private TextView tvPostingName;
    private TextInputEditText tiContentInput;
    private TextInputLayout tiContentLayout;
    private ImageView ivPostUserPic;

    // firebase variables
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference postsRef = database.getReference("Posts");
    private DatabaseReference roomsRef = database.getReference("Rooms");
    private FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference usersRef = database.getReference("Users");
    private String user;
    private String pfp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_post));
        Log.i(TAG, "I'm in PostActivity");

        // link the private variables to the elements in the xml files
        btnReturnFromPost=findViewById(R.id.btnReturnFromPost);
        btnMakePost=findViewById(R.id.btnMakePost);
        tvPostingName=findViewById(R.id.tvPostingName);
        tiContentInput=findViewById(R.id.tiContentInput);
        tiContentLayout=findViewById(R.id.tiContentLayout);
        ivPostUserPic=findViewById(R.id.ivPostUserPic);

        // Set current posting user name and image
        usersRef.child(userID.getUid()).child("username").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                user = String.valueOf(task.getResult().getValue());
                tvPostingName.setText(user);
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });
        usersRef.child(userID.getUid()).child("profileImage").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                pfp = String.valueOf(task.getResult().getValue());
                Glide.with(this).load(pfp).into(ivPostUserPic);
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });

        btnReturnFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick make post button");
                goRoomActivity();
            }
        });

        btnMakePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick make post button");
                // TODO: update the post
                // set description
                String description = tiContentInput.getText().toString();
                if(description.isEmpty()) {
                    Toast.makeText(PostActivity.this, "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(description, usersRef);

                // reload the room activity
                goRoomActivity();
            }
        });
    }

    private void savePost(String description, DatabaseReference usersRef) {
        // TODO: save in Room as well.
        usersRef.child(userID.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FBUser user = dataSnapshot.getValue(FBUser.class);
                FBPost post = new FBPost(
                        userID.getUid(),
                        user.username,
                        user.roomID,
                        description,
                        0,
                        0,
                        UUID.randomUUID().toString()
                );
                roomsRef.child(RoomFragment.currentRoomID).child("posts").child(post.getPostID()).setValue(post);
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


    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }
}

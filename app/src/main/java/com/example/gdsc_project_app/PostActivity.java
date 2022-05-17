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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "PostActivity";

    // create private variables (btn, tv..)
    private Button btnReturnFromPost;
    private Button btnMakePost;
    private TextView tvPostingName;
    private TextInputEditText tiContentInput;
    private TextInputLayout tiContentLayout;
    private ImageView ivPostUserPic;

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
        tvPostingName.setText(ParseUser.getCurrentUser().getUsername());
        Glide.with(this).load(ParseUser.getCurrentUser().getParseFile(KEY_USER_PROFILE_IMAGE).getUrl()).into(ivPostUserPic);

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
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser);

                // reload the room activity
                goRoomActivity();
            }
        });
    }

    private void savePost(String description, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(description);
        post.setUserID(currentUser.getObjectId());
        post.setUsername(currentUser.getUsername());
        post.setQuestionID(currentUser.getString("questionID"));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving!", e);
                    Toast.makeText(PostActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                tiContentInput.setText("");
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

package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
                goRoomActivity();
            }
        });
    }



    // declaring transition functions
    private void goRoomActivity(){
        Intent i = new Intent(this, RoomActivity.class);
        Log.i(TAG, "Going into RoomActivity");
        startActivity(i);
    }
}

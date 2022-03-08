package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "PostActivity";

    // create private variables (btn, tv..)
    private Button btnMakePost;
    private TextView tvPostingName;
    private TextInputEditText tiContentInput;
    private TextInputLayout tiContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_post));
        Log.i(TAG, "I'm in PostActivity");

        // link the private variables to the elements in the xml files
        btnMakePost=findViewById(R.id.btnMakePost);
        tvPostingName=findViewById(R.id.tvPostingName);
        tiContentInput=findViewById(R.id.tiContentInput);
        tiContentLayout=findViewById(R.id.tiContentLayout);

        tvPostingName.setText("Doggo371");

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

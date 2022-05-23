package com.example.gdsc_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    ImageButton swipeFragmentBtn, roomFragmentBtn, profileFragmentBtn;

    FirebaseUser currentUser;
    String currentUserID;
    String currentRoomID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "I'm in MainActivity");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG, "MA Get current userinstance");
        currentUserID = currentUser.getUid();
        Log.i(TAG, "MA CurrentUID:"+currentUserID);

        swipeFragmentBtn = findViewById(R.id.btnSwipe);
        roomFragmentBtn = findViewById(R.id.btnRoom);
        profileFragmentBtn = findViewById(R.id.btnProfile);



        // default fragment
        replaceFragment(new RoomFragment());

        swipeFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new SwipeFragment());

            }
        });

        roomFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new RoomFragment());

            }

        });

        profileFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ProfileFragment());

            }

        });

    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment_view,fragment);
        fragmentTransaction.commit();

    }
}
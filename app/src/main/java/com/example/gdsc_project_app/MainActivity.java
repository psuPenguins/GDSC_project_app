package com.example.gdsc_project_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    ImageButton swipeFragmentBtn, roomFragmentBtn, profileFragmentBtn;

    FirebaseUser currentUser;
    String currentUserUID;
    String currentRoomID;
    FBUser user;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "I'm in MainActivity");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG, "MA Get current userinstance");
        currentUserUID = currentUser.getUid();
        Log.i(TAG, "MA CurrentUID:"+currentUserUID);
        usersRef.child("roomID").equalTo(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d(TAG, "Current roomID:"+ (String.valueOf(task.getResult().getValue())));
                    currentRoomID = String.valueOf(task.getResult().getValue());
                }
            }
        });


        swipeFragmentBtn = findViewById(R.id.btnSwipe);
        roomFragmentBtn = findViewById(R.id.btnRoom);
        profileFragmentBtn = findViewById(R.id.btnProfile);



        // default fragment
        if (currentRoomID != null) {
            replaceFragment(new RoomFragment());
        }
        else {
            replaceFragment(new ProfileFragment());
        }

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
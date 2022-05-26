package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";
    public Button btnLogOut;
    public ImageView ivProfilePic;
    public TextView tvProfileUsername;
    //FBUser user;
    FirebaseUser currentUser;
    String currentUserUID;
    String currentUsername;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");

    public ProfileFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "I'm in ProfileFragment");
        super.onCreate(savedInstanceState);

        //Getting User
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUID = currentUser.getUid();
        Log.i(TAG, "PF CurrentUID:"+currentUserUID);
        //Getting Username
        usersRef.child(currentUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FBUser user = dataSnapshot.getValue(FBUser.class);
                //setting username
                tvProfileUsername.setText(user.username);
                //setting profilepic
                Log.i(TAG, "ProfileImageURL: "+user.profileImage);
                Glide.with(getActivity()).load(user.profileImage).into(ivProfilePic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG,"The read failed for user: " + databaseError.getCode());
            }
        });

        btnLogOut = view.findViewById(R.id.btnLogOut);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvProfileUsername = view.findViewById(R.id.tvProfileUsername);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Logout button");
                FirebaseAuth.getInstance().signOut();
                goLoginActivity();
            }
        });

    }

    private void goLoginActivity(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

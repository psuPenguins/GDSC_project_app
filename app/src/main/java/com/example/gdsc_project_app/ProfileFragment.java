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

import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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

    public ProfileFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "I'm in ProfileFragment");
        super.onCreate(savedInstanceState);

        btnLogOut = view.findViewById(R.id.btnLogOut);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvProfileUsername = view.findViewById(R.id.tvProfileUsername);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Logout button");
                goLoginActivity();
            }
        });

        // Setting the shown user pic and username
        Glide.with(getActivity()).load(ParseUser.getCurrentUser().getParseFile(KEY_USER_PROFILE_IMAGE).getUrl()).into(ivProfilePic);
        tvProfileUsername.setText(ParseUser.getCurrentUser().getUsername());

    }

    private void goLoginActivity(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

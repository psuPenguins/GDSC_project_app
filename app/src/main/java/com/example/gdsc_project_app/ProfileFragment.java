package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_room, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "I'm in ProfileFragment");
        super.onCreate(savedInstanceState);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Logout button");
                goLoginActivity();
            }
        });
    }

    private void goLoginActivity(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        Log.i(TAG, "Going into LoginActivity");
        startActivity(i);
    }
}

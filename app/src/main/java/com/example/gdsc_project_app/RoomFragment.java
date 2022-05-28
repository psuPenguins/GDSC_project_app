package com.example.gdsc_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.PostAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RoomFragment extends Fragment {
    public static final String TAG = "RoomFragment";

    //TODO: create private variables (btn, tv..)
    private RecyclerView rvPosts;
    private FloatingActionButton btnAddPost;
    private TextView tvRoomTopic;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");
    private DatabaseReference roomsRef = database.getReference("Rooms");
    private DatabaseReference postsRef = database.getReference("Posts");
    private String postID;
    private ArrayList<String> postIDs;
    private ArrayList<FBPost> posts;

    private String currentUserID;
    private String currentRoomID;

    public RoomFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_room, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "I'm in RoomFragment");

        // link the private variables to the elements in the xml files
        btnAddPost = view.findViewById(R.id.btnAddPost);
        tvRoomTopic = view.findViewById(R.id.tvRoomTopic);
        //for recycler view
        rvPosts = view.findViewById(R.id.rvPosts);

        posts = new ArrayList<FBPost>();


        postIDs = new ArrayList<String>();

        queryPostIDs();


        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Log.i(TAG, "onClick post button");
                goPostActivity();
            }
        });
    }


    private void goPostActivity(){
        Intent i = new Intent(getContext(), PostActivity.class);
        Log.i(TAG, "Going into PostActivity");
        startActivity(i);
    }

    // Get the posts from the data
    private void queryPostIDs() {
        // get the room that the user is in
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usersRef.child(currentUserID).child("roomID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentRoomID = (String) snapshot.getValue();

                Log.i(TAG, "ROOM ID= "+currentRoomID);
                roomsRef.child(currentRoomID).child("postIDs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postIDs.clear();
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            postID = childSnapshot.getKey();
                            Log.i(TAG, "POST ID= " + postID);
                            postIDs.add(postID);
                        }
                        Log.i(TAG, "postIDs" + postIDs);
                        postsRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                posts.clear();
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    FBPost post = childSnapshot.getValue(FBPost.class);
                                    posts.add(post);
                                }
                                Log.i(TAG, "posts" + posts);

                                rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
                                FirebaseRecyclerOptions<FBPost> options = new FirebaseRecyclerOptions.Builder<FBPost>().setQuery(postsRef, FBPost.class).build();
                                PostAdapter adapter = new PostAdapter(options);
                                rvPosts.setAdapter(adapter);
                                adapter.startListening();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("firebase", "Error getting data");
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("firebase", "Error getting data");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}


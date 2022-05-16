package com.example.gdsc_project_app;

import static com.example.gdsc_project_app.Post.KEY_QUESTIONID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class RoomFragment extends Fragment {
    public static final String TAG = "RoomFragment";

    //TODO: create private variables (btn, tv..)
    private RecyclerView rvPosts;
    private FloatingActionButton btnAddPost;
    private TextView tvRoomTopic;

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
        // 1 Create layout for one row in the list
        // 2 Create the adapter
        // 3 Create the data source
        // 4 Set the adapter on the recycler view
        // 5 Set the layout manager on the recycler view
        queryTopic();
        queryPosts();



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
    private void queryPosts() {
        ParseQuery<Post> query = new ParseQuery<>("Post");
        query.whereEqualTo(Post.KEY_QUESTIONID, ParseUser.getCurrentUser().getString(KEY_QUESTIONID));
        query.orderByDescending("likeCount");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                PostAdapter adapter = new PostAdapter(getContext(), objects);
                rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
                rvPosts.setAdapter(adapter);
                Log.d(TAG, "New adapter. Objects: " + objects);
                return;
            } else {
                Log.e(TAG, "Something is wrong with querying data!");
            }
        });
    }

    // set the topic text to the right topic
    private void queryTopic(){
        ParseQuery<Question> query = new ParseQuery<Question>("Question");
        query.whereEqualTo(Question.KEY_QUESTIONID, ParseUser.getCurrentUser().getString(KEY_QUESTIONID));
        Log.i(TAG, Question.KEY_QUESTIONID +" and "+ ParseUser.getCurrentUser().getString(KEY_QUESTIONID));
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> dbQs, ParseException e) {
                if (e == null) {
                    Log.i(TAG, "size: " + dbQs.size());
                    tvRoomTopic.setText("Topic: " + dbQs.get(0).getTopic());
                } else {
                    Log.e(TAG,"ERROR getting data");
                }
            }
        });
    }
}

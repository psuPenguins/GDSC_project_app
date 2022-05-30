package com.example.gdsc_project_app.adapters;

import static com.example.gdsc_project_app.RoomFragment.TAG;
import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.Comment;
import com.example.gdsc_project_app.FBComment;
import com.example.gdsc_project_app.FBPost;
import com.example.gdsc_project_app.FBUser;
import com.example.gdsc_project_app.MainActivity;
import com.example.gdsc_project_app.Post;
import com.example.gdsc_project_app.R;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

public class CommentsAdapter extends FirebaseRecyclerAdapter<FBComment, CommentsAdapter.commentsViewholder> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    DatabaseReference postRef = database.getReference("Posts");

    public CommentsAdapter(@NonNull FirebaseRecyclerOptions<FBComment> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull commentsViewholder holder, int position, @NonNull FBComment comment) {
        holder.tvUsername.setText(comment.username.toString());
        holder.tvCommentText.setText(comment.description.toString());
        holder.tvLikeAmount.setText(comment.likeCount.toString());
        holder.tvDislikeAmount.setText(comment.dislikeCount.toString());

        ArrayList<String> userIDs = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String userID = snapshot.getKey().toString();
                    FBUser user = snapshot.getValue(FBUser.class);
                    userIDs.add(userID);
                    urls.add(user.profileImage.toString());
                }
                Log.i(MainActivity.TAG, "userIDs: "+userIDs);
                Log.i(MainActivity.TAG, "urls: "+urls);
                for (int i=0; i<userIDs.size(); i++){
                    if(userIDs.get(i).equals(comment.userID)){
                        FBUser user = dataSnapshot.getValue(FBUser.class);
                        Log.i(MainActivity.TAG, "ProfileImageURL: "+urls.get(i));
                        Glide.with(holder.ivProfilePic.getContext()).load(urls.get(i)).into(holder.ivProfilePic);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(MainActivity.TAG,"The read failed for user: " + databaseError.getCode());
            }
        });
    }


    @NonNull
    @Override
    public commentsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentsAdapter.commentsViewholder(view);
    }

    class commentsViewholder extends RecyclerView.ViewHolder{
        private ImageView ivProfilePic;
        private TextView tvUsername;
        private TextView tvTime;
        private TextView tvCommentText;
        private TextView tvLikeAmount;
        private TextView tvDislikeAmount;
        private RadioGroup rgVote;

        public commentsViewholder(@NonNull View itemView){
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
            rgVote = itemView.findViewById(R.id.rgVote);
            tvLikeAmount = itemView.findViewById(R.id.tvLikeAmount);
            tvDislikeAmount = itemView.findViewById(R.id.tvDislikeAmount);
        }
    }
}
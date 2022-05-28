package com.example.gdsc_project_app.adapters;

import static com.example.gdsc_project_app.MainActivity.TAG;
import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdsc_project_app.CommentActivity;
import com.example.gdsc_project_app.FBPost;
import com.example.gdsc_project_app.FBUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import com.example.gdsc_project_app.Post;
import com.example.gdsc_project_app.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<FBPost, PostAdapter.postsViewholder>{
   final FirebaseDatabase database = FirebaseDatabase.getInstance();
   DatabaseReference usersRef = database.getReference("Users");

   public PostAdapter(@NonNull FirebaseRecyclerOptions<FBPost> options){
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull postsViewholder holder, int position, @NonNull FBPost post){
      holder.UserID.setText(post.getUsername());
      holder.Content.setText(post.getDescription());
      holder.LikeAmount.setText(post.getLikeCount().toString());
      holder.DislikeAmount.setText(post.getDislikeCount().toString());

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
            Log.i(TAG, "userIDs: "+userIDs);
            Log.i(TAG, "urls: "+urls);
            for (int i=0; i<userIDs.size(); i++){
               if(userIDs.get(i).equals(post.getUserID())){
                  FBUser user = dataSnapshot.getValue(FBUser.class);
                  Log.i(TAG, "ProfileImageURL: "+urls.get(i));
                  Glide.with(holder.Content.getContext()).load(urls.get(i)).into(holder.ivUserPic);
               }
            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
            Log.i(TAG,"The read failed for user: " + databaseError.getCode());
         }
      });
   }

   @NonNull
   @Override
   public postsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
      return new PostAdapter.postsViewholder(view);
   }


   class postsViewholder extends RecyclerView.ViewHolder{
      TextView UserID;
      TextView Content;
      Button btnViewReply;
      ImageView ivUserPic;
      TextView LikeAmount;
      TextView DislikeAmount;
      RadioGroup rgVote;

      public postsViewholder(@NonNull View itemView){
         super(itemView);

         UserID = itemView.findViewById(R.id.tvUsername);
         Content = itemView.findViewById(R.id.tvPostContent);
         btnViewReply = itemView.findViewById(R.id.btnViewReply);
         LikeAmount = itemView.findViewById(R.id.tvLikeAmount);
         DislikeAmount = itemView.findViewById(R.id.tvDislikeAmount);
         ivUserPic = itemView.findViewById(R.id.ivUserPic);
         rgVote = itemView.findViewById(R.id.rgVote);
      }
   }
}
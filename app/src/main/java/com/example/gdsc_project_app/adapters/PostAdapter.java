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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdsc_project_app.CommentActivity;
import com.example.gdsc_project_app.FBPost;
import com.example.gdsc_project_app.FBUser;
import com.example.gdsc_project_app.RoomFragment;
import com.google.firebase.auth.FirebaseAuth;
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

public class PostAdapter extends FirebaseRecyclerAdapter<FBPost, PostAdapter.postsViewholder> {
   final FirebaseDatabase database = FirebaseDatabase.getInstance();
   DatabaseReference usersRef = database.getReference("Users");
   DatabaseReference postsRef = database.getReference("Rooms").child(RoomFragment.currentRoomID).child("posts");

   public static String currentPostId;
   private String currentUserID;

   public PostAdapter(@NonNull FirebaseRecyclerOptions<FBPost> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull postsViewholder holder, int position, @NonNull FBPost post) {
      holder.UserID.setText(post.getUsername());
      holder.Content.setText(post.getDescription());
      holder.LikeAmount.setText(post.getLikeCount().toString());
      holder.DislikeAmount.setText(post.getDislikeCount().toString());

      currentPostId = post.getPostID();
      currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
      ArrayList<String> userIDs = new ArrayList<>();
      ArrayList<String> urls = new ArrayList<>();

      getLikedOrDisliked(holder);

      usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
               String userID = snapshot.getKey().toString();
               FBUser user = snapshot.getValue(FBUser.class);
               userIDs.add(userID);
               urls.add(user.profileImage.toString());
            }
            Log.i(TAG, "userIDs: " + userIDs);
            Log.i(TAG, "urls: " + urls);
            for (int i = 0; i < userIDs.size(); i++) {
               if (userIDs.get(i).equals(post.getUserID())) {
                  FBUser user = dataSnapshot.getValue(FBUser.class);
                  Log.i(TAG, "ProfileImageURL: " + urls.get(i));
                  Glide.with(holder.ivUserPic.getContext()).load(urls.get(i)).into(holder.ivUserPic);
               }
            }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
            Log.i(TAG, "The read failed for user: " + databaseError.getCode());
         }
      });

      holder.btnViewReply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent i = new Intent(holder.Content.getContext(), CommentActivity.class);
            currentPostId = post.getPostID();
            holder.Content.getContext().startActivity(i);
         }
      });


      holder.rgVote.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup group, int checkedID) {
            currentPostId = post.getPostID();
            // checkedID is the RadioButton selected
            if (checkedID == R.id.rbLike) {
               Log.i(TAG, "onClick like button");
               addOneLike(post);
               post.liked = true;
               if (post.disliked == true) {
                  post.disliked = false;
                  minusOneDislike(post);
               }
               holder.LikeAmount.setText(post.getLikeCount().toString());
               holder.DislikeAmount.setText(post.getDislikeCount().toString());
            }
            if (checkedID == R.id.rbUnlike) {
               Log.i(TAG, "onClick dislike button");
               addOneDislike(post);
               post.disliked = true;

               if (post.liked == true) {
                  post.liked = false;
                  minusOneLike(post);
               }
               holder.DislikeAmount.setText(post.getDislikeCount().toString());
               holder.LikeAmount.setText(post.getLikeCount().toString());
            }
         }
      });
   }

   @NonNull
   @Override
   public postsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
      return new PostAdapter.postsViewholder(view);
   }


   class postsViewholder extends RecyclerView.ViewHolder {
      TextView UserID;
      TextView Content;
      Button btnViewReply;
      ImageView ivUserPic;
      TextView LikeAmount;
      TextView DislikeAmount;
      RadioGroup rgVote;
      RadioButton rbDislike;
      RadioButton rbLike;

      public postsViewholder(@NonNull View itemView) {
         super(itemView);

         UserID = itemView.findViewById(R.id.tvUsername);
         Content = itemView.findViewById(R.id.tvPostContent);
         btnViewReply = itemView.findViewById(R.id.btnViewReply);
         LikeAmount = itemView.findViewById(R.id.tvLikeAmount);
         DislikeAmount = itemView.findViewById(R.id.tvDislikeAmount);
         ivUserPic = itemView.findViewById(R.id.ivUserPic);
         rgVote = itemView.findViewById(R.id.rgVote);
         rbLike = itemView.findViewById(R.id.rbLike);
         rbDislike = itemView.findViewById(R.id.rbUnlike);
      }
   }

   // updates the likeCount in database
   private void addOneLike(FBPost post) {
      Integer newLikeCount = post.getLikeCount() + 1;
      post.setLikeCount(newLikeCount);
//      postsRef.child(currentPostId).child("likeCount").setValue(newLikeCount);
//      postsRef.child(currentPostId).child("likedUserIDs").child(currentUserID).setValue(true);
   }

   private void minusOneLike(FBPost post) {
      Integer newLikeCount = post.getLikeCount() - 1;
      post.setLikeCount(newLikeCount);
//      postsRef.child(currentPostId).child("likeCount").setValue(newLikeCount);
//      postsRef.child(currentPostId).child("likedUserIDs").child(currentUserID).setValue(false);
   }

   // updates the dislikeCount in database
   private void addOneDislike(FBPost post) {
      Integer newDislikeCount = post.getDislikeCount() + 1;
      post.setDislikeCount(newDislikeCount);
//      postsRef.child(currentPostId).child("dislikeCount").setValue(newDislikeCount);
//      postsRef.child(currentPostId).child("dislikedUserIDs").child(currentUserID).setValue(true);
   }

   private void minusOneDislike(FBPost post) {
      Integer newDislikeCount = post.getDislikeCount() - 1;
      post.setDislikeCount(newDislikeCount);
//      postsRef.child(currentPostId).child("dislikeCount").setValue(newDislikeCount);
//      postsRef.child(currentPostId).child("dislikedUserIDs").child(currentUserID).setValue(false);
   }

   private void getLikedOrDisliked(@NonNull postsViewholder holder) {
      postsRef.child(currentPostId).child("likedUserIDs").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
               if (snapshot.getValue(Boolean.class)) {
                  holder.rgVote.check(holder.rbLike.getId());
               }
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });


      postsRef.child(currentPostId).child("dislikedUserIDs").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
               if (snapshot.getValue(Boolean.class)) {
                  holder.rgVote.check(holder.rbDislike.getId());
               }
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }


}
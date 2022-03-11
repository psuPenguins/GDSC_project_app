package com.example.gdsc_project_app.adapters;

import static com.example.gdsc_project_app.MainActivity.TAG;
import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.annotation.SuppressLint;
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
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import com.example.gdsc_project_app.Post;
import com.example.gdsc_project_app.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PostAdapter extends RecyclerView.Adapter<PostHolder>{
   private Context context;
   private List<Post> list;

   public static String currentPostId;

   public PostAdapter(Context context, List<Post> list) {
      this.list = list;
      this.context = context;
   }

   public void clearList(){
      list = new ArrayList<>();
      notifyDataSetChanged();
   }

   @NonNull
   @Override
   public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
      return new PostHolder(v);
   }

   @SuppressLint("SetText18n")
   @Override
   public void onBindViewHolder(@NonNull PostHolder holder, int position) {
      Post post = list.get(position);
      holder.UserID.setText(post.getUsername());
      holder.Content.setText(post.getDescription());
      holder.LikeAmount.setText(post.getLikeCount().toString());
      holder.DislikeAmount.setText(post.getDislikeCount().toString());
      holder.btnViewReply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent i = new Intent(context, CommentActivity.class);
            currentPostId = post.getPostID();
            context.startActivity(i);
         }
      });

      holder.rgVote.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup group, int checkedID) {
            // checkedID is the RadioButton selected
            if (checkedID == R.id.rbLike){
               Log.i(TAG, "onClick like button");
            }
            if (checkedID == R.id.rbDislike){
               Log.i(TAG, "onClick dislike button");
            }
         }
      });


      ParseQuery<ParseUser> query = ParseUser.getQuery();
      List<ParseUser> allUsers = new ArrayList<>();
      query.findInBackground(new FindCallback<ParseUser>() {
         @Override
         public void done(List<ParseUser> users, ParseException e) {
            if(e != null){
               return;
            }
            allUsers.addAll(users);
            for(ParseUser user:allUsers){
               if(user.getObjectId().equals(post.getUserID())){
                  Glide.with(context).load(user.getParseFile(KEY_USER_PROFILE_IMAGE).getUrl()).into(holder.ivUserPic);
               }
            }
         }
      });


   }

   @Override
   public int getItemCount() {
      return list.size();
   }
}


class PostHolder extends RecyclerView.ViewHolder {

   TextView UserID;
   TextView Content;
   Button btnViewReply;
   ImageView ivUserPic;
   TextView LikeAmount;
   TextView DislikeAmount;
   RadioGroup rgVote;

   public PostHolder(@NonNull View itemView) {
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
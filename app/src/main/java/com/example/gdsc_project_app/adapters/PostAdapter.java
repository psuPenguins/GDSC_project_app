package com.example.gdsc_project_app.adapters;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdsc_project_app.CommentActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import com.example.gdsc_project_app.Post;
import com.example.gdsc_project_app.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PostAdapter extends RecyclerView.Adapter<PostHolder>{
   private Context context;
   private List<Post> list;

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
      holder.btnViewReply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //Log.i(TAG, "onClick view reply button");
            Intent i = new Intent(context, CommentActivity.class);
            //Log.i(TAG, "Going into RoomActivity");
            context.startActivity(i);
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

   public PostHolder(@NonNull View itemView) {
      super(itemView);
      UserID = itemView.findViewById(R.id.tvUsername);
      Content = itemView.findViewById(R.id.tvPostContent);
      btnViewReply = itemView.findViewById(R.id.btnViewReply);
      ivUserPic = itemView.findViewById(R.id.ivUserPic);
   }
}
package com.example.gdsc_project_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import com.example.gdsc_project_app.Post;
import com.example.gdsc_project_app.R;

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
      holder.UserID.setText(post.getString("userID"));
      holder.Content.setText(post.getDescription());
   }


   @Override
   public int getItemCount() {
      return list.size();
   }
}


class PostHolder extends RecyclerView.ViewHolder {

   TextView UserID;
   TextView Content;

   public PostHolder(@NonNull View itemView) {
      super(itemView);
      UserID = itemView.findViewById(R.id.tvUsername);
      Content = itemView.findViewById(R.id.tvPostContent);
   }
}
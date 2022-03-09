package com.example.gdsc_project_app.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.R;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Pass in the context and list of Comments

    // For each row, inflate the layout for the comment

    // Bind values based on te position of the element

    // Define a viewHolder
    public class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePic;
        TextView tvUsername;
        TextView tvTime;
        TextView tvCommentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);


        }
    }
}

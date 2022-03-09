package com.example.gdsc_project_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.Comment;
import com.example.gdsc_project_app.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{

    Context context;
    List<Comment> comments;

    // Pass in the context and list of Comments
    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    // For each row, inflate the layout for the comment
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    // Bind values based on te position of the element
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Get the data at position
        Comment comment = comments.get(position);

        // Bind the comment with view holder
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }




    // Define a viewHolder
    public class CommentViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivProfilePic;
        private TextView tvUsername;
        private TextView tvTime;
        private TextView tvCommentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
        }

        public void bind(Comment comment) {
            // TODO: set the profile Image
            //ivProfilePic.setImage;
            tvUsername.setText(comment.getCommentUserName());
            tvTime.setText(comment.getCommentTime());
            tvCommentText.setText(comment.getCommentDescription());

        }
    }
}

package com.example.gdsc_project_app.adapters;

import static com.example.gdsc_project_app.User.KEY_USER_PROFILE_IMAGE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.Comment;
import com.example.gdsc_project_app.R;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
        private RadioButton rbPromote;
        private RadioButton rbDemote;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
            rbPromote = itemView.findViewById(R.id.rbLike);
            rbDemote = itemView.findViewById(R.id.rbUnlike);
        }

        public void bind(Comment comment) {
            // TODO: set the profile Image
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
                        if(user.getObjectId().equals(comment.getCommentUserId())){
                            Glide.with(context).load(user.getParseFile(KEY_USER_PROFILE_IMAGE).getUrl()).into(ivProfilePic);
                        }
                    }
                }
            });


            tvUsername.setText(comment.getCommentUserName());
            tvTime.setText(comment.getCommentTime());
            tvCommentText.setText(comment.getCommentDescription());
            rbPromote.setText(comment.getCommentLikeCount().toString());
            rbDemote.setText(comment.getCommentDislikeCount().toString());
        }
    }
}

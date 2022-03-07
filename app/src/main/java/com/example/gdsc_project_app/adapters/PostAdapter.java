package com.example.gdsc_project_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    // TODO: resolve "Cannot resolve symbol 'Post' error
    Context context;
    List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    // Populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the post at the passed in position
        Post post = posts.get(position);
        // Bind the post data into the ViewHolder
        holder.bind(post);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName;
        TextView tvPostContent;
        ImageView imUserPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            tvPostContent = itemView.findViewById(R.id.tvPostContent);
            imUserPic = itemView.findViewById(R.id.ivUserPic);
        }
    }

    // STOPPED AT 16:30
}

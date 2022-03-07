package com.example.gdsc_project_app.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdsc_project_app.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

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
}

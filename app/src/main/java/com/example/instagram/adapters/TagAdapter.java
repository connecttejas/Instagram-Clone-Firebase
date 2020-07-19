package com.example.instagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {


    private Context mContext;
    private List<String> mTags;
    private List<String> mTagsCount;

    public TagAdapter(Context mContext, List<String> mTags, List<String> mTagsCount) {
        this.mContext = mContext;
        this.mTags = mTags;
        this.mTagsCount = mTagsCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item,parent,false    );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tag.setText("# " + mTags.get(position));
        holder.tagCount.setText(mTagsCount.get(position) + " posts");
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public void filter(List<String> filterTags, List<String> filterTagCount) {
        this.mTags = filterTags;
        this.mTagsCount = filterTagCount;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tag;
        public TextView tagCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tagTextView);
            tagCount = itemView.findViewById(R.id.tagCountTextView);

        }
    }



}





package com.khoirullatif.consumerapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.khoirullatif.consumerapp.MainItems;
import com.khoirullatif.consumerapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private ArrayList<MainItems> mUser = new ArrayList<>();

    public void setData(ArrayList<MainItems> items) {
        mUser.clear();
        mUser.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowingAdapter.FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowingAdapter.FollowingViewHolder holder, int position) {
        MainItems user = mUser.get(position);

        try {
            holder.tvUsername.setText(user.getName());
            Glide.with(holder.itemView.getContext())
                    .load(user.getPhoto())
                    .placeholder(R.drawable.loading_picture)
                    .error(R.drawable.error_image_loading)
                    .apply(new RequestOptions().override(80, 80))
                    .into(holder.ivPhoto);
        } catch (Exception e) {
            Log.d("Exception: FAdap ", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public static class FollowingViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername;
        public ImageView ivPhoto;
        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_name_item);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_fav);
        }
    }
}
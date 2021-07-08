package com.khoirullatif.githubuser.adapter;

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
import com.khoirullatif.githubuser.MainItems;
import com.khoirullatif.githubuser.R;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<MainItems> mUser = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<MainItems> items) {
        mUser.clear();
        mUser.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        MainItems user = mUser.get(position);

        try {
            holder.tvRealname.setText(user.getRealname());
            holder.tvUsername.setText(user.getName());
            holder.tvCompany.setText(user.getCompany());
            Glide.with(holder.itemView.getContext())
                    .load(user.getPhoto())
                    .apply(new RequestOptions().override(80, 80))
                    .into(holder.ivPhoto);
        } catch (Exception e) {
            Log.d("Exception ", Objects.requireNonNull(e.getMessage()));
        }

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(mUser.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        public TextView tvRealname, tvUsername, tvCompany;
        public ImageView ivPhoto;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRealname = itemView.findViewById(R.id.tv_fullname_item_fav);
            tvUsername = itemView.findViewById(R.id.tv_username_item_fav);
            tvCompany = itemView.findViewById(R.id.tv_company_item_fav);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_fav);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MainItems data);
    }
}

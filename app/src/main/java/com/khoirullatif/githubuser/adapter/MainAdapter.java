package com.khoirullatif.githubuser.adapter;

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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<MainItems> mData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<MainItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MainViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(holder, mData.get(position));

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivPhoto;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_item);
            ivPhoto = itemView.findViewById(R.id.iv_photo_item_fav);
        }

        void bind(MainViewHolder holder, MainItems mainItems) {
            tvName.setText(mainItems.getName());
            Glide.with(holder.itemView.getContext())
                    .load(mainItems.getPhoto())
                    .apply(new RequestOptions().override(80, 80))
                    .placeholder(R.drawable.loading_picture)
                    .error(R.drawable.error_image_loading)
                    .into(holder.ivPhoto);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MainItems data);
    }
}

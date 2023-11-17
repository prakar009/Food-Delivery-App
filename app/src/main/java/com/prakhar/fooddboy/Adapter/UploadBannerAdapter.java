package com.prakhar.fooddboy.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.fooddboy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UploadBannerAdapter extends RecyclerView.Adapter<UploadBannerAdapter.ViewHolder> {

    private List<String> imageUrls;
    private Context context;

    public UploadBannerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        // Use Glide to load and display the image from the URL
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // placeholder image while loading
                .error(R.drawable.error) // error image if loading fails
                .fit()
                .into(holder.bannerImageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImageView = itemView.findViewById(R.id.purl);
        }
    }
}

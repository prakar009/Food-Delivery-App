package com.prakhar.fooddboy.Marketing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakhar.fooddboy.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapterUpload extends RecyclerView.Adapter<myadapterUpload.ViewHolder> {
    private ArrayList<modelUpload> list;
    private Context context;

    public myadapterUpload(ArrayList<modelUpload> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView purl;
        TextView userNameEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            purl = itemView.findViewById(R.id.purl);
            userNameEditText = itemView.findViewById(R.id.userNameEditText);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singlerow_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        modelUpload data = list.get(position);
        Glide.with(context).load(data.getPurl()).into(holder.purl);
        holder.userNameEditText.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

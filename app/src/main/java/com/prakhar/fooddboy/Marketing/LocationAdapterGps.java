package com.prakhar.fooddboy.Marketing;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.fooddboy.R;

import java.util.ArrayList;

public class LocationAdapterGps extends RecyclerView.Adapter<LocationAdapterGps.ViewHolder> {
    private ArrayList<LocationDataGps> list;
    private Context context;

    public LocationAdapterGps(ArrayList<LocationDataGps> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView latitudeTextView;
        TextView longitudeTextView;
        TextView addressTextView;
        TextView dateTimeTextView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            latitudeTextView = itemView.findViewById(R.id.latitudeTextView);
            longitudeTextView = itemView.findViewById(R.id.longitudeTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            name = itemView.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_gps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationDataGps locationData = list.get(position);
        holder.latitudeTextView.setText(String.valueOf(locationData.getLatitude()));
        holder.longitudeTextView.setText(String.valueOf(locationData.getLongitude()));
        holder.addressTextView.setText(locationData.getAddress());
        holder.dateTimeTextView.setText(locationData.getDatetime());
        holder.name.setText(locationData.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

package com.prakhar.fooddboy.Marketing;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.fooddboy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadLocationActivityGps extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationAdapterGps adapter;
    private DatabaseReference locationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_location_gps);

        recyclerView = findViewById(R.id.vertical_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<LocationDataGps> locationList = new ArrayList<>();
        adapter = new LocationAdapterGps(locationList, this);
        recyclerView.setAdapter(adapter);

        // Get a reference to the "location" node in the Firebase Realtime Database
        locationsRef = FirebaseDatabase.getInstance().getReference().child("location");

        // Read data from Firebase
        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LocationDataGps locationData = snapshot.getValue(LocationDataGps.class);
                    if (locationData != null) {
                        locationList.add(locationData);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle read error if needed
            }
        });

        // Fetch a specific location by its ID (replace "LOCATION_ID" with the actual ID you want to retrieve)
        fetchLocationById("LOCATION_ID");
    }

    private void fetchLocationById(String locationId) {
        DatabaseReference locationByIdRef = locationsRef.child(locationId);
        locationByIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationDataGps locationData = dataSnapshot.getValue(LocationDataGps.class);
                if (locationData != null) {
                    // Do something with the location data (e.g., display it)
                    // locationData contains the location details with the specified ID
                    Log.d("Firebase", "Address: " + locationData.getAddress());
                    Log.d("Firebase", "Latitude: " + locationData.getLatitude());
                    Log.d("Firebase", "Longitude: " + locationData.getLongitude());
                    Log.d("Firebase", "Date and Time: " + locationData.getDatetime());
                } else {
                    // Location with the specified ID not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle read error if needed
            }
        });
    }
}

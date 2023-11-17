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

public class ReadUpload extends AppCompatActivity {

    private RecyclerView recyclerView;
    private myadapterUpload adapter;
    private DatabaseReference locationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_upload);

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<modelUpload> locationList = new ArrayList<>();
        adapter = new myadapterUpload(locationList, this);
        recyclerView.setAdapter(adapter);

        // Get a reference to the "location" node in the Firebase Realtime Database
        locationsRef = FirebaseDatabase.getInstance().getReference().child("Users");

        // Read data from Firebase
        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    modelUpload locationData = snapshot.getValue(modelUpload.class);
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
//        fetchLocationById("school");
    }


    private void fetchLocationById(String locationId) {
        DatabaseReference locationByIdRef = locationsRef.child(locationId);
        locationByIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelUpload model = dataSnapshot.getValue(modelUpload.class);
                if (model != null) {
                    // Do something with the location data (e.g., display it)
                    // locationData contains the location details with the specified ID
                    Log.d("Firebase", "purl: " + model.getPurl());
                    Log.d("Firebase", "name: " + model.getName());


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

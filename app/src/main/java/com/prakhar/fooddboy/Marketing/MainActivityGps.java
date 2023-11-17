package com.prakhar.fooddboy.Marketing;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.prakhar.fooddboy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class MainActivityGps extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView locationTextView;
    private ImageView btnStartLocationUpdates;
    private ImageView btnStopLocationUpdates;


    EditText name;
    Button upload;
    private String lastStoredAddress = ""; // Variable to store the last saved address

    Button btn;
    private boolean isLocationUpdatesStarted = false; // Flag to track if location updates are started

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gps);
        lastStoredAddress = "";

        locationTextView = findViewById(R.id.locationTextView);
        name = findViewById(R.id.name);
        upload = findViewById(R.id.upload);
//        save = findViewById(R.id.save);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UploadImage.class));
            }
        });


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(90000); // 10 seconds

        btnStartLocationUpdates = findViewById(R.id.btnStartLocationUpdates);
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
                isLocationUpdatesStarted = true; // Set the flag to true when the button is clicked
                Toast.makeText(MainActivityGps.this, "GPS updates start", Toast.LENGTH_SHORT).show();
            }
        });

        btnStopLocationUpdates = findViewById(R.id.btnStopLocationUpdates);
        btnStopLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
                isLocationUpdatesStarted = false; // Set the flag to false when the button is clicked to stop updates
                Toast.makeText(MainActivityGps.this, "GPS updates stopped", Toast.LENGTH_SHORT).show();
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (isLocationUpdatesStarted && locationResult != null) { // Only update location if the flag is true
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        updateLocationTextView(location);
                    }
                }
            }
        };
    }




    private void updateLocationTextView(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        String locationText = "Latitude: " + latitude + "\nLongitude: " + longitude;

        String currentDateTime = getCurrentDateTime();
        locationText += "\nDate and Time: " + currentDateTime;


        String currentName = name.getText().toString().trim(); // Get the text from the EditText

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("location");
        String key = myRef.push().getKey(); // Generate a unique key for the new location entry
        myRef = myRef.child(key);

        // Save latitude and longitude
        myRef.child("latitude").setValue(latitude);
        myRef.child("longitude").setValue(longitude);
        myRef.child("address").setValue(lastStoredAddress); // Use the last stored address
        myRef.child("datetime").setValue(currentDateTime);
        myRef.child("name").setValue(currentName); // Save the name from EditText

        locationTextView.setText(locationText);


        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = "\nAddress: " + address.getAddressLine(0);
                locationText += addressText;

                // Save address only if it's different from the last stored address
                if (!address.getAddressLine(0).equals(lastStoredAddress)) {
                    lastStoredAddress = address.getAddressLine(0);
                    // Save address
                    myRef.child("address").setValue(lastStoredAddress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        locationTextView.setText(locationText);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }


    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchLocationFromDatabase() {
        Intent intent = new Intent(this, ReadLocationActivityGps.class);
        startActivity(intent);
    }
}

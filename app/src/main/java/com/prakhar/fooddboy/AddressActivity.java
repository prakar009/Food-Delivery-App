package com.prakhar.fooddboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prakhar.fooddboy.Model.ShippingAddress;
import com.prakhar.fooddboy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class AddressActivity extends AppCompatActivity {

    private EditText fullNameEditText, addressEditText, mobileNumberEditText, pincodeEditText, landmarkEditText;
    private Button submitButton;
    private ProgressBar progressBar;

    // Initialize Firebase database reference
    private DatabaseReference shippingAddressRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        fullNameEditText = findViewById(R.id.nme);
        addressEditText = findViewById(R.id.addres);
        mobileNumberEditText = findViewById(R.id.mobile2);
        pincodeEditText = findViewById(R.id.pincode);
        landmarkEditText = findViewById(R.id.landmark);
        submitButton = findViewById(R.id.submit3);
        progressBar = findViewById(R.id.progressbar);

        Paper.init(this);

        // Initialize Firebase database reference for shipping addresses
        String userPhone = Paper.book().read(Prevalent.UserphoneKey);
        shippingAddressRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userPhone).child("shippingaddress");

        progressBar.setVisibility(View.INVISIBLE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveShippingAddress();
            }
        });
    }

    private void saveShippingAddress() {
        String fullName = fullNameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();
        String pincode = pincodeEditText.getText().toString().trim();
        String landmark = landmarkEditText.getText().toString().trim();

        if (fullName.isEmpty() || address.isEmpty() || mobileNumber.isEmpty() || pincode.isEmpty()) {
            // Handle validation and show an error message to the user
            Toast.makeText(AddressActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Create a ShippingAddress object
        ShippingAddress shippingAddress = new ShippingAddress(fullName, address, mobileNumber, pincode, landmark);

        // Generate a unique key for the new shipping address entry
        String key = shippingAddressRef.push().getKey();

        // Save the shipping address data to Firebase
        shippingAddressRef.child(key).setValue(shippingAddress)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data saved successfully
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddressActivity.this, "Shipping address saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), OrderSuccess.class);
                        startActivity(intent);
                        finish(); // Close the activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddressActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

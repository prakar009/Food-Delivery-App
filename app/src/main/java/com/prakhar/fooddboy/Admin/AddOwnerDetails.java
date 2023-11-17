package com.prakhar.fooddboy.Admin;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.fooddboy.Model.CompanyInfo;
import com.prakhar.fooddboy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddOwnerDetails extends AppCompatActivity {

    EditText cname, veg, north_indian, rating1, rating2;

    Button upload;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CompanyInfo companyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_owner_details);

        cname=findViewById(R.id.cname);
        veg=findViewById(R.id.veg);
        north_indian=findViewById(R.id.north_indian);
        rating1=findViewById(R.id.rating1);
        rating2=findViewById(R.id.rating2);

        upload=findViewById(R.id.upload);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("CompanyInfo");

        companyInfo = new CompanyInfo();


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cname = cname.getText().toString();
                String Veg = veg.getText().toString();
                String North_Indian = north_indian.getText().toString();
                String Rating1 = rating1.getText().toString();
                String Rating2 = rating2.getText().toString();

                if (TextUtils.isEmpty(Cname) && TextUtils.isEmpty(Veg) && TextUtils.isEmpty(North_Indian) && TextUtils.isEmpty(Rating1) && TextUtils.isEmpty(Rating2))
                {

                    Toast.makeText(AddOwnerDetails.this, "Please add all data.", Toast.LENGTH_SHORT).show();
                }else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(Cname, Veg, North_Indian, Rating1, Rating2);
                }
            }
        });

    }

    private void addDatatoFirebase(String Cname, String Veg, String North_Indian, String Rating1, String Rating2) {
        companyInfo.setCanme(Cname);
        companyInfo.setVeg(Veg);
        companyInfo.setNorth_indian(North_Indian);
        companyInfo.setRating1(Rating1);
        companyInfo.setRating2(Rating2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(companyInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(AddOwnerDetails.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(AddOwnerDetails.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

            }
        });
    }


}

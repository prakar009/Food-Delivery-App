package com.prakhar.fooddboy.Marketing;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.prakhar.fooddboy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivityMarketing extends AppCompatActivity {

    TextView profileName, profileEmail, profileDesignation, profileMobile, profileAddress, profilePassword;
    Button dashboard1;
    ImageView purl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_marketing);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileMobile = findViewById(R.id.profileMobile);
        profilePassword = findViewById(R.id.profilePassword);
        profileAddress = findViewById(R.id.profileAddress);
        profileDesignation = findViewById(R.id.profileDesignation);
        dashboard1 = findViewById(R.id.dashboard1);
        purl = findViewById(R.id.purl);

        showAllUserData();

        dashboard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MarketingActivities.class));
            }
        });
    }

    public void showAllUserData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String mobileUser = intent.getStringExtra("designation");
        String addressUser = intent.getStringExtra("address");
        String passwordUser = intent.getStringExtra("password");

        String designationUser = intent.getStringExtra("mobile");
        String profileImageUrl = intent.getStringExtra("purl");

        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileDesignation.setText(designationUser);
        profilePassword.setText(passwordUser);
        profileAddress.setText(addressUser);
        profileMobile.setText(mobileUser);

        // Load the profile image into the ImageView using Glide or Picasso
        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(profileImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(purl);
        } else {
            purl.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    public void passUserData() {
        String userUsername = profileName.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Marketing");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                        String mobileFromDB = dataSnapshot.child("mobile").getValue(String.class);
                        String designationFromDB = dataSnapshot.child("designation").getValue(String.class);
                        String addressFromDB = dataSnapshot.child("address").getValue(String.class);
                        String profileImageFromDB = dataSnapshot.child("purl").getValue(String.class);

                        Intent intent2 = new Intent(ProfileActivityMarketing.this, MarketingActivities.class);
                        intent2.putExtra("name", nameFromDB);
                        intent2.putExtra("email", emailFromDB);
                        intent2.putExtra("mobile", mobileFromDB);
                        intent2.putExtra("designation", designationFromDB);
                        intent2.putExtra("address", addressFromDB);
                        intent2.putExtra("purl", profileImageFromDB);
                        startActivity(intent2);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }
}

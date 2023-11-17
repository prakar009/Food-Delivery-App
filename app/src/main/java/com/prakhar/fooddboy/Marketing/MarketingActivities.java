package com.prakhar.fooddboy.Marketing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.prakhar.fooddboy.R;
import com.prakhar.fooddboy.User.calling;
import com.prakhar.fooddboy.User.filemanagerUser;


public class MarketingActivities extends AppCompatActivity {

    CardView makecall, calldata, startLocation;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_activities);

//        viewprofile=findViewById(R.id.viewprofile);
        calldata=findViewById(R.id.calldata);
        makecall=findViewById(R.id.makecall);
        startLocation=findViewById(R.id.startLocation);


        makecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), calling.class);
                startActivity(intent);

            }
        });

        calldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), filemanagerUser.class);
                startActivity(intent);

            }
        });

        startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivityGps.class);
                startActivity(intent);

            }
        });
    }
}
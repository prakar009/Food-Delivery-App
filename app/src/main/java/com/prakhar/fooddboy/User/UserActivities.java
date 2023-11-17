package com.prakhar.fooddboy.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.prakhar.fooddboy.R;


public class UserActivities extends AppCompatActivity {

    CardView makecall, calldata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activities);

//        viewprofile=findViewById(R.id.viewprofile);
        calldata=findViewById(R.id.calldata);
        makecall=findViewById(R.id.makecall);


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
    }
}
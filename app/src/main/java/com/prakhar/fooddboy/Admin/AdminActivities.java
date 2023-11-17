package com.prakhar.fooddboy.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.prakhar.fooddboy.R;
import com.prakhar.fooddboy.ViewAllDataAdmin;


public class AdminActivities extends AppCompatActivity {

    CardView addproduct;
    TextView viewuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addproduct = findViewById(R.id.addproduct);

        viewuser = findViewById(R.id.viewuser);


        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterSpinner.class));

            }
        });



        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivities.this, ViewAllDataAdmin.class);
                startActivity(intent);

            }
        });


    }
}
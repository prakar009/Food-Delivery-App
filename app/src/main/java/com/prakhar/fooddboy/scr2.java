package com.prakhar.fooddboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class scr2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scr2);



        ImageView image3 = findViewById(R.id.imageView3); // Replace with the actual ID of your image view

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the system vibrator service
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Check if the device has a vibrator
                if (vibrator.hasVibrator()) {
                    // Vibrate the phone for 500 milliseconds (0.5 seconds)
                    vibrator.vibrate(500);
                    Intent intent = new Intent(getApplicationContext(), MainActivityNew.class);
                    startActivity(intent);
                } else {
                    // Handle the case where the device doesn't have a vibrator
                    Toast.makeText(getApplicationContext(), "Your device doesn't have a vibrator.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
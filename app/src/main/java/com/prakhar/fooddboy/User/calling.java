package com.prakhar.fooddboy.User;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.prakhar.fooddboy.R;

public class calling extends AppCompatActivity {

    EditText collegeNameEditText, mobileEditText, responseEditText;
    Button callButton;
    static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calling);

        collegeNameEditText = findViewById(R.id.collegeNameEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        responseEditText = findViewById(R.id.responseEditText);
        callButton = findViewById(R.id.callButton);

        if (ContextCompat.checkSelfPermission(calling.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(calling.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collegeName = collegeNameEditText.getText().toString().trim();
                String mobileNumber = mobileEditText.getText().toString().trim();
                String response = responseEditText.getText().toString().trim();

                if (!mobileNumber.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mobileNumber));
                    startActivity(intent);
                } else {
                    Toast.makeText(calling.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

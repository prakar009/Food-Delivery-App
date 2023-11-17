package com.prakhar.fooddboy.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.prakhar.fooddboy.Admin.modelRegister;
import com.prakhar.fooddboy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityUser extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
//        signupRedirectText = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    checkUser();
                }
            }
        });
//
//        signupRedirectText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivityUser.this, SignUpActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private boolean validateInput() {
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (username.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        }

        if (password.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        }

        return true;
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Office");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                         modelRegister user = userSnapshot.getValue(modelRegister.class);

                        if (user != null && user.getMobile().equals(userPassword)) {
                            Intent intent = new Intent(LoginActivityUser.this, ProfileActivityUser.class);
//                            intent.putExtra("name", user.getName());
//                            intent.putExtra("email", user.getEmail());
//                            intent.putExtra("designation", user.getPassword());
//                            intent.putExtra("address", user.getAddress());
                            intent.putExtra("mobile", user.getDesignation());
                            intent.putExtra("password", user.getMobile());
                            intent.putExtra("purl", user.getPurl());

                            startActivity(intent);
                            return;
                        }
                    }
                    // Invalid password
                    loginPassword.setError("Invalid Credentials");
                    loginPassword.requestFocus();
                } else {
                    // User does not exist
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivityUser.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

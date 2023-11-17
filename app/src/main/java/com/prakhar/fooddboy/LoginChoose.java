package com.prakhar.fooddboy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.prakhar.fooddboy.Marketing.LoginActivityMarketing;
import com.prakhar.fooddboy.User.LoginActivityUser;


public class LoginChoose extends AppCompatActivity {

    CardView card_admin, card_user, card_marketing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginchoose);

        card_admin=findViewById(R.id.card_admin);
        card_user=findViewById(R.id.card_user);
        card_marketing=findViewById(R.id.card_marketing);


        card_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(getApplicationContext(), AdminLogin.class);
//                startActivity(intent);
            }
        });

        card_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), LoginActivityUser.class);
                startActivity(intent);
            }
        });

        card_marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), LoginActivityMarketing.class);
                startActivity(intent);
            }
        });
    }
}
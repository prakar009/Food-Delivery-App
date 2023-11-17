package com.prakhar.fooddboy.User;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.prakhar.fooddboy.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class filemanagerUser extends AppCompatActivity
{
//   FloatingActionButton fb;
   RecyclerView recview;
   fileAdapterUser adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemanageruser);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        fb = (FloatingActionButton) findViewById(R.id.floatingActionButton);
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), uploadfile.class));
//            }
//        });

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<modelFileUser> options =
                new FirebaseRecyclerOptions.Builder<modelFileUser>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("employees"), modelFileUser.class)
                        .build();

        adapter=new fileAdapterUser(options);
        recview.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
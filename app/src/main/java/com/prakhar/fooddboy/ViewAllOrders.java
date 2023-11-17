package com.prakhar.fooddboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.prakhar.fooddboy.Admin.modelRegister;
import com.prakhar.fooddboy.Admin.myadapterRead;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAllOrders extends AppCompatActivity {


    RecyclerView recview;
    myadapterRead adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_orders);



        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));






        FirebaseRecyclerOptions<modelRegister> options =
                new FirebaseRecyclerOptions.Builder<modelRegister>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Office"), modelRegister.class)
                        .build();

        adapter=new myadapterRead(options);
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

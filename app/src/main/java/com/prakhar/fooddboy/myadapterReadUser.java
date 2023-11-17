package com.prakhar.fooddboy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakhar.fooddboy.Admin.modelRegister;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapterReadUser extends FirebaseRecyclerAdapter<modelRegister, myadapterReadUser.myviewholder> {
    public myadapterReadUser(@NonNull FirebaseRecyclerOptions<modelRegister> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final modelRegister model) {
        holder.email.setText(model.getEmail());
        holder.dish_name.setText(model.getMobile());
        holder.description.setText(model.getAddress());
        holder.name.setText(model.getName());
        holder.price.setText(model.getDesignation());

        // Load image using Glide (you should have an ImageView with the id "purl" in your layout)
        Glide.with(holder.purl.getContext()).load(model.getPurl()).into(holder.purl);

        // Add click listeners to Plus and Minus buttons
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQty = Integer.parseInt(holder.qty.getText().toString());
                currentQty++; // Increase the quantity by 1
                holder.qty.setText(String.valueOf(currentQty)); // Update the quantity TextView
                updateTotalQuantity(holder, currentQty, model); // Pass the model to the helper method
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQty = Integer.parseInt(holder.qty.getText().toString());
                if (currentQty > 0) {
                    currentQty--; // Decrease the quantity by 1 if it's greater than 0
                    holder.qty.setText(String.valueOf(currentQty)); // Update the quantity TextView
                    updateTotalQuantity(holder, currentQty, model); // Pass the model to the helper method
                }
            }
        });



        holder.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a bundle to pass data
                Bundle bundle = new Bundle();
                bundle.putString("name", model.getName());
                bundle.putString("price", model.getDesignation());
                bundle.putString("email", model.getEmail());
                bundle.putString("dish_name", model.getMobile());
                bundle.putString("description", model.getAddress());
                bundle.putString("imageURL", model.getPurl());

                // Include the quantity in the bundle
                bundle.putInt("quantity", Integer.parseInt(holder.qty.getText().toString()));

                // Create an instance of MyBottomSheetDialogFragment and set the arguments
                MyBottomSheetDialogFragment bottomSheetDialogFragment = new MyBottomSheetDialogFragment();
                bottomSheetDialogFragment.setArguments(bundle);

                // Show the fragment
                bottomSheetDialogFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });



    }

    // Helper method to update the total quantity and display it
    private void updateTotalQuantity(myviewholder holder, int currentQty, modelRegister model) {
        // Calculate the total quantity (You can modify this calculation as needed)
//        int itemPrice = Integer.parseInt(model.getDesignation()); // Convert the item price to an integer
//        int totalQty = currentQty * itemPrice;

        // Set the total quantity in the TextView
//        holder.totalqty.setText(String.valueOf(totalQty));
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowuser, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        ImageView purl;
        ImageView edit, delete;
        TextView name, price, email, dish_name, description, password;
        Button plus, minus;
        TextView qty, totalqty;

        RelativeLayout card1;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            purl = itemView.findViewById(R.id.purl); // Removed unnecessary casting
            name = itemView.findViewById(R.id.name); // Removed unnecessary casting
            price = itemView.findViewById(R.id.designation); // Removed unnecessary casting
            email = itemView.findViewById(R.id.email); // Removed unnecessary casting
            dish_name = itemView.findViewById(R.id.mobile); // Removed unnecessary casting
            description = itemView.findViewById(R.id.address); // Removed unnecessary casting
            password = itemView.findViewById(R.id.password); // Removed unnecessary casting
            plus = itemView.findViewById(R.id.Plus); // Removed unnecessary casting
            minus = itemView.findViewById(R.id.minus1); // Removed unnecessary casting
            qty = itemView.findViewById(R.id.quantity); // Removed unnecessary casting
            totalqty = itemView.findViewById(R.id.totalqty); // Removed unnecessary casting
            edit = itemView.findViewById(R.id.editicon); // Removed unnecessary casting
            delete = itemView.findViewById(R.id.deleteicon); // Removed unnecessary casting
            card1 = itemView.findViewById(R.id.card1); // Removed unnecessary casting
        }
    }
}

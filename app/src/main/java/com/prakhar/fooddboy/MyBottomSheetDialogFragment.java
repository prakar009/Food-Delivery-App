package com.prakhar.fooddboy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {






    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.bottom_sheet_layout);


        Button addToCartButton = dialog.findViewById(R.id.add_to_cart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from the arguments
                Bundle args = getArguments();
                if (args != null) {
                    String dish_name = args.getString("dish_name");
                    int quantity = args.getInt("quantity");
                    String price = args.getString("price");

                    // Create an intent and add extras
                    Intent intent = new Intent(getContext(), AddToCartActivity.class);
                    intent.putExtra("dish_name", dish_name);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("price", price);

                    // Start the AddToCartActivity with the intent
                    startActivity(intent);
                }
            }
        });





        // Retrieve data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String designation = args.getString("price");
            String mobile = args.getString("dish_name");
            String address = args.getString("description");
            String imageURL = args.getString("imageURL");

            // Retrieve the quantity
            int quantity = args.getInt("quantity");

            // Initialize TextViews and set their text
            TextView namep = dialog.findViewById(R.id.namep);
            TextView designationp = dialog.findViewById(R.id.designationp);
            TextView mobilep = dialog.findViewById(R.id.mobilep);
            TextView addressp = dialog.findViewById(R.id.addressp);
            TextView quantityTextView = dialog.findViewById(R.id.quantityTextView); // Replace with the actual TextView ID

            namep.setText("Name: " + name);
            designationp.setText("price: " + designation);
            mobilep.setText("Description: " + mobile);
            addressp.setText("Dish Name: " + address);

            // Set the quantity text
            quantityTextView.setText("Quantity: " + String.valueOf(quantity));

            // Load image using Glide
            ImageView imageView = dialog.findViewById(R.id.img3);
            Glide.with(this).load(imageURL).into(imageView);
        }

        return dialog;
    }

}

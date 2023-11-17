    package com.prakhar.fooddboy;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import com.prakhar.fooddboy.Prevalent.Prevalent;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

    import io.paperdb.Paper;

    public class AddToCartActivity extends AppCompatActivity {
        private TextView ttotal, addmoreitems, stotal, gst, dcharges, gtotal, netpay, discount;

        private TextView printinvoice;

        private LinearLayout lp;

        private  TextView map, maptext;


        private String typeofuser = "";
        private RecyclerView recyclerView, recyclerView2;
        private CartAdapter adapter;
        private List<CartItem> cartItems = new ArrayList<>();
        private DatabaseReference cartRef;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_to_cart);

            addmoreitems = findViewById(R.id.addmoreitems);
            addmoreitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ReadUser.class);
                    startActivity(intent);
                }
            });

            stotal = findViewById(R.id.stotal);
            gst = findViewById(R.id.gst);
            gtotal = findViewById(R.id.gtotal);
            dcharges = findViewById(R.id.dcharges);
            discount = findViewById(R.id.discount);
            netpay = findViewById(R.id.netpay);


            lp=findViewById(R.id.lp);
            map=findViewById(R.id.map);

            lp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userPhone = Paper.book().read(Prevalent.UserphoneKey);
                    DatabaseReference previousOrderRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userPhone).child("previousOrder");

                    // Move cart items to previousOrder
                    for (CartItem cartItem : cartItems) {
                        String itemId = cartItem.getId();
                        previousOrderRef.child(itemId).setValue(cartItem);
                    }

                    // Clear the cart by removing all items
                    cartRef.removeValue();
                    Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
                    startActivity(intent);
                }
            });





            recyclerView = findViewById(R.id.cartRecyclerView);
            recyclerView2 = findViewById(R.id.cartRecyclerView2);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(cartItems);
            recyclerView.setAdapter(adapter);





            Paper.init(this);

            // Get a reference to your Firebase database
            // Use Paper library to retrieve the user's phone number from cache
            String userPhone = Paper.book().read(Prevalent.UserphoneKey);
            cartRef = FirebaseDatabase.getInstance().getReference("Users");

            if (userPhone != null && !userPhone.isEmpty()) {
                cartRef = cartRef.child(userPhone).child("cart");
            } else {
                // Handle the case where userPhone is null or empty
                Log.e("AddToCartActivity", "User phone is null or empty");
                // You may want to show an error message or take appropriate action here
            }

            Intent intent = getIntent();

            // Retrieve the data passed as extras
            String dish_name = intent.getStringExtra("dish_name");
            int quantity = intent.getIntExtra("quantity", 0);
            String price = intent.getStringExtra("price");

            // Save the cart item to Firebase
            if (dish_name != null && !dish_name.isEmpty() && quantity > 0 && price != null) {
                String itemId = cartRef.push().getKey();
                CartItem cartItem = new CartItem(itemId, dish_name, quantity, price);
                cartRef.child(itemId).setValue(cartItem);
            }

            // Listen for changes in the cart and update the local list
            cartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cartItems.clear(); // Clear the local list before populating it again

                    double totalAmount = 0.0; // Initialize the total amount

                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        CartItem cartItem = itemSnapshot.getValue(CartItem.class);

                        // Ensure cartItem and price are not null before processing
                        if (cartItem != null && cartItem.getPrice() != null) {
                            cartItems.add(cartItem);

                            // Extract the numeric part from the price string
                            String priceString = cartItem.getPrice();

                            // Check if priceString is not null before further processing
                            if (priceString != null) {
                                String numericPart = priceString.replaceAll("[^\\d.]+", ""); // Remove non-numeric characters
                                double itemPrice = 0.0;
                                try {
                                    itemPrice = Double.parseDouble(numericPart);
                                } catch (NumberFormatException e) {
                                    // Handle the case where the price cannot be parsed
                                    Log.e("Firebase", "Error parsing price: " + priceString);
                                }

                                // Calculate the total amount for each cart item and add to the total
                                totalAmount += (cartItem.getQuantity() * itemPrice);
                            }
                        }
                    }

                    // Update the ttotal TextView with the total amount

                    stotal.setText("" + totalAmount);

                    // Calculate GST and delivery charges (you can adjust these values)
                    double gstAmount = totalAmount * 0.18; // Assuming GST is 18% of the total amount
                    double deliveryCharges = 50.0; // Assuming a fixed delivery charge of 50 Rs

                    double discountAmount = 50.0;

                    // Calculate the grand total
                    double grandTotal = totalAmount + gstAmount + deliveryCharges;

                    double netPayable = grandTotal - discountAmount;

                    gst.setText("" + gstAmount);
                    dcharges.setText("" + deliveryCharges);
                    gtotal.setText("" + grandTotal);
                    discount.setText("" + discountAmount); // Set the discount value in the discount TextView
                    netpay.setText("" + netPayable);

                    Intent paymentIntent = new Intent(AddToCartActivity.this, PaymentActivity.class);
                    paymentIntent.putExtra("amount", grandTotal);

                    adapter.notifyDataSetChanged(); // Notify the RecyclerView adapter of data changes
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors, if any
                    Log.e("Firebase", "Error: " + databaseError.getMessage());
                }
            });
        }



    }

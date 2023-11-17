package com.prakhar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.fooddboy.CartItem;
import com.prakhar.fooddboy.Prevalent.Prevalent;
import com.prakhar.fooddboy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.paperdb.Paper;

public class CartAdapter2 extends RecyclerView.Adapter<CartAdapter2.CartViewHolder> {
    private List<CartItem> cartItems;

    public CartAdapter2(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.dish_nameTextView.setText("Dish Name: " + cartItem.getDishName());
        holder.quantityTextView.setText("Quantity: " + cartItem.getQuantity());
        holder.priceTextView.setText("Price: " + cartItem.getPrice());
        String itemId = cartItem.getId();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = Paper.book().read(Prevalent.UserphoneKey);

                // Check if itemId is not null before creating the cartItemsRef
                if (itemId != null) {
                    DatabaseReference cartItemsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userPhone).child("previousOrder").child(itemId);

                    cartItemsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Removal from Firebase was successful
                                // Check if the position is valid before removing the item from the local list
                                if (position >= 0 && position < cartItems.size()) {
                                    cartItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            } else {
                                // Handle the case where removal from Firebase failed
                            }
                        }
                    });
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView dish_nameTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public ImageView delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_nameTextView = itemView.findViewById(R.id.itemdish_nameTextView);
            quantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
            priceTextView = itemView.findViewById(R.id.itemPriceTextView);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}

package com.prakhar.fooddboy;
// Import statements
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.prakhar.fooddboy.Adapter.UploadBannerAdapter;
import com.prakhar.fooddboy.Admin.modelRegister;
import com.prakhar.fooddboy.Marketing.myadapterReadM;
import com.prakhar.fooddboy.Model.BannerModel;
import com.prakhar.fooddboy.Model.CompanyInfo;
import com.prakhar.fooddboy.Slider.AutoImageSliderAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import io.paperdb.Paper;

public class ReadUser extends AppCompatActivity {




 private TextView cname, veg, north_indian, rating1, rating2;


    private String typeOfUser = "";
    RecyclerView recview;
    myadapterReadUser adapter;

    UploadBannerAdapter uploadBannerAdapter;

    RecyclerView recview2, recbanner;
    myadapterReadM adapter2;

    private ViewPager2 viewPager;
    private AutoImageSliderAdapter autoImageSliderAdapter;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 3000;
    private final long PERIOD_MS = 5000;

    FirebaseDatabase firebaseDatabase, firebaseDatabase2;
    DatabaseReference databaseReference, databaseReference2;
    CompanyInfo companyInfo;

    BannerModel bannerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readuser);


        cname=findViewById(R.id.cname);
        veg=findViewById(R.id.veg);
        north_indian=findViewById(R.id.north_indian);
        rating1=findViewById(R.id.rating1);
        rating2=findViewById(R.id.rating2);




//banner recyclerview
        recbanner = findViewById(R.id.recbanner);
        recbanner.setNestedScrollingEnabled(false);
        recbanner.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Firebase Storage reference
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("banner/images");

        // Create a list to hold the image URLs
        List<String> imageUrls = new ArrayList<>();

        storageReference.listAll().addOnSuccessListener(result -> {
            for (StorageReference item : result.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Get the URL for each image and add it to the list
                    imageUrls.add(uri.toString());

                    // Check if this is the last image URL
                    if (imageUrls.size() == result.getItems().size()) {
                        // Create and set the adapter
                        uploadBannerAdapter = new UploadBannerAdapter(ReadUser.this, imageUrls);
                        recbanner.setAdapter(uploadBannerAdapter);

                        // Start auto-scrolling the recbanner
                        startAutoScrolling();
                    }
                }).addOnFailureListener(exception -> {
                    // Handle any errors that may occur during URL retrieval
                });
            }
        }).addOnFailureListener(e -> {
            // Handle any errors that may occur during list retrieval
        });




        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("CompanyInfo");

        companyInfo = new CompanyInfo();
        getdata();



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Home");
//        setSupportActionBar(toolbar);



            // Paper
            Paper.init(this);


//            FloatingActionButton fab = findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Open a new activity to display Firebase data
////                Intent intent = new Intent(getApplicationContext(), FabAddTocART.class);
////                startActivity(intent);
//                }
//            });

//            viewPager = findViewById(R.id.recbanner);
//            List<Integer> imageList = getImageList();
//            autoImageSliderAdapter = new AutoImageSliderAdapter(this, imageList);
//            viewPager.setAdapter(autoImageSliderAdapter);
//
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new AutoSliderTask(), DELAY_MS, PERIOD_MS);



            recview = findViewById(R.id.recview3);
            recview.setNestedScrollingEnabled(false);
            recview.setLayoutManager(new LinearLayoutManager(this));




            FirebaseRecyclerOptions<modelRegister> options =
                    new FirebaseRecyclerOptions.Builder<modelRegister>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Office"), modelRegister.class)
                            .build();

            adapter = new myadapterReadUser(options);
            recview.setAdapter(adapter);


            recview2 = findViewById(R.id.recview);
            recview2.setNestedScrollingEnabled(false);
            recview2.setLayoutManager(new LinearLayoutManager(this));

            FirebaseRecyclerOptions<modelRegister> option =
                    new FirebaseRecyclerOptions.Builder<modelRegister>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Marketing"), modelRegister.class)
                            .build();

            adapter2 = new myadapterReadM(option);
            recview2.setAdapter(adapter2);
        }

    private void startAutoScrolling() {
        final int speedScroll = 2000; // Set your desired scrolling speed (milliseconds)
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == uploadBannerAdapter.getItemCount())
                    count = 0;
                if (count < uploadBannerAdapter.getItemCount()) {
                    recbanner.smoothScrollToPosition(count);
                    count++;
                    handler.postDelayed(this, speedScroll);
                }
            }
        };

        handler.postDelayed(runnable, speedScroll);
    }
    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Assuming that "CompanyInfo" has child nodes with the keys "cname", "veg", "rating1", and "rating2"
                    String companyName = snapshot.child("canme").getValue(String.class);
                    String vegType = snapshot.child("veg").getValue(String.class);
                    String ratingValue1 = snapshot.child("rating1").getValue(String.class);
                    String ratingValue2 = snapshot.child("rating2").getValue(String.class);

                    // Set the retrieved data in TextViews
                    cname.setText(companyName);
                    veg.setText(vegType);
                    rating1.setText(ratingValue1);
                    rating2.setText(ratingValue2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that may occur during data retrieval
            }
        });


}


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }

//    private List<Integer> getImageList() {
//        List<Integer> imageList = new ArrayList<>();
//        imageList.add(R.drawable.t1);
//        imageList.add(R.drawable.t2);
//        imageList.add(R.drawable.t3);
//        // Add more images if needed
//        return imageList;
//    }
//
//    private class AutoSliderTask extends TimerTask {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    currentPage = (currentPage + 1) % autoImageSliderAdapter.getItemCount();
//                    viewPager.setCurrentItem(currentPage);
//                }
//            });
//        }
    }


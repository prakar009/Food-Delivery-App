package com.prakhar.fooddboy.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.fooddboy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class uploadfile extends AppCompatActivity {
    ImageView imageBrowse, imageUpload, fileLogo, cancelFile;
    Uri filePath;
    EditText fileTitle;

    StorageReference storageReference;


    Spinner userSpinner; // Add Spinner for selecting users
    ArrayAdapter<String> userAdapter; // Adapter for the user Spinner
    List<String> userList;
    String selectedUser;
    DatabaseReference selectedUserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize variables...
        userSpinner = findViewById(R.id.userSpinner);
        userList = new ArrayList<>();

        // Create adapter for user Spinner
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userList);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);



        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = userList.get(position);
                selectedUserRef = FirebaseDatabase.getInstance().getReference("Office").child(selectedUser);
                Toast.makeText(uploadfile.this, "Selected User: " + selectedUser, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle any actions when no user is selected, if needed
            }
        });

        // Load user names from Firebase Database
        loadUserNames();

        storageReference = FirebaseStorage.getInstance().getReference();
        selectedUserRef = FirebaseDatabase.getInstance().getReference("employees");

        fileTitle = findViewById(R.id.filetitle);
        imageBrowse = findViewById(R.id.imagebrowse);
        imageUpload = findViewById(R.id.imageupload);
        fileLogo = findViewById(R.id.filelogo);
        cancelFile = findViewById(R.id.cancelfile);

        fileLogo.setVisibility(View.INVISIBLE);
        cancelFile.setVisibility(View.INVISIBLE);

        cancelFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileLogo.setVisibility(View.INVISIBLE);
                cancelFile.setVisibility(View.INVISIBLE);
                imageBrowse.setVisibility(View.VISIBLE);
            }
        });

        imageBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("*/*");  // Allow all file types
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Files"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if a valid user is selected
                if (selectedUser.isEmpty() || !userList.contains(selectedUser)) {
                    Toast.makeText(uploadfile.this, "Please select a valid user before uploading the file.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If a valid user is selected, proceed with the file upload process
                processUpload(filePath);
            }
        });
    }

    private void loadUserNames() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Office");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userName = snapshot.child("name").getValue(String.class);
                    if (userName != null) {
                        userList.add(userName);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            fileLogo.setVisibility(View.VISIBLE);
            cancelFile.setVisibility(View.VISIBLE);
            imageBrowse.setVisibility(View.INVISIBLE);

            // Get the file name from the file path
            String filename = getFileNameFromUri(filePath);

            // Update the EditText with the file name
            if (filename != null && !filename.isEmpty()) {
                fileTitle.setText(filename);
            } else {
                Toast.makeText(uploadfile.this, "File name not found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Helper method to get the file name from the Uri
    // Helper method to get the file name from the Uri
    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(nameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }



    public void processUpload(Uri filePath) {
        // Rest of the code remains the same...
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading...");
        pd.show();

        final String selectedUser = userSpinner.getSelectedItem().toString();

        final DatabaseReference selectesUserref = selectedUserRef.child("Office");

        final StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis());
        final String uniqueKey = selectesUserref.push().getKey(); // Use userFilesRef instead of selectedUserRef
        reference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                model obj = new model(fileTitle.getText().toString(), uri.toString(), 0, 0, 0);
                                selectesUserref.child(uniqueKey).setValue(obj); // Use userFilesRef instead of selectedUserRef

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "File Uploaded for " + selectedUser, Toast.LENGTH_LONG).show();

                                fileLogo.setVisibility(View.INVISIBLE);
                                cancelFile.setVisibility(View.INVISIBLE);
                                imageBrowse.setVisibility(View.VISIBLE);
                                fileTitle.setText("");
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        pd.setMessage("Uploaded: " + (int) percent + "%");

//                        startActivity(new Intent(getApplicationContext(),ReadFileData.class));
                    }
                });
    }


}

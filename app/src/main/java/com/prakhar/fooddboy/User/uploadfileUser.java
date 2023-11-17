package com.prakhar.fooddboy.User;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.fooddboy.Admin.model;
import com.prakhar.fooddboy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class uploadfileUser extends AppCompatActivity {
    ImageView imageBrowse, imageUpload, fileLogo, cancelFile;
    Uri filePath;
    EditText fileTitle;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageReference = FirebaseStorage.getInstance().getReference("uploads/");
            databaseReference = FirebaseDatabase.getInstance().getReference("Office/files");

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
                processUpload(filePath);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            filePath = data.getData();
            fileLogo.setVisibility(View.VISIBLE);
            cancelFile.setVisibility(View.VISIBLE);
            imageBrowse.setVisibility(View.INVISIBLE);
        }
    }

    public void processUpload(Uri filePath) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading...");
        pd.show();

        final StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis());
        reference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                model obj = new model(fileTitle.getText().toString(), uri.toString(), 0, 0, 0);
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();

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
                    }
                });
    }
}
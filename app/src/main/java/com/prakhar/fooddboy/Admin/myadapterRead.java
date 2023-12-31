package com.prakhar.fooddboy.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakhar.fooddboy.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class myadapterRead extends FirebaseRecyclerAdapter<modelRegister, myadapterRead.myviewholder>
{
    public myadapterRead(@NonNull FirebaseRecyclerOptions<modelRegister> options)
    {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final modelRegister model)
    {
        holder.name.setText(model.getName());

        holder.email.setText(model.getEmail());
        holder.mobile.setText(model.getMobile());
        holder.address.setText(model.getAddress());
        holder.password.setText(model.getPassword());
        holder.designation.setText(model.getDesignation());


        Glide.with(holder.purl.getContext()).load(model.getPurl()).into(holder.purl);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.purl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText purl=myview.findViewById(R.id.uimgurl);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText designation=myview.findViewById(R.id.udesignation);
                final EditText email=myview.findViewById(R.id.uemail);
                Button submit=myview.findViewById(R.id.usubmit);

                purl.setText(model.getPurl());
                name.setText(model.getName());

                email.setText(model.getEmail());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("purl",purl.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("designation",designation.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Office")                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.purl.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Office")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView purl;
        ImageView edit,delete;
        TextView name,designation,email, mobile, address, password;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            purl=(CircleImageView) itemView.findViewById(R.id.purl);
            name=(TextView)itemView.findViewById(R.id.name);
            designation=(TextView)itemView.findViewById(R.id.designation);
            email=(TextView)itemView.findViewById(R.id.name);
            mobile=(TextView)itemView.findViewById(R.id.mobile);
            address=(TextView)itemView.findViewById(R.id.address);
            password=(TextView)itemView.findViewById(R.id.password);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}

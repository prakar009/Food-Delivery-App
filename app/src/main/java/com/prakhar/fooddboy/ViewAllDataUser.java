package com.prakhar.fooddboy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.fooddboy.Marketing.ReadM;

public class ViewAllDataUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String selectedStaffType;
    Button select;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_data_spinner);

        Spinner staffTypeSpinner = findViewById(R.id.staff_type_spinner);
        select=findViewById(R.id.select);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.staff_types_array2, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staffTypeSpinner.setAdapter(adapter);

        // Set the selection listener
        staffTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Retrieve the selected staff type
        selectedStaffType = parent.getItemAtPosition(position).toString();


    select.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedStaffType.equals("Back Office Data")) {
                // Start the RegisterActivity for back office staff
                Intent intent = new Intent(ViewAllDataUser.this, ReadUser.class);
                startActivity(intent);
            } else if (selectedStaffType.equals("CHOOSE OPTION")) {
                Toast.makeText(ViewAllDataUser.this, "Please select any one", Toast.LENGTH_SHORT).show();

            } else if (selectedStaffType.equals(("Marketing Data"))) {
                Intent intent = new Intent(ViewAllDataUser.this, ReadM.class);
                startActivity(intent);

            }
        }
    });


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle the case where nothing is selected
        selectedStaffType = null;
    }

    // Rest of your code for registering the staff
}

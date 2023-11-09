package com.example.newlocationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddLocationActivity extends AppCompatActivity {
    private EditText newAddressEditText;
    private EditText newLatitudeEditText;
    private EditText newLongitudeEditText;
    private Button addLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        // Initialize UI elements
        newAddressEditText = findViewById(R.id.newAddressEditText);
        newLatitudeEditText = findViewById(R.id.newLatitudeEditText);
        newLongitudeEditText = findViewById(R.id.newLongitudeEditText);
        addLocationButton = findViewById(R.id.addLocationButton);

        // Set a click listener for the Add Location button
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input from the EditText fields
                String address = newAddressEditText.getText().toString();
                double latitude = Double.parseDouble(newLatitudeEditText.getText().toString());
                double longitude = Double.parseDouble(newLongitudeEditText.getText().toString());

                // Create a new LocationInfo object with the input data
                LocationInfo newLocation = new LocationInfo(address, latitude, longitude);

                // Save the new location to the database (implement this)
                saveLocationToDatabase(newLocation);

                // Optionally, you can return to the previous activity or perform other actions
                finish();
            }
        });
    }

    // Implement a method to save the new location to the database
    private void saveLocationToDatabase(LocationInfo location) {
        // You should implement the database operations here (inserting a new location)
        // You can use the LocationDatabaseHelper or a data source class to perform database operations.
    }
}

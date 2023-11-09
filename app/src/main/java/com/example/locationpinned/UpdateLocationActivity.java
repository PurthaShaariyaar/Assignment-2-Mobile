package com.example.newlocationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditLocationActivity extends AppCompatActivity {

    private EditText newAddressEditText;
    private EditText newLatitudeEditText;
    private EditText newLongitudeEditText;
    private Button saveChangesButton;

    private long locationIdToUpdate; // The ID of the location to be updated

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        // Initialize UI elements
        newAddressEditText = findViewById(R.id.editAddressEditText);
        newLatitudeEditText = findViewById(R.id.editLatitudeEditText);
        newLongitudeEditText = findViewById(R.id.editLongitudeEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        // Retrieve the location ID to update from the intent (you should pass this ID from the previous activity)
        locationIdToUpdate = getIntent().getLongExtra("LOCATION_ID_TO_UPDATE", -1);

        // Set a click listener for the Save Changes button
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input from the EditText fields
                String newAddress = newAddressEditText.getText().toString();
                double newLatitude = Double.parseDouble(newLatitudeEditText.getText().toString());
                double newLongitude = Double.parseDouble(newLongitudeEditText.getText().toString());

                // Create a new Location object with the updated data
                Location updatedLocation = new Location(newAddress, newLatitude, newLongitude);
                updatedLocation.setId(locationIdToUpdate); // Set the ID of the location to be updated

                // Update the location in the database (implement this)
                updateLocationInDatabase(updatedLocation);

                // Optionally, you can return to the previous activity or perform other actions
                finish();
            }
        });
    }

    // Implement a method to update the location in the database
    private void updateLocationInDatabase(Location location) {
        // You should implement the database operations here (updating the location)
        // You can use the LocationDBHelper or a data source class to perform database operations.
    }
}

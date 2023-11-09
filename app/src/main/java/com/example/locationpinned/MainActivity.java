package com.example.newlocationapp;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationListActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSIONS = 1;

    private LocationDatabaseHelper locationDatabaseHelper;
    private ListView locationListView;
    private LocationListAdapter locationListAdapter;
    private Button searchButton;
    private EditText searchAddressEditText;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        locationDatabaseHelper = new LocationDatabaseHelper(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        locationListView = findViewById(R.id.locationListView);
        searchButton = findViewById(R.id.searchButton);
        searchAddressEditText = findViewById(R.id.searchAddressEditText);

        locationListAdapter = new LocationListAdapter(this, new ArrayList<>());
        locationListView.setAdapter(locationListAdapter);

        // Request location permissions
        requestLocationPermissions();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryAddress = searchAddressEditText.getText().toString();
                queryLocations(queryAddress);
            }
        });

        // Read input file and populate the database (implement this)
        readInputFileAndPopulateDatabase();
    }

    // Request location permissions
    private void requestLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), REQUEST_LOCATION_PERMISSIONS);
        }
    }

    // Query locations by address
    private void queryLocations(String queryAddress) {
        List<Location> matchingLocations = new ArrayList<>();

        // Implement the database query based on the provided address
        // You should use the LocationDatabaseHelper or a data source class to perform the database query.
        SQLiteDatabase database = locationDatabaseHelper.getReadableDatabase();

        String selection = LocationContract.LocationEntry.COLUMN_ADDRESS + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + queryAddress + "%"};

        Cursor cursor = database.query(
                LocationContract.LocationEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_ID));
                String address = cursor.getString(cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_ADDRESS));
                double latitude = cursor.getDouble(cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(LocationContract.LocationEntry.COLUMN_LONGITUDE));

                Location location = new Location(address, latitude, longitude);
                location.setId(id);

                matchingLocations.add(location);
            }
        } finally {
            cursor.close();
        }

        // Update the locationListAdapter with the results
        locationListAdapter.clear();
        locationListAdapter.addAll(matchingLocations);
        locationListAdapter.notifyDataSetChanged();
    }

    // Read input file and populate the database (you should implement this)
    private void readInputFileAndPopulateDatabase() {
        try {
            // Open and read the input file (assuming it's in the "assets" folder)
            InputStream inputStream = getAssets().open("input_locations.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into latitude and longitude
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    double latitude = Double.parseDouble(parts[0].trim());
                    double longitude = Double.parseDouble(parts[1].trim());

                    // Use Geocoding to find the address for the given latitude and longitude
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (!addresses.isEmpty()) {
                        String address = addresses.get(0).getAddressLine(0);

                        // Create a new Location object and save it to the database
                        Location newLocation = new Location(address, latitude, longitude);
                        saveLocationToDatabase(newLocation);
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implement a method to save the new location to the database
    private void saveLocationToDatabase(Location location) {
        SQLiteDatabase database = locationDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocationContract.LocationEntry.COLUMN_ADDRESS, location.getAddress());
        values.put(LocationContract.LocationEntry.COLUMN_LATITUDE, location.getLatitude());
        values.put(LocationContract.LocationEntry.COLUMN_LONGITUDE, location.getLongitude());

        long newRowId = database.insert(LocationContract.LocationEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Location saved to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving location to database", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // You can use location services here if needed
            } else {
                Toast.makeText(this, "Location permissions are required for this app.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

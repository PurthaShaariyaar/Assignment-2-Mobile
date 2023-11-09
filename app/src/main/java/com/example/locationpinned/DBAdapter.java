package com.example.newlocationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<LocationInfo> {
    private Context context;

    public LocationAdapter(Context context, List<LocationInfo> locations) {
        super(context, 0, locations);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_item, parent, false);
        }

        // Get the data item for this position
        LocationInfo location = getItem(position);

        // Lookup view for data population
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);
        TextView latitudeTextView = convertView.findViewById(R.id.latitudeTextView);
        TextView longitudeTextView = convertView.findViewById(R.id.longitudeTextView);

        // Populate the data into the template view using the data object
        addressTextView.setText(location.getLocationAddress());
        latitudeTextView.setText("Latitude: " + location.getLocationLatitude());
        longitudeTextView.setText("Longitude: " + location.getLocationLongitude());

        // Return the completed view to render on screen
        return convertView;
    }
}

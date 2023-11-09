package com.example.locationpinned;

import android.provider.BaseColumns;

public class DBContract {

    private DBContract() {}

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_ID = BaseColumns._ID; // Auto-increment primary key
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }

}

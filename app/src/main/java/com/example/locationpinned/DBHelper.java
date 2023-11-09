package com.example.locationpinned;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.locationpinned.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "locationHistory.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {

        // SQL statements for creating and deleting the table
        final String SQL_LITE_CREATE_LOCATIONHISTORY_TABLE = "CREATE TABLE " +
                LocationEntry.TABLE_NAME + " (" +
                LocationEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LocationEntry.COLUMN_ADDRESS + " TEXT," +
                LocationEntry.COLUMN_LATITUDE + " REAL," +
                LocationEntry.COLUMN_LONGITUDE + " REAL);";

        database.execSQL(SQL_LITE_CREATE_LOCATIONHISTORY_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        onCreate(database);
    }
}

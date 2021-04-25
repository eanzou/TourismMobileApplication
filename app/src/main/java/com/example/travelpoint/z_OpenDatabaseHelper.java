package com.example.travelpoint;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class z_OpenDatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "tourist_spot.db";
    private static final int DATABASE_VERSION=1;

    public z_OpenDatabaseHelper(Context context)
    {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}

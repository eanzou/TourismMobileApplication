package com.example.travelpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class  z_DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "myUser.db";

    //Using this constructor to create database
    public z_DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    // use execSQL to create table under OnCreate method
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL(" create Table USER_PROFILE(email TEXT PRIMARY KEY, username STRING, password STRING)");
    }

    //drop table if it already exist
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("DROP TABLE IF EXISTS USER_PROFILE");
    }

    //data insertion
    public boolean insertData(String email, String username, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = myDB.insert("USER_PROFILE", null, contentValues);

        if (result == 1)
            return false;
        else
            return true;
    }

    public Boolean checkemail(String email)
    {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USER_PROFILE WHERE email = ?", new String[] {email});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_PROFILE WHERE username = ?", new String[] {username});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_PROFILE WHERE username = ? AND password = ?", new String[] {username, password});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean authenticateUser (String username, String password) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USER_PROFILE WHERE username = ? " +
                "AND password = ?", new String[]{username, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean updateUsername (String username, String newUsername) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", newUsername);

        long result = myDB.update("USER_PROFILE", contentValues, "username = ?", new String[] {username});

        if(result == 1)
            return true;
        else
            return false;
    }

    public Boolean updatePassword(String username, String newPassword) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);

        long result = myDB.update("USER_PROFILE", contentValues, "username = ?", new String[] {username});

        if(result == 1)
            return true;
        else
            return false;
    }

    public Boolean deleteAcc (String user)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();

        long result = myDB.delete("USER_PROFILE", "username = ?", new String[] {user});

        if(result == 1)
            return true;
        else
            return false;
    }

}

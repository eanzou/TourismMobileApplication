package com.example.travelpoint;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.nio.IntBuffer;
import java.sql.Blob;
import java.util.ArrayList;

public class z_AccessDatabaseHelper {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static z_AccessDatabaseHelper instance;
    Cursor c = null;

    private z_AccessDatabaseHelper (Context context)
    {
        this.openHelper = new z_OpenDatabaseHelper(context);
    }

    public static z_AccessDatabaseHelper getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new z_AccessDatabaseHelper(context);
        }
        return instance;
    }

    public void open()
    {
        this.db=openHelper.getWritableDatabase();
    }

    public void close()
    {
        if (db!=null)
        {
            this.db.close();
        }
    }

    public String get_name(int id)
    {
        c=db.rawQuery("SELECT title FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }

    public String get_shortdes(int id)
    {
        c=db.rawQuery("SELECT short_des FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }

    public String get_longdes(int id)
    {
        c=db.rawQuery("SELECT long_des FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String des = c.getString(0);
            buffer.append(""+des);
        }
        return buffer.toString();
    }

    public String get_state(int id)
    {
        c=db.rawQuery("SELECT state FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String des = c.getString(0);
            buffer.append(""+des);

        }
        return buffer.toString();
    }

    public ByteArrayInputStream get_image(int id)
    {

        ByteArrayInputStream inputStream = null;
        c=db.rawQuery("SELECT img FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        while (c.moveToNext())
        {
            byte[] blob = c.getBlob(0);
            inputStream = new ByteArrayInputStream(blob);
        }
        return inputStream;
    }


    public ArrayList<String> get_id(String state) {
        ArrayList<String> obtain_id = new ArrayList<>();

        c = db.rawQuery("select id from tourist_spot WHERE state = '"+state+"'", new String[]{});

        while (c.moveToNext())
        {
            String des = c.getString(0);
            obtain_id.add(des);
        }

        return obtain_id;
    }

    public String get_id_by_name(String name)
    {
        c=db.rawQuery("SELECT id FROM tourist_spot WHERE title = '"+name+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String des = c.getString(0);
            buffer.append(""+des);

        }
        return buffer.toString();
    }

    public String get_link(int id)
    {
        c=db.rawQuery("SELECT url FROM tourist_spot WHERE id = '"+id+"'", new String[]{});

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext())
        {
            String url = c.getString(0);
            buffer.append(""+url);

        }
        return buffer.toString();
    }

}
